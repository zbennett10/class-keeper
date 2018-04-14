package com.example.classkeeper;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TermDetails extends AppCompatActivity {

    private int termID;

    private Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.TermDetailsAppBar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem selectedItem) {
        switch (selectedItem.getItemId()) {
            case R.id.DeleteTermIcon:
                ArrayList<Course> termCourses = DatabaseQueryBank.getAllCoursesWithTermID(TermDetails.this, termID);
                if(termCourses.size() < 1) {
                    DatabaseQueryBank.deleteTermByID(TermDetails.this, termID);
                    Intent intentToViewTermList = new Intent(TermDetails.this, TermListView.class);
                    startActivity(intentToViewTermList);
                } else {
                    View rootView = TermDetails.this.getWindow().getDecorView().findViewById(R.id.TermDetailsContent);
                    Snackbar unableToDeleteSnack = Snackbar.make(rootView, "Unable to delete - courses are attached to this term.", Snackbar.LENGTH_SHORT);
                    unableToDeleteSnack.show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(selectedItem);

        }
    }
}
