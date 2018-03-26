package com.example.classkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Zachary Bennett on 3/25/2018.
 * This class provides utility functions for interacting with the database.
 */

public class DatabaseQueryBank {
   private static final String AUTHORITY = "com.example.classkeeper.provider";
   private static final Uri TERMS_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.TermTable.NAME);
   private static final Uri COURSE_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.CourseTable.NAME);
   private static final Uri ASSESSMENT_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.AssessmentTable.NAME);
   private static final Uri NOTE_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.NoteTable.NAME);
   private static final Uri MENTOR_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.MentorTable.NAME);
   private static final Uri COURSE_ALERTS_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.CourseAlertsTable.NAME);
   private static final Uri ASSESSMENT_ALERTS_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.AssessmentAlertsTable.NAME);

   public static Term getTerm(Context context, int termID) {
      Cursor cursor = context.getContentResolver()
                             .query(TERMS_TABLE_URI, ClassKeeperDBSchema.getColumnNames(ClassKeeperDBSchema.TermTable.NAME),
                                     ClassKeeperDBSchema.TermTable.Columns.ID + " = " + termID, null, null);
      cursor.moveToFirst();
      Term term = new Term(
              termID,
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.TITLE)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.START)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.END))
      );
      cursor.close();
      return term;
   }

   public static Uri insertTerm(Context context, Term term) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.TITLE, term.getTitle());
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.START, term.getStart());
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.END, term.getStart());
      return context.getContentResolver()
                    .insert(TERMS_TABLE_URI, insertionValues);
   }

   public static Course getCourse(Context context, int courseID) {
      Cursor cursor = context.getContentResolver()
                             .query(COURSE_TABLE_URI, ClassKeeperDBSchema.getColumnNames(ClassKeeperDBSchema.CourseTable.NAME),
                      ClassKeeperDBSchema.CourseTable.Columns.ID + " = " + courseID, null, null);
      cursor.moveToFirst();
      Course course = new Course(
              courseID,
              cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.TERM_ID)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.STATUS)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.TITLE)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.START)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.END))
      );
      cursor.close();
      return course;
   }

   public static Uri insertCourse(Context context, Course course) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.TERM_ID, course.getTermID());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.TITLE, course.getTitle());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.START, course.getStart());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.END, course.getEnd());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.STATUS, course.getStatus());
      return context.getContentResolver()
              .insert(TERMS_TABLE_URI, insertionValues);
   }

   public static Assessment getAssessment(Context context, int assessmentID) {
      Cursor cursor = context.getContentResolver()
              .query(COURSE_TABLE_URI, ClassKeeperDBSchema.getColumnNames(ClassKeeperDBSchema.AssessmentTable.NAME),
                      ClassKeeperDBSchema.AssessmentTable.Columns.ID + " = " + assessmentID, null, null);
      cursor.moveToFirst();
      Assessment assessment = new Assessment(
              assessmentID,
              cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.COURSE_ID)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.TYPE)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.TITLE)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.DUE_DATE))
      );
      cursor.close();
      return assessment;
   }

   public static Uri insertAssessment(Context context, Assessment assessment) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.COURSE_ID, assessment.getCourseID());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TITLE, assessment.getTitle());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TYPE, assessment.getType());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.DUE_DATE, assessment.getDueDate());
      return context.getContentResolver()
              .insert(TERMS_TABLE_URI, insertionValues);
   }

   public static Note getNote(Context context, int noteID) {
      Cursor cursor = context.getContentResolver()
              .query(NOTE_TABLE_URI, ClassKeeperDBSchema.getColumnNames(ClassKeeperDBSchema.NoteTable.NAME),
                      ClassKeeperDBSchema.AssessmentTable.Columns.ID + " = " + noteID, null, null);
      cursor.moveToFirst();
      Note note = new Note(
              noteID,
              cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.COURSE_ID)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.CONTENT))
      );
      cursor.close();
      return note;
   }

   public static Uri insertNote(Context context, Note note) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.COURSE_ID, note.getCourseID());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TITLE, note.getContent());
      return context.getContentResolver()
              .insert(TERMS_TABLE_URI, insertionValues);
   }
}
