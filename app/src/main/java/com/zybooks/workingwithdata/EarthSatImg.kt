package com.zybooks.workingwithdata

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zybooks.workingwithdata.NasaAPI.ImageData

class EarthSatImg : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var imageCustomAdapter: ImageCustomAdapter
    lateinit var imageDataSet: ArrayList<ImageData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_earth_sat_img)

        imageDataSet = arrayListOf(
            ImageData("https://i.ytimg.com/vi/n4IhCSMkADc/mqdefault.jpg", "Close up image of Earth with the moon in the background"),
            ImageData("https://images.immediate.co.uk/production/volatile/sites/7/2018/02/Earth-from-space-1-64e9a7c.jpg?quality=90&resize=980,654", "Image of clouds on Earth"),
            ImageData("https://images6.alphacoders.com/131/1310467.jpeg", "Lights on Earth"),
            ImageData("https://t4.ftcdn.net/jpg/08/35/68/87/360_F_835688759_4NbnzolDGjDw5a1ZChNIWdJ8tvCQnkFt.jpg", "Blue Earth with clouds"),
            ImageData("https://www.thoughtco.com/thmb/OF-px_ExkttyyADHk-j4smdq2m8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AboveDenmark-58b849025f9b5880809d344a.jpg", "Earth Landmass"))
        imageCustomAdapter = ImageCustomAdapter(imageDataSet)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = imageCustomAdapter


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}