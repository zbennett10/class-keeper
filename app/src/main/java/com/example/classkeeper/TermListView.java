package com.example.classkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.classkeeper.R.*;

public class TermListView extends AppCompatActivity {

    ArrayList<String> appTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_term_list_view);

        appTerms.add("First Term");
        appTerms.add("Second Term");

        ListView appTermListView = findViewById(R.id.TermListView);

        appTermListView.setAdapter(new ArrayAdapter<String>(TermListView.this,
                android.R.layout.simple_list_item_1, appTerms));
    }
}
