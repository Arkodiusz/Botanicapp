package com.arje.botanicapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.arje.botanicapp.data.PlantDatabase
import com.arje.botanicapp.data.model.Plant
import com.arje.botanicapp.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Plant>>
    private val repository: PlantRepository

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantDao()
        repository = PlantRepository(plantDao)
        readAllData = repository.readAllData
    }

    fun addPlant(plant: Plant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlant(plant)
        }
    }

    fun updatePlant(plant: Plant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePlant(plant)
        }
    }

    fun deletePlant(plantId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlant(plantId)
        }
    }

}