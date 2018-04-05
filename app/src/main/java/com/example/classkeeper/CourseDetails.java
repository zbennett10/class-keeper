package com.example.classkeeper;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

//this shows a way of dynamically switching TextViews to Plain Texts (EditText) so have view/edit on one screen
//https://stackoverflow.com/questions/4214441/how-to-convert-textview-to-edittext-in-android

public class CourseDetails extends AppCompatActivity {

    private int courseID;
    private Course currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);
        currentCourse = DatabaseQueryBank.getCourse(CourseDetails.this, courseID);
        ArrayList<Mentor> courseMentors = DatabaseQueryBank.getAllMentorsWithCourseID(CourseDetails.this, courseID);

        TextView titleView = (TextView) findViewById(R.id.CourseDetailsTitle);
        titleView.setText(currentCourse.getTitle());
        TextView startDateView = (TextView) findViewById(R.id.CourseDetailsStart);
        startDateView.setText(currentCourse.getStart());
        TextView endDateView = (TextView) findViewById(R.id.CourseDetailsEnd);
        endDateView.setText(currentCourse.getEnd());
        TextView statusView = (TextView) findViewById(R.id.CourseDetailsStatus);
        statusView.setText(currentCourse.getStatus());

        Button viewMentorsBtn = (Button) findViewById(R.id.ViewMentorsBtn);
        viewMentorsBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //get assessments for this course as arraylist - pass it to the next intent;
                  Intent intentToViewCourseMentors = new Intent(CourseDetails.this, CourseMentorsView.class);
                  intentToViewCourseMentors.putExtra("COURSE_ID", courseID);
                  startActivity(intentToViewCourseMentors);
              }
          });

        Button viewNotesBtn = (Button) findViewById(R.id.ViewNotesBtn);
        viewNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get assessments for this course as arraylist - pass it to the next intent;
                Intent intentToViewCourseNotes = new Intent(CourseDetails.this, CourseNotesView.class);
                intentToViewCourseNotes.putExtra("COURSE_ID", courseID);
                startActivity(intentToViewCourseNotes);
            }
        });

        Button viewAssessmentsBtn = (Button) findViewById(R.id.ViewAssessmentListBtn);
        viewAssessmentsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get assessments for this course as arraylist - pass it to the next intent;
                Intent intentToViewAssessmentList = new Intent(CourseDetails.this, AssessmentListView.class);
                intentToViewAssessmentList.putExtra("COURSE_ID", courseID);
                startActivity(intentToViewAssessmentList);
            }
        });
    }
}
