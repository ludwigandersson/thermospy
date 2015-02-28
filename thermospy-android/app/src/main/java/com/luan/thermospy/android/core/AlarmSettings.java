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

import com.luan.thermospy.android.fragments.AlarmCondition;

/**
 * Settings object. Contains alarm related data.
 */
public class AlarmSettings {
    AlarmCondition mAlarmCondition;
    String mAlarm;
    boolean mAlarmSwitchEnabled;

    private static interface AlarmSettingsStrings
    {
        String ALARM_TEXT = "alarm";
        String ALARM_SWITCH = "alarmswitch";
        String ALARM_CONDITION = "alarmcondition";

    }

    public static AlarmSettings fromDb(Database db) throws Exception {
        return new AlarmSettings(db.getString(AlarmSettingsStrings.ALARM_TEXT),
                                 db.getBoolean(AlarmSettingsStrings.ALARM_SWITCH),
                                 AlarmCondition.fromInt(db.getInt(AlarmSettingsStrings.ALARM_CONDITION)));
    }

    public AlarmSettings(String alarm, boolean alarmSwitchEnabled, AlarmCondition alarmCondition) {
        this.mAlarmCondition = alarmCondition;
        this.mAlarm = alarm;
        this.mAlarmSwitchEnabled = alarmSwitchEnabled;
    }

    public AlarmCondition getAlarmCondition() {
        return mAlarmCondition;
    }

    public void setAlarmCondition(AlarmCondition alarmCondition) {
        this.mAlarmCondition = alarmCondition;
    }

    public String getAlarm() {
        return mAlarm;
    }

    public void setAlarm(String alarm) {
        this.mAlarm = alarm;
    }

    public boolean isAlarmSwitchEnabled() {
        return mAlarmSwitchEnabled;
    }

    public void setAlarmSwitchEnabled(boolean alarmSwitchEnabled) {
        this.mAlarmSwitchEnabled = alarmSwitchEnabled;
    }

    protected void serialize(Database database) {
        try {
            database.putString(AlarmSettingsStrings.ALARM_TEXT, mAlarm);
            database.putInt(AlarmSettingsStrings.ALARM_CONDITION, mAlarmCondition.getId());
            database.putBoolean(AlarmSettingsStrings.ALARM_SWITCH, mAlarmSwitchEnabled);
        } catch (Exception e) {

        }
    }
}
