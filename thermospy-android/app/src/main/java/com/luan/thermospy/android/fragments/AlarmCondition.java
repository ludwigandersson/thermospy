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
 * Thermospy-server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.luan.thermospy.android.fragments;

/**
 * Created by ludde on 15-02-03.
 */
public enum AlarmCondition {

    GREATER_THAN_OR_EQUAL(0),
    LESSER_THAN_OR_EQUAL(1);

    private final int id;
    AlarmCondition(int id)
    {
        this.id = id;
    }

    public int getId() { return this.id; }

    public <T extends Comparable<T>> boolean evaluate(T lval, T rval)
    {
        int cmp = lval.compareTo(rval);
        if (cmp == 0) {
            return true;
        } else {
            switch (fromInt(id)) {
                case GREATER_THAN_OR_EQUAL:
                    return cmp > 0;
                case LESSER_THAN_OR_EQUAL:
                    return cmp < 0;
                default:
                    return false;
            }

        }
    }

    public static AlarmCondition fromInt(int id) {
        switch (id)
        {
            case 0:
                return GREATER_THAN_OR_EQUAL;
            case 1:
                return LESSER_THAN_OR_EQUAL;
            default:
                return GREATER_THAN_OR_EQUAL;
        }
    }
}
