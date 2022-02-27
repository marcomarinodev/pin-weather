package com.mamarino.pinweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val weatherListView = findViewById<ListView>(R.id.weatherList)
        val addWeatherButton = findViewById<FloatingActionButton>(R.id.addWeatherButton)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getWeatherObjects().observe(this, Observer<ArrayList<WeatherObject>> { newWeatherObject ->
            weatherListView.adapter = WeatherListAdapter(this, newWeatherObject)
        })

        addWeatherButton.setOnClickListener {
            viewModel.suggestLocations()
        }
    }
}