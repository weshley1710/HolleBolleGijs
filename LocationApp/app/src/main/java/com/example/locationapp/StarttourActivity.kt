package com.example.locationapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity

class StarttourActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starttour) // Zorg ervoor dat de juiste layout wordt ingesteld
    }
        fun overviewactivity(view: View) {
            startActivity(Intent(this, OverviewActivity::class.java))
        }

//        val popUpBtn = findViewById<Button>(R.id.btn)
//
//        popUpBtn.setOnClickListener{ view ->
//
//            val popUpMenu = PopupMenu(this@StarttourActivity, view)
//            popUpMenu.inflate(R.menu.popup_menu_item)
//
//            popUpMenu.setOnMenuItemClickListener { menuItem ->
//                when (menuItem.itemId) {
//                    R.id.menu_item_1 -> {
//                        // Voer hier de actie uit voor het eerste menu-item
//                        true
//                    }
//                    R.id.menu_item_2 -> {
//                        // Voer hier de actie uit voor het tweede menu-item
//                        true
//                    }
//                    else -> false
//                }
//            }
//
//            popUpMenu.show()
//        }

    }

