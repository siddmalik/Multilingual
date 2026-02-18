package com.example.multilingual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var inptxt: EditText
    lateinit var chngbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initall()

        chngbtn.setOnClickListener {
            val inpTxt = inptxt.text.toString()
            if (inpTxt.isEmpty()) {
                Toast.makeText(this, "Please enter text", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intent = Intent(this, MainActivity2::class.java).apply {
                putExtra("inptxt", inpTxt)
            }
            startActivity(intent)
        }
    }

    private fun initall() {
        inptxt = findViewById(R.id.inptxt)
        chngbtn = findViewById(R.id.chngbtn)
    }
}
