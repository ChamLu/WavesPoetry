package com.cc.t820.exts

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

/**
 *  获取屏幕宽度
 */
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * 获取屏幕高度
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

// 屏幕的三分之二
val Context.screenHeight32
    get() = resources.displayMetrics.heightPixels * 2 / 3

val Context.TAG_CURRENT_THREAD
    get() = "_${this.javaClass.simpleName} thread:[${Thread.currentThread().name}]"

val Context.TAG
    get() = "_${this.javaClass.simpleName}"

val View.TAG_CURRENT_THREAD
    get() = "_${this.javaClass.simpleName} thread:[${Thread.currentThread().name}]"

val View.TAG
    get() = "_${this.javaClass.simpleName}"

val ViewModel.TAG
    get() = "_${this.javaClass.simpleName}"

val Int.dp2px1: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

infix fun TextInputLayout.hints(num: Int) {
    this.hint = "当前切换时间${num} min"
}

inline val Int.dp2px2: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

inline val Int.dp2pxF: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

inline val Int.px2dp: Float
    get() {
        return this / Resources.getSystem().displayMetrics.density
    }

val String.color: Int get() = Color.parseColor(this)

/**
 * 判断是否为深色模式
 * */
inline val Context.isDarkMode: Boolean
    get() = (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_YES) != 0


//TODO show toast
@SuppressLint("ShowToast")
infix fun <T> T.toast(context: Context) {
    Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()
}

infix fun <T> Context.toast(any: T) {
    Toast.makeText(this, any.toString(), Toast.LENGTH_SHORT).show()
}

// TODO show Snack
infix fun <T : CharSequence> T.showSnack(view: View) {
    Snackbar.make(view, this, Snackbar.LENGTH_SHORT).show()
}

infix fun <T> View.showSnack(any: T?) {
    any?.toString()?.also {
        if (it.isNotEmpty()) {
            Snackbar.make(this, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}


