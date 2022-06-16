package com.marcomarino.pinweather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.marcomarino.pinweather.model.dao.LocationsDao
import com.marcomarino.pinweather.model.dao.UserDefaultDao
import com.marcomarino.pinweather.model.entities.DetailedLocation
import com.marcomarino.pinweather.model.entities.UserDefault

@Database(entities = [UserDefault::class, DetailedLocation::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDefaultDao(): UserDefaultDao
    abstract fun locationsDao(): LocationsDao

    companion object {
        // the singleton prevents multiple instances of db opening at the same time
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var _instance = instance

                if (_instance == null) {
                    _instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    instance = _instance
                }

                return _instance
            }
        }
    }
}