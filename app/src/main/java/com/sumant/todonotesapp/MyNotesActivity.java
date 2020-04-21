package com.sumant.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sumant.todonotesapp.adapter.NotesAdapter;
import com.sumant.todonotesapp.clickListener.ItemClickListener;
import com.sumant.todonotesapp.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class MyNotesActivity extends AppCompatActivity {

    TextView fullNameTv;
    FloatingActionButton floatingActionButton;
    SharedPreferences sharedPreferences;
    String fullName;

    RecyclerView notesRv;
    ArrayList<Notes> notesList = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        bindViews();
        setupSharedPref();
        getIntentData();

        fullNameTv.setText( fullName );

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDialogBox();
            }
        });
    }

    private void getIntentData() {
        fullName = getIntent().getStringExtra(AppConstrain.Full_Name);
        if ( TextUtils.isEmpty(fullName) ){
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME,"");
        }
    }

    private void bindViews() {
        fullNameTv = findViewById(R.id.fullNameTV);
        floatingActionButton = findViewById(R.id.fab);
        notesRv = findViewById(R.id.notesRV);
    }

    private void setupSharedPref() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME,MODE_PRIVATE);

    }

    private void setupDialogBox() {
        View view  = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes,null);

        final EditText titleEditText = view.findViewById(R.id.titleAddNoteEditText);
        final EditText descriptionEditText = view.findViewById(R.id.descriptionAddNoteEditText);
        Button submitButton = view.findViewById(R.id.submitButton);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        alertDialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();

                if ( !TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) ){
                    Notes notes = new Notes(title,description);
                    notesList.add( notes );
                    setupRecyclerView();
                }else
                    Toast.makeText(MyNotesActivity.this, "Title and Description can't be empty", Toast.LENGTH_SHORT).show();
                alertDialog.hide();
            }
        });
    }

    private void setupRecyclerView() {

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Notes notes) {
                Intent intent = new Intent(MyNotesActivity.this,DetailActivity.class);
                intent.putExtra(AppConstrain.TITLE,notes.getTitle());
                intent.putExtra(AppConstrain.DESCRIPTION,notes.getDescription());
                startActivity(intent);
            }
        };

        NotesAdapter notesAdapter = new NotesAdapter(notesList,itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        notesRv.setLayoutManager(linearLayoutManager);
        notesRv.setAdapter(notesAdapter);
    }
}
