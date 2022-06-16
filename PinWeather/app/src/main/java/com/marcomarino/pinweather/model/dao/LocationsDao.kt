package com.marcomarino.pinweather.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marcomarino.pinweather.model.entities.LightLocation
import com.marcomarino.pinweather.model.entities.DetailedLocation

@Dao
interface LocationsDao {

    @Query("SELECT uuid, lat, lon FROM locations")
    fun getLightLocations(): List<LightLocation>

    @Query("SELECT uuid, bitmap, lat, lon FROM locations")
    fun getDetailedLocations(): List<DetailedLocation>

    @Insert
    suspend fun insertLocation(location: DetailedLocation)

}