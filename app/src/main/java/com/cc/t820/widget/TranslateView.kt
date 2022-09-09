package com.cc.t820.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.cc.t820.exts.TAG

class TranslateView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {


    var mWidth = 0
    var mHeight = 0

    val mPath by lazy {
        Path()
    }

    val mPath2 by lazy {
        Path()
    }
    val mPaint by lazy {
        Paint()
    }
    val  mMatrix by lazy {
        Matrix()
    }

    init {
        mPaint.apply {
            color = Color.CYAN
            isAntiAlias = true
        }
    }


    private var isRunning = true
    private var mLastTime: Long = 0

    private var mStatrOffsetX = 200
    private var mDuration = 1000f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.e(TAG, "onSizeChanged: $w")
        mWidth = w
        mHeight = h
        mPaint.shader = LinearGradient(0f, 0f,mWidth.toFloat(), mHeight.toFloat(), Color.WHITE, Color.BLUE, Shader.TileMode.CLAMP)
    }

    /**
     * TranslateAnimation）本质就是在onDraw中通过Canvas的translate方法，不断对其移动
     * 在一段连续时间内对Canvas的translate值做修改，就能实现一个连贯的动画效果。
     * */
    var p = 0f
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val thisTime = System.currentTimeMillis()
        mMatrix.reset()
        canvas?.save()
        if (mLastTime > 0) {

            val s = (thisTime - mLastTime) / mDuration * mStatrOffsetX

            p += s
            if(p-200> mWidth/2){
                p=0f
            }
            Log.e(TAG, "onDraw: $p")

            mMatrix.setTranslate(-p, 0f)

            canvas?.translate(p, 0f)

        } else {
            //200 正方形
            mPath.addRoundRect(
                RectF(
                    -200f,
                    (mHeight / 2 - 100).toFloat(),
                    0f,
                    (mHeight / 2 + 100).toFloat()
                ), 0f, 0f, Path.Direction.CW
            )
        }

        mPaint.shader.setLocalMatrix(mMatrix)
        canvas?.drawPath(mPath, mPaint)
        canvas?.restore()

        mLastTime = thisTime
      //  invalidate()

    }

    fun rectFFF(){
        mPath.addRoundRect(
            RectF(
                -200f,
                (mHeight / 2 - 100).toFloat(),
                0f,
                (mHeight / 2 + 100).toFloat()
            ), 0f, 0f, Path.Direction.CW
        )

        mPath2.addRoundRect(
            RectF(
                -200f,
                (mHeight / 2 - 100).toFloat(),
                0f,
                (mHeight / 2 + 100).toFloat()
            ), 0f, 0f, Path.Direction.CW
        )
    }


    override fun computeScroll() {
        super.computeScroll()
        if (isRunning) {
            invalidate()
        }
    }
}