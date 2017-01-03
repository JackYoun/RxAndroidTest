package com.jack.app.rxtest.subject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jack on 2017-01-03.
 */

public class SubjectActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, SubjectActivity.class);
        activity.startActivity(intent);
    }
}
