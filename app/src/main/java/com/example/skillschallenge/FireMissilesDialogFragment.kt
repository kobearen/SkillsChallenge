package com.example.skillschallenge

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class FireMissilesDialogFragment : DialogFragment() {



        var onPositiveListener:DialogInterface.OnClickListener? = null
        var onNegativeListener:DialogInterface.OnClickListener? = null

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(context!!)
            builder.apply {
                setTitle(arguments?.getString("title"))
                setMessage(arguments?.getString("message"))
                setPositiveButton(arguments?.getString("positiveButtonLabel"), onPositiveListener)
                setNegativeButton(arguments?.getString("negativeButtonLabel"),onNegativeListener)
            }
            // Create the AlertDialog object and return it
            return builder.create()
        }

}