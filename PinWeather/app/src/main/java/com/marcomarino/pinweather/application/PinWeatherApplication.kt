package com.marcomarino.pinweather.application

import android.app.Application
import com.marcomarino.pinweather.db.AppDatabase

class PinWeatherApplication: Application() {
    // using lazy so the db and repo are only created when needed
    val database by lazy { AppDatabase.getInstance(this) }
}