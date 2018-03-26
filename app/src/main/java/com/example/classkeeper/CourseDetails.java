package com.example.classkeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//this shows a way of dynamically switching TextViews to Plain Texts (EditText) so have view/edit on one screen
//https://stackoverflow.com/questions/4214441/how-to-convert-textview-to-edittext-in-android

public class CourseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
    }
}
