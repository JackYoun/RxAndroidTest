package com.jack.app.rxtest.single;

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
import rx.SingleSubscriber;

/**
 * Created by Jack on 2017-01-03.
 */

public class SingleActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, SingleActivity.class);
        activity.startActivity(intent);
    }

    private TextViewLogger mLogger;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single);

        findViewById(R.id.btn_activity_single_1).setOnClickListener(v -> test1());
        findViewById(R.id.btn_activity_single_2).setOnClickListener(v -> test2());
        findViewById(R.id.btn_activity_single_3).setOnClickListener(v -> test3());

        mLogger = new TextViewLogger((TextView) findViewById(R.id.tv_activity_single_log), true);
    }

    /**
     * Single은 하나의 값 또는 오류 알림 둘 중 하나만 발행한다.
     */
    private void test1() {
        Single.create(new Single.OnSubscribe<String>() {
            @Override
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                singleSubscriber.onSuccess("It is a result."); // or singleSubscriber.onError(new RuntimeException("test error"));
            }
        }).subscribe(result -> mLogger.d("test1", result));
    }

    /**
     * concat: 여러개의 Single이 발행한 결과를 Observable이 발행하는 형태로 변환한다.
     */
    private void test2() {
        Single<String> single1 = Single.just("I'll send message to you.");
        Single<String> single2 = Single.just("It is a message.");//.delay(3, TimeUnit.SECONDS); //.observeOn(AndroidSchedulers.mainThread());
        Single.concat(single1, single2)
                .subscribe(result -> mLogger.d("test2", result));
    }

    private void test3() {
        Observable<String> observable = Observable.just("When your minds made up.");
        observable.toSingle()
                .concatWith(Single.just("There's no point trying to change it."))
                .subscribe(result -> mLogger.d("test3", result));
    }
}
