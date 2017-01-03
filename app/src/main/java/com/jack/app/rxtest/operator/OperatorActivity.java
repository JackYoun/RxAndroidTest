package com.jack.app.rxtest.operator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jack on 2017-01-03.
 */

public class OperatorActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, OperatorActivity.class);
        activity.startActivity(intent);
    }
}
