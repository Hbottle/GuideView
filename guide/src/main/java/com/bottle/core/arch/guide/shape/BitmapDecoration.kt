package com.bottle.core.arch.guide.shape

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

/**
 * @Description: 绘制一个bitmap(指引中箭头等指示图标)
 */
class BitmapDecoration(
    private val bitmap: Bitmap,
    private val left: Float,
    private val top: Float
) : Shape {

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmap, left, top, paint)
    }

}