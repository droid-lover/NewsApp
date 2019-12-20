package com.pathshala.example.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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

    fun getFormattedDate(format: String, time: Date): String {
        val simpleDateFormat = SimpleDateFormat(format)
        val instance = Calendar.getInstance()
        instance.time = time
        var long1 = instance.timeInMillis
        //Log.e("time", long1 + "" + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(instance.getTime()));
        long1 += (5 * 60 * 60 * 1000 + 30 * 60 * 1000)
        instance.timeInMillis = long1
        //Log.e("time", long1 + "" + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(instance.getTime()));
        return simpleDateFormat.format(instance.time)
    }

    fun showToastMessage(strMessage: String?, length: Int = Toast.LENGTH_SHORT) {
        if (!TextUtils.isEmpty(strMessage)) {
            sToast?.cancel()
            sToast = Toast.makeText(ExampleApp.instance, strMessage, Toast.LENGTH_SHORT)
            sToast?.show()
        }
    }


    private val G_PLAY_SERVICE_CODE = 7777

    fun checkPlayServices(context: Context): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(context)

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(
                    context as Activity?,
                    resultCode,
                    G_PLAY_SERVICE_CODE
                )
            }
            return false
        }

        return true
    }


    fun permissionsToRequest(
        wantedPermissions: ArrayList<String>,
        context: Context
    ): ArrayList<String> {
        val result = ArrayList<String>()

        for (permission in wantedPermissions) {
            if (!hasPermission(permission, context)) {
                result.add(permission)
            }
        }

        return result
    }

    fun hasPermission(permission: String, context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        } else true

    }


    fun showPermissionDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Allow This App to access Current Location")
        val strMessage =
            "Hi,We will be needing your current location to fetch current weather details :)"
        builder.setMessage(strMessage)
            .setPositiveButton("Go to Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun checkLocation(context: Context): Boolean {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

}
