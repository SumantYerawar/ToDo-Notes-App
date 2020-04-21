package com.sumant.todonotesapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private var usernameEditText: EditText? = null
    private var fullNameEditText: EditText? = null
    private var buttonLogin: Button? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setupSharedPref()
        buttonLogin!!.setOnClickListener {
            val fullName = fullNameEditText!!.text.toString()
            val userName = usernameEditText!!.text.toString()
            if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {
                val intent = Intent(applicationContext, MyNotesActivity::class.java)
                intent.putExtra(AppConstrain.Full_Name, fullName)
                startActivity(intent)
                saveLoginStatus()
                saveFullName(fullName)
            } else {
                Toast.makeText(this@LoginActivity, "FullName and UserName can't be empty!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindViews() {
        usernameEditText = findViewById(R.id.usernameEditText)
        fullNameEditText = findViewById(R.id.fullNameEditText)
        buttonLogin = findViewById(R.id.loginButton)
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences!!.edit()
        editor.putString(PrefConstant.FULL_NAME, fullName)
        editor.commit()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences!!.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, true)
        editor.commit()
    }

    private fun setupSharedPref() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}