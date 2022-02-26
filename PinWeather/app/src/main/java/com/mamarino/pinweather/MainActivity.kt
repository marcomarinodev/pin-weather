package com.mamarino.pinweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getName().observe(this, Observer<String> { newName ->
            textView.text = newName
        })

        button.setOnClickListener {
            viewModel.suggestName()
        }
    }
}