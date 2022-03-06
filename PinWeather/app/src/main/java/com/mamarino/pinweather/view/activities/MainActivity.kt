package com.mamarino.pinweather.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamarino.pinweather.viewmodels.MainViewModel
import com.mamarino.pinweather.R
import com.mamarino.pinweather.view.adapters.WeatherListAdapter
import com.mamarino.pinweather.viewmodels.MainViewModelFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    // UI references
    private lateinit var weatherListView: ListView
    private lateinit var addWeatherButton: FloatingActionButton

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherListView = findViewById<ListView>(R.id.weatherList)
        addWeatherButton = findViewById<FloatingActionButton>(R.id.addWeatherButton)

        val factory = MainViewModelFactory();
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.getWeatherObjects().observe(this) { newWeatherObject ->
            weatherListView.adapter = WeatherListAdapter(this, newWeatherObject, viewModel)
        }

        viewModel.getSelectedWeatherObject().observe(this) { selectedWeatherObjectId ->
            val detailedWeatherIntent = Intent(this, DetailedWeatherActivity::class.java)
            val selectedWeatherObject = viewModel.getWeatherObjects().value?.find { weatherObject ->
                weatherObject.id == selectedWeatherObjectId
            }
            detailedWeatherIntent.putExtra("weatherObj", Json.encodeToString(selectedWeatherObject))
            startActivity(detailedWeatherIntent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out);
        }

        addWeatherButton.setOnClickListener {
            viewModel.suggestLocations()
        }
    }
}