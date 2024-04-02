package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView

class StamboomActivity : AppCompatActivity() {

    fun mainactivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun overviewactivity(view: View) {
        startActivity(Intent(this, OverviewActivity::class.java))
    }

    fun locationactivity(view: View) {
        startActivity(Intent(this, LocationActivity::class.java))
    }

    fun helpactivity(view: View) {
        startActivity(Intent(this, HelpActivity::class.java))
    }

    fun beloningactivity(view: View) {
        startActivity(Intent(this, BeloningActivity::class.java))
    }

    // counter Holle Bolle Gijs //
    private lateinit var imageViewStamboom: AppCompatImageView
    private lateinit var textViewUnsolved: TextView
    private lateinit var textViewSolved: TextView
    private lateinit var imagebuttonUnsolved: ImageButton
    private lateinit var imagebuttonSolved: ImageButton
    private var currentValue = 0
    private var totalValue = 10
    private val imageIds = intArrayOf(
        R.drawable.stamboomgijs0,
        R.drawable.stamboomgijs1,
        R.drawable.stamboomgijs2,
        R.drawable.stamboomgijs3,
        R.drawable.stamboomgijs4,
        R.drawable.stamboomgijs5,
        R.drawable.stamboomgijs6,
        R.drawable.stamboomgijs7,
        R.drawable.stamboomgijs8,
        R.drawable.stamboomgijs9,
        R.drawable.stamboomgijs10
    )

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamboom)
        imageViewStamboom = findViewById(R.id.imageviewstamboom)
        textViewUnsolved = findViewById(R.id.counterhbgunsolved)
        textViewSolved = findViewById(R.id.counterhbgsolved)
        imagebuttonUnsolved = findViewById(R.id.unsolvedimagebutton)
        imagebuttonSolved = findViewById(R.id.solvedimagebutton)
        imagebuttonSolved.visibility = View.GONE
        textViewSolved.visibility = View.GONE
        updateText(currentValue, totalValue)
        handler.postDelayed(timerRunnable, 1000)
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (currentValue < totalValue) {
                if (currentValue == 0) {
                    handler.postDelayed({
                        currentValue++
                        updateText(currentValue, totalValue)
                        handler.postDelayed(this, 1000)
                    }, 5000)
                } else {
                    currentValue++
                    updateText(currentValue, totalValue)
                    handler.postDelayed(this, 1000)
                }
            } else {
                imagebuttonUnsolved.visibility = View.GONE
                imagebuttonSolved.visibility = View.VISIBLE
                textViewUnsolved.visibility = View.GONE
                textViewSolved.visibility = View.VISIBLE
            }
        }
    }

    private fun updateText(current: Int, total: Int) {
        val counterText = getString(R.string.counter_text, current, total)
        textViewUnsolved.text = counterText

        if (current in imageIds.indices) {
            val imageId = imageIds[current]
            imageViewStamboom.setImageResource(imageId)
        }
    }
}
