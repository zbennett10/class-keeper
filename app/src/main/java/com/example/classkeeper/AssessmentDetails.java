package com.example.classkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class AssessmentDetails extends AppCompatActivity {

    private int assessmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentID = getIntent().getIntExtra("ASSESSMENT_ID", 0);
        Assessment currentAssessment = DatabaseQueryBank.getAssessment(AssessmentDetails.this, assessmentID);

        TextView titleView = (TextView) findViewById(R.id.AssessmentTitleView);
        titleView.setText(currentAssessment.getTitle());
        TextView dueDateView = (TextView) findViewById(R.id.AssessmentDueDateView);
        dueDateView.setText(currentAssessment.getDueDate());
        TextView typeView = (TextView) findViewById(R.id.AssessmentTypeView);
        typeView.setText(currentAssessment.getType());

        Button setAlertBtn = (Button) findViewById(R.id.SetAssessmentAlertBtn);
        setAlertBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //get assessments for this course as arraylist - pass it to the next intent;
//                Intent intentToViewAssessmentList = new Intent(CourseDetails.this, AssessmentListView.class);
//                intentToViewAssessmentList.putExtra("CURRENT_COURSE", getIntent().getStringExtra("CURRENT_COURSE"));
//                startActivity(intentToViewAssessmentList);
            }
        });
    }
}
