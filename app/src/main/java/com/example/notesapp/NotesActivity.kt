package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesActivity : AppCompatActivity() {

    private lateinit var mtitleofnotedetail : TextView
    private lateinit var mcontentofnotedetail : TextView
    private lateinit var mgotoeditnote : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        mtitleofnotedetail = findViewById(R.id.titleofnotedetail)
        mcontentofnotedetail = findViewById(R.id.contentofnotedetail)
        mgotoeditnote = findViewById(R.id.gotoeditnote)
        val toolbar: Toolbar = findViewById(R.id.toolbarofnotedetail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent

        mgotoeditnote.setOnClickListener {
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("title", data.getStringExtra("title"))
            intent.putExtra("content", data.getStringExtra("content"))
            intent.putExtra("noteId", data.getStringExtra("noteId"))
            it.context.startActivity(intent)
        }

        mcontentofnotedetail.text = data.getStringExtra("content")
        mtitleofnotedetail.text = data.getStringExtra("title")


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}