package com.mamarino.pinweather.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamarino.pinweather.viewmodels.MainViewModel
import com.mamarino.pinweather.R
import com.mamarino.pinweather.view.adapters.WeatherListAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherListView = findViewById<ListView>(R.id.weatherList)
        val addWeatherButton = findViewById<FloatingActionButton>(R.id.addWeatherButton)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getWeatherObjects().observe(this) { newWeatherObject ->
            weatherListView.adapter = WeatherListAdapter(this, newWeatherObject, viewModel)
        }

        addWeatherButton.setOnClickListener {
            viewModel.suggestLocations()
        }
    }
}