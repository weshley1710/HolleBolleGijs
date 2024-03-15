package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class StamboomActivity : AppCompatActivity() {

    fun mainactivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    // counter quantity Holle Bolle Gijs //
    private lateinit var textViewUnsolved: TextView
    private lateinit var textViewSolved: TextView
    private lateinit var imagebuttonUnsolved: ImageButton
    private lateinit var imagebuttonSolved: ImageButton
    private var currentValue = 0
    private var totalValue = 10

    private val handler = Handler(Looper.getMainLooper())

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (currentValue < totalValue) {
                currentValue++
                updateText(currentValue, totalValue)
                handler.postDelayed(this, 1000)
            } else {
                imagebuttonUnsolved.visibility = View.GONE
                imagebuttonSolved.visibility = View.VISIBLE
                textViewUnsolved.visibility = View.GONE
                textViewSolved.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamboom)
        textViewUnsolved = findViewById(R.id.counterhbgunsolved)
        textViewSolved = findViewById(R.id.counterhbgsolved)
        imagebuttonUnsolved = findViewById(R.id.unsolvedimagebutton)
        imagebuttonSolved = findViewById(R.id.solvedimagebutton)
        imagebuttonSolved.visibility = View.GONE
        textViewSolved.visibility = View.GONE
        updateText(currentValue, totalValue)
        handler.postDelayed(timerRunnable, 1000)
    }

    private fun updateText(current: Int, total: Int) {
        val counterText = getString(R.string.counter_text, current, total)
        textViewUnsolved.text = counterText
        textViewSolved.text = counterText
    }
}
