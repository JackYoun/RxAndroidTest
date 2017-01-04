package com.jack.app.rxtest.operator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jack.app.rxtest.R;
import com.jack.app.rxtest.util.TextViewLogger;

import rx.Observable;
import rx.Single;

/**
 * Created by Jack on 2017-01-03.
 */

public class OperatorActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, OperatorActivity.class);
        activity.startActivity(intent);
    }

    private TextViewLogger mLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_operator);

        findViewById(R.id.btn_activity_operator_just).setOnClickListener(v -> testJust());

        mLogger = new TextViewLogger((TextView) findViewById(R.id.tv_activity_operator_log), true);
    }

    /**
     * just: 명시한 항목을 배출한다.
     */
    private void testJust() {
        Observable.just("observable first item", "observable second item")
                .subscribe(result -> mLogger.d("testJust", result));

        Single.just("single item")
                .subscribe(result -> mLogger.d("testJust", result));
    }
}