package com.example.classkeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 * This class serves as the Database Helper for the application.
 */

public class ClassKeeperDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ClassKeeper.db";

    public ClassKeeperDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassKeeperDBSchema.TermTable.CREATE_TABLE);
        db.execSQL(ClassKeeperDBSchema.CourseTable.CREATE_TABLE);
        db.execSQL(ClassKeeperDBSchema.AssessmentTable.CREATE_TABLE);
        db.execSQL(ClassKeeperDBSchema.NoteTable.CREATE_TABLE);
        db.execSQL(ClassKeeperDBSchema.MentorTable.CREATE_TABLE);
        db.execSQL(ClassKeeperDBSchema.CourseAlertsTable.CREATE_TABLE);
        db.execSQL(ClassKeeperDBSchema.AssessmentAlertsTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop existing tables
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.TermTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.CourseTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.AssessmentTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.NoteTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.MentorTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.CourseAlertsTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassKeeperDBSchema.AssessmentAlertsTable.NAME);

        //create new tables
        onCreate(db);
    }
}
