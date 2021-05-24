package com.example.myapplicationhw3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplicationhw3.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val SubmitBtn = findViewById<Button>(R.id.button)
        SubmitBtn.setOnClickListener {
            Toast.makeText(this,"Sumbitted", Toast.LENGTH_SHORT).show()

        }
        val HomeBtn = findViewById<Button>(R.id.button2)
        HomeBtn.setOnClickListener {
            Toast.makeText(this,"Returning Home", Toast.LENGTH_SHORT).show()

        }
        val CameraBtn = findViewById<Button>(R.id.button3)
        CameraBtn.setOnClickListener {
            Toast.makeText(this,"Getting Camera", Toast.LENGTH_SHORT).show()

        }


        val ZListBtn = findViewById<Button>(R.id.button4)
        ZListBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, ZombieMovies::class.java))

        }

        val MapBtn = findViewById<Button>(R.id.button5)
        MapBtn.setOnClickListener {
            Toast.makeText(this,"Maps Loading!", Toast.LENGTH_SHORT).show()

        }


    }
}


