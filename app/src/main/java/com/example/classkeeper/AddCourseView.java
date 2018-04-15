package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddCourseView extends AppCompatActivity {

    private int termID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_view);

        termID = getIntent().getIntExtra("TERM_ID", 0);

        List<String> statuses = new ArrayList<String>(Constants.CourseStatuses);

        final Spinner statusInput = (Spinner) findViewById(R.id.AddCourseStatusInput);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(AddCourseView.this,
                android.R.layout.simple_spinner_item, statuses);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusInput.setAdapter(statusAdapter);

        final EditText titleInput = (EditText) findViewById(R.id.AddCourseTitleInput);
        final EditText startInput = (EditText) findViewById(R.id.AddCourseStartInput);
        startInput.setText("mm-dd-yyyy");
        final EditText endInput = (EditText) findViewById(R.id.AddCourseEndInput);
        endInput.setText("mm-dd-yyyy");
        final Button addCourseButton = (Button) findViewById(R.id.AddCourseToDBBtn);

        addCourseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String start = startInput.getText().toString();
                String end = endInput.getText().toString();
                String status = statusInput.getSelectedItem().toString();

                if(!title.isEmpty() && !start.isEmpty() && !end.isEmpty() && !status.isEmpty()) {
                    Course newCourse = new Course(termID, title, start, end, status);
                    DatabaseQueryBank.insertCourse(AddCourseView.this, newCourse);

                    //go back to course list view
                    Intent intentToViewCourseList = new Intent(AddCourseView.this, CourseListView.class);
                    intentToViewCourseList.putExtra("TERM_ID", termID);
                    startActivity(intentToViewCourseList);
                }
            }
        });

    }
}
