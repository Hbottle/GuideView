package com.bottle.core.arch.guide.shape

import android.graphics.Canvas
import android.graphics.Paint

/**
 * @Description: 自定义Shape，最终绘制在GuideView，代表指引步骤中要显示的内容，可能包括：高亮View(区域)，
 * 指示性的图片、文本，如箭头，描述文本等。
 */
interface Shape {
    /**
     * @param canvas 来自GuideView
     * @param paint 来自GuideView，需要先设置画笔，然后再使用，如设置颜色，字体大小等
     */
    fun draw(canvas: Canvas, paint: Paint)
}
