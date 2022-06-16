package com.marcomarino.pinweather.network.repositories

import androidx.annotation.WorkerThread
import com.marcomarino.pinweather.model.dao.LocationsDao
import com.marcomarino.pinweather.model.entities.DetailedLocation
import java.util.*

class PostRepository(private val locationsDao: LocationsDao) {

    suspend fun addPost(
        bitmap: String,
        lat: Float,
        lon: Float
    ) {
        val randomId = UUID.randomUUID().toString()

        insert(
            DetailedLocation(
                uuid = randomId,
                bitmap = bitmap,
                lat = lat,
                lon = lon
            )
        )
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun insert(location: DetailedLocation) {
        locationsDao.insertLocation(location)
    }

}