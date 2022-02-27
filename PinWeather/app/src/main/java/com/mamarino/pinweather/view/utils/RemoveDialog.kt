package com.mamarino.pinweather.view.utils

import android.app.AlertDialog
import android.content.Context

class RemoveDialog(
    context: Context,
    title: String,
    message: String,
    iconId: Int,
    val removeHandler: () -> Unit
) {

    private var builder = AlertDialog.Builder(context)
    private var alertDialog: AlertDialog

    init {
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setIcon(iconId)
        builder.setPositiveButton("Yes") { _, _ ->
            removeHandler()
        }
        builder.setNegativeButton("No"){ _, _ -> }
        alertDialog = builder.create()
        alertDialog.setCancelable(false)
    }

    fun show() {
        alertDialog.show()
    }
}