package com.sumant.todonotesapp.onBoarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.utils.AppConstants
import com.sumant.todonotesapp.utils.PrefConstant
import com.sumant.todonotesapp.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick, OnBoardingTwoFragment.OnOptionsClick {

    lateinit var viewPager : ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        bindViews()
        setupSharedPrefs()
    }

    private fun setupSharedPrefs() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    }

    private fun bindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionDone() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,true)
        editor.commit()

        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }
}
