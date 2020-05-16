package com.bottle.core

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bottle.core.arch.guide.GuideInfo
import com.bottle.core.arch.guide.GuideListener
import com.bottle.core.arch.guide.GuideManager
import com.bottle.core.arch.guide.shape.BitmapDecoration
import com.bottle.core.arch.guide.shape.TextDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imgLogo.setOnClickListener(this)
        imgLogo.postDelayed(Runnable {
            showGuide(this)
        }, 200)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgLogo) {
            showGuide(this)
        }
    }

    private fun showGuide(context: Activity) {
        GuideManager(context).apply {
            addGuideStep(GuideInfo(imgLogo, isOval = true).apply {
                val textShape = TextDecoration(
                        "这是圆形高亮区域，点击高亮进入下一步",
                        sp2px(context, 12F).toFloat(),
                        ContextCompat.getColor(context, R.color.white),
                        targetBound.right + dip2px(context, 8F), targetBound.centerY()
                )
                addShape(textShape)
            })
            addGuideStep(GuideInfo(imgLogo2, radius = 16F).apply {
                val textShape = TextDecoration(
                        "这是圆角矩形高亮区域，点击高亮继续进入下一步",
                        sp2px(context, 12F).toFloat(),
                        ContextCompat.getColor(context, R.color.white),
                        targetBound.left, targetBound.bottom + dip2px(context, 24F)
                )
                addShape(textShape)
            })
            addGuideStep(GuideInfo(tvName, padding = 20).apply {
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_add_location_white_48dp)
                val bitmapShape =
                        BitmapDecoration(
                                bitmap,
                                targetBound.left,
                                targetBound.top - dip2px(context, 45F)
                        )
                addShape(bitmapShape)
                val textShape = TextDecoration(
                        "点击高亮结束指引",
                        sp2px(context, 14F).toFloat(),
                        ContextCompat.getColor(context, R.color.white),
                        targetBound.centerX(),
                        targetBound.bottom + dip2px(context, 32F)
                )
                addShape(textShape)
            })
            mGuideListener = object: GuideListener {
                override fun onNextStep(step: Int) {
                    Toast.makeText(context, "当前步骤：${step + 1}", Toast.LENGTH_SHORT).show()
                }

                override fun onCompleted() {
                    tvShowGuide.visibility = View.VISIBLE
                }
            }
            // 如果要显示“上一步”，“下一步”，可以设置GuideManager中的mGuideDialog，
        }.show()
    }

}
