package com.arje.botanicapp.permissions

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.arje.botanicapp.MainActivity

class PermissionManager {

    companion object {
        fun showDialogForPermissionRequest(context: Context, activity: FragmentActivity) {
            showDialog(
                context,
                "Permission request",
                "Camera permission is necessary to take photos",
                { requestForCameraPermission(activity) },
                { promptPermissionDenied(context) }
            )
        }

        fun showDialogForOpenSettings(context: Context, activity: FragmentActivity) {
            showDialog(
                context,
                "Permission request",
                "Camera permission denied! Do you want to open settings?",
                { openSettingsPage(activity) },
                { promptPermissionDenied(context) }
            )
        }

        private fun showDialog(context: Context, title: String, message: String, okFun: () -> Unit, cancelFun: () -> Unit) {
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("OK") {
                        _, _ -> okFun()
                }
                setNegativeButton("Cancel") {
                        _, _ -> cancelFun()
                }
            }
            val dialog = builder.create()
            dialog.show()
        }

        private val openSettingsPage: (FragmentActivity)->Unit = {
            activity ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }

        private val requestForCameraPermission: (FragmentActivity)->Unit = {
            activity ->
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), MainActivity.CAMERA_REQUEST_CODE)
        }

        private val promptPermissionDenied: (Context)->Unit = {
            context ->
            Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }
}