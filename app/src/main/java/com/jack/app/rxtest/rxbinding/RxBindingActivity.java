package com.jack.app.rxtest.rxbinding;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jack on 2017-01-03.
 */

public class RxBindingActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, RxBindingActivity.class);
        activity.startActivity(intent);
    }
}
