package com.example.classkeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Zachary Bennett on 4/8/2018.
 */

public class AlertReceiver extends BroadcastReceiver {
   private static final String ASSESSMENT = "ASSESSMENT";

   public AlertReceiver() {}

   @Override
   public void onReceive(Context context, Intent intent) {
      String alertType = intent.getStringExtra("ALERT_TYPE");
      int alertSourceID = intent.getIntExtra("SOURCE_ID", 0);

      Class alertServiceClass = alertType.equals(ASSESSMENT) ? AssessmentAlertService.class : CourseAlertService.class;
      Intent alertServiceIntent = new Intent(context, alertServiceClass);
      alertServiceIntent.putExtra("SOURCE_ID", alertSourceID);
      alertServiceIntent.putExtra("IS_NOTIFYING_OF_ALARM", intent.getBooleanExtra("IS_NOTIFYING_OF_ALARM", false));

      context.startService(alertServiceIntent);
   }
}
