package com.example.classkeeper;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

//this shows a way of dynamically switching TextViews to Plain Texts (EditText) so have view/edit on one screen
//https://stackoverflow.com/questions/4214441/how-to-convert-textview-to-edittext-in-android

public class CourseDetails extends AppCompatActivity {

    private int courseID;
    private Course currentCourse;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.DeleteCourseIcon:
                    DatabaseQueryBank.deleteCourseByID(CourseDetails.this, courseID);
                    Intent intentToViewCourseList = new Intent(CourseDetails.this, CourseListView.class);
                    intentToViewCourseList.putExtra("TERM_ID", currentCourse.getTermID());
                    startActivity(intentToViewCourseList);
                return true;
            default:
                return super.onOptionsItemSelected(selectedItem);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.CourseDetailsAppBar);
        setSupportActionBar(toolbar);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);
        currentCourse = DatabaseQueryBank.getCourse(CourseDetails.this, courseID);
        ArrayList<Mentor> courseMentors = DatabaseQueryBank.getAllMentorsWithCourseID(CourseDetails.this, courseID);

        final EditText titleView = (EditText) findViewById(R.id.CourseDetailsTitle);
        titleView.setText(currentCourse.getTitle());
        final EditText startDateView = (EditText) findViewById(R.id.CourseDetailsStart);
        startDateView.setText(currentCourse.getStart());
        final EditText endDateView = (EditText) findViewById(R.id.CourseDetailsEnd);
        endDateView.setText(currentCourse.getEnd());
        final Spinner statusView = (Spinner) findViewById(R.id.CourseDetailsStatus);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(CourseDetails.this,
                android.R.layout.simple_spinner_item, Constants.CourseStatuses);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusView.setAdapter(statusAdapter);
        statusView.setSelection(statusAdapter.getPosition(currentCourse.getStatus()));

        Button setCourseAlertsBtn = (Button) findViewById(R.id.SetCourseAlertsBtn);
        setCourseAlertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long currentTime = Util.getCurrentTime();
                Long courseStartDate = Util.convertAppDateToLong(startDateView.getText().toString());
                Long courseEndDate = Util.convertAppDateToLong(endDateView.getText().toString());

                CourseAlertService.setCourseAlerts(getApplicationContext(), currentCourse);
            }
        });

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

        Button saveCourseBtn = (Button) findViewById(R.id.SaveCourseBtn);
        saveCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course newCourse = new Course(
                        currentCourse.getID(),
                        currentCourse.getTermID(),
                        titleView.getText().toString(),
                        startDateView.getText().toString(),
                        endDateView.getText().toString(),
                        statusView.getSelectedItem().toString()
                );
                DatabaseQueryBank.updateCourse(CourseDetails.this, newCourse);
                Intent intentToViewUpdatedCourse = new Intent(CourseDetails.this, CourseDetails.class);
                intentToViewUpdatedCourse.putExtra("COURSE_ID",  courseID);
                startActivity(intentToViewUpdatedCourse);
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
