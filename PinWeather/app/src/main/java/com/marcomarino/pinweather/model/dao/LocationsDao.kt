package com.marcomarino.pinweather.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marcomarino.pinweather.model.entities.DetailedLocation
import com.marcomarino.pinweather.model.entities.LightLocation

@Dao
interface LocationsDao {

    @Query("SELECT uuid, lat, lon FROM locations")
    fun getLightLocations(): List<LightLocation>

    @Query("SELECT uuid, bitmap, lat, lon FROM locations")
    fun getDetailedLocations(): List<DetailedLocation>

    @Query("SELECT bitmap FROM locations WHERE uuid = :id")
    fun getLocationImage(id: String): String

    @Insert
    suspend fun insertLocation(location: DetailedLocation)

}