package com.luthtan.eye_beacon_android.base.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.google.android.material.button.MaterialButton
import com.luthtan.eye_beacon_android.R

object AlertLocationDialog {

    fun showRequestPermission(context: Context, onButtonClick:()->Unit) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_location_permission)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val button = dialog.findViewById<MaterialButton>(R.id.btnPermission)
        button.setOnClickListener {
            onButtonClick()
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialog.show()
    }

}