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

package com.luan.thermospy.android.fragments.tabs.listadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.luan.thermospy.android.R;
import com.luan.thermospy.android.fragments.tabs.listcontent.generic.ListContent;

import java.util.List;

/**
 * Created by ludde on 15-03-14.
 */
public class GenericItemListAdapter extends ArrayAdapter<ListContent> {

    private final Context mContext;
    private final List<ListContent> mList;

    public GenericItemListAdapter(Context context, List<ListContent> objects) {
        super(context, R.layout.fragment_serverinfo_list, objects);
        mList = objects;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListContent content = mList.get(position);
        if (content != null) {
            View v = inflater.inflate(content.getContentLayoutId(), parent, false);
            return content.populateContentView(v);
        }
        else
        {
            return null;
        }
    }
}
