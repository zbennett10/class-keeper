package com.example.classkeeper;

/**
 * Created by Zachary Bennett on 3/25/2018.
 */

public class Note {
   private int id;
   private int courseID;
   private String content;

   public Note(int id, int courseID, String content) {
     this.id = id;
     this.courseID = courseID;
     this.content = content;
   }

   public Note(int courseID, String content) {
        this.courseID = courseID;
        this.content = content;
   }

   public int getID() {
       return this.id;
   }

   public int getCourseID() {
       return this.courseID;
   }

   public String getContent() {
       return this.content;
   }

    @Override
    public String toString() {
        if(this.content.isEmpty()) {
            return "<Empty Note>";
        }
        int length = this.content.length() < 20 ? this.content.length() : 20;
        return this.content.substring(0, length) + "...";
    }
}
