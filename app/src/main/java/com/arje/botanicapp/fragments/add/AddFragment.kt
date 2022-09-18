package com.arje.botanicapp.fragments.add

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arje.botanicapp.MainActivity.Companion.CAMERA_REQUEST_CODE
import com.arje.botanicapp.R
import com.arje.botanicapp.data.model.Plant
import com.arje.botanicapp.data.viewmodel.PlantViewModel
import com.arje.botanicapp.permissions.PermissionManager
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class AddFragment : Fragment() {

    private lateinit var mPlantViewModel: PlantViewModel

    private lateinit var filePhoto: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        view.btn_add.setOnClickListener {
            insertNewPlant()
        }

        view.btn_camera_add.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(requireContext(), CAMERA) == PERMISSION_GRANTED -> {
                    startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST_CODE)
                }
                shouldShowRequestPermissionRationale(CAMERA) -> {
                    PermissionManager.showDialogForPermissionRequest(requireContext(), requireActivity())
                }
                else -> {
                    PermissionManager.showDialogForOpenSettings(requireContext(), requireActivity())
                }
            }
        }

    return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val capturedImage = data!!.extras!!.get("data") as Bitmap
            filePhoto = getPhotoFile(requireActivity())

            try {
                val fOut = FileOutputStream(filePhoto)
                capturedImage.compress(Bitmap.CompressFormat.PNG, 85, fOut)
                fOut.flush()
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            imageView_add.setImageBitmap(BitmapFactory.decodeFile(filePhoto.absolutePath))
        }
    }

    private fun insertNewPlant() {
        val plantName = et_plantName_add.text.toString()
        val path = filePhoto.path

        if (!validate(plantName)) {
            Toast.makeText(requireContext(), "Plant name can't be blank!", Toast.LENGTH_SHORT).show()
            return
        }
        val plant = Plant(0, plantName, path)
        mPlantViewModel.addPlant(plant)
        Toast.makeText(requireContext(), "Successfully added plant!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }


    companion object {
        fun validate(plantName: String): Boolean {
            return plantName.isNotBlank()
        }

        fun getPhotoFile(activity: FragmentActivity): File {
            val timeStamp: String = DateTimeFormatter
                .ofPattern("yyyyMMddHHmmssSSSSSS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
            val filename = "plant_${timeStamp}_"
            val directoryStorage = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(filename, ".jpg", directoryStorage)
        }
    }

}