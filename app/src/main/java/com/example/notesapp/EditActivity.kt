package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditActivity : AppCompatActivity() {
    private lateinit var data : Intent
    private lateinit var medittitleofnote : EditText
    private lateinit var meditcontentofnote : EditText
    private lateinit var msaveeditnote : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        medittitleofnote = findViewById(R.id.edittitleofnote)
        meditcontentofnote = findViewById(R.id.editcontentofnote)
        msaveeditnote = findViewById(R.id.saveeditnote)
        val toolbar: Toolbar = findViewById(R.id.toolbarofeditnote)
        data = intent
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        msaveeditnote.setOnClickListener {
            Toast.makeText(applicationContext, "savebutton click", Toast.LENGTH_SHORT).show()
        }

        val notetitle = data.getStringExtra("title")
        val notecontent = data.getStringExtra("content")
        meditcontentofnote.setText(notecontent)
        medittitleofnote.setText(notetitle)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}