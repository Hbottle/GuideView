package com.bottle.core.arch.guide.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * @Description: 椭圆或者圆，主要看宽高比，指引中的高亮View
 */
class Oval(private val rect: RectF) : Shape {

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawOval(rect, paint)
    }

}