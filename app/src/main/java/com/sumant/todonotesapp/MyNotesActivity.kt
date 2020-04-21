package com.sumant.todonotesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sumant.todonotesapp.adapter.NotesAdapter
import com.sumant.todonotesapp.clickListener.ItemClickListener
import com.sumant.todonotesapp.model.Notes
import java.util.*

class MyNotesActivity : AppCompatActivity() {
    private lateinit var fullNameTv: TextView
    private lateinit var floatingActionButton: FloatingActionButton
    var sharedPreferences: SharedPreferences? = null
    var fullName: String? = null
    private lateinit var notesRv: RecyclerView
    var notesList = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindViews()
        setupSharedPref()
        intentData
        fullNameTv.text = fullName
        floatingActionButton.setOnClickListener { setupDialogBox() }
    }

    private val intentData: Unit
        private get() {
            fullName = intent.getStringExtra(AppConstrain.Full_Name)
            if (TextUtils.isEmpty(fullName)) {
                fullName = sharedPreferences!!.getString(PrefConstant.FULL_NAME, "")
            }
        }

    private fun bindViews() {
        fullNameTv = findViewById(R.id.fullNameTV)
        floatingActionButton = findViewById(R.id.fab)
        notesRv = findViewById(R.id.notesRV)
    }

    private fun setupSharedPref() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes, null)
        val titleEditText = view.findViewById<EditText>(R.id.titleAddNoteEditText)
        val descriptionEditText = view.findViewById<EditText>(R.id.descriptionAddNoteEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val alertDialog = AlertDialog.Builder(this)
                .setView(view)
                .create()
        alertDialog.show()
        submitButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                val notes = Notes(title, description)
                notesList.add(notes)
                setupRecyclerView()
            } else Toast.makeText(this@MyNotesActivity, "Title and Description can't be empty", Toast.LENGTH_SHORT).show()
            alertDialog.hide()
        }
    }

    private fun setupRecyclerView() {
        val itemClickListener: ItemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes?) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstrain.TITLE, notes!!.title)
                intent.putExtra(AppConstrain.DESCRIPTION, notes.description)
                startActivity(intent)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        notesRv!!.layoutManager = linearLayoutManager
        notesRv!!.adapter = notesAdapter
    }
}