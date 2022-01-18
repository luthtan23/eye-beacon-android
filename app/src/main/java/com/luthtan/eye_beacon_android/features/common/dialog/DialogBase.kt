package com.luthtan.eye_beacon_android.features.common.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.luthtan.eye_beacon_android.R
import com.luthtan.eye_beacon_android.databinding.DialogBaseBinding

class DialogBase(private val context: Context, private val layoutInflater: LayoutInflater) {

    private var binding: DialogBaseBinding = DialogBaseBinding.inflate(layoutInflater)
    private var alertDialog: AlertDialog =
        MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_rounded)
            .setView(binding.root)
            .create()

    lateinit var onConfirmClick:()->Unit
    lateinit var dialogState:String

    init {
        binding.btnConfirm.setOnClickListener {
            if(this::onConfirmClick.isInitialized){
                onConfirmClick.invoke()
            }else{
                dismiss()
            }
        }
    }

    fun seOnConfirmClick(onConfirmClick:()->Unit){
        this.onConfirmClick = onConfirmClick
    }

    fun showLoading() {
        binding.desc = "Please wait"
        binding.showProgress = true
        alertDialog.show()
    }

    fun showError(errorMessage: String) {
        binding.title = "Error"
        binding.desc = errorMessage
        binding.showProgress = false
        if (!alertDialog.isShowing)
            alertDialog.show()
    }

    fun showSuccess(successMessage: String) {
        binding.title = "Success"
        binding.desc = successMessage
        binding.showProgress = false
        alertDialog.show()
    }

    fun dismiss() {
        if (alertDialog.isShowing)
            alertDialog.cancel()
    }

    fun updateState(pair : Pair<String, String?>){
        this.dialogState = pair.first
        when (pair.first) {
            SUCCESS -> {
                showSuccess(pair.second + "")
            }
            ERROR -> {
                showError(pair.second + "")
            }
            LOADING -> {
                showLoading()
            }
            else -> {
                dismiss()
            }
        }
    }

    companion object{
        val SUCCESS = "SUCCESS"
        val ERROR = "ERROR"
        val LOADING = "LOADING"
        val HIDE = "HIDE"
    }
}