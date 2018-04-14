package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

public class AssessmentDetails extends AppCompatActivity {

    private int assessmentID;
    private Assessment currentAssessment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.DeleteAssessmentIcon:
                DatabaseQueryBank.deleteAssessmentByID(AssessmentDetails.this, assessmentID);
                Intent intentToViewAssessmentList = new Intent(AssessmentDetails.this, AssessmentListView.class);
                intentToViewAssessmentList.putExtra("COURSE_ID", currentAssessment.getCourseID());
                startActivity(intentToViewAssessmentList);
                return true;
            default:
                return super.onOptionsItemSelected(selectedItem);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.AssessmentDetailsAppBar);
        setSupportActionBar(toolbar);

        assessmentID = getIntent().getIntExtra("ASSESSMENT_ID", 0);
        currentAssessment = DatabaseQueryBank.getAssessment(AssessmentDetails.this, assessmentID);

        final EditText titleView = (EditText) findViewById(R.id.AssessmentTitleView);
        titleView.setText(currentAssessment.getTitle());
        final EditText dueDateView = (EditText) findViewById(R.id.AssessmentDueDateView);
        dueDateView.setText(currentAssessment.getDueDate());
        final Spinner typeView = (Spinner) findViewById(R.id.AssessmentTypeView);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(AssessmentDetails.this,
                android.R.layout.simple_spinner_item, Constants.AssessmentTypes);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeView.setAdapter(statusAdapter);
        typeView.setSelection(statusAdapter.getPosition(currentAssessment.getType()));

        Button setAlertBtn = (Button) findViewById(R.id.SetAssessmentAlertBtn);
        setAlertBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Long currentTime = Util.getCurrentTime();
                Long assessmentDueDate = Util.convertAppDateToLong(dueDateView.getText().toString());

                if(assessmentDueDate > currentTime) {
                    AssessmentAlertService.setAssessmentAlert(getApplicationContext(), currentAssessment);
                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AssessmentDetails.this);
                    alertBuilder.setMessage("Alert Not Scheduled - Please Set Due Date Past The Current Time.")
                                .show();
                }
            }
        });

        Button saveAssessmentBtn = (Button) findViewById(R.id.SaveAssessmentBtn);
        saveAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assessment newAssessment = new Assessment(
                        currentAssessment.getID(),
                        currentAssessment.getCourseID(),
                        typeView.getSelectedItem().toString(),
                        titleView.getText().toString(),
                        dueDateView.getText().toString()
                );
                DatabaseQueryBank.updateAssessment(AssessmentDetails.this, newAssessment);
                Intent intentToViewUpdatedAssessment = new Intent(AssessmentDetails.this, AssessmentDetails.class);
                intentToViewUpdatedAssessment.putExtra("ASSESSMENT_ID",  assessmentID);
                startActivity(intentToViewUpdatedAssessment);
            }
        });
    }
}
