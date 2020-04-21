package com.sumant.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.clickListener.ItemClickListener
import com.sumant.todonotesapp.model.Notes

class NotesAdapter(private val notesList: List<Notes>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = notesList[position]
        holder.titleTV.text = notes.title
        holder.descriptionTV.text = notes.description
        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView
        var descriptionTV: TextView

        init {
            titleTV = itemView.findViewById(R.id.titleTextView)
            descriptionTV = itemView.findViewById(R.id.descriptionTextView)
        }
    }

}