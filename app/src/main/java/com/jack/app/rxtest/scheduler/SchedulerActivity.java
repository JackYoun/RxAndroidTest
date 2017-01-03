package com.jack.app.rxtest.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jack on 2017-01-03.
 */

public class SchedulerActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, SchedulerActivity.class);
        activity.startActivity(intent);
    }
}
