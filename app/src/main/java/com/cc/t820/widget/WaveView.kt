package com.cc.t820.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.cc.t820.R
import com.cc.t820.exts.TAG
import com.cc.t820.exts.dp2px2
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin


/**
 * 单个水波纹
 * 实现 Wave 动画的基础及核心就是三角函数
 *
 * */
class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPath by lazy {
        Path()
    }

    private val mPaint by lazy { Paint() }
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var waveHeight = 0  //波幅（振幅）

    private var waveWidth = 0 //波长
    private var offsetX = 0f //水波的水平偏移量
    private var offsetY = 300 //水波的竖直偏移量
    private var velocity = 100f //水波移动速度（像素/秒）
    private var mScaleX = 0.8f //水平拉伸比例  波长拉伸
    private val mScaleY = 0f  //竖直拉伸比例
    private var mCanvasWidth = 0f  //拉伸过后的 宽度

    private var isRunning = true

    private var mLastTime: Long = 0

    private var mDuration = 1000f


    init {
        mPaint.apply {
            this.style = Paint.Style.FILL_AND_STROKE
            //  color = Color.CYAN
            //  strokeWidth = 2.dp2pxF
            isAntiAlias = true
        }

        val arr: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.wave)
        velocity = arr.getFloat(R.styleable.wave_mVelocity, 100f)
        mScaleX = arr.getFloat(R.styleable.wave_mScaleX, 0.8f)
        offsetY = arr.getInteger(R.styleable.wave_mOffsetY, 300)
        arr.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w

        mHeight = h

        waveWidth = mWidth

        mCanvasWidth = 2 * mWidth / mScaleX * 2  //前面是波长 后面2 是扩大两倍画布

        waveHeight = mHeight / 2 - offsetY //上下幅度  300是 振幅缩小 300


        makePath()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e(TAG, "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.e(TAG, "onLayout: left$left top$top right$right bottom$bottom")
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val thisTime = System.currentTimeMillis()

        canvas?.save()


        if (isRunning && mLastTime > 0) {
            val s = (thisTime - mLastTime) / mDuration * velocity

            offsetX += s

//            if ((offsetX - mCanvasWidth / 2) >= 0f) {
//                offsetX = 0f
//            }

            offsetX %= mCanvasWidth / 2


            //          Log.e(TAG, "onDraw: " + offsetX)
            canvas?.translate(-offsetX, 0f)

        }
        canvas?.drawPath(mPath, mPaint)
        canvas?.restore()


        mLastTime = thisTime
        if (isRunning) invalidate()

    }


    private fun makePath() {

        mPath.reset()

        mPath.moveTo(0f, (mHeight).toFloat() / 2)

        var xDp: Int = 1.dp2px2    //x =3

        while (xDp < mCanvasWidth) {
            //这个幅度 要经过计算才行  waveWidth 周期
            val dy = sin(2 * PI * xDp / waveWidth * mScaleX) * (waveHeight)

            mPath.lineTo(xDp.toFloat(), (dy + mHeight / 2).toFloat())

            xDp = xDp.plus(1.dp2px2)

        }
        mPath.lineTo(mCanvasWidth.toFloat(), mHeight.toFloat())
        mPath.lineTo(0f, mHeight.toFloat())
        mPath.close()


        //这样可以渐变
//        //反向
//        val mPath1 = Path()
//        mPath1.addRoundRect(
//            RectF(0f, 0f, mWidth.toFloat(), mHeight.toFloat()),
//            30f,
//            30f,
//            Path.Direction.CW
//        )
//
//        //改变了画布的可绘制区域。
////        canvas?.clipPath(mPath1)
////        canvas?.save()
//
//        mPath1.op(mPath, Path.Op.XOR)//包含Path1与Path2但不包括两者相交的部分
//
//        mPaint.shader = LinearGradient(
//            0f, 0f, mWidth.toFloat(), mHeight.toFloat(),
//            Color.WHITE,
//            Color.BLUE,
//            Shader.TileMode.CLAMP
//        )

    }


    fun setWaveColor(color: Int) {
        mPaint.color = color
    }


}