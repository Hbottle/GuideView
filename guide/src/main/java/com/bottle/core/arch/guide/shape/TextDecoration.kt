package com.bottle.core.arch.guide.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface

/**
 * @Description: 绘制一段文本(指引中的文本提示)
 */
open class TextDecoration(
    protected val text: String,         // 要绘制的文本
    protected val textSize: Float,      // 字体大小
    protected val textColor: Int,       // 字体颜色
    protected val startX: Float,        // x轴起点(left)
    protected val startY: Float,        // y轴七点(top)
    protected val bold: Boolean = false // 粗体
) : Shape {

    override fun draw(canvas: Canvas, paint: Paint) {
        if (onDraw(canvas, paint)) {
            return
        }
        paint.color = textColor
        paint.textSize = textSize
        paint.typeface = if (bold) {
            Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        } else {
            Typeface.DEFAULT_BOLD
        }
        canvas.drawText(text, startX, startY, paint)
        // void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
    }

    /**
     * TextDecoration没有实现控制文本换行或者方向，如果确实需要，可以重写此方法实现
     */
    protected open fun onDraw(canvas: Canvas, paint: Paint): Boolean {
        return false
    }
}