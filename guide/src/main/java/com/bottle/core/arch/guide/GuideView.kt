package com.bottle.core.arch.guide

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.bottle.core.R

class GuideView : View, View.OnTouchListener, GestureDetector.OnGestureListener {

    interface OnClickListener {
        fun onClick()
    }

    private val location = IntArray(2)
    private var initLocation = false
    private val mPaint = Paint()
    private var mGuideInfo: GuideInfo? = null
    private var background: Int = 0
    private lateinit var mGestureDetector: GestureDetector
    var mOnClickListener: OnClickListener? = null

    private fun initView(attrs: AttributeSet) {
        background = ContextCompat.getColor(context, R.color.translucent)
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.GuideView)
        background = typedArray.getColor(R.styleable.GuideView_background_translucent, background)
        typedArray.recycle()
        mPaint.isAntiAlias = true
        setOnTouchListener(this)
        mGestureDetector = GestureDetector(context, this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(!initLocation) {
            getLocationOnScreen(location)
            initLocation = true
        }
        drawBackGround(canvas)
        drawShapes(canvas)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != this || event == null || mGuideInfo == null) {
            return false
        }
        return mGestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(event: MotionEvent?): Boolean {
        if (event == null || mGuideInfo == null) {
            return false
        }
        if (mGuideInfo!!.targetBound.contains(location[0] + event.x,
                location[1] + event.y)) {
            mOnClickListener?.onClick()
            return true
        }
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    fun showGuide(guideStep: GuideInfo) {
        this.mGuideInfo = guideStep
        postInvalidate()
    }

    private fun drawBackGround(canvas: Canvas) {
        mPaint.xfermode = null
        mPaint.color = background
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), mPaint)
    }

    private fun drawShapes(canvas: Canvas) {
        if (mGuideInfo == null) {
            return
        }
        // 先转换一下坐标，这样绘制得到的和底层目标View区域重叠
        canvas.translate(-location[0].toFloat(), -location[1].toFloat())
        // 1.先绘制要抠图的部分，也就是高亮的区域
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        mGuideInfo?.mTargetHighlightShape?.draw(canvas, mPaint)
        // 2.再绘制其它的箭头，文本等指示性的Shape
        mPaint.xfermode = null
        mGuideInfo?.mShapes?.forEach {
            it.draw(canvas, mPaint)
        }
    }

}