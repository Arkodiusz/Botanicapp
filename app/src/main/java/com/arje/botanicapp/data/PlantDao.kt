package com.arje.botanicapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.arje.botanicapp.data.model.Plant

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)

    @Query("DELETE FROM plants WHERE id = :plantId")
    suspend fun deletePlant(plantId: Int)

    @Query("SELECT * FROM plants ORDER BY id ASC")
    fun readAllData(): LiveData<List<Plant>>

}