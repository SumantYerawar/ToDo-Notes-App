package com.sumant.todonotesapp.clickListener

import com.sumant.todonotesapp.db.Notes


interface ItemClickListener {
    fun onClick(notes: Notes?)
    fun onUpdate(notes: Notes)
}