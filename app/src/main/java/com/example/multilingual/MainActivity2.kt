package com.example.multilingual

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.core.graphics.toColorInt
import com.example.multilingual.databinding.ActivityMain2Binding
import com.example.multilingual.databinding.ActivityMainBinding
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import org.intellij.lang.annotations.Language
import java.util.Locale

class MainActivity2 : AppCompatActivity() {
    var inptxt: String = ""
    lateinit var srclangcode:String
    lateinit var langbtn:Button
    var clickedPosition:Int=-1
    var loc:Int=-1
    lateinit var phrase: EditText
    lateinit var srchlang: SearchView
    lateinit var listlang: ListView
    lateinit var langlist: ArrayList<langformat>
    lateinit var langitems: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>
    lateinit var selected:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        initall()
        loadalllang()
        listlang.choiceMode = ListView.CHOICE_MODE_SINGLE
        inptxt = intent.getStringExtra("inptxt").toString()
        detectlang(inptxt)
        phrase.setText(inptxt)
        srchlang.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        listlang.setOnItemClickListener(object:AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selected=adapter.getItem(position).toString()
                loc=position
                langbtn.setText("How to say in "+"${selected}")
                clickedPosition = if(clickedPosition==position) -1 else position
                adapter.notifyDataSetChanged()
            }
        })
        langbtn.setOnClickListener {
            val langsel=selected
            if(langsel.isEmpty()){
                Toast.makeText(this,"Please select a language",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intented=Intent(this,MainActivity3::class.java).apply {
                putExtra("inptxt",inptxt)
                putExtra("srclangcode",srclangcode)
                putExtra("targetlangcode",langlist[loc].langcode)
            }
            startActivity(intented)
        }
    }

    fun initall() {
        srchlang = findViewById(R.id.srchlang)
        listlang = findViewById(R.id.listlang)
        phrase = findViewById(R.id.phrase2)
        langbtn=findViewById(R.id.langbtn)
    }

    fun loadalllang() {
        langlist = ArrayList()
        val langcodels = TranslateLanguage.getAllLanguages()
        langitems = ArrayList()

        for (code in langcodels) {
            val langtitle = Locale(code).displayLanguage
            val langfor = langformat(code, langtitle)
            langlist.add(langfor)
            langitems.add(langtitle)
        }
        adapter = object : ArrayAdapter<String>(this,R.layout.itemstyle,langitems) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView

                // Change text color if this is the clicked item
                if (position == clickedPosition) {
                    view.setTextColor(Color.WHITE)
                } else {
                    view.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        listlang.adapter=adapter
    }

    fun detectlang(langtxt:String){
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(langtxt)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    Toast.makeText(this,"Cannot identify the language",Toast.LENGTH_LONG).show()
                } else {
                    srclangcode= languageCode
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"There was some error",Toast.LENGTH_LONG).show()
            }
    }

}
