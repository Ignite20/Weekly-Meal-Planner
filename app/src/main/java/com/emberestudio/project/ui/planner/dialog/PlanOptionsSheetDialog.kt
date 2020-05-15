package com.emberestudio.project.ui.planner.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emberestudio.project.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_plan_options.*


const val PLAN_OPTIONS_SHEET_TAG = "plan_options_sheet_tag"
class PlanOptionsSheetDialog(var callback: PlanOptionsCallback?) : BottomSheetDialogFragment() {

    interface PlanOptionsCallback{
        fun onSharePlan()
        fun onDeletePlan()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_plan_options,container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_share_plan.setOnClickListener {
            dismissAllowingStateLoss()
            callback?.onSharePlan()
        }

        tv_delete_plan.setOnClickListener {
            dismissAllowingStateLoss()
            callback?.onDeletePlan()
        }
    }
}