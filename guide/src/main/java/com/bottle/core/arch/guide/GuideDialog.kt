package com.bottle.core.arch.guide

import android.app.Dialog
import android.content.Context
import android.view.View
import com.bottle.core.R
import kotlinx.android.synthetic.main.dialog_guide.*

class GuideDialog : Dialog, View.OnClickListener, GuideView.OnClickListener {

    var mOnNextStepListener: OnNextStepListener? = null

    constructor(context: Context): super(context, R.style.DialogFullScreenTranslucent){
        setContentView(R.layout.dialog_guide)
        btnNextStep.setOnClickListener(this)
        btnPreStep.setOnClickListener(this)
        guideView.mOnClickListener = this
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnNextStep) {
            mOnNextStepListener?.onNextStep()
        } else if (view.id == R.id.btnPreStep) {
            mOnNextStepListener?.onPreStep()
        }
    }

    interface OnNextStepListener {
        fun onNextStep()

        fun onPreStep()
    }

    override fun onClick() {
        mOnNextStepListener?.onNextStep()
    }
}