package com.arje.botanicapp.fragments.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arje.botanicapp.R
import com.arje.botanicapp.data.model.Plant
import com.arje.botanicapp.data.viewmodel.PlantViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private lateinit var mPlantViewModel: PlantViewModel

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        view.et_plantName.setText(args.currentPlant.name)

        view.btn_update.setOnClickListener {
            updatePlant()
        }

        view.btn_delete.setOnClickListener {
            deletePlant()
        }

        return view
    }

    private fun updatePlant() {
        val plantName = et_plantName.text.toString()
        if (!validate(plantName)) {
            Toast.makeText(requireContext(), "Plant name can't be blank!", Toast.LENGTH_SHORT).show()
            return
        }
        val plant = Plant(args.currentPlant.id, plantName)
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

    private fun validate(plantName: String): Boolean {
        return plantName.isNotBlank()
    }
}