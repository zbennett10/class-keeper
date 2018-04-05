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

public class CourseMentorsView extends AppCompatActivity {

    private ArrayList<Mentor> courseMentors;
    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_mentors_view);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);
        courseMentors = DatabaseQueryBank.getAllMentorsWithCourseID(CourseMentorsView.this, courseID);

        ListView mentorListView = findViewById(R.id.MentorListView);
        mentorListView.setAdapter(new ArrayAdapter<Mentor>(CourseMentorsView.this,
                android.R.layout.simple_list_item_1, courseMentors));

        mentorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Gson gson = new Gson();
                String mentorJSON = gson.toJson(courseMentors.get(position));
                Intent intentToViewMentor = new Intent(CourseMentorsView.this, MentorView.class);
                intentToViewMentor.putExtra("CURRENT_MENTOR", mentorJSON);
                startActivity(intentToViewMentor);
            }
        });

        Button addMentorBtn = (Button) findViewById(R.id.AddMentorBtn);
        addMentorBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentToAddCourseMentor = new Intent(CourseMentorsView.this, AddCourseMentorView.class);
                intentToAddCourseMentor.putExtra("COURSE_ID", courseID);
                startActivity(intentToAddCourseMentor);
            }
        });

    }
}
