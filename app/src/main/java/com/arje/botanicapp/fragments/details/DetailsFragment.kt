package com.arje.botanicapp.fragments.details

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arje.botanicapp.MainActivity.Companion.selectedPlant
import com.arje.botanicapp.R
import com.arje.botanicapp.data.viewmodel.PlantViewModel
import kotlinx.android.synthetic.main.fragment_details.view.*

class DetailsFragment : Fragment() {

    private lateinit var mPlantViewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        view.txt_plantName_details.text = selectedPlant.name
        view.imageView_details.setImageBitmap(BitmapFactory.decodeFile(selectedPlant.imagePath))
        view.txt_description_details.text = selectedPlant.description


        view.btn_edit_details.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_updateFragment)
        }

        return view
    }

}