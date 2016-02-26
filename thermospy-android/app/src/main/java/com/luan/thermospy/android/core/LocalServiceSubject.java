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

import com.luan.thermospy.android.core.pojo.Temperature;

/**
 * Created by ludde on 15-03-04.
 */
public interface LocalServiceSubject {
    public void registerObserver(LocalServiceObserver listener);
    public void unregisterObserver(LocalServiceObserver listener);
    public void temperatureChanged(Temperature temperatureEntry);
    public void alarmTriggered();
}
