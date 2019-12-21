package com.pathshala.example.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.assignment.rxjavakotlinway.app.ExampleApp
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.pathshala.example.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sachin
 */
object Utils {

    private val TAG = Utils::class.java.name
    @Suppress("DEPRECATION")
    var sProgressDialog: ProgressDialog? = null
    private var sToast: Toast? = null
    var alertDialog: AlertDialog? = null

    fun showProgressDialog(context: Context) {
        showProcessingDialog(context, "Processing...")
    }


    private fun showProcessingDialog(context: Context, message: String) {
        if (context is AppCompatActivity || context is Activity) {

            if (alertDialog == null) {
                val builder = AlertDialog.Builder(context)
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.layout_processing_dialog, null, false)
                (view.findViewById<View>(R.id.tv_processing_message) as TextView).text = message
                builder.setView(view)
                alertDialog = builder.create()
                alertDialog!!.setCancelable(false)
            }
            if (context is AppCompatActivity) {
                if (!(context as Activity).isFinishing)
                    alertDialog!!.show()
            }
        }
    }

    fun hideProgressDialog() {
        try {
            if (sProgressDialog != null && sProgressDialog!!.isShowing)
                sProgressDialog!!.dismiss()
            sProgressDialog = null

            if (alertDialog != null && alertDialog!!.isShowing)
                alertDialog!!.dismiss()
            alertDialog = null
        } catch (ignored: Throwable) {
        }

    }


    fun dp2px(resources: Resources, dp: Float): Float {
        return dp * resources.displayMetrics.density + 0.5f
    }

}
