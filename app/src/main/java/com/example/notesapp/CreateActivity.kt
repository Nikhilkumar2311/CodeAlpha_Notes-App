package com.example.notesapp


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore


class CreateActivity : AppCompatActivity() {
    private lateinit var mCreateTitleOfNote: EditText
    private lateinit var mCreateContentOfNote: EditText
   private lateinit var mSaveNote: FloatingActionButton
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        firebaseFirestore = FirebaseFirestore.getInstance()

        mSaveNote = findViewById<FloatingActionButton>(R.id.savenote)
        mSaveNote.setOnClickListener {
            val note = hashMapOf(
                "title" to "My Title",
                "content" to "My Content"
            )

        mCreateContentOfNote = findViewById(R.id.createcontentofnote)
        mCreateTitleOfNote = findViewById(R.id.createtitleofnote)

        toolbar = findViewById(R.id.toolbarofcreatenote)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mSaveNote.setOnClickListener {

            val title = mCreateTitleOfNote.text.toString()
            val content = mCreateContentOfNote.text.toString()

            val note = hashMapOf(
                "title" to title,
                "content" to content
            )

            firebaseFirestore.collection("notes")
                .add(note)
                .addOnSuccessListener {
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add note", Toast.LENGTH_SHORT).show()
                }
        }
    }


}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}