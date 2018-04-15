package com.example.classkeeper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddTermView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_view);

        final EditText titleInput = (EditText) findViewById(R.id.termTitleInput);
        final EditText startInput = (EditText) findViewById(R.id.termStartInput);
        startInput.setText("mm-dd-yyyy");
        final EditText endInput = (EditText) findViewById(R.id.termEndInput);
        endInput.setText("mm-dd-yyyy");
        final Button addTermButton = (Button) findViewById(R.id.BTN_ACTION_ADD_TERM);

        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current input title, start and end dates
                String title = titleInput.getText().toString();
                String start = startInput.getText().toString();
                String end = endInput.getText().toString();
                if(!title.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
                    Term newTerm = new Term(title, start, end);
                    DatabaseQueryBank.insertTerm(AddTermView.this, newTerm);

                    //go back to term list view
                    Intent intentToViewTermList = new Intent(AddTermView.this, TermListView.class);
                    startActivity(intentToViewTermList);
                }
            }
        });


    }
}
