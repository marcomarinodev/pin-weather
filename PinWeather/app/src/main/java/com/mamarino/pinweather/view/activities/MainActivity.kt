package com.mamarino.pinweather.view.activities

import android.app.ActionBar
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamarino.pinweather.viewmodels.MainViewModel
import com.mamarino.pinweather.R
import com.mamarino.pinweather.databinding.ActivityMainBinding
import com.mamarino.pinweather.view.adapters.WeatherListAdapter
import com.mamarino.pinweather.view.fragments.AddWeatherEntryFragment
import com.mamarino.pinweather.view.startAnimation
import com.mamarino.pinweather.viewmodels.MainViewModelFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    // UI references
    private lateinit var weatherListView: ListView
    private lateinit var addWeatherButton: FloatingActionButton

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var animation = AnimationUtils.loadAnimation(this, R.anim.circle_explosion_anim).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }



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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        binding.addWeatherButton.setOnClickListener {
            viewModel.suggestLocations()
            binding.addWeatherButton.isVisible = false
            binding.circle.isVisible = true
            binding.circle.startAnimation(animation) {
                // display add weather entry fragment
                weatherListView.isVisible = false
                binding.addWeatherButton.isVisible = false
                supportFragmentManager.beginTransaction().replace(R.id.addWeatherEntryContainer, AddWeatherEntryFragment()).commit()
            }
        }
    }
}