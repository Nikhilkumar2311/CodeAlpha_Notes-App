package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.CreateActivity
import com.example.notesapp.Firebasemodel
import com.example.notesapp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.integrity.v
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Random

class MainActivity : AppCompatActivity() {
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var mCreateNotesFab: FloatingActionButton
    private lateinit var mrecyclerview: RecyclerView
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.title = "All Notes"

        mCreateNotesFab = findViewById(R.id.create)

        firestore = FirebaseFirestore.getInstance()

        mCreateNotesFab.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
        mrecyclerview = findViewById(R.id.recyclerview)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mrecyclerview.layoutManager = linearLayoutManager

        val query = firestore
            .collection("notes")
            .orderBy("title", Query.Direction.ASCENDING)


        val allNotes = FirestoreRecyclerOptions.Builder<Firebasemodel>()
            .setQuery(query, Firebasemodel::class.java)
            .build()

        noteAdapter = NoteAdapter(allNotes, this)

        mrecyclerview.adapter = noteAdapter


        mrecyclerview.setHasFixedSize(true)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mrecyclerview.layoutManager = staggeredGridLayoutManager
        mrecyclerview.adapter = noteAdapter
    }

    override fun onStart() {
        super.onStart()
        noteAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        noteAdapter.stopListening()
    }

}

class NoteAdapter(options: FirestoreRecyclerOptions<Firebasemodel>, private val context: Context) :
    FirestoreRecyclerAdapter<Firebasemodel, NoteAdapter.NoteViewHolder>(options) {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notetitle: TextView = itemView.findViewById(R.id.notetitle)
        val notecontent: TextView = itemView.findViewById(R.id.notecontent)
        val mNote: LinearLayout = itemView.findViewById(R.id.note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, model: Firebasemodel) {

        val colorCode = getRandomColor()
        holder.mNote.setBackgroundColor(holder.itemView.resources.getColor(colorCode, null))

        val popupButton: ImageView = holder.itemView.findViewById(R.id.menupopbutton)


        holder.notetitle.text = model.title
        holder.notecontent.text = model.content

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NotesActivity::class.java)
            context.startActivity(intent)
            //Toast.makeText(it.context, "This is Clicked", Toast.LENGTH_SHORT).show()
        }

        popupButton.setOnClickListener { v ->
            val popupMenu = PopupMenu(v.context, v)
            popupMenu.gravity = Gravity.END

            popupMenu.menu.add("Edit").setOnMenuItemClickListener {
                val intent = Intent(v.context, EditActivity::class.java)
                v.context.startActivity(intent)
                false
            }

            popupMenu.menu.add("Delete").setOnMenuItemClickListener {
                Toast.makeText(v.context, "This note is deleted", Toast.LENGTH_SHORT).show()
                false
            }

            popupMenu.show()
        }


    }

    private fun getRandomColor(): Int {
        val colorCodes = mutableListOf<Int>()
        colorCodes.add(R.color.gray)
        colorCodes.add(R.color.yellow)
        colorCodes.add(R.color.lightgreen)
        colorCodes.add(R.color.pink)
        colorCodes.add(R.color.lightpurple)
        colorCodes.add(R.color.skyblue)
        colorCodes.add(R.color.red)
        colorCodes.add(R.color.blue)
        colorCodes.add(R.color.greenlight)
        colorCodes.add(R.color.notgreen)

        val random = Random()
        val number = random.nextInt(colorCodes.size)
        return colorCodes[number]

    }
}
