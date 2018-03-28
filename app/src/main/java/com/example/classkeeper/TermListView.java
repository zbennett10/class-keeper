package com.example.classkeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.classkeeper.R.*;

public class TermListView extends AppCompatActivity {

    private ArrayList<Term> appTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_term_list_view);
        appTerms = DatabaseQueryBank.getTerms(TermListView.this);

        ListView appTermListView = findViewById(R.id.TermListView);

        appTermListView.setAdapter(new ArrayAdapter<Term>(TermListView.this,
                android.R.layout.simple_list_item_1, appTerms));

        Button setAddTermViewButton = (Button) findViewById(id.AddTermBtn);
        setAddTermViewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentToViewAddTermActivity = new Intent(TermListView.this, AddTermView.class);
                startActivity(intentToViewAddTermActivity);
            }
        });
    }
}
