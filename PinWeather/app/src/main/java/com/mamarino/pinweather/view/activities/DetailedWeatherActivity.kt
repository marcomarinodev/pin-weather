package com.mamarino.pinweather.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mamarino.pinweather.R
import com.mamarino.pinweather.model.WeatherObject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DetailedWeatherActivity : AppCompatActivity() {

    private lateinit var weatherObj: WeatherObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)

        val actionBar = supportActionBar

        // get data from main activity intent
        weatherObj = Json.decodeFromString(intent.extras?.get("weatherObj") as String)

        actionBar!!.title = weatherObj.cityName

        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return true
    }
}