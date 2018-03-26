package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class Course {
    private int id;
    private int termID;
    private String title;
    private String start;
    private String end;
    private String status;

    public Course(int id, int termID, String title, String start, String end, String status) {
       this.id = id;
       this.termID = termID;
       this.title = title;
       this.start = start;
       this.end = end;
       this.status = status;
    }

    public int getID() {
        return this.id;
    }

    public int getTermID() {
        return this.termID;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    public String getStatus() {
        return this.status;
    }
}
