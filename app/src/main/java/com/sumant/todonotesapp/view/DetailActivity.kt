package com.sumant.todonotesapp.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sumant.todonotesapp.utils.AppConstants
import com.sumant.todonotesapp.R

class DetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        intentData
    }

    private val intentData: Unit
        private get() {
            titleTextView!!.text = intent.getStringExtra(AppConstants.TITLE)
            descriptionTextView!!.text = intent.getStringExtra(AppConstants.DESCRIPTION)

            val imagePath = intent.getStringExtra(AppConstants.IMAGE_PATH)
            if (!imagePath.isNullOrBlank()) {
                Glide.with(this).load(imagePath).into(imageView)
            }
        }

    private fun bindViews() {
        titleTextView = findViewById(R.id.titleDetailTextView)
        descriptionTextView = findViewById(R.id.descriptionDetailTextView)
        imageView = findViewById(R.id.imageViewDetail)
    }
}