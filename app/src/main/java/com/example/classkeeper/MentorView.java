package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class MentorView extends AppCompatActivity {

    private Mentor mentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_view);

        Gson gson = new Gson();
        mentor = gson.fromJson(getIntent().getStringExtra("CURRENT_MENTOR"), Mentor.class);

        final EditText nameInput = (EditText) findViewById(R.id.EditMentorNameInput);
        nameInput.setText(mentor.getName());
        final EditText phoneNumberInput = (EditText) findViewById(R.id.EditMentorPhoneNumberInput);
        phoneNumberInput.setText(mentor.getPhoneNumber());

        final Button saveMentorBtn = (Button) findViewById(R.id.SaveMentorBtn);

        saveMentorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String phoneNumber = phoneNumberInput.getText().toString();

                if(!name.isEmpty() && !phoneNumber.isEmpty()) {
                    Mentor newMentor = new Mentor(mentor.getID(), mentor.getCourseID(), name, phoneNumber);
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
