package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class EditActivity : AppCompatActivity() {
    private lateinit var data : Intent
    private lateinit var medittitleofnote : EditText
    private lateinit var meditcontentofnote : EditText
    private lateinit var msaveeditnote : FloatingActionButton
    private lateinit var db: FirebaseFirestore
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

        db = FirebaseFirestore.getInstance()

        msaveeditnote.setOnClickListener {
            val newtitle = medittitleofnote.text.toString()
            val newcontent = meditcontentofnote.text.toString()

            if (newtitle.isEmpty() || newcontent.isEmpty()) {
                Toast.makeText(applicationContext, "Something is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val docId = data.getStringExtra("noteId")!!
                val documentReference = db.collection("notes")
                    .document("notes")
                    .collection("notes")
                    .document(docId)

                val note = hashMapOf(
                    "title" to newtitle,
                    "content" to newcontent
                )

                documentReference.set(note)
                documentReference.get()
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Note is Updated", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(applicationContext, "Failed to Update Note", Toast.LENGTH_SHORT).show()
                    }

            }
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