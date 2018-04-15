package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class Assessment {
    private int id;
    private int courseID;
    private String type;
    private String title;
    private String dueDate;

    public Assessment(int id, int courseID, String type, String title, String dueDate) {
        this.id = id;
        this.courseID = courseID;
        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
    }

    public Assessment(int courseID, String type, String title, String dueDate) {
        this.courseID = courseID;
        this.type = type;
        this.title = title;
        this.dueDate = dueDate;
    }

    public int getID() {
        return this.id;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
