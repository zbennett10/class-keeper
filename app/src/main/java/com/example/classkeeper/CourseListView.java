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

public class CourseListView extends AppCompatActivity {

    private ArrayList<Course> termCourses;
    private int termID;
    private Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_view);

        Gson gson = new Gson();
        termID = getIntent().getIntExtra("TERM_ID", 0);
        termCourses = DatabaseQueryBank.getAllCoursesWithTermID(CourseListView.this, termID);

        ListView courseListView = findViewById(R.id.CourseListView);
        courseListView.setAdapter(new ArrayAdapter<Course>(CourseListView.this,
                android.R.layout.simple_list_item_1, termCourses));

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intentToViewCourseDetails = new Intent(CourseListView.this, CourseDetails.class);
                intentToViewCourseDetails.putExtra("COURSE_ID", termCourses.get(position).getID());
                startActivity(intentToViewCourseDetails);
            }
        });

        Button addCourseBtn = (Button) findViewById(R.id.AddCourseBtn);
        addCourseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentToViewAddCourseActivity = new Intent(CourseListView.this, AddCourseView.class);
                intentToViewAddCourseActivity.putExtra("TERM_ID", termID);
                startActivity(intentToViewAddCourseActivity);
            }
        });

    }
}
