package com.bottle.core.arch.guide

import android.app.Activity
import android.view.View
import com.bottle.core.R
import kotlinx.android.synthetic.main.dialog_guide.*

interface GuideListener {
    fun onNextStep(step: Int)

    fun onCompleted()
}

class GuideManager(activity: Activity) :
    GuideDialog.OnNextStepListener {

    private val mGuideSteps = mutableListOf<GuideInfo>()
    private var currentStep = -1
    val mGuideDialog: GuideDialog =
        GuideDialog(activity)
    var mGuideListener: GuideListener? = null
    var showGuideButton = false

    init {
        mGuideDialog.mOnNextStepListener = this
        if (!showGuideButton) {
            mGuideDialog.btnPreStep.visibility = View.GONE
            mGuideDialog.btnNextStep.visibility = View.GONE
        }
    }

    fun addGuideStep(guideInfo: GuideInfo) {
        mGuideSteps.add(guideInfo)
    }

    fun guideStepCount(): Int {
        return mGuideSteps.size
    }

    fun show() {
        mGuideDialog.show()
        onNextStep()
    }

    override fun onNextStep() {
        if (currentStep >= mGuideSteps.size - 1) {
            mGuideDialog.dismiss()
            mGuideListener?.onCompleted()
            return
        }
        currentStep++
        val guideStep: GuideInfo = mGuideSteps[currentStep]
        mGuideDialog.guideView.showGuide(guideStep)
        if (currentStep > 0 && showGuideButton) {
            mGuideDialog.btnPreStep.visibility = View.VISIBLE
        }
        updateNextText()
    }

    override fun onPreStep() {
        if (currentStep == 0) {
            return
        }
        currentStep -= 1
        val guideStep: GuideInfo = mGuideSteps[currentStep]
        mGuideDialog.guideView.showGuide(guideStep)
        if (currentStep == 0 && showGuideButton) {
            mGuideDialog.btnPreStep.visibility = View.GONE
        }
        updateNextText()
    }

    private fun updateNextText() {
        if (currentStep == mGuideSteps.size - 1) {
            mGuideDialog.btnNextStep.setText(R.string.end)
        } else {
            mGuideDialog.btnNextStep.setText(R.string.next_step)
        }
        mGuideListener?.onNextStep(currentStep)
    }
}