package com.arje.botanicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.arje.botanicapp.data.model.Plant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        var selectedPlant: Plant = Plant(0, "", "", "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        when(fragment.findNavController().currentDestination?.id) {
            R.id.detailsFragment -> fragment.findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
            R.id.updateFragment -> fragment.findNavController().navigate(R.id.action_updateFragment_to_detailsFragment)
            R.id.addFragment -> fragment.findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        return super.onSupportNavigateUp()
    }
}