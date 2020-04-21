package com.sumant.todonotesapp.db

import androidx.room.*

@Dao
interface NotesDao{
    @Query("SELECT * FROM notesData")
    fun getAllNotes(): List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status: Boolean)

}