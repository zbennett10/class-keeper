package com.example.classkeeper;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toListViewButton = (Button) findViewById(R.id.ToTermListView);
        toListViewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentToViewTermList = new Intent(MainActivity.this, TermListView.class);
                startActivity(intentToViewTermList);
            }
        });
    }
}
