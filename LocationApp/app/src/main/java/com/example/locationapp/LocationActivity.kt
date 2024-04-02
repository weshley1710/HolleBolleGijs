package com.example.locationapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.location.GpsStatus
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
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
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URISyntaxException
import io.socket.client.IO
import io.socket.client.Socket
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.concurrent.timer
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocationActivity: AppCompatActivity(), MapListener, GpsStatus.Listener  {

    fun stamboomactivity(view: View) {
        startActivity(Intent(this, StamboomActivity::class.java))
    }

    private lateinit var mMap: MapView
    private lateinit var controller: IMapController
    private lateinit var mMyLocationOverlay: MyLocationNewOverlay
    private lateinit var mSocket: Socket  // Use lateinit to avoid potential null issues

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Load Osmdroid configuration
        Configuration.getInstance().load(this.applicationContext, getSharedPreferences(getString(R.string.app_name),
            MODE_PRIVATE
        ))

        // Obtain MapView reference
        mMap = findViewById(R.id.osmmap)

        // Set map tile source
        mMap.setTileSource(TileSourceFactory.MAPNIK)

        // Custom marker
        val gson = Gson()
        val locations:Array<Location> = gson.fromJson(ReadJSONFromAssets(baseContext, "locations.json"),
            Array<Location>::class.java)

        for (location in locations) {
            var geoPoint = GeoPoint(location.latitude, location.longitude)
            var marker = Marker(mMap)
            marker.position = geoPoint

            val fileName = location.data
            val iconResId = resources.getIdentifier(fileName, "drawable", packageName)

            try {
                val drawable = ContextCompat.getDrawable(this, iconResId)
                if (drawable != null) {
                    marker.icon = drawable
                } else {
                    Log.e("TAG", "Drawable resource is null for file: $fileName")
                    marker.icon = ContextCompat.getDrawable(this, R.drawable.marker_icon)
                }
            } catch (e: Resources.NotFoundException) {
                Log.e("TAG", "Resource not found for file: $fileName", e)
                marker.icon = ContextCompat.getDrawable(this, R.drawable.marker_icon)
            }

            marker.title = location.naam
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            mMap.overlays.add(marker)
        }

        // Center map on specific position
        centerMapOnPosition(51.6506518, 5.0497462, 17.0)

        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller

        // Enable location overlay and set properties
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true

        // Center map on user location when the first fix is received
        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                val userLocation = mMyLocationOverlay.myLocation
                if (userLocation != null) {
                    val userGeoPoint = GeoPoint(userLocation.latitude, userLocation.longitude)
                    println("Latitude: ${userLocation.latitude}, Longitude: ${userLocation.longitude}")
                    controller.setCenter(userGeoPoint)
                    controller.animateTo(userGeoPoint)
                } else {
                    // Handle the case where user location is null
                    println("User location is null")
                }
            }
        }

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
//                val userLocation = GeoPoint(51.647609, 5.049018)
                val userLocation = mMyLocationOverlay.myLocation
                if (userLocation != null) {
                    checkLocation(userLocation)
                }
                mainHandler.postDelayed(this, 5000)
            }
        })


        // Add location overlay to map
        mMap.overlays.add(mMyLocationOverlay)
    }

    private fun checkLocation(userLocation: GeoPoint) {
        val Username = "Ellie"
        val gson = Gson()
        val locations:Array<Location> = gson.fromJson(ReadJSONFromAssets(baseContext, "locations.json"),
            Array<Location>::class.java)

        if (userLocation != null) {
            val gijsRadius = 20

            for (location in locations) {
                val markerLocation = GeoPoint(location.latitude, location.longitude)
                val distance = calculateDistance(userLocation, markerLocation)
                println("${location.naam}: $distance meters")
                if(distance < gijsRadius) {
                    sendData(Username, location.naam)
                    val text = "Gefelicteerd je hebt ${location.naam} gevonden!"
                    val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        } else {
            // Handle the case where user location is null
            println("User location is null")
            val toast = Toast.makeText(this, "Er is iets fout gegaan.", Toast.LENGTH_SHORT)
            toast.show()
        }

    }
    private fun sendData(name: String, gijs: String) {
        try {
            mSocket = IO.socket("https://9c4793a3-aa33-4e20-b262-a577c59ab5ed-00-1hbimrom5u2be.worf.replit.dev/")
            mSocket.connect()

            // Send a name to the server
            mSocket.emit("message", name, gijs)
        } catch (e: URISyntaxException) {
            e.printStackTrace() // Handle the exception (e.g., display an error message)
        }
    }
    private fun calculateDistance(location1: GeoPoint?, location2: GeoPoint): Double {
        if (location1 == null) return Double.MAX_VALUE

        val lat1 = location1.latitude * PI / 180
        val lon1 = location1.longitude * PI / 180
        val lat2 = location2.latitude * PI / 180
        val lon2 = location2.longitude * PI / 180

        val dlon = lon2 - lon1
        val dlat = lat2 - lat1

        val a = sin(dlat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dlon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        val earthRadius = 6371000 // Radius of the Earth in meters
        return earthRadius * c
    }
    fun centerMapOnPosition(latitude: Double, longitude: Double, zoomLevel: Double) {
        // Retrieve map controller
        val mapController = mMap.controller

        // Create GeoPoint for map center
        val mapCenter = GeoPoint(latitude, longitude)

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

    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
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