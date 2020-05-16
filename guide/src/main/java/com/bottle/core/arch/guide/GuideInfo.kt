package com.bottle.core.arch.guide

import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.View
import com.bottle.core.arch.guide.shape.Oval
import com.bottle.core.arch.guide.shape.Rectangle
import com.bottle.core.arch.guide.shape.Shape
import java.lang.reflect.Field

class GuideInfo(
    private val targetView: View,            // 高亮显示的，要指引的View
    val padding: Int = 0,                    // 高亮区域的padding(如果要显示大一些时可设置padding)
    val isOval: Boolean = false,             // 高亮区域是否时圆形
    val radius: Float = 0F,                  // 如果时矩形，那么可以设置圆角
    private val paddingLeft: Int = 0,        // 四个方向的padding
    private val paddingTop: Int = 0,
    private val paddingRight: Int = 0,
    private val paddingBottom: Int = 0,
    autoShape: Boolean = false               // 是否使用自定义的高亮区域，true: 自动根据View的Background获取Shape
) {

    val mShapes = mutableListOf<Shape>()
    var mTargetHighlightShape: Shape? = null // 这就是高亮显示的View
    val targetBound: RectF                   // 高亮View的矩形区域，可根据这个矩形设置其它Shape的位置

    init {
        targetBound = targetViewRectF()
        mTargetHighlightShape = if (autoShape) {
            autoBuildTargetViewShape()
        } else {
            if (isOval) {
                Oval(targetViewRectF())
            } else {
                Rectangle(
                    targetViewRectF(),
                    radius,
                    radius
                )
            }
        }
    }

    /**
     * 可以调用此方法，添加额外的辅助箭头，文本等
     */
    fun addShape(shape: Shape) {
        mShapes.add(shape)
    }

    /**
     * 获取目标高亮显示的View的矩形，通过这个矩形范围，可以摆放响应的其它图片，文本等
     */
    private fun targetViewRectF(): RectF {
        val location = IntArray(2)
        targetView.getLocationOnScreen(location)
        val rectF = RectF(
            location[0].toFloat(),
            location[1].toFloat(),
            location[0].toFloat() + targetView.width,
            location[1].toFloat() + targetView.height
        )
        rectF.apply {
            if (padding != 0) {
                left -= padding
                top -= padding
                right += padding
                bottom += padding
            } else {
                left -= paddingLeft
                top -= paddingTop
                right += paddingRight
                bottom += paddingBottom
            }
        }
        return rectF
    }

    /**
     * FIXME 此方法暂时有bug
     */
    private fun autoBuildTargetViewShape(): Shape {
        val location = IntArray(2)
        targetView.getLocationOnScreen(location)
        val rectF = targetViewRectF()
        var drawable: Drawable? = targetView.background
        if (drawable is StateListDrawable) {
            if (drawable.getCurrent() is GradientDrawable) {
                drawable = drawable.getCurrent()
            }
        }
        if (drawable == null) {
            return Rectangle(rectF)
        }
        val fieldGradientState: Field
        var mGradientState: Any? = null
        var shape = GradientDrawable.RECTANGLE
        try {
            fieldGradientState =
                Class.forName("android.graphics.drawable.GradientDrawable")
                    .getDeclaredField("mGradientState")
            fieldGradientState.isAccessible = true
            mGradientState = fieldGradientState[drawable]
            val fieldShape = mGradientState.javaClass.getDeclaredField("mShape")
            fieldShape.isAccessible = true
            shape = fieldShape[mGradientState] as Int
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var mRadius = 0f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mRadius = (drawable as GradientDrawable).cornerRadius
        } else {
            try {
                val fieldRadius = mGradientState!!.javaClass.getDeclaredField("mRadius")
                fieldRadius.isAccessible = true
                mRadius = fieldRadius[mGradientState] as Float
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return if (shape == GradientDrawable.OVAL) {
            Oval(rectF)
        } else {
            val rad = mRadius.coerceAtMost(rectF.width().coerceAtMost(rectF.height()) * 0.5f)
            Rectangle(rectF, rad, rad)
        }
    }
}