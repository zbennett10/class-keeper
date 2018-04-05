package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class TermDetails extends AppCompatActivity {

    private int termID;

    private Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        termID = getIntent().getIntExtra("TERM_ID", 0);
        currentTerm = DatabaseQueryBank.getTerm(TermDetails.this, termID);

        final EditText titleView = (EditText) findViewById(R.id.TermTitle);
        titleView.setText(currentTerm.getTitle());
        final EditText startDateView = (EditText) findViewById(R.id.TermStartDate);
        startDateView.setText(currentTerm.getStart());
        final EditText endDateView = (EditText) findViewById(R.id.TermEndDate);
        endDateView.setText(currentTerm.getEnd());

        Button viewCoursesBtn = (Button) findViewById(R.id.ViewCoursesBtn);
        viewCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get courses for this term as arraylist - pass it to the next intent;
                Intent intentToViewCourseList = new Intent(TermDetails.this, CourseListView.class);
                intentToViewCourseList.putExtra("TERM_ID", termID);
                startActivity(intentToViewCourseList);
            }
        });

        Button saveTermBtn = (Button) findViewById(R.id.SaveTermBtn);
        saveTermBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Term newTerm = new Term(
                        currentTerm.getID(),
                        titleView.getText().toString(),
                        startDateView.getText().toString(),
                        endDateView.getText().toString()
                );
                DatabaseQueryBank.updateTerm(TermDetails.this, newTerm);
                Intent intentToViewUpdatedTerm = new Intent(TermDetails.this, TermDetails.class);
                intentToViewUpdatedTerm.putExtra("TERM_ID",  termID);
                startActivity(intentToViewUpdatedTerm);
            }
        });

    }
}
