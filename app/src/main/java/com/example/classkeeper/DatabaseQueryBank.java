package com.example.classkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Zachary Bennett on 3/25/2018.
 * This class provides utility functions for interacting with the database.
 */

public class DatabaseQueryBank {
   private static final String AUTHORITY = "com.example.classkeeper.classkeeperdataprovider";
   private static final Uri TERMS_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.TermTable.NAME);
   private static final Uri COURSE_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.CourseTable.NAME);
   private static final Uri ASSESSMENT_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.AssessmentTable.NAME);
   private static final Uri NOTE_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.NoteTable.NAME);
   private static final Uri MENTOR_TABLE_URI = Uri.parse("content://" + AUTHORITY + "/" + ClassKeeperDBSchema.MentorTable.NAME);

   public static Term getTerm(Context context, int termID) {
      Cursor cursor = context.getContentResolver()
                             .query(TERMS_TABLE_URI, ClassKeeperDBSchema.TermTable.Columns.names,
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

   public static ArrayList<Term> getTerms(Context context) {
      ArrayList<Term> terms = new ArrayList<Term>();
      Cursor cursor = context.getContentResolver()
                             .query(TERMS_TABLE_URI, ClassKeeperDBSchema.TermTable.Columns.names,
                                     null, null, null );
      System.out.println(cursor);
      if(cursor != null) {
         while(cursor.moveToNext()) {
            Term term = new Term(
                    cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.TITLE)),
                    cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.START)),
                    cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.TermTable.Columns.END))
            );
            terms.add(term);
         }
      }
      cursor.close();
      return terms;
   }

   public static Uri insertTerm(Context context, Term term) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.TITLE, term.getTitle());
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.START, term.getStart());
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.END, term.getStart());
      return context.getContentResolver()
                    .insert(TERMS_TABLE_URI, insertionValues);
   }

   public static int updateTerm(Context context, Term term) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.TITLE, term.getTitle());
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.START, term.getStart());
      insertionValues.put(ClassKeeperDBSchema.TermTable.Columns.END, term.getStart());
      return context.getContentResolver()
              .update(TERMS_TABLE_URI, insertionValues, ClassKeeperDBSchema.TermTable.Columns.ID + " = " + term.getID(), null);
   }

   public static boolean deleteTermByID(Context context, int termID) {
      //make sure term is not delete if courses are assigned
      Cursor courseCursor = context.getContentResolver()
                                   .query(COURSE_TABLE_URI, ClassKeeperDBSchema.CourseTable.Columns.names,
                                           ClassKeeperDBSchema.CourseTable.Columns.TERM_ID + " = " + termID, null, null);
      //there are course assigned to this term - don't allow delete
      if(courseCursor.moveToFirst() == true) {
         return false;
      } else {
         context.getContentResolver()
                 .delete(TERMS_TABLE_URI, ClassKeeperDBSchema.TermTable.Columns.ID + " = " + termID, null);
         return true;
      }
   }

   public static Course getCourse(Context context, int courseID) {
      Cursor cursor = context.getContentResolver()
                             .query(COURSE_TABLE_URI, ClassKeeperDBSchema.CourseTable.Columns.names,
                      ClassKeeperDBSchema.CourseTable.Columns.ID + " = " + courseID, null, null);
      cursor.moveToFirst();
      Course course = new Course(
              courseID,
              cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.TERM_ID)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.TITLE)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.START)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.END)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.STATUS))
      );
      cursor.close();
      return course;
   }

   public static ArrayList<Course> getAllCoursesWithTermID(Context context, int termID) {
      ArrayList<Course> courses = new ArrayList<Course>();
      Cursor cursor = context.getContentResolver()
              .query(COURSE_TABLE_URI, ClassKeeperDBSchema.CourseTable.Columns.names,
                      null, null, null );
      if(cursor != null) {
         while(cursor.moveToNext()) {
            int currentCourseTermID = cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.TERM_ID));
            if(currentCourseTermID == termID) {
               Course course = new Course(
                       cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.ID)),
                       currentCourseTermID,
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.TITLE)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.START)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.END)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.CourseTable.Columns.STATUS))
               );
               courses.add(course);
            }
         }
      }
      cursor.close();
      return courses;
   }

   public static Uri insertCourse(Context context, Course course) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.TERM_ID, course.getTermID());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.TITLE, course.getTitle());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.START, course.getStart());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.END, course.getEnd());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.STATUS, course.getStatus());
      return context.getContentResolver()
              .insert(COURSE_TABLE_URI, insertionValues);
   }

   public static int updateCourse(Context context, Course course) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.TITLE, course.getTitle());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.START, course.getStart());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.END, course.getEnd());
      insertionValues.put(ClassKeeperDBSchema.CourseTable.Columns.STATUS, course.getStatus());
      return context.getContentResolver()
              .update(COURSE_TABLE_URI, insertionValues, ClassKeeperDBSchema.CourseTable.Columns.ID + " = " + course.getID(), null);
   }

   public static boolean deleteCourseByID(Context context, int courseID) {
      //delete each assessment associated with this course
      Cursor assessmentCursor = context.getContentResolver()
              .query(ASSESSMENT_TABLE_URI, ClassKeeperDBSchema.AssessmentTable.Columns.names,
                      ClassKeeperDBSchema.AssessmentTable.Columns.COURSE_ID + " = " + courseID, null, null);
      while(assessmentCursor.moveToNext()) {
         int assessmentID = assessmentCursor.getInt(assessmentCursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.ID));
         deleteAssessmentByID(context, assessmentID);
      }

      //delete each note associated with this course
      Cursor noteCursor = context.getContentResolver()
              .query(NOTE_TABLE_URI, ClassKeeperDBSchema.NoteTable.Columns.names,
                      ClassKeeperDBSchema.NoteTable.Columns.COURSE_ID + " = " + courseID, null, null);
      while(noteCursor.moveToNext()) {
         int noteID = noteCursor.getInt(noteCursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.ID));
         deleteNoteByID(context, noteID);
      }

      //delete each mentor associated with this course
      Cursor mentorCursor = context.getContentResolver()
              .query(MENTOR_TABLE_URI, ClassKeeperDBSchema.MentorTable.Columns.names,
                      ClassKeeperDBSchema.MentorTable.Columns.COURSE_ID + " = " + courseID, null, null);
      while(mentorCursor.moveToNext()) {
         int mentorID = mentorCursor.getInt(mentorCursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.ID));
         deleteMentorByID(context, mentorID);
      }

      context.getContentResolver()
              .delete(COURSE_TABLE_URI, ClassKeeperDBSchema.CourseTable.Columns.ID + " = " + courseID, null);
      return true;
   }

   public static Assessment getAssessment(Context context, int assessmentID) {
      Cursor cursor = context.getContentResolver()
              .query(ASSESSMENT_TABLE_URI, ClassKeeperDBSchema.AssessmentTable.Columns.names,
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

   public static ArrayList<Assessment> getAllAssessmentsWithCourseID(Context context, int courseID) {
      ArrayList<Assessment> assessments = new ArrayList<Assessment>();
      Cursor cursor = context.getContentResolver()
              .query(ASSESSMENT_TABLE_URI, ClassKeeperDBSchema.AssessmentTable.Columns.names,
                      null, null, null );
      if(cursor != null) {
         while(cursor.moveToNext()) {
            int currentAssessmentCourseID = cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.COURSE_ID));
            if(currentAssessmentCourseID == courseID) {
               Assessment assessment = new Assessment(
                       cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.ID)),
                       currentAssessmentCourseID,
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.TYPE)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.TITLE)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.AssessmentTable.Columns.DUE_DATE))

               );
               assessments.add(assessment);
            }
         }
      }
      cursor.close();
      return assessments;
   }

   public static Uri insertAssessment(Context context, Assessment assessment) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.COURSE_ID, assessment.getCourseID());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TITLE, assessment.getTitle());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TYPE, assessment.getType());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.DUE_DATE, assessment.getDueDate());
      return context.getContentResolver()
              .insert(ASSESSMENT_TABLE_URI, insertionValues);
   }

   public static int updateAssessment(Context context, Assessment assessment) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TITLE, assessment.getTitle());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.TYPE, assessment.getType());
      insertionValues.put(ClassKeeperDBSchema.AssessmentTable.Columns.DUE_DATE, assessment.getDueDate());
      return context.getContentResolver()
              .update(ASSESSMENT_TABLE_URI, insertionValues, ClassKeeperDBSchema.AssessmentTable.Columns.ID + " = " + assessment.getID(), null);
   }

   public static boolean deleteAssessmentByID(Context context, int assessmentID) {
      context.getContentResolver()
              .delete(ASSESSMENT_TABLE_URI, ClassKeeperDBSchema.AssessmentTable.Columns.ID + " = " + assessmentID, null);
      return true;
   }

   public static Note getNote(Context context, int noteID) {
      Cursor cursor = context.getContentResolver()
              .query(NOTE_TABLE_URI, ClassKeeperDBSchema.NoteTable.Columns.names,
                      ClassKeeperDBSchema.NoteTable.Columns.ID + " = " + noteID, null, null);
      cursor.moveToFirst();
      Note note = new Note(
              noteID,
              cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.COURSE_ID)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.CONTENT))
      );
      cursor.close();
      return note;
   }

   public static ArrayList<Note> getAllNotesWithCourseID(Context context, int courseID) {
      ArrayList<Note> notes = new ArrayList<Note>();
      Cursor cursor = context.getContentResolver()
              .query(NOTE_TABLE_URI, ClassKeeperDBSchema.NoteTable.Columns.names,
                      null, null, null );
      if(cursor != null) {
         while(cursor.moveToNext()) {
            int currentCourseID = cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.COURSE_ID));
            if(currentCourseID == courseID) {
               Note note = new Note(
                       cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.ID)),
                       courseID,
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.NoteTable.Columns.CONTENT))
               );
               notes.add(note);
            }
         }
      }
      cursor.close();
      return notes;
   }

   public static Uri insertNote(Context context, Note note) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.NoteTable.Columns.COURSE_ID, note.getCourseID());
      insertionValues.put(ClassKeeperDBSchema.NoteTable.Columns.CONTENT, note.getContent());
      return context.getContentResolver()
              .insert(NOTE_TABLE_URI, insertionValues);
   }

   public static int updateNote(Context context, Note note) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.NoteTable.Columns.CONTENT, note.getContent());
      return context.getContentResolver()
              .update(NOTE_TABLE_URI, insertionValues, ClassKeeperDBSchema.NoteTable.Columns.ID + " = " + note.getID(), null);
   }

   public static boolean deleteNoteByID(Context context, int noteID) {
      context.getContentResolver()
              .delete(NOTE_TABLE_URI, ClassKeeperDBSchema.NoteTable.Columns.ID + " = " + noteID, null);
      return true;
   }

   public static Mentor getMentor(Context context, int mentorID) {
      Cursor cursor = context.getContentResolver()
              .query(MENTOR_TABLE_URI, ClassKeeperDBSchema.MentorTable.Columns.names,
                      ClassKeeperDBSchema.MentorTable.Columns.ID + " = " + mentorID, null, null);
      cursor.moveToFirst();
      Mentor mentor = new Mentor(
              mentorID,
              cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.COURSE_ID)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.NAME)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.PHONE_NUMBER)),
              cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.EMAIL_ADDRESS))
      );
      cursor.close();
      return mentor;
   }

   public static ArrayList<Mentor> getAllMentorsWithCourseID(Context context, int courseID) {
      ArrayList<Mentor> mentors = new ArrayList<Mentor>();
      Cursor cursor = context.getContentResolver()
              .query(MENTOR_TABLE_URI, ClassKeeperDBSchema.MentorTable.Columns.names,
                      null, null, null );
      if(cursor != null) {
         while(cursor.moveToNext()) {
            int currentMentorCourseID = cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.COURSE_ID));
            if(currentMentorCourseID == courseID) {
               Mentor mentor = new Mentor(
                       cursor.getInt(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.ID)),
                       courseID,
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.NAME)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.PHONE_NUMBER)),
                       cursor.getString(cursor.getColumnIndex(ClassKeeperDBSchema.MentorTable.Columns.EMAIL_ADDRESS))
               );
               mentors.add(mentor);
            }
         }
      }
      cursor.close();
      return mentors;
   }

   public static Uri insertMentor(Context context, Mentor mentor) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.COURSE_ID, mentor.getCourseID());
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.NAME, mentor.getName());
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.PHONE_NUMBER, mentor.getPhoneNumber());
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.EMAIL_ADDRESS, mentor.getEmailAddress());
      return context.getContentResolver()
              .insert(MENTOR_TABLE_URI, insertionValues);
   }

   public static int updateMentor(Context context, Mentor mentor) {
      ContentValues insertionValues = new ContentValues();
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.NAME, mentor.getName());
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.PHONE_NUMBER, mentor.getPhoneNumber());
      insertionValues.put(ClassKeeperDBSchema.MentorTable.Columns.EMAIL_ADDRESS, mentor.getEmailAddress());
      return context.getContentResolver()
              .update(MENTOR_TABLE_URI, insertionValues, ClassKeeperDBSchema.MentorTable.Columns.ID + " = " + mentor.getID(), null);
   }

   public static boolean deleteMentorByID(Context context, int mentorID) {
      context.getContentResolver()
              .delete(MENTOR_TABLE_URI, ClassKeeperDBSchema.MentorTable.Columns.ID + " = " + mentorID, null);
      return true;
   }

}
