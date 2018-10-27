/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.system;

import static android.content.Context.CARRIER_CONFIG_SERVICE;
import static android.content.Context.SYSTEM_UPDATE_SERVICE;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemUpdateManager;
import android.os.UserManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.telephony.CarrierConfigManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SystemUpdatePreferenceController extends BasePreferenceController {

    private static final String TAG = "SimplixUpdatePrefContr";

    private static final String KEY_SYSTEM_UPDATE_SETTINGS = "system_update_settings";
    
    private Intent centerintent;

    public SystemUpdatePreferenceController(Context context) {
        super(context, KEY_SYSTEM_UPDATE_SETTINGS);
        Intent centerintent = new Intent().setComponent(new ComponentName("com.simplix.center", "com.simplix.center.MainActivity"));
    }

    @Override
    public int getAvailabilityStatus() {
    	centerintent = new Intent().setComponent(new ComponentName("com.simplix.center", "com.simplix.center.MainActivity"));
    	if (centerintent == null) {
    		Log.w(TAG, "Oops! Center app missing...");
    	}
        return centerintent != null
                ? AVAILABLE
                : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        if (isAvailable()) {
            Utils.updatePreferenceToSpecificActivityOrRemove(mContext, screen,
                    getPreferenceKey(),
                    Utils.UPDATE_PREFERENCE_FLAG_SET_TITLE_TO_MATCHING_ACTIVITY);
        }
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(getPreferenceKey(), preference.getKey())) {
        	mContext.startActivity(centerintent);
        }
        // always return false here because this handler does not want to block other handlers.
        return false;
    }

    @Override
    public CharSequence getSummary() {
        CharSequence summary = mContext.getString(R.string.simplix_version_summary);
        return summary;
    }

    /**
     * Trigger client initiated action (send intent) on system update
     */
    private void ciActionOnSysUpdate(PersistableBundle b) {
        return;
    }
}
