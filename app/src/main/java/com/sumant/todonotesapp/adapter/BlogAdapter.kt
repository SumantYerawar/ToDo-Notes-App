package com.sumant.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.model.Data

class BlogAdapter(private val list: List<Data>): RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        Glide.with(holder.itemView).load(item.img_url).into(holder.imageView)
        holder.titleTxt.text = item.title
        holder.descpTxt.text = item.description
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView = itemView.findViewById<ImageView>(R.id.imageViewBlog)
        val titleTxt = itemView.findViewById<TextView>(R.id.titleTextViewBlog)
        val descpTxt = itemView.findViewById<TextView>(R.id.descriptionTextViewBlog)


    }

}