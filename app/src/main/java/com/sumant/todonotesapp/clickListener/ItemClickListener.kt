package com.sumant.todonotesapp.clickListener

import com.sumant.todonotesapp.model.Notes

interface ItemClickListener {
    fun onClick(notes: Notes?)
}