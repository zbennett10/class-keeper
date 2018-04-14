package com.example.classkeeper;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Zachary Bennett on 4/8/2018.
 */

public class AssessmentAlertService extends IntentService {
    public static final String ALERT_CHANNEL_ID = "com.example.classkeeper.ASSESSMENT";
    public static final String ALERT_CHANNEL_NAME = "ASSESSMENT_ALERT";

    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    NotificationChannel alertServiceChannel = null;
    NotificationManager notificationManager = null;

    public AssessmentAlertService() {
        super("AssessmentAlertService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int assessmentID = intent.getIntExtra("SOURCE_ID", 0);
        boolean isNotifyingOfAlarm = intent.getBooleanExtra("IS_NOTIFYING_OF_ALARM", false);
        Assessment correspondingAssessment = DatabaseQueryBank.getAssessment(this, assessmentID);


        //Grab the current alert id from preferences
        int currentAlarmID = getNextAlertID(this);

        //Instantiate intent
        Intent intentToViewAssessment = new Intent(this, AssessmentDetails.class);
        intentToViewAssessment.putExtra("ASSESSMENT_ID", assessmentID);

        //Add intent to the TaskStack
        stackBuilder.addNextIntentWithParentStack(intentToViewAssessment);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //build and send notification
        if(notificationManager == null) {
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if(Build.VERSION.SDK_INT >= 26 && alertServiceChannel == null) {
            alertServiceChannel = new NotificationChannel(ALERT_CHANNEL_ID, ALERT_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(alertServiceChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ALERT_CHANNEL_ID);
        notificationBuilder.setContentTitle("Assessment Alert For: " + correspondingAssessment.getTitle());
        notificationBuilder.setContentText("Assessment scheduled for " + correspondingAssessment.getDueDate());
        if(isNotifyingOfAlarm) {
           notificationBuilder.setContentTitle("Assessment Scheduled For: " + correspondingAssessment.getTitle());
        }
        notificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        notificationBuilder.setContentIntent(resultPendingIntent).setAutoCancel(true);

        notificationManager.notify(currentAlarmID, notificationBuilder.build());
    }

    public static boolean setAssessmentAlert(Context context, Assessment assessment) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        SharedPreferences assessmentAlertPrefs = context.getSharedPreferences("assessmentAlerts", Context.MODE_PRIVATE);
        int alertID = assessmentAlertPrefs.getInt("currentAlertID", 0);

        Intent intentAlarm = new Intent(context, AlertReceiver.class);
        intentAlarm.putExtra("ALERT_TYPE", "ASSESSMENT");
        intentAlarm.putExtra("SOURCE_ID", assessment.getID());

        alarmManager.set(AlarmManager.RTC_WAKEUP, Util.convertAppDateToLong(assessment.getDueDate()), PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        Intent intentToNotifyOfAlarm = new Intent(context, AlertReceiver.class);
        intentToNotifyOfAlarm.putExtra("ALERT_TYPE", "ASSESSMENT");
        intentToNotifyOfAlarm.putExtra("SOURCE_ID", assessment.getID());
        intentToNotifyOfAlarm.putExtra("IS_NOTIFYING_OF_ALARM", true);

        //this alarm notifies that a notification was set
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), PendingIntent.getBroadcast(context, 1, intentToNotifyOfAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        if(Build.VERSION.SDK_INT >= 21) {
            for (AlarmManager.AlarmClockInfo aci = alarmManager.getNextAlarmClock();
                 aci != null;
                 aci = alarmManager.getNextAlarmClock()) {
                Log.d(TAG, aci.getShowIntent().toString());
                Log.d(TAG, String.format("Trigger time: %d", aci.getTriggerTime()));
            }
        }

        SharedPreferences.Editor editor = assessmentAlertPrefs.edit();
        editor.putInt(Integer.toString(assessment.getID()), alertID);
        editor.commit();
        return true;
    }

    private int getNextAlertID(Context context) {
        SharedPreferences assessmentAlertPrefs = context.getSharedPreferences("assessmentAlerts", Context.MODE_PRIVATE);
        return assessmentAlertPrefs.getInt("currentAlertID", 0);
    }

    private void incrementAlertID(Context context) {
        SharedPreferences alertPreferences = this.getSharedPreferences("assessmentAlerts", Context.MODE_PRIVATE);
        int currentAlertID = alertPreferences.getInt("currentAlertID", 0);
        SharedPreferences.Editor prefEditor = alertPreferences.edit();
        prefEditor.putInt("currentAlertID", currentAlertID + 1);
        prefEditor.commit();
    }

}
