package com.bottle.core.arch.guide.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * @Description: 一个矩形(带圆角)，指引中的高亮View
 */
class Rectangle(
    private val rect: RectF,
    private val xRadius: Float = 0F,
    private val yRadius: Float = 0F
) : Shape {

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawRoundRect(rect, xRadius, yRadius, paint)
    }

}