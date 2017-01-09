package com.jack.app.rxtest.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jack.app.rxtest.R;
import com.jack.app.rxtest.util.TextViewLogger;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 2017-01-03.
 */

public class SchedulerActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, SchedulerActivity.class);
        activity.startActivity(intent);
    }

    private TextViewLogger mLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scheduler);

        findViewById(R.id.btn_activity_scheduler_1).setOnClickListener(v -> test1());
        findViewById(R.id.btn_activity_scheduler_2).setOnClickListener(v -> test2());
        findViewById(R.id.btn_activity_scheduler_3).setOnClickListener(v -> test3());
        findViewById(R.id.btn_activity_scheduler_4).setOnClickListener(v -> test4());

        mLogger = new TextViewLogger((TextView) findViewById(R.id.tv_activity_scheduler_log), true);
    }

    /**
     * observeOn:
     */
    private void test1() {
        Observable<String> observable = Observable.just("first item", "second item", "third item");
        observable.observeOn(Schedulers.computation())
                .subscribe(next -> printLog("test1", Thread.currentThread().getName() + " : " + next),
                        error -> printLog("test1", Thread.currentThread().getName() + " : " + error.getMessage()));
    }

    private void test2() {
        Observable<String> observable = Observable.just("first item", "second item", "third item");
        observable.subscribeOn(Schedulers.computation())
                .subscribe(next -> printLog("test2", Thread.currentThread().getName() + " : " + next),
                        error -> printLog("test2", Thread.currentThread().getName() + " : " + error.getMessage()));
    }

    private void test3() {
        Observable<String> observable = Observable.create(subscriber -> {
            printLog("test3", Thread.currentThread().getName() + " : OnSubscribe call");
            subscriber.onNext("first item");
            subscriber.onNext("second item");
            subscriber.onNext("third item");
        });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(next -> printLog("test3", Thread.currentThread().getName() + " : " + next),
                        error -> printLog("test3", Thread.currentThread().getName() + " : " + error.getMessage()));
        observable.subscribe(next -> printLog("test3", Thread.currentThread().getName() + " : (subscribe2) " + next));
    }

    private void test4() {
        final Observable<String> observable = Observable.create(subscriber -> {
            printLog("test4", Thread.currentThread().getName() + " : OnSubscribe call");
            subscriber.onNext("first item");
            subscriber.onNext("second item");
            subscriber.onNext("third item");
        });
        observable.subscribeOn(Schedulers.computation())
                .map(next -> {
                    printLog("test4", Thread.currentThread().getName() + " : (map) " + next);
                    return next.length();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next -> printLog("test4", Thread.currentThread().getName() + " : next: " + next),
                        error -> printLog("test4", Thread.currentThread().getName() + " : error: " + error.getMessage()),
                        () -> printLog("test4", Thread.currentThread().getName() + " : complete"));
    }

    private void printLog(String tag, String message) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mLogger.d(tag, message);
        } else {
            runOnUiThread(() -> mLogger.d(tag, message));
        }
    }
}
