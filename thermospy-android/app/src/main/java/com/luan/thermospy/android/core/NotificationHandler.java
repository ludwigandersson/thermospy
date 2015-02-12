/*
 * Copyright 2015 Ludwig Andersson
 *
 * This file is part of Thermospy-android.
 *
 * Thermospy-android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Thermospy-android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermospy-android.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.luan.thermospy.android.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.activities.MainActivity;

/**
 * Responsible for displaying notifications, updating notifications and sounding the alarm.
 */
public class NotificationHandler {
    private final int mId = 1;
    public void show(Context c, String temperature, String alarm, boolean playSound)
    {
        NotificationCompat.Builder  mBuilder = createBuilder(c, temperature, alarm, playSound);

        NotificationManager mNotificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(mId, mBuilder.build());
    }

    public void cancel(Context c)
    {
        NotificationManager mNotificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(mId);
    }

    public void update(Context c, String temperature, String alarm)
    {

        NotificationManager mNotificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = createBuilder(c, temperature, alarm, false);

        mNotificationManager.notify(
                mId,
                builder.build());
    }

    public void playSound(Context c, String temperature, String alarm) {
        NotificationManager mNotificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = createBuilder(c, temperature, alarm, true);

        mNotificationManager.notify(
                mId,
                builder.build());
    }

    private NotificationCompat.Builder createBuilder(Context c, String temperature, String alarm, boolean playSound)
    {
        String text = "Current temperature is "+temperature;
        if (!alarm.isEmpty())
        {
            text += " of "+alarm;
        }

        NotificationCompat.Builder mBuilder  = new NotificationCompat.Builder(c)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentTitle("Thermospy")
                .setContentText(text)
                .setOnlyAlertOnce(false)
                .setSmallIcon(R.drawable.ic_stat_action_assignment_late);

        Uri alarmSound = null;
        if (playSound )
        {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }

        mBuilder.setSound(alarmSound);

        Intent resultIntent = new Intent(c, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        return mBuilder;
    }

}
