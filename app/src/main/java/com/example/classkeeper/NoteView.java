package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class NoteView extends AppCompatActivity {

    private Note note;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.DeleteNoteIcon:
                DatabaseQueryBank.deleteNoteByID(NoteView.this, note.getID());
                Intent intentToViewCourse = new Intent(NoteView.this, CourseDetails.class);
                intentToViewCourse.putExtra("COURSE_ID", note.getCourseID());
                startActivity(intentToViewCourse);
                return true;
            case R.id.ShareNoteIcon:
                System.out.println("Attempting to share note.");
                Intent intentToShareNote = new Intent(Intent.ACTION_SEND);
                intentToShareNote.setType("text/plain");
                intentToShareNote.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                intentToShareNote.putExtra(Intent.EXTRA_SUBJECT, "ClassKeeper Note");
                intentToShareNote.putExtra(Intent.EXTRA_TEXT, note.getContent());

                startActivity(Intent.createChooser(intentToShareNote, "Share Note"));
                return true;
            default:
                return super.onOptionsItemSelected(selectedItem);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        Toolbar toolbar = (Toolbar)findViewById(R.id.NoteViewAppBar);
        setSupportActionBar(toolbar);

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
