package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddAssessmentView extends AppCompatActivity {

    private int courseID;
    private Course currentCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_view);

        courseID = getIntent().getIntExtra("COURSE_ID", 0);

        List<String> assessmentTypes = new ArrayList<String>(Constants.AssessmentTypes);

        final Spinner typeInput = (Spinner) findViewById(R.id.AssessmentTypeInput);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(AddAssessmentView.this,
                android.R.layout.simple_spinner_item, assessmentTypes);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeInput.setAdapter(typeAdapter);

        final EditText titleInput = (EditText) findViewById(R.id.AssessmentTitleInput);
        final EditText dueDateInput = (EditText) findViewById(R.id.AssessmentDueDateInput);
        dueDateInput.setText("mm-dd-yyyy");
        final Button addAssessmentBtn = (Button) findViewById(R.id.AddAssessmentToDBBtn);

        addAssessmentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String dueDate = dueDateInput.getText().toString();
                String type = typeInput.getSelectedItem().toString();

                if(!title.isEmpty() && !dueDate.isEmpty() && !type.isEmpty()) {
                    Assessment newAssessment = new Assessment(courseID, type, title, dueDate);
                    DatabaseQueryBank.insertAssessment(AddAssessmentView.this, newAssessment);

                    //go back to assessment list view
                    Intent intentToViewAssessmentList = new Intent(AddAssessmentView.this, AssessmentListView.class);
                    intentToViewAssessmentList.putExtra("COURSE_ID", courseID);
                    startActivity(intentToViewAssessmentList);
                }
            }
        });
    }
}
