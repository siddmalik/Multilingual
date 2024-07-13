package com.example.multilingual

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(private val context:Activity,private val arraylist:ArrayList<Langitems>):ArrayAdapter<Langitems>(context,R.layout.itemstyle,arraylist){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater:LayoutInflater=LayoutInflater.from(context)
        val view:View=inflater.inflate(R.layout.itemstyle,null)
        val listitem:TextView=view.findViewById(R.id.listitem)
        listitem.text=arraylist[position].lang

        return view
    }
}