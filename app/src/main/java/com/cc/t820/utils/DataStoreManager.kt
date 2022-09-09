package com.cc.t820.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


object DataStoreManager {

    /**
     * val/var <属性名>:<类型> by <表达式>
     * */

    //扩展属性  属性委托
    val Context.dataStoreT: DataStore<Preferences> by preferencesDataStore(name = "wave_datastore")



    val PoetryToken = stringPreferencesKey("poetrytoken")

    val TextType = intPreferencesKey("textType")







}


