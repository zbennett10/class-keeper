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

public class AssessmentListView extends AppCompatActivity {
    private ArrayList<Assessment> assessments;
    private int courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list_view);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);
        assessments = DatabaseQueryBank.getAllAssessmentsWithCourseID(AssessmentListView.this, courseID);

        ListView assessmentListView = findViewById(R.id.AssessmentListView);

        assessmentListView.setAdapter(new ArrayAdapter<Assessment>(AssessmentListView.this,
                android.R.layout.simple_list_item_1, assessments));

        assessmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intentToViewDetails = new Intent(AssessmentListView.this, AssessmentDetails.class);
                intentToViewDetails.putExtra("ASSESSMENT_ID", assessments.get(position).getID());
                startActivity(intentToViewDetails);
            }
        });

        Button addAssessmentBtn = (Button) findViewById(R.id.AddAsessmentBtn);
        addAssessmentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentToViewAddAssessmentActivity = new Intent(AssessmentListView.this, AddAssessmentView.class);
                intentToViewAddAssessmentActivity.putExtra("COURSE_ID", courseID);
                startActivity(intentToViewAddAssessmentActivity);
            }
        });
    }
}
