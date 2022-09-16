package com.arje.botanicapp.fragments.add

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arje.botanicapp.MainActivity.Companion.CAMERA_REQUEST_CODE
import com.arje.botanicapp.R
import com.arje.botanicapp.data.model.Plant
import com.arje.botanicapp.data.viewmodel.PlantViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mPlantViewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        view.btn_add.setOnClickListener {
            insertNewPlant()
        }

        view.btn_camera.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    CAMERA
                ) == PERMISSION_GRANTED -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, CAMERA_REQUEST_CODE)
                }
                shouldShowRequestPermissionRationale(CAMERA) ->
                    showDialog(
                        "Permission request",
                        "Camera permission is necessary to take photos",
                        ::requestForCameraPermission,
                        ::promptPermissionDenied
                    )
                else ->
                    showDialog(
                        "Permission request",
                        "Camera permission denied! Do you want to open settings?",
                        ::openSettingsPage,
                        ::promptPermissionDenied
                    )
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(requireContext(), "FOTKA", Toast.LENGTH_SHORT).show()
            imageView.setImageBitmap(data!!.extras!!.get("data") as Bitmap)
        }
    }

    private fun showDialog(title: String, message: String, okFun: () -> Unit, cancelFun: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") {
                    dialog, which -> okFun()
            }
            setNegativeButton("Cancel") {
                    dialog, which -> cancelFun()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun openSettingsPage() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun promptPermissionDenied() {
        Toast.makeText(requireContext(), "Permission denied!", Toast.LENGTH_SHORT).show()
    }

    private fun requestForCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(CAMERA), CAMERA_REQUEST_CODE)
    }

    private fun insertNewPlant() {
        val plantName = et_plantName.text.toString()
        if (!validate(plantName)) {
            Toast.makeText(requireContext(), "Plant name can't be blank!", Toast.LENGTH_SHORT).show()
            return
        }
        val plant = Plant(0, plantName)
        mPlantViewModel.addPlant(plant)
        Toast.makeText(requireContext(), "Successfully added plant!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    private fun validate(plantName: String): Boolean {
        return plantName.isNotBlank()
    }

}