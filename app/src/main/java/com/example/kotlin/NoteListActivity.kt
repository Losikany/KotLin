package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            val activityIntent= Intent(this, MainActivity::class.java)

            startActivity(activityIntent)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
        listNotes.adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, DataManager.notes)
        listNotes.setOnItemClickListener{parent, view, position, id ->

            val activityIntent=Intent(this, MainActivity::class.java)
            activityIntent.putExtra(NOTE_POSITION,position)
            startActivity(activityIntent)
        }


        
    }
//adding and saving a new note
    override fun onResume() {
        super.onResume()
        (listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()

    }
}