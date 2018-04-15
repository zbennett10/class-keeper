package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class Term {
    private int id;
    private String title;
    private String start;
    private String end;

    public Term(int id, String title, String start, String end) {
       this.id = id;
       this.title = title;
       this.start = start;
       this.end = end;
    }

    public Term(String title, String start, String end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public int getID() {
        return this.id;
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

    @Override
    public String toString() {
        return this.title;
    }
}
