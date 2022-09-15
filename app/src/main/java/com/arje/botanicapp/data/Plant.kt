package com.arje.botanicapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "plants")
data class Plant (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
): Parcelable