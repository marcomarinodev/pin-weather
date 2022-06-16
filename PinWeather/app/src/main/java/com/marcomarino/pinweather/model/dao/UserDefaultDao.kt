package com.marcomarino.pinweather.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marcomarino.pinweather.model.entities.UserDefault

@Dao
interface UserDefaultDao {

    @Query("SELECT token FROM userDefaults LIMIT 1")
    fun getLocalToken(): String?

    @Query("SELECT COUNT(*) FROM userDefaults")
    fun tableSize(): Int

    @Insert
    suspend fun insertUser(user: UserDefault)

    @Query("DELETE FROM userDefaults")
    suspend fun deleteCurrentUser()
}