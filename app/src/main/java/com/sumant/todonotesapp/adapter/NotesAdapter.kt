package com.sumant.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.clickListener.ItemClickListener
import com.sumant.todonotesapp.db.Notes
import java.util.*

class NotesAdapter(private val notesList: List<Notes>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = notesList[position]
        holder.titleTV.text = notes.title
        holder.descriptionTV.text = notes.description
        holder.checkBoxCompleteStatus.isChecked = notes.isTaskCompleted

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                itemClickListener.onClick(notes)
            }

        })

        holder.checkBoxCompleteStatus.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted = isChecked
                itemClickListener.onUpdate(notes)
            }

        })
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTV: TextView = itemView.findViewById(R.id.descriptionTextView)
        val checkBoxCompleteStatus : CheckBox = itemView.findViewById(R.id.completeStatus)

    }

}