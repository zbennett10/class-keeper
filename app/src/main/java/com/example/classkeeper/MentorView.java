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

import java.util.ArrayList;

public class MentorView extends AppCompatActivity {

    private Mentor mentor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mentor_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.DeleteMentorIcon:
                DatabaseQueryBank.deleteMentorByID(MentorView.this, mentor.getID());
                Intent intentToViewCourse = new Intent(MentorView.this, CourseDetails.class);
                intentToViewCourse.putExtra("COURSE_ID", mentor.getCourseID());
                startActivity(intentToViewCourse);
                return true;

            default:
                return super.onOptionsItemSelected(selectedItem);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_view);

        Toolbar toolbar = (Toolbar)findViewById(R.id.MentorViewAppBar);
        setSupportActionBar(toolbar);

        Gson gson = new Gson();
        mentor = gson.fromJson(getIntent().getStringExtra("CURRENT_MENTOR"), Mentor.class);

        final EditText nameInput = (EditText) findViewById(R.id.EditMentorNameInput);
        nameInput.setText(mentor.getName());
        final EditText phoneNumberInput = (EditText) findViewById(R.id.EditMentorPhoneNumberInput);
        phoneNumberInput.setText(mentor.getPhoneNumber());
        final EditText emailInput = (EditText) findViewById(R.id.EditMentorEmailAdressInput);
        emailInput.setText(mentor.getEmailAddress());

        final Button saveMentorBtn = (Button) findViewById(R.id.SaveMentorBtn);

        saveMentorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String phoneNumber = phoneNumberInput.getText().toString();
                String emailAddress = emailInput.getText().toString();

                if(!name.isEmpty() && !phoneNumber.isEmpty() && !emailAddress.isEmpty()) {
                    Mentor newMentor = new Mentor(mentor.getID(), mentor.getCourseID(), name, phoneNumber, emailAddress);
                    DatabaseQueryBank.updateMentor(MentorView.this, newMentor);

                    //go back to course list view
                    Intent intentToViewCourseNotes = new Intent(MentorView.this, CourseMentorsView.class);
                    intentToViewCourseNotes.putExtra("COURSE_ID", newMentor.getCourseID());
                    startActivity(intentToViewCourseNotes);
                }
            }
        });
    }
}
