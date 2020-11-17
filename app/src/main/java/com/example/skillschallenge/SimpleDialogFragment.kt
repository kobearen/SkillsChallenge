package com.example.skillschallenge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SimpleDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Here Title")
            .setMessage("Here Message")
            .setPositiveButton("done") { dialog, id ->
                println("dialog:$dialog which:$id")
            }
            .setNegativeButton("cancel") { dialog, id ->
                println("dialog:$dialog which:$id")
            }
        return builder.create()
    }
}