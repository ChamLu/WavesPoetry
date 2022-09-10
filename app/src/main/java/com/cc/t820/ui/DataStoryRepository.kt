package com.cc.t820.ui

import android.util.Log
import androidx.datastore.preferences.core.edit
import com.cc.t820.MyApp
import com.cc.t820.data.Colors
import com.cc.t820.exts.isDarkMode
import com.cc.t820.utils.DataStoreManager
import com.cc.t820.utils.DataStoreManager.dataStoreT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.flow.first

/**
 * DataStory 读写操作 应该在这里
 * */
class DataStoryRepository {

    companion object{
        const val JXZT=0
        const val XWWK=1
    }

    /**
     * 从dataStory 读取字段
     * */
    suspend fun dataStoryGetTextType(): Int {
        return MyApp.getInstance()!!.dataStoreT.data.first()[DataStoreManager.TextType] ?: 0
    }

    suspend fun dataStorySetTextType(type: Int = JXZT) {
        MyApp.getInstance()!!.dataStoreT.edit { setting ->
            if (setting[DataStoreManager.TextType] != type)
                setting[DataStoreManager.TextType] = type
        }
    }

    /**
     * 从 dataStory 读 Token
     * */
    suspend fun dataStoryGetPoetryToken(): String? =
        MyApp.getInstance()!!.dataStoreT.data.first()[DataStoreManager.PoetryToken]

    suspend fun setDataStoreToken(str: String) {
        if (str.isNotBlank()) {
            MyApp.getInstance()!!.dataStoreT.edit { setting ->
                //写数据
                setting[DataStoreManager.PoetryToken] = str
            }
        }
    }

    /**
     * 获取颜色列表
     * */
    fun colorList(): MutableList<Colors> {
        MyApp.getInstance()?.let { app ->
            app.assets.open("wavesColors.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val colorsType = object : TypeToken<List<Colors>>() {}.type
                    return Gson().fromJson<MutableList<Colors>?>(
                        jsonReader,
                        colorsType
                    ).filter {
                        it.darkSuitable == app.isDarkMode
                    } as MutableList<Colors>
                }
            }
        }
        return mutableListOf()
    }

}