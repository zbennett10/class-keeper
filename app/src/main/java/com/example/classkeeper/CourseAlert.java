package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class CourseAlert extends Alert {
   private int id;
   private int courseID;
   private String date;
   private String message;

   public CourseAlert(int id, int courseID, String date, String message) {
     this.id = id;
     this.courseID = courseID;
     this.date = date;
     this.message = message;
   }

   public int getID() {
       return this.id;
   }

   public int getCourseID() {
       return this.courseID;
   }

   public String getDate() {
       return this.date;
   }

   public String getMessage() {
       return this.message;
   }
}
