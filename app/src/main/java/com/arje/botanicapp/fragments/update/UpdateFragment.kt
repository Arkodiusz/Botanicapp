package com.arje.botanicapp.fragments.update

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arje.botanicapp.R
import com.arje.botanicapp.data.model.Plant
import com.arje.botanicapp.data.viewmodel.PlantViewModel
import com.arje.botanicapp.fragments.add.AddFragment.Companion.validate
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

        view.btn_update.setOnClickListener {
            updatePlant()
        }

        view.btn_delete.setOnClickListener {
            deletePlant()
        }

        return view
    }

    private fun updatePlant() {
        val plantName = et_plantName_update.text.toString()
        if (!validate(plantName)) {
            Toast.makeText(requireContext(), "Plant name can't be blank!", Toast.LENGTH_SHORT).show()
            return
        }
        val plant = Plant(args.currentPlant.id, plantName, pathToPhoto)
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