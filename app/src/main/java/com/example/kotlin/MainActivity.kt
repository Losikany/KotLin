package com.example.kotlin

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    //if a fab is clicked to create a new note
    private var noteposition = POSITION_NOT_SET
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val adapterCourses = ArrayAdapter<CourseInfo>(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCourses.adapter = adapterCourses
//Retrieving the position of the note if the activity is destroyed and recreatead
        noteposition = savedInstanceState?.getInt(NOTE_POSITION , POSITION_NOT_SET)?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (noteposition != POSITION_NOT_SET) {

            displayNote()
        }
        //Creating a new Note
        else
        {
         DataManager.notes.add(NoteInfo())
            //displaying the note below the already created notes
            noteposition=DataManager.notes.lastIndex

        }


    }
//Displaying the already selected item on the screen even when the screen orientation changes
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NOTE_POSITION, noteposition)
        super.onSaveInstanceState(outState)
    }

    //display a course selected from the list
    private fun displayNote() {
        val note = DataManager.notes[noteposition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)
//to populate course selected in a spinner
        val courseposition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(courseposition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Move to the next note once next is clicked
    private fun moveNext() {
        ++noteposition
        displayNote()
        invalidateOptionsMenu()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (noteposition >= DataManager.notes.lastIndex) {
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.block)
                menuItem.isEnabled=false
                Toast.makeText(this, "End",Toast.LENGTH_LONG)


            }


        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note=DataManager.notes[noteposition]

        note.title=textNoteTitle.text.toString()
        note.text=textNoteText.text.toString()

        note.course= spinnerCourses.selectedItem as CourseInfo


    }
}