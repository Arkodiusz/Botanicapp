package com.arje.botanicapp.data.repository

import androidx.lifecycle.LiveData
import com.arje.botanicapp.data.PlantDao
import com.arje.botanicapp.data.model.Plant

class PlantRepository(private val plantDao: PlantDao) {

    val readAllData: LiveData<List<Plant>> = plantDao.readAllData()

    suspend fun addPlant(plant: Plant) {
        plantDao.addPlant(plant)
    }

    suspend fun updatePlant(plant: Plant) {
        plantDao.updatePlant(plant)
    }

    suspend fun deletePlant(plantId: Int) {
        plantDao.deletePlant(plantId)
    }

}