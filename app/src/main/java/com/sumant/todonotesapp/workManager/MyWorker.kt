package com.sumant.todonotesapp.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sumant.todonotesapp.NotesApp

class MyWorker(val context: Context, val params: WorkerParameters): Worker(context, params) {
    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDB().notesDao()
        notesDao.deleteNotes(true)
        return Result.success()
    }
}