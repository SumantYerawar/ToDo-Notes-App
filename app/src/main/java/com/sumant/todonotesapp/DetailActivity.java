package com.sumant.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView titleTextView , descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bindViews();
        getIntentData();
    }

    private void getIntentData() {
        titleTextView.setText( getIntent().getStringExtra(AppConstrain.TITLE) );
        descriptionTextView.setText( getIntent().getStringExtra(AppConstrain.DESCRIPTION) );
    }

    private void bindViews() {
        titleTextView = findViewById(R.id.titleDetailTextView);
        descriptionTextView = findViewById(R.id.descriptionDetailTextView);
    }
}
