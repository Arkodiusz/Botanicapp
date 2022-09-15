package com.arje.botanicapp.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        view.btn_add.setOnClickListener {
            insertNewPlant()
        }

        return view
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