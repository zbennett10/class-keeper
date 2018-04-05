package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class NoteView extends AppCompatActivity {

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        Gson gson = new Gson();
        note = gson.fromJson(getIntent().getStringExtra("NOTE"), Note.class);

        final EditText contentInput = (EditText) findViewById(R.id.EditNoteContentInput);
        contentInput.setText(note.getContent());
        final Button saveNoteBtn = (Button) findViewById(R.id.SaveNoteBtn);

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String content = contentInput.getText().toString();

                if(!content.isEmpty()) {
                    Note newNote = new Note(note.getID(), note.getCourseID(), content);
                    DatabaseQueryBank.updateNote(NoteView.this, newNote);

                    //go back to course list view
                    Intent intentToViewCourseNotes = new Intent(NoteView.this, CourseNotesView.class);
                    intentToViewCourseNotes.putExtra("COURSE_ID", note.getID());
                    startActivity(intentToViewCourseNotes);
                }
            }
        });

    }
}
