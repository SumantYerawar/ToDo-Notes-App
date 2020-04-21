package com.sumant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.sumant.todonotesapp.PrefConstant.FULL_NAME;
import static com.sumant.todonotesapp.PrefConstant.IS_LOGGED_IN;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText ,fullNameEditText;
    private Button buttonLogin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();
        setupSharedPref();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = fullNameEditText.getText().toString();
                String userName = usernameEditText.getText().toString();

                if ( !TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName) ){
                    Intent intent = new Intent(getApplicationContext(),MyNotesActivity.class);
                    intent.putExtra(AppConstrain.Full_Name,fullName);
                    startActivity(intent);
                    saveLoginStatus();
                    saveFullName( fullName );
                }else {
                    Toast.makeText(LoginActivity.this, "FullName and UserName can't be empty!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void bindViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        buttonLogin = findViewById(R.id.loginButton);
    }

    private void saveFullName(String fullName) {
        editor = sharedPreferences.edit();
        editor.putString(PrefConstant.FULL_NAME,fullName);
        editor.commit();
    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(PrefConstant.IS_LOGGED_IN,true);
        editor.commit();
    }

    private void setupSharedPref() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
    }
}
