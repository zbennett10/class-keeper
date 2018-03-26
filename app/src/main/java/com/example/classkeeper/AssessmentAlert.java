package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class AssessmentAlert {
    private int id;
    private int assessmentID;
    private String date;
    private String message;

    public AssessmentAlert(int id, int assessmentID, String date, String message) {
       this.id = id;
       this.assessmentID = assessmentID;
       this.date = date;
       this.message = message;
    }

    public int getID() {
        return this.id;
    }

    public int getAssessmentID() {
        return this.assessmentID;
    }

    public String getDate() {
        return this.date;
    }

    public String getMessage() {
        return this.message;
    }
}
