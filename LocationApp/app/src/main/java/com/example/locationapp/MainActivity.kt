package com.example.locationapp

import android.content.Context
import android.content.res.Resources
import android.location.GpsStatus
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.locationapp.Models.Location
import com.google.gson.Gson
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity(), MapListener, GpsStatus.Listener {

    private lateinit var mMap: MapView
    private lateinit var controller: IMapController
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load Osmdroid configuration
        Configuration.getInstance().load(this.applicationContext, getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE))

        // Obtain MapView reference
        mMap = findViewById(R.id.osmmap)

        // Set map tile source
        mMap.setTileSource(TileSourceFactory.MAPNIK)


        val gson = Gson()
        val locations:Array<Location> = gson.fromJson(ReadJSONFromAssets(baseContext, "locations.json"),
            Array<Location>::class.java)

        for (location in locations) {
            var geoPoint = org.osmdroid.util.GeoPoint(location.latitude, location.longitude) // Replace with desired coordinates
            var marker = Marker(mMap)
            marker.position = geoPoint
            // Custom marker
            var iconResId = location.image
            if (iconResId != 0) {
                try {
                    val drawable = ContextCompat.getDrawable(this, iconResId)
                    marker.icon = drawable
                } catch (e: Resources.NotFoundException) {
                    Log.e("TAG", "Resource not found: $iconResId", e)
                    marker.icon = ContextCompat.getDrawable(this, R.drawable.marker_icon)
                }
            } else {
                marker.icon = ContextCompat.getDrawable(this, R.drawable.marker_icon)
            }

//            marker.icon = ContextCompat.getDrawable(this, R.drawable.marker_icon) // Replace with your icon resource ID
            marker.title = location.naam
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            mMap.overlays.add(marker)
        }

        // Center map on specific position
        centerMapOnPosition(51.6506518, 5.0497462, 17.0)

    }

    fun centerMapOnPosition(latitude: Double, longitude: Double, zoomLevel: Double) {
        // Retrieve map controller
        val mapController = mMap.controller

        // Create GeoPoint for map center
        val mapCenter = org.osmdroid.util.GeoPoint(latitude, longitude)

        // Set map center and zoom level
        mapController.setCenter(mapCenter)
        mapController.setZoom(zoomLevel)
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        event?.source?.mapCenter?.let {
            Log.e("TAG", "onCreate:la ${it.latitude}")
            Log.e("TAG", "onCreate:lo ${it.longitude}")
        }
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        event?.zoomLevel?.let { controller.setZoom(it) }
        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false
    }

    override fun onGpsStatusChanged(event: Int) {
    }


    fun ReadJSONFromAssets(context: Context, path: String): String {
        val identifier = "[ReadJSON]"
        try {
            val file = context.assets.open("$path")
            val bufferedReader = BufferedReader(InputStreamReader(file))
            val stringBuilder = StringBuilder()
            bufferedReader.useLines { lines ->
                lines.forEach {
                    stringBuilder.append(it)
                }
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

}

