package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class Mentor {
    private int id;
    private int courseID;
    private String name;
    private String phoneNumber;

    public Mentor(int id, int courseID, String name, String phoneNumber) {
        this.id = id;
        this.courseID = courseID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getID() {
        return this.id;
    }

    public int courseID() {
        return this.courseID;
    }

    public String getName() {
        return this.name;
    }

    public String phoneNumber() {
       return this.phoneNumber;
    }
}
