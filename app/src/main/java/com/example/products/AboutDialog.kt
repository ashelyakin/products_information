package com.example.products

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class AboutDialog(private val reference: String?): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Открыть сайт в браузере?")
                .setCancelable(true)
                .setPositiveButton("ОК"){dialog, id ->
                    /*Toast.makeText(activity, "perfect choice",
                        Toast.LENGTH_LONG).show()*/
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(reference)
                    startActivity(i)
                }
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener{dialog, id ->
                        dialog.cancel()
                    })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}