package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class Mentor {
    private int id;
    private int courseID;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Mentor(int id, int courseID, String name, String phoneNumber, String emailAddress) {
        this.id = id;
        this.courseID = courseID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public Mentor(int courseID, String name, String phoneNumber, String emailAddress) {
        this.courseID = courseID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public int getID() {
        return this.id;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
       return this.phoneNumber;
    }

    public String getEmailAddress() { return this.emailAddress; }
}
