package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CourseNotesView extends AppCompatActivity {

    private int courseID;
    private ArrayList<Note> courseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes_view);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);
        courseNotes = DatabaseQueryBank.getAllNotesWithCourseID(CourseNotesView.this, courseID);

        ListView noteListView = findViewById(R.id.NoteListView);
        noteListView.setAdapter(new ArrayAdapter<Note>(CourseNotesView.this,
                android.R.layout.simple_list_item_1, courseNotes));

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Gson gson = new Gson();
                String noteJSON = gson.toJson(courseNotes.get(position));
                Intent intentToViewNote = new Intent(CourseNotesView.this, NoteView.class);
                intentToViewNote.putExtra("COURSE_ID", courseID);
                intentToViewNote.putExtra("NOTE", noteJSON);
                startActivity(intentToViewNote);
            }
        });

        Button addNoteBtn = (Button) findViewById(R.id.AddNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentToAddCourseNote = new Intent(CourseNotesView.this, AddCourseNotesView.class);
                intentToAddCourseNote.putExtra("COURSE_ID", courseID);
                startActivity(intentToAddCourseNote);
            }
        });
    }
}
