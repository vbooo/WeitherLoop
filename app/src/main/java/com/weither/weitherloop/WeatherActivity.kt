package com.weither.weitherloop

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.weither.weitherloop.databinding.ActivityMainBinding
import com.weither.weitherloop.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWeatherBinding.inflate(layoutInflater)
        val view = binding.root

        viewModel.label?.observe(
            this,
            {
                binding.activityWeatherLabel.text = it
            }
        )

        viewModel.progress.observe(
            this,
            {
                binding.activityWeatherDeterminateBar.progress = it.toInt()
            }
        )

        setContentView(view)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
