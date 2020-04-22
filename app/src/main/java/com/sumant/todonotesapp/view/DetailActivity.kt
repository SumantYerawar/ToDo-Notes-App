package com.sumant.todonotesapp.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sumant.todonotesapp.utils.AppConstants
import com.sumant.todonotesapp.R

class DetailActivity : AppCompatActivity() {
    var titleTextView: TextView? = null
    var descriptionTextView: TextView? = null
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
        }

    private fun bindViews() {
        titleTextView = findViewById(R.id.titleDetailTextView)
        descriptionTextView = findViewById(R.id.descriptionDetailTextView)
    }
}