package com.example.locationapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity

class StarttourActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starttour) // Zorg ervoor dat de juiste layout wordt ingesteld

        val popUpBtn = findViewById<Button>(R.id.btnOk)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextName2 = findViewById<EditText>(R.id.edittextname2)

        popUpBtn.setOnClickListener { view ->
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.input_name_layout, null)

            val btnCancel = popupView.findViewById<Button>(R.id.button2)

            val popupWindow = PopupWindow(
                popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            popUpBtn.setOnClickListener {
                val name1 = editTextName.text.toString()
                val name2 = editTextName2.text.toString()
                // Doe iets met de ingevoerde namen

                // Voorbeeld: Start een nieuwe activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            btnCancel.setOnClickListener {
                // Sluit de popup
                popupWindow.dismiss()
            }
            popupWindow.isFocusable = true
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)
        }
    }
}

