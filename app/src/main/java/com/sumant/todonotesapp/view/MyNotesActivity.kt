package com.sumant.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sumant.todonotesapp.NotesApp
import com.sumant.todonotesapp.utils.AppConstants
import com.sumant.todonotesapp.utils.PrefConstant
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.adapter.NotesAdapter
import com.sumant.todonotesapp.clickListener.ItemClickListener
import com.sumant.todonotesapp.db.Notes
import com.sumant.todonotesapp.workManager.MyWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {
    private lateinit var fullNameTv: TextView
    private lateinit var floatingActionButton: FloatingActionButton
    var sharedPreferences: SharedPreferences? = null
    var fullName: String? = null
    private lateinit var notesRv: RecyclerView
    var notesList = ArrayList<Notes>()

    private val ADD_NOTES_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindViews()
        setupSharedPref()
        intentData
        getDatafromDB()
        clickListeners()
        setupRecyclerView()
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun clickListeners() {
        floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddNotesActivity::class.java)
            startActivityForResult(intent, ADD_NOTES_CODE)
        }
    }

    private val intentData: Unit
        private get() {
            fullName = intent.getStringExtra(AppConstants.Full_Name)
            if (TextUtils.isEmpty(fullName)) {
                fullName = sharedPreferences!!.getString(PrefConstant.FULL_NAME, "")
            }
            fullNameTv.text = fullName
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
                val notes = Notes(title = title, description = description)
                notesList.add(notes)
                notesRv.adapter?.notifyItemChanged( notesList.size - 1)
                addNotesToDB(notes)

            } else Toast.makeText(this@MyNotesActivity, "Title and Description can't be empty", Toast.LENGTH_SHORT).show()
            alertDialog.hide()

        }
    }

    private fun getDatafromDB() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDB().notesDao()
        notesList.addAll( notesDao.getAllNotes() )
    }

    private fun addNotesToDB(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDB().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener: ItemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes?) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstants.TITLE, notes!!.title)
                intent.putExtra(AppConstants.DESCRIPTION, notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDB().notesDao()
                notesDao.insert(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        notesRv!!.layoutManager = linearLayoutManager
        notesRv!!.adapter = notesAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_NOTES_CODE){
            val title = data?.getStringExtra(AppConstants.TITLE)
            val description = data?.getStringExtra(AppConstants.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstants.IMAGE_PATH)

//            val note = Notes(title = title!!, desp = descp!!, imagePath = imagePath!!)
            val notes = Notes(title = title!!, description =  description!!, imagePath = imagePath!!)
            addNotesToDB(notes)
            notesList.add(notes)
            notesRv.adapter?.notifyItemChanged( notesList.size - 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.blogs){
            val intent = Intent(this, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}