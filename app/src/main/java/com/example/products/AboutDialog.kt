package com.example.products

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

//класс для диалога
class AboutDialog(private val reference: String?): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Открыть сайт в браузере?")
                .setCancelable(true)
                //если нажали на Ок то открываем ссылку в браузере
                .setPositiveButton("ОК"){dialog, id ->
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(reference)
                    startActivity(i)
                }
                .setNegativeButton("Отмена"){dialog, id ->
                        dialog.cancel()
                    }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}