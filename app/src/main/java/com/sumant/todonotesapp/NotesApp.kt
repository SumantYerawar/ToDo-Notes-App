package com.sumant.todonotesapp

import android.app.Application
import com.sumant.todonotesapp.db.NotesDatabase

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun getNotesDB(): NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}