package com.cc.t820.data

data class Colors(
    var CMYK: MutableList<Int>,
    var RGB: MutableList<Int>,
    var hex: String,
    var name: String,
    var pinyin: String,
    var lightSuitable: Boolean,
    var darkSuitable: Boolean
)
