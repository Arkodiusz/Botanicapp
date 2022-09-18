package com.arje.botanicapp.fragments.update

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arje.botanicapp.MainActivity
import com.arje.botanicapp.R
import com.arje.botanicapp.data.model.Plant
import com.arje.botanicapp.data.viewmodel.PlantViewModel
import com.arje.botanicapp.fragments.add.AddFragment
import com.arje.botanicapp.permissions.PermissionManager
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private lateinit var mPlantViewModel: PlantViewModel

    private var pathToPhoto = ""

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        view.et_plantName_update.setText(args.currentPlant.name)

        pathToPhoto = args.currentPlant.image

        view.imageView_update.setImageBitmap(BitmapFactory.decodeFile(pathToPhoto))

        view.btn_camera_update.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startActivityForResult(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        MainActivity.CAMERA_REQUEST_CODE
                    )
                }
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    PermissionManager.showDialogForPermissionRequest(requireContext(), requireActivity())
                }
                else -> {
                    PermissionManager.showDialogForOpenSettings(requireContext(), requireActivity())
                }
            }
        }

        view.btn_delete.setOnClickListener {
            deletePlant()
        }

        view.btn_update.setOnClickListener {
            updatePlant()
        }

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MainActivity.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val capturedImage = data!!.extras!!.get("data") as Bitmap
            pathToPhoto = AddFragment.saveImageToFile(requireActivity(), capturedImage)
            imageView_update.setImageBitmap(BitmapFactory.decodeFile(pathToPhoto))
        }
    }

    private fun updatePlant() {
        val id = args.currentPlant.id
        val plantName = et_plantName_update.text.toString()
        val path = pathToPhoto

        if (!AddFragment.validate(plantName)) {
            Toast.makeText(requireContext(), "Plant name can't be blank!", Toast.LENGTH_SHORT).show()
            return
        }

        val plant = Plant(id, plantName, path)
        mPlantViewModel.updatePlant(plant)
        Toast.makeText(requireContext(), "Successfully updated plant!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun deletePlant() {
        val plantId = args.currentPlant.id
        mPlantViewModel.deletePlant(plantId)
        Toast.makeText(requireContext(), "Plant deleted!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }
}