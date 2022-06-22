package com.marcomarino.pinweather.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDefaults")
data class UserDefault(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "token") val token: String?,
    @ColumnInfo(name = "full_name") val fullName: String?,
    @ColumnInfo(name = "email") val email: String?
)