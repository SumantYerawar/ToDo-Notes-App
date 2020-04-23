package com.sumant.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
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

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var sharedPreferences: SharedPreferences
    var fullName: String? = null
    private lateinit var notesRv: RecyclerView
    var notesList = ArrayList<Notes>()

    private val ADD_NOTES_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        bindViews()
        setupSharedPref()
        getIntentData()
        getDatafromDB()
        clickListeners()
        setupRecyclerView()
        setupWorkManager()

        supportActionBar?.setTitle( fullName)
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstants.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstants.FULL_NAME)
        }
        if (fullName.isNullOrBlank()) {
            fullName = sharedPreferences?.getString(PrefConstant.FULL_NAME, "")!!
        }
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

    private fun bindViews() {
        floatingActionButton = findViewById(R.id.fab)
        notesRv = findViewById(R.id.notesRV)
    }

    private fun setupSharedPref() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
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
                intent.putExtra(AppConstants.IMAGE_PATH,notes.imagePath)
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
        }else if(item.itemId == R.id.logout){
            logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val editor: SharedPreferences.Editor
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, false)
        editor.apply()

        startActivity(Intent(this@MyNotesActivity, LoginActivity::class.java))
    }

}