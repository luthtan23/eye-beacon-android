package com.luthtan.eye_beacon_android.features.common.dialog

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

object DialogHelper {

    fun showConfirmationDialog(
        fragmentManager: FragmentManager,
        title: String,
        description: String,
        leftBtnText: String,
        rightBtnText: String,
        actionRightBtn: (DialogFragment, View) -> Unit
    ) {
        val confirmationDialog =
            ConfirmationDialog.newInstance(title, description, leftBtnText, rightBtnText)

        confirmationDialog.onLeftButtonClickListener = { dialogFragment, view ->
            dialogFragment.dismiss()
        }

        confirmationDialog.onRightButtonClickListener = { dialogFragment, view ->
            dialogFragment.dismiss()
            actionRightBtn(dialogFragment, view)
        }

        confirmationDialog.show(fragmentManager, "ConfirmationDialog")
    }

    fun showConfirmationDialogOneButton(
        fragmentManager: FragmentManager,
        title: String,
        description: String,
        leftBtnText: String,
        actionRightBtn: (DialogFragment, View) -> Unit
    ) {
        val confirmationDialogOneButton =
            ConfirmationDialogOneButton.newInstance(title, description, leftBtnText)

        confirmationDialogOneButton.onLeftButtonClickListener = { dialogFragment, view ->
            actionRightBtn.invoke(dialogFragment, view)
            dialogFragment.dismiss()
        }

        confirmationDialogOneButton.show(fragmentManager, "ConfirmationDialogOneButton")
    }

    fun showConfirmationDialogOneButtonNoTitle(
        fragmentManager: FragmentManager,
        description: String,
        leftBtnText: String,
        actionRightBtn: (DialogFragment, View) -> Unit
    ) {
        val confirmationDialogOneButtonNoTitle = ConfirmationDialogOneButtonNoTitle.newInstance(description, leftBtnText)

        confirmationDialogOneButtonNoTitle.onLeftButtonClickListener = { dialogFragment, view ->
            actionRightBtn.invoke(dialogFragment, view)
            dialogFragment.dismiss()
        }

        confirmationDialogOneButtonNoTitle.show(fragmentManager, "ConfirmationDialogOneButtonNoTitle")
    }
}