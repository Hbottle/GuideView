# GuideView
一行代码接入Android app指引
## 如何接入?
demo中又参考例子，这里简单概括一下
### 1. 创建GuideManager实例，并设置指引回调
```kotlin
val guideManager = GuideManager(activity)
guideManager.mGuideListener = object: GuideListener {
    override fun onNextStep(step: Int) {
        Toast.makeText(context, "当前步骤：${step + 1}", Toast.LENGTH_SHORT).show()
    }

    override fun onCompleted() {
        tvShowGuide.visibility = View.VISIBLE
    }
}
```
### 2. 创建指引步骤实例 GuideInfo，并添加到GuideManager
```kotlin
// imgLogo is the view you want to heighlight
val guideStep1 = GuideInfo(imgLogo, isOval = true).apply {
    val textShape = TextDecoration(
        "这是圆形高亮区域，点击高亮进入下一步",
        sp2px(context, 12F).toFloat(),
        ContextCompat.getColor(context, R.color.white),
        targetBound.right, targetBound.centerY()
    )
    addShape(textShape)
}
val guideStep2 = ...
val guideStep3 = ...
...
guideManager.apply{
    addGuideStep(guideStep1)
    addGuideStep(guideStep2)
    addGuideStep(guideStep3)
    ...
}
```
### 3. 显示指引
```kotlin
guideManager.show()
```
### 4. 	完整代码如下
```kotlin
GuideManager(activity).apply {
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

```

