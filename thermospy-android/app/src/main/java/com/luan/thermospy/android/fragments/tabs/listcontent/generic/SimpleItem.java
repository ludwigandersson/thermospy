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

package com.luan.thermospy.android.fragments.tabs.listcontent.generic;

import android.view.View;
import android.widget.TextView;

import com.luan.thermospy.android.R;

/**
 * Item used in a list view. Holds text, description and value
 */




    /**
     * A simple text/description/value item representing a piece of content.
     */
     public class SimpleItem implements ListContent {
        private String mText;
        private String mDescription;
        private String mValue;
        private TextView mTxtValue;
        private TextView mTxtDesc;
        private TextView mTxtName;

        public SimpleItem(String text, String des, String value) {
            this.mText = text;
            this.mValue = value;
            this.mDescription = des;
        }

        public SimpleItem(String text, String value) {
            this.mText = text;
            this.mValue = value;
            this.mDescription = "";
        }

        public void setText(String text) {
            mText = text;
            if (mTxtName != null) {
                mTxtName.setText(mText);
            }
        }

        public void setValue(String value) {
            mValue = value;
            if (mTxtValue != null) {
                mTxtValue.setText(mValue);
            }
        }

        public void setDescription(String description) {
            mDescription = description;
            if (mTxtDesc != null) {
                mTxtDesc.setText(mDescription);
            }
        }

        @Override
        public int getContentLayoutId() {
            return R.layout.simple_list_item;
        }

        @Override
        public View populateContentView(View view) {

            mTxtName = (TextView)view.findViewById(R.id.txtText);
            mTxtDesc = (TextView)view.findViewById(R.id.txtDescription);
            mTxtValue = (TextView)view.findViewById(R.id.txtValue);

            mTxtName.setText(mText);
            mTxtDesc.setText(mDescription);
            mTxtValue.setText(mValue);

            return view;
        }
    }


