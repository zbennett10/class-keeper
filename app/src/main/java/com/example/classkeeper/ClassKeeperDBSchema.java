package com.example.classkeeper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by Zachary Bennett on 3/25/2018.
 * The following class is the schema for the application database.
 */

public class ClassKeeperDBSchema {

    public static String[] getColumnNames(String tableName) {
        Class columnClass = getColumnClassByTableName(tableName);
        Field[] columnFields = columnClass.getDeclaredFields();
        String[] columnNames = new String[columnFields.length];
        int index = 0;
        for(Field field : columnFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                columnNames[index] = field.getName();
            }
        }
        return columnNames;
    }

    public static Class getColumnClassByTableName(String tableName) {
        Class table;
        switch(tableName) {
            case TermTable.NAME:
                table = TermTable.Columns.class;
                break;
            case CourseTable.NAME:
                table = CourseTable.Columns.class;
                break;
            case AssessmentTable.NAME:
                table = AssessmentTable.Columns.class;
                break;
            case NoteTable.NAME:
                table = NoteTable.Columns.class;
                break;
            case MentorTable.NAME:
                table = MentorTable.Columns.class;
                break;
            case CourseAlertsTable.NAME:
                table = CourseAlertsTable.Columns.class;
                break;
            case AssessmentAlertsTable.NAME:
                table = AssessmentAlertsTable.Columns.class;
                break;
            default:
                table = null;
        }
        return table;
    }

    public static final class TermTable {
        public static final String NAME = "terms";

        public static final class Columns {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String START = "start";
            public static final String END = "end";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.TITLE + " TEXT, " +
                        Columns.START + " DATE, " +
                        Columns.END + " DATE, " +
                    ")";

    }

    public static final class CourseTable {
        public static final String NAME = "courses";

        public static final class Columns {
            public static final String ID = "id";
            public static final String TERM_ID = "termID";
            public static final String TITLE = "title";
            public static final String START = "start";
            public static final String END = "end";
            public static final String STATUS = "status";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.TERM_ID + " INTEGER, " +
                        Columns.TITLE + " TEXT, " +
                        Columns.START + " DATE, " +
                        Columns.END + " DATE, " +
                        Columns.STATUS + " TEXT, " +
                        "FOREIGN KEY(" +
                                Columns.TERM_ID + ") REFERENCES " + TermTable.NAME + "(" + TermTable.Columns.ID +
                            ")" +
                        ")";
    }

    public static final class AssessmentTable {
        public static final String NAME = "assessments";

        public static final class Columns {
            public static final String ID = "id";
            public static final String COURSE_ID = "courseID";
            public static final String TYPE = "type";
            public static final String TITLE = "title";
            public static final String DUE_DATE = "dueDate";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COURSE_ID + " INTEGER, " +
                        Columns.TITLE + " TEXT, " +
                        Columns.TYPE + " TEXT, " +
                        Columns.DUE_DATE + " DATE, " +
                        "FOREIGN KEY(" +
                                Columns.COURSE_ID + ") REFERENCES " + CourseTable.NAME + "(" + CourseTable.Columns.ID +
                            ")" +
                        ")";
    }

    public static final class NoteTable {
        public static final String NAME = "notes";

        public static final class Columns {
            public static final String ID = "id";
            public static final String COURSE_ID = "courseID";
            public static final String CONTENT = "content";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COURSE_ID + " INTEGER, " +
                        Columns.CONTENT + " TEXT, " +
                        "FOREIGN KEY(" +
                                Columns.COURSE_ID + ") REFERENCES " + CourseTable.NAME + "(" + CourseTable.Columns.ID +
                            ")" +
                        ")";
    }

    public static final class MentorTable {
        public static final String NAME = "mentors";

        public static final class Columns {
            public static final String ID = "id";
            public static final String COURSE_ID = "courseID";
            public static final String NAME = "name";
            public static final String PHONE_NUMBER = "phoneNumber";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COURSE_ID + " INTEGER, " +
                        Columns.NAME + " TEXT, " +
                        Columns.PHONE_NUMBER + " TEXT, " +
                        "FOREIGN KEY(" +
                                Columns.COURSE_ID + ") REFERENCES " + CourseTable.NAME + "(" + CourseTable.Columns.ID +
                            ")" +
                        ")";
    }

    public static final class CourseAlertsTable {
        public static final String NAME = "coursealerts";

        public static final class Columns {
            public static final String ID = "id";
            public static final String COURSE_ID = "courseID";
            public static final String DATE = "date";
            public static final String MESSAGE = "message";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.COURSE_ID + " INTEGER, " +
                        Columns.DATE + " DATE, " +
                        Columns.MESSAGE + " TEXT, " +
                        "FOREIGN KEY(" +
                                Columns.COURSE_ID + ") REFERENCES " + CourseTable.NAME + "(" + CourseTable.Columns.ID +
                            ")" +
                        ")";
    }

    public static final class AssessmentAlertsTable {
        public static final String NAME = "assessmentalerts";

        public static final class Columns {
            public static final String ID = "id";
            public static final String ASSESSMENT_ID = "assessmentID";
            public static final String DATE = "date";
            public static final String MESSAGE = "message";
        }

        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + NAME + " (" +
                        Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Columns.ASSESSMENT_ID + " INTEGER, " +
                        Columns.DATE + " DATE, " +
                        Columns.MESSAGE + " TEXT, " +
                        "FOREIGN KEY(" +
                                Columns.ASSESSMENT_ID + ") REFERENCES " + AssessmentTable.NAME + "(" + AssessmentTable.Columns.ID +
                            ")" +
                        ")";
    }
}
