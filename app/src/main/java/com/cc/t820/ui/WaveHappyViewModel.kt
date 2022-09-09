package com.cc.t820.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc.t820.data.Colors
import com.cc.t820.exts.TAG
import com.cc.t820.net.RetrofitBuild
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 * 每一个意图会产生若干个结果，每个结果对应一个界面状态
 * 保证数据流的单向流动 LiveData向外暴露时需要转化成immutable
 * */
class WaveHappyViewModel : ViewModel() {
    companion object {
        private const val DEFAULT_QUEUE_LENGTH = 1
    }

    //单例一
    private val mServices by lazy {
        RetrofitBuild.create("https://v2.jinrishici.com/")
    }

    /**
     * StateFlow 需要将初始状态传递给构造函数，而 LiveData 不需要。
    当 View 进入 STOPPED 状态时，LiveData.observe() 会自动取消注册使用方，
    而从 StateFlow 或任何其他数据流收集数据的操作并不会自动停止。如需实现相同的行为，
    您需要从 Lifecycle.repeatOnLifecycle 块收集数据流。

     * StateFlow 是热数据流，只要该数据流被收集，或对它的任何其他引用在垃圾回收根中存在，
     * 该数据流就会一直存于内存中。您可以使用 shareIn 运算符将冷数据流变为热数据流。
     * */


    //由于 replay 会重走设定次数中队列的元素，故重走 STARTED 时会重走所有
    // ，包括已消费和未消费过，视觉上给人感觉即，控件上旧数据 “一闪而过”
    private val uiState: MutableSharedFlow<WaveViewState> by lazy {
        MutableSharedFlow(
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            extraBufferCapacity = DEFAULT_QUEUE_LENGTH,
            replay = DEFAULT_QUEUE_LENGTH
        )
    }

    val uiFlow = uiState.asSharedFlow()

    var mDelay = 5

    var colors: MutableList<Colors> = mutableListOf()

    var mToken: String? = ""

    var mStr = StringBuilder()

    var job: Job? = null

    fun dispatch(viewIntent: WaveIntent) {
        when (viewIntent) {
            is WaveIntent.Init -> {
                uiState.tryEmit(WaveViewState.InitView)
            }
            is WaveIntent.PoetryToken -> {
                poetryToken()
            }

            is WaveIntent.PoetryInfo -> {
                if (job != null) {
                    job!!.cancel()
                    job = null
                }
                delayPoetryInfo(viewIntent.token)
            }

        }
    }


    private fun poetryToken() {
        viewModelScope.launch {
            try {
                mServices.info().apply {
                    if (this.status == "success") {
                        mToken = this.data.token
                        uiState.emit(WaveViewState.PoetryToken(this.data))
                    }

                }
            } catch (e: Exception) {
            }
        }
    }

    private fun poetryInfo(string: String) {
        viewModelScope.launch {
            try {
                mServices.sentence(string).apply {
                    if (this.status == "success") {
                        uiState.emit(WaveViewState.PoetryInfo(this.data))
                    }
                }
            } catch (e: Exception) {
            }
        }
    }


    private fun delayPoetryInfo(token: String) {

        job = viewModelScope.launch {
            flow {
                while (true) {
                    emit(1)
                    delay(TimeUnit.MINUTES.toMillis(mDelay.toLong()))
                }
            }.catch { e ->
                Log.e(TAG, "delayPoetryInfo: 3")
            }.collect {
                Log.e(TAG, "delayPoetryInfo: ")
                poetryInfo(token)
            }

        }


    }
}