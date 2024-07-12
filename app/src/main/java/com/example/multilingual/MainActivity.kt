package com.example.multilingual

import android.os.Bundle
import android.widget.Adapter
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
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var inptxt:EditText
    lateinit var chngbtn:Button
    lateinit var srchlang:SearchView
    lateinit var listlang:ListView
    lateinit var inpTxt:String
    lateinit var langlist:ArrayList<langformat>
    lateinit var langitems:ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initall()
        loadalllang()
        srchlang.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        inpTxt=inptxt.text.toString()
        chngbtn.setOnClickListener(){
            if (inpTxt.isEmpty()) {
                Toast.makeText(this,"Please enter text", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }

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
                langitems.add(langlist[i].langtitle)
            }
            adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,langitems)
            listlang.adapter=adapter
        }
    }
}