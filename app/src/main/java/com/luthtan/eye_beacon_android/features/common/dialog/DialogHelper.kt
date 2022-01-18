package com.luthtan.eye_beacon_android.features.common.dialog

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DialogHelper {

    fun showConfirmationDialog(
        fragmentManager: FragmentManager,
        title: String,
        description: String,
        leftBtnText: String,
        rightBtnText: String,
        actionRightBtn: (DialogFragment, View) -> Unit
    ) {
        val confirmationDialog = ConfirmationDialog.newInstance(title, description, leftBtnText, rightBtnText)

        confirmationDialog.onLeftButtonClickListener = { dialogFragment, view ->
            dialogFragment.dismiss()
        }

        confirmationDialog.onRightButtonClickListener = { dialogFragment, view ->
            dialogFragment.dismiss()
            actionRightBtn(dialogFragment, view)
        }

        confirmationDialog.show(fragmentManager, "ConfirmationDialog")
    }
}