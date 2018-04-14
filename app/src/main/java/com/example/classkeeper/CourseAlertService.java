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
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Zachary Bennett on 4/8/2018.
 */

public class CourseAlertService extends IntentService {

    public static final String COURSE_CHANNEL_ID = "com.example.classkeeper.COURSE";
    public static final String COURSE_CHANNEL_NAME = "COURSE_ALERT";

    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    NotificationChannel courseServiceChannel = null;
    NotificationManager notificationManager = null;

    public CourseAlertService() {
        super("CourseAlertService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int courseID = intent.getIntExtra("SOURCE_ID", 0);
        boolean isNotifyingOfAlarm = intent.getBooleanExtra("IS_NOTIFYING_OF_ALARM", false);
        System.out.println("is notifying bool: " + isNotifyingOfAlarm);
        Course correspondingCourse = DatabaseQueryBank.getCourse(this, courseID);

        //Grab the current alert id from preferences
        int currentAlarmID = getNextAlertID(this);

        //Instantiate intent
        Intent intentToViewCourse = new Intent(this, CourseDetails.class);
        intentToViewCourse.putExtra("COURSE_ID", courseID);

        //Add intent to the TaskStack
        stackBuilder.addNextIntentWithParentStack(intentToViewCourse);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //build and send notification
        if(notificationManager == null) {
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if(Build.VERSION.SDK_INT >= 26 && courseServiceChannel == null) {
            courseServiceChannel = new NotificationChannel(COURSE_CHANNEL_ID, COURSE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(courseServiceChannel);
        }

        NotificationCompat.Builder notificationMeta = new NotificationCompat.Builder(this, COURSE_CHANNEL_ID);

        NotificationCompat.Builder startDateNotificationBuilder = new NotificationCompat.Builder(this, COURSE_CHANNEL_ID);
        startDateNotificationBuilder.setContentTitle("Course Alert For: " + correspondingCourse.getTitle());
        startDateNotificationBuilder.setContentText("Course starting: " + correspondingCourse.getStart());
        startDateNotificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        startDateNotificationBuilder.setContentIntent(resultPendingIntent).setAutoCancel(true);

        NotificationCompat.Builder endDateNotificationBuilder = new NotificationCompat.Builder(this, COURSE_CHANNEL_ID);
        endDateNotificationBuilder.setContentTitle("Course Alert For: " + correspondingCourse.getTitle());
        endDateNotificationBuilder.setContentText("Course ending: " + correspondingCourse.getEnd());
        endDateNotificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        endDateNotificationBuilder.setContentIntent(resultPendingIntent).setAutoCancel(true);
        if(isNotifyingOfAlarm) {
            notificationMeta.setContentTitle("Course Alerts Set For " + correspondingCourse.getTitle());
            notificationMeta.setContentText("Course starting: " + correspondingCourse.getStart() + " and " + "ending " + correspondingCourse.getEnd());
            notificationMeta.setSmallIcon(android.R.drawable.ic_dialog_alert);
            notificationMeta.setContentIntent(resultPendingIntent).setAutoCancel(true);
            notificationManager.notify(currentAlarmID, notificationMeta.build());
        } else {
            notificationManager.notify(currentAlarmID, startDateNotificationBuilder.build());
            notificationManager.notify(currentAlarmID, endDateNotificationBuilder.build());
        }
    }

    public static boolean setCourseAlerts(Context context, Course course) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        SharedPreferences courseAlertPrefs = context.getSharedPreferences("courseAlerts", Context.MODE_PRIVATE);
        int alertID = courseAlertPrefs.getInt("currentAlertID", 0);

        Intent intentAlarm = new Intent(context, AlertReceiver.class);
        intentAlarm.putExtra("ALERT_TYPE", "COURSE");
        intentAlarm.putExtra("SOURCE_ID", course.getID());


        alarmManager.set(AlarmManager.RTC_WAKEUP, Util.convertAppDateToLong(course.getStart()), PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager.set(AlarmManager.RTC_WAKEUP, Util.convertAppDateToLong(course.getEnd()), PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));


        if(Build.VERSION.SDK_INT >= 21) {
            for (AlarmManager.AlarmClockInfo aci = alarmManager.getNextAlarmClock();
                 aci != null;
                 aci = alarmManager.getNextAlarmClock()) {
                Log.d(TAG, aci.getShowIntent().toString());
                Log.d(TAG, String.format("Trigger time: %d", aci.getTriggerTime()));
            }
        }

        Intent intentToNotifyOfAlarm = new Intent(context, AlertReceiver.class);
        intentToNotifyOfAlarm.putExtra("ALERT_TYPE", "COURSE");
        intentToNotifyOfAlarm.putExtra("SOURCE_ID", course.getID());
        intentToNotifyOfAlarm.putExtra("IS_NOTIFYING_OF_ALARM", true);

        //this alarm notifies that a notification was set
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), PendingIntent.getBroadcast(context, 1, intentToNotifyOfAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        SharedPreferences.Editor editor = courseAlertPrefs.edit();
        editor.putInt(Integer.toString(course.getID()), alertID);
        editor.commit();
        return true;
    }

    private int getNextAlertID(Context context) {
        SharedPreferences courseAlertPrefs = context.getSharedPreferences("courseAlerts", Context.MODE_PRIVATE);
        return courseAlertPrefs.getInt("currentAlertID", 0);
    }

    private void incrementAlertID(Context context) {
        SharedPreferences courseAlertPrefs = this.getSharedPreferences("courseAlerts", Context.MODE_PRIVATE);
        int currentAlertID = courseAlertPrefs.getInt("currentAlertID", 0);
        SharedPreferences.Editor prefEditor = courseAlertPrefs.edit();
        prefEditor.putInt("currentAlertID", currentAlertID + 1);
        prefEditor.commit();
    }
}
