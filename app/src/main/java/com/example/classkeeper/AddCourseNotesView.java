package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class AddCourseNotesView extends AppCompatActivity {

    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_notes_view);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);


        final EditText contentInput = (EditText) findViewById(R.id.AddNoteContentInput);
        final Button addNoteBtn = (Button) findViewById(R.id.AddNoteToDBBtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = contentInput.getText().toString();

                if(!content.isEmpty()) {
                    Note newNote = new Note(courseID, content);
                    DatabaseQueryBank.insertNote(AddCourseNotesView.this, newNote);

                    //go back to course list view
                    Intent intentToViewCourseNotes = new Intent(AddCourseNotesView.this, CourseNotesView.class);
                    intentToViewCourseNotes.putExtra("COURSE_ID", courseID);
                    startActivity(intentToViewCourseNotes);
                }
            }
        });

    }
}
