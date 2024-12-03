package com.zybooks.workingwithdata

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.zybooks.workingwithdata.NasaAPI.ImageData

class EarthSatImg : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var imageCustomAdapter: ImageCustomAdapter
    lateinit var imageDataSet: ArrayList<ImageData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_earth_sat_img)

        val searchButton: Button = findViewById(R.id.search_img_button)
        searchButton.setOnClickListener{
            searchAPOD()
        }

        imageDataSet = arrayListOf(
            ImageData("https://i.ytimg.com/vi/n4IhCSMkADc/mqdefault.jpg", "Close up image of Earth with the moon in the background"),
            ImageData("https://images.immediate.co.uk/production/volatile/sites/7/2018/02/Earth-from-space-1-64e9a7c.jpg?quality=90&resize=980,654", "Image of clouds on Earth"),
            ImageData("https://images6.alphacoders.com/131/1310467.jpeg", "Lights on Earth"),
            ImageData("https://t4.ftcdn.net/jpg/08/35/68/87/360_F_835688759_4NbnzolDGjDw5a1ZChNIWdJ8tvCQnkFt.jpg", "Blue Earth with clouds"),
            ImageData("https://www.thoughtco.com/thmb/OF-px_ExkttyyADHk-j4smdq2m8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AboveDenmark-58b849025f9b5880809d344a.jpg", "Earth Landmass"))
        imageCustomAdapter = ImageCustomAdapter(imageDataSet)

        recyclerView = findViewById(R.id.earth_sat_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = imageCustomAdapter


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun searchAPOD() {
        var lon = "100.75"
        var lat = "1.5"
        var date = "2014-02-01"

        var lonText: EditText = findViewById(R.id.lon_edit_text)
        var latText: EditText = findViewById(R.id.lat_edit_text)

        val base_url = "https://api.nasa.gov/planetary/earth/imagery"

        if (lonText.text != null){
            var temp = lonText.text.toString()
            try {
                if (temp.toDouble() >= -180 && temp.toDouble() <= 180) {
                    lon = lonText.text.toString()
                } else {
                    Toast.makeText(this, "Invalid Range", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid Longitude", Toast.LENGTH_SHORT).show()
            }
        }

        if (latText.text != null){
            var temp = latText.text.toString()
            try {
                if (temp.toDouble() >= -90 && temp.toDouble() <= 90) {
                    lat = latText.text.toString()
                } else {
                    Toast.makeText(this, "Invalid Range", Toast.LENGTH_SHORT).show()
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid Latitude", Toast.LENGTH_SHORT).show()
            }
        }

        if (findViewById<EditText>(R.id.sat_img_date).text != null){
            date = findViewById<EditText>(R.id.sat_img_date).text.toString()
        }

        var url = base_url +
                "?lon=${lon}&lat=${lat}&date=${date}&api_key=${BuildConfig.NASA_API_KEY}"

        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)

        val imageRequest = com.android.volley.toolbox.ImageRequest(
            url,
            { bitmap ->
                val imageData = ImageData(url, "Satellite Image")
                imageDataSet.add(imageData)
                imageCustomAdapter.notifyDataSetChanged()

                Log.d(TAG, "Image downloaded and added to dataset.")
            },
            0, 0, null, null,
            { error ->
                Log.e(TAG, "Error: ${error.message}")
                Toast.makeText(this, "Failed to retrieve image. Check inputs or API key.", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(imageRequest)
    }
}