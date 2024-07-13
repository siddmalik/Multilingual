package com.example.multilingual

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.multilingual.R.layout.itemstyle
import com.example.multilingual.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var inptxt:EditText
    lateinit var chngbtn:Button
    lateinit var srchlang:SearchView
    lateinit var listlang:ListView
    lateinit var langlist:ArrayList<langformat>
    lateinit var langitems:ArrayList<Langitems>
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initall()
        loadalllang()


    }

    fun initall(){
        inptxt=findViewById(R.id.inptxt)
        chngbtn=findViewById(R.id.chngbtn)
        srchlang=findViewById(R.id.srchlang)
        listlang=findViewById(R.id.listlang)
    }
    fun loadalllang(){
        langlist=ArrayList()
        val langcodels= com.google.mlkit.nl.translate.TranslateLanguage.getAllLanguages()
        for(code in langcodels){
            val langtitle= Locale(code).displayLanguage

            val langfor=langformat(code,langtitle)
            langlist.add(langfor)
            langitems= ArrayList()
            for (i in langlist.indices){
                var langi=Langitems(langlist[i].langtitle)
                langitems.add(langi)
            }
            binding.listlang.adapter=Adapter(this,langitems)
        }
    }
}