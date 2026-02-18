package com.example.multilingual

import android.app.ProgressDialog
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.StringBuilderPrinter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.intl.Locale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateLanguage.*
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import org.w3c.dom.Text

class MainActivity3 : AppCompatActivity(),TextToSpeech.OnInitListener {
    private lateinit var msg:TextView
    private lateinit var msg2:TextView
    private lateinit var langtitle:String
    private lateinit var inptxt:String
    private lateinit var lang:String
    private lateinit var speak:Button
    private lateinit var srclangcode:String
    private lateinit var targetlangcode:String
    private lateinit var translated:TextView
    private lateinit var translatorOptions: TranslatorOptions
    private lateinit var translator: Translator
    private lateinit var progressDialog: ProgressDialog
    private lateinit var tts:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        initall()
        langtitle=intent.getStringExtra("srclangcode").toString()
        lang=intent.getStringExtra("targetlangcode").toString()
        inptxt=intent.getStringExtra("inptxt").toString()
        srclangcode="${langtitle}"
        targetlangcode="${lang}"
        msg.setText(inptxt)
        msg2.setText("in "+lang+" is")
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)
        translatelang()
        speak.isEnabled=false
        tts=TextToSpeech(this,this)
        speak.setOnClickListener {
            speakOut()
        }
    }

    fun initall(){
        msg=findViewById(R.id.msg)
        msg2=findViewById(R.id.msg2)
        translated=findViewById(R.id.translated)
        speak=findViewById(R.id.speak)
    }

    private fun translatelang() {
        progressDialog.setMessage("Processing language model")
        progressDialog.show()
        translatorOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.fromLanguageTag("${srclangcode}").toString())
            .setTargetLanguage(TranslateLanguage.fromLanguageTag("${targetlangcode}").toString())
            .build()
        translator=Translation.getClient(translatorOptions)
        val Dwnld=DownloadConditions.Builder().requireWifi().build()
        translator.downloadModelIfNeeded(Dwnld)
            .addOnSuccessListener {
            progressDialog.setMessage("Translating...")
            translator.translate(inptxt)
                .addOnSuccessListener { translatedText ->
                    progressDialog.dismiss()
                    translated.text=translatedText

                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this,"Failed to translate",Toast.LENGTH_LONG).show()
                }
        }

    }
    private fun speakOut() {
        val text=translated.text.toString()
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }
    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts.setLanguage(java.util.Locale.forLanguageTag(targetlangcode))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this,"The language isn't supported",Toast.LENGTH_LONG).show()
            }else{
                speak.isEnabled=true
            }
        }
    }
    public override fun onDestroy() {
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}