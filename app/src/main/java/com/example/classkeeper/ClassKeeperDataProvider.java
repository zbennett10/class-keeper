package com.example.classkeeper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Zachary Bennett on 3/25/2018.
 * This class acts as the interface between the application and the database.
 */

public class ClassKeeperDataProvider extends ContentProvider{
  private static final String AUTH = "com.example.classkeeper.classkeeperdataprovider";
  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  private static final int TERM_CODE = 1;
  private static final int TERM_ID_CODE = 2;
  private static final int COURSE_CODE = 3;
  private static final int COURSE_ID_CODE = 4;
  private static final int ASSESSMENT_CODE = 5;
  private static final int ASSESSMENT_ID_CODE = 6;
  private static final int NOTE_CODE = 7;
  private static final int NOTE_ID_CODE = 8;
  private static final int MENTOR_CODE = 9;
  private static final int MENTOR_ID_CODE = 10;
  private static final int COURSE_ALERT_CODE = 11;
  private static final int COURSE_ALERT_ID_CODE = 12;
  private static final int ASSESSMENT_ALERT_CODE = 13;
  private static final int ASSESSMENT_ALERT_ID_CODE = 14;

  private SQLiteDatabase db;

  static {
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.TermTable.NAME, 1);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.TermTable.NAME + "/#", 2);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.CourseTable.NAME, 3);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.CourseTable.NAME + "/#", 4);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.AssessmentTable.NAME, 5);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.AssessmentTable.NAME + "/#", 6);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.NoteTable.NAME, 7);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.NoteTable.NAME + "/#", 8);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.MentorTable.NAME, 9);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.MentorTable.NAME + "/#", 10);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.CourseAlertsTable.NAME, 11);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.CourseAlertsTable.NAME + "/#", 12);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.AssessmentAlertsTable.NAME, 13);
    uriMatcher.addURI(AUTH, ClassKeeperDBSchema.AssessmentAlertsTable.NAME + "/#", 14);
  }

  private String[] getColumnNamesByTableName(String tableName) {
    switch(tableName) {
      case ClassKeeperDBSchema.TermTable.NAME:
        return ClassKeeperDBSchema.TermTable.Columns.names;
      case ClassKeeperDBSchema.CourseTable.NAME:
        return ClassKeeperDBSchema.CourseTable.Columns.names;
      case ClassKeeperDBSchema.AssessmentTable.NAME:
        return ClassKeeperDBSchema.AssessmentTable.Columns.names;
      case ClassKeeperDBSchema.NoteTable.NAME:
        return ClassKeeperDBSchema.NoteTable.Columns.names;
      case ClassKeeperDBSchema.MentorTable.NAME:
        return ClassKeeperDBSchema.MentorTable.Columns.names;
      case ClassKeeperDBSchema.CourseAlertsTable.NAME:
        return ClassKeeperDBSchema.CourseAlertsTable.Columns.names;
      case ClassKeeperDBSchema.AssessmentAlertsTable.NAME:
        return ClassKeeperDBSchema.AssessmentAlertsTable.Columns.names;
      default:
        throw new IllegalArgumentException("Table not found when trying to get column names: " + tableName);
    }
  }

  @Override
  public boolean onCreate() {
    ClassKeeperDBHelper dbHelper = new ClassKeeperDBHelper(getContext());
    db = dbHelper.getWritableDatabase();
    return true;
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  private Cursor getTableCursor(String selection, String tableName, String primaryKeyName) {
    return db.query(tableName, getColumnNamesByTableName(tableName),
            selection, null, null, null,
            primaryKeyName + " ASC");
  }

  private Cursor getTableIDCursor(Uri uri, String tableName, String primaryKeyName) {
    return db.query(tableName, getColumnNamesByTableName(tableName),
            primaryKeyName + "=" + uri.getLastPathSegment(),
            null, null, null,
            primaryKeyName + " ASC");
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArguments, String sortOrder) {
    switch(uriMatcher.match(uri)) {
      case TERM_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.TermTable.NAME, ClassKeeperDBSchema.TermTable.Columns.ID);
      case TERM_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.TermTable.NAME, ClassKeeperDBSchema.TermTable.Columns.ID);
      case COURSE_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.CourseTable.NAME, ClassKeeperDBSchema.CourseTable.Columns.ID);
      case COURSE_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.CourseTable.NAME, ClassKeeperDBSchema.CourseTable.Columns.ID);
      case ASSESSMENT_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.AssessmentTable.NAME, ClassKeeperDBSchema.AssessmentTable.Columns.ID);
      case ASSESSMENT_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.AssessmentTable.NAME, ClassKeeperDBSchema.AssessmentTable.Columns.ID);
      case NOTE_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.NoteTable.NAME, ClassKeeperDBSchema.NoteTable.Columns.ID);
      case NOTE_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.NoteTable.NAME, ClassKeeperDBSchema.NoteTable.Columns.ID);
      case MENTOR_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.MentorTable.NAME, ClassKeeperDBSchema.MentorTable.Columns.ID);
      case MENTOR_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.MentorTable.NAME, ClassKeeperDBSchema.MentorTable.Columns.ID);
      case COURSE_ALERT_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.CourseAlertsTable.NAME, ClassKeeperDBSchema.CourseAlertsTable.Columns.ID);
      case COURSE_ALERT_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.CourseAlertsTable.NAME, ClassKeeperDBSchema.CourseAlertsTable.Columns.ID);
      case ASSESSMENT_ALERT_CODE:
        return getTableCursor(selection, ClassKeeperDBSchema.AssessmentAlertsTable.NAME, ClassKeeperDBSchema.AssessmentAlertsTable.Columns.ID);
      case ASSESSMENT_ALERT_ID_CODE:
        return getTableIDCursor(uri, ClassKeeperDBSchema.AssessmentAlertsTable.NAME, ClassKeeperDBSchema.AssessmentAlertsTable.Columns.ID);
      default:
        throw new IllegalArgumentException("URI to table not found: " + uri);
    }
  }

    private Uri getInsertionUri(Uri uri, ContentValues insertionValues, String tableName) {
     long id = db.insert(tableName, null, insertionValues);
     return uri.parse(tableName + "/" + id);
    }

    @Override
    public Uri insert(Uri uri, ContentValues insertionValues) {
      switch (uriMatcher.match(uri)) {
        case TERM_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.TermTable.NAME);
        case COURSE_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.CourseTable.NAME);
        case ASSESSMENT_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.AssessmentTable.NAME);
        case NOTE_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.NoteTable.NAME);
        case MENTOR_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.MentorTable.NAME);
        case COURSE_ALERT_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.CourseAlertsTable.NAME);
        case ASSESSMENT_ALERT_CODE:
          return getInsertionUri(uri, insertionValues, ClassKeeperDBSchema.AssessmentAlertsTable.NAME);
        default:
          throw new IllegalArgumentException("URI to table not found: " + uri);
      }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArguments) {
      switch(uriMatcher.match(uri)) {
        case TERM_CODE:
          return db.delete(ClassKeeperDBSchema.TermTable.NAME, selection, selectionArguments);
        case COURSE_CODE:
          return db.delete(ClassKeeperDBSchema.CourseTable.NAME, selection, selectionArguments);
        case ASSESSMENT_CODE:
          return db.delete(ClassKeeperDBSchema.AssessmentTable.NAME, selection, selectionArguments);
        case NOTE_CODE:
          return db.delete(ClassKeeperDBSchema.NoteTable.NAME, selection, selectionArguments);
        case MENTOR_CODE:
          return db.delete(ClassKeeperDBSchema.MentorTable.NAME, selection, selectionArguments);
        case COURSE_ALERT_CODE:
          return db.delete(ClassKeeperDBSchema.CourseAlertsTable.NAME, selection, selectionArguments);
        case ASSESSMENT_ALERT_CODE:
          return db.delete(ClassKeeperDBSchema.AssessmentAlertsTable.NAME, selection, selectionArguments);
        default:
          throw new IllegalArgumentException("URI to table not found: " + uri);
      }
    }

    @Override
    public int update(Uri uri, ContentValues updateValues, String selection, String[] selectionArguments) {
      switch(uriMatcher.match(uri)) {
        case TERM_CODE:
          return db.update(ClassKeeperDBSchema.TermTable.NAME, updateValues, selection, selectionArguments);
        case COURSE_CODE:
          return db.update(ClassKeeperDBSchema.CourseTable.NAME, updateValues, selection, selectionArguments);
        case ASSESSMENT_CODE:
          return db.update(ClassKeeperDBSchema.AssessmentTable.NAME, updateValues, selection, selectionArguments);
        case NOTE_CODE:
          return db.update(ClassKeeperDBSchema.NoteTable.NAME, updateValues, selection, selectionArguments);
        case MENTOR_CODE:
          return db.update(ClassKeeperDBSchema.MentorTable.NAME, updateValues, selection, selectionArguments);
        case COURSE_ALERT_CODE:
          return db.update(ClassKeeperDBSchema.CourseAlertsTable.NAME, updateValues, selection, selectionArguments);
        case ASSESSMENT_ALERT_CODE:
          return db.update(ClassKeeperDBSchema.AssessmentAlertsTable.NAME, updateValues, selection, selectionArguments);
        default:
          throw new IllegalArgumentException("URI to table not found: " + uri);
      }
    }
}
