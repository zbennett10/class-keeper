package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class AddCourseMentorView extends AppCompatActivity {

    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_mentor_view);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);


        final EditText nameInput = (EditText) findViewById(R.id.AddMentorNameInput);
        final EditText phoneInput = (EditText) findViewById(R.id.AddMentorPhoneInput);
        final Button addMentorButton = (Button) findViewById(R.id.AddMentorToDBBtn);

        addMentorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String phoneNumber = phoneInput.getText().toString();

                if(!name.isEmpty() && !phoneNumber.isEmpty()) {
                    Mentor newMentor = new Mentor(courseID, name, phoneNumber);
                    DatabaseQueryBank.insertMentor(AddCourseMentorView.this, newMentor);

                    //go back to course list view
                    Intent intentToViewCourseMentors = new Intent(AddCourseMentorView.this, CourseMentorsView.class);
                    intentToViewCourseMentors.putExtra("COURSE_ID", courseID);
                    startActivity(intentToViewCourseMentors);
                }
            }
        });

    }
}
