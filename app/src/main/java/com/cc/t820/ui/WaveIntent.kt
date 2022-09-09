package com.cc.t820.ui

import com.cc.t820.data.InfoDataBean
import com.cc.t820.data.PoetryDataBean

sealed class WaveIntent {


    object Init : WaveIntent()

    object PoetryToken : WaveIntent()

    data class PoetryInfo(var token: String) : WaveIntent()


}

sealed class WaveViewState {

    object InitView : WaveViewState()

    data class PoetryToken(var dataBean: InfoDataBean) : WaveViewState()

    data class PoetryInfo(var poetryBean: PoetryDataBean) : WaveViewState()


}