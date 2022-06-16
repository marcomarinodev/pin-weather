package com.marcomarino.pinweather.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class DetailedLocation(
    @PrimaryKey val uuid: String,
    @ColumnInfo val bitmap: String,
    @ColumnInfo val lat: Float,
    @ColumnInfo val lon: Float
)

data class LightLocation(
    val uuid: String,
    val lat: Float,
    val lon: Float
)
