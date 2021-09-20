package com.weither.weitherloop.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.weither.weitherloop.R
import com.weither.weitherloop.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWeatherBinding.inflate(layoutInflater)
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

        viewModel.countDownIsFinished.observe(
            this,
            {
                if (it) {
                    binding.activityWeatherTable.visibility = View.VISIBLE
                    binding.activityWeatherLabel.visibility = View.GONE
                    binding.activityWeatherDeterminateBar.visibility = View.GONE
                    binding.activityWeatherButtonRestart.visibility = View.VISIBLE

                    displayTable()
                } else {
                    binding.activityWeatherTable.visibility = View.GONE
                    binding.activityWeatherLabel.visibility = View.VISIBLE
                    binding.activityWeatherDeterminateBar.visibility = View.VISIBLE
                    binding.activityWeatherButtonRestart.visibility = View.GONE
                }
            }
        )

        binding.activityWeatherButtonRestart.setOnClickListener {
            viewModel.launchWeatherLoop()
        }

        setContentView(view)
    }

    private fun displayTable() {
        val table = binding.activityWeatherTable
        table.removeAllViews()

        viewModel.listCurrentWeather.value?.let {
            for (city in it) {
                val tr = TableRow(this)
                // add city name
                val tvName = TextView(this)
                tvName.text = city.name
                tvName.setTextColor(ContextCompat.getColor(this, R.color.black))
                tvName.textSize = 24.toFloat()
                tr.addView(tvName)

                // add city temperature
                val tvTemp = TextView(this)
                tvTemp.text = kalvinToCelcius(city.temp)?.roundToInt().toString() + " °C"
                tvTemp.setTextColor(ContextCompat.getColor(this, R.color.black))
                tvTemp.textSize = 24.toFloat()
                tr.addView(tvTemp)

                // add cloud picto
                val imgCloud = ImageView(this)
                city.cloudPercentage?.let { cloud ->
                    when (cloud) {
                        in 1..25 -> {
                            imgCloud.setBackgroundResource(R.drawable.cloud_clear)
                        }
                        in 26..50 -> {
                            imgCloud.setBackgroundResource(R.drawable.cloud_1)
                        }
                        in 51..75 -> {
                            imgCloud.setBackgroundResource(R.drawable.cloud_2)
                        }
                        else -> {
                            imgCloud.setBackgroundResource(R.drawable.cloud_3)
                        }
                    }
                }
                tr.addView(imgCloud)

                // add the row to the table
                table.addView(tr)

                println("display table")
            }
        }
    }

    private fun kalvinToCelcius(temp: Double?): Double? {
        return temp?.let {
            temp - 273.15
        }
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
