package com.example.locationapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class StarttourActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starttour)

        val startTourButton = findViewById<ImageButton>(R.id.starttourimagebutton)
        val overlay = findViewById<View>(R.id.overlay)

        startTourButton.setOnClickListener {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.input_name_layout, null)

            val btnAddEditText = popupView.findViewById<ImageButton>(R.id.imageButton4)

            val closeButton = popupView.findViewById<ImageButton>(R.id.btnOk)

            val popupWindow = PopupWindow(
                popupView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            overlay.visibility = View.VISIBLE

            btnAddEditText.setOnClickListener {
                val newEditText = EditText(this@StarttourActivity)
                newEditText.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                newEditText.background = ContextCompat.getDrawable(this@StarttourActivity, R.drawable.edittext_border)
                newEditText.hint = getString(R.string.vulnaamin)

                // Zoek de edittextname2 binnen de LinearLayout
                val linearLayout = popupView.findViewById<LinearLayout>(R.id.linearLayout)
                val existingEditText = linearLayout.findViewById<EditText>(R.id.edittextname2)

                // Kopieer de layout eigenschappen van de bestaande EditText
                newEditText.layoutParams = existingEditText.layoutParams
                newEditText.background = existingEditText.background
                newEditText.hint = existingEditText.hint

                // Zoek de index van button4
                val button4Index = linearLayout.indexOfChild(btnAddEditText)

                // Voeg de nieuwe EditText toe vóór button4
                linearLayout.addView(newEditText, button4Index)
            }



            popupWindow.isFocusable = true
            popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0)

            popupWindow.setOnDismissListener {
                overlay.visibility = View.GONE
            }

            popupWindow.setOnDismissListener {
                val intent = Intent(this@StarttourActivity, OverviewActivity::class.java)
                startActivity(intent)
            }

            closeButton.setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }
}

