package com.sumant.todonotesapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.sumant.todonotesapp.db.NotesDatabase

class NotesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

    fun getNotesDB(): NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}