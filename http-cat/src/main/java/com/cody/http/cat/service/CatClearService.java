package com.cody.http.cat.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.cody.http.cat.notification.NotificationManagement;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatClearService
 */
public class CatClearService extends IntentService {

    public CatClearService() {
        super(CatClearService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationManagement holder = NotificationManagement.getInstance(this);
        holder.clearBuffer();
        holder.dismiss();
    }

}