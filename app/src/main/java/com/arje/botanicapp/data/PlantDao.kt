package com.arje.botanicapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Query("SELECT * FROM plants ORDER BY id ASC")
    fun readAllData(): LiveData<List<Plant>>

}