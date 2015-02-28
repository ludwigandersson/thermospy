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

package com.luan.thermospy.android.service;

import android.os.Binder;

/**
<<<<<<< HEAD
 * The Service binder object. Requested by the MainActivity so it can fetch the LocalService
 * after a successfull bound.
=======
 * Created by ludde on 15-02-24.
>>>>>>> 490ad3b24612c7ca510805e33294de062c538504
 */
public class ServiceBinder extends Binder {

    private final LocalService mLocalService;

    public ServiceBinder(LocalService localService) {
        mLocalService = localService;
    }

    public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return mLocalService;
    }

    
}
