package com.example.classkeeper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zachary Bennett on 4/9/2018.
 */

public class Util {

   public static long convertAppDateToLong(String MMDDYYYY) {
      SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
      long time = 0;
      try {
         Date date = dateFormatter.parse(MMDDYYYY);
         time = date.getTime();
      } catch (ParseException e) {
         e.printStackTrace();
      }

      return time;
   }

   public static long getCurrentTime() {
      SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
      return convertAppDateToLong(dateFormatter.format(new Date()));
   }
}
