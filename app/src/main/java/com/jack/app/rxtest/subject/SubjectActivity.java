package com.jack.app.rxtest.subject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jack.app.rxtest.R;
import com.jack.app.rxtest.util.TextViewLogger;

import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by Jack on 2017-01-03.
 */

public class SubjectActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, SubjectActivity.class);
        activity.startActivity(intent);
    }

    private TextViewLogger mLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subject);

        findViewById(R.id.btn_activity_subject_async_1).setOnClickListener(v -> testAsyncSubject1());
        findViewById(R.id.btn_activity_subject_async_2).setOnClickListener(v -> testAsyncSubject2());
        findViewById(R.id.btn_activity_subject_async_3).setOnClickListener(v -> testAsyncSubject3());
        findViewById(R.id.btn_activity_subject_behavior_1).setOnClickListener(v -> testBehaviorSubject1());
        findViewById(R.id.btn_activity_subject_behavior_2).setOnClickListener(v -> testBehaviorSubject2());
        findViewById(R.id.btn_activity_subject_publish_1).setOnClickListener(v -> testPublishSubject1());
        findViewById(R.id.btn_activity_subject_publish_2).setOnClickListener(v -> testPublishSubject2());
        findViewById(R.id.btn_activity_subject_replay_1).setOnClickListener(v -> testReplaySubject1());
        findViewById(R.id.btn_activity_subject_replay_2).setOnClickListener(v -> testReplaySubject2());

        mLogger = new TextViewLogger((TextView) findViewById(R.id.tv_activity_subject_log), true);
    }

    /**
     * AsyncSubject: Observable로 부터 발행된 마지막 값만 발행하고 source observable의 동작이 완료된 후에 동작한다.
     */
    private void testAsyncSubject1() {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(result -> mLogger.d("testAsyncSubject1", result));
        asyncSubject.onNext("first");
        asyncSubject.onNext("second");
        mLogger.d("testAsyncSubject1", "before complete");
        asyncSubject.onCompleted();
        asyncSubject.onNext("third");
    }

    /**
     * Observable이 오류로 인해 종료될 경우 AsyncSubject는 아무 항목도 발행하지 않고 발생한 오류를 그대로 전달한다.
     */
    private void testAsyncSubject2() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(result -> mLogger.d("testAsyncSubject2", result.toString()),
                error -> mLogger.d("testAsyncSubject2", error.getMessage()));
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        mLogger.d("testAsyncSubject2", "before error");
        asyncSubject.onError(new RuntimeException("Error occurred."));
    }

    /**
     * 마지막 값만 발행되고 완료된 이후에 subscribe 할 경우에도 마지막 값을 발행한다.
     */
    private void testAsyncSubject3() {
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(result -> mLogger.d("testAsyncSubject3", "first subscriber: " + result.toString()));
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.subscribe(result -> mLogger.d("testAsyncSubject3", "second subscriber: " + result.toString()));
        asyncSubject.onNext(3);
        mLogger.d("testAsyncSubject3", "before complete");
        asyncSubject.onCompleted();
        asyncSubject.subscribe(result -> mLogger.d("testAsyncSubject3", "third subscriber: " + result.toString()));
    }

    /**
     * BehaviorSubject: subscribe를 시작하면 source observable이 가장 최근에 발행한 항목의 발행을 시작하며 이후 source observable에 의해 발행된 항목을 계속 발행한다.
     * Source observable이 아무값도 발행하지 않았다면 맨 처음 값이나 기본 값의 발행을 시작한다.
     */
    private void testBehaviorSubject1() {
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.subscribe(result -> mLogger.d("testBehaviorSubject1", "first subscriber: " + result));
        behaviorSubject.onNext("first");
        behaviorSubject.onNext("second");
        behaviorSubject.subscribe(result -> mLogger.d("testBehaviorSubject1", "second subscriber: " + result)); // 가장 최근에 발행한 항목이 먼저 발행된다.
        behaviorSubject.onNext("third");
        behaviorSubject.onNext("fourth");
        behaviorSubject.onNext("fifth");
        behaviorSubject.onCompleted();
        behaviorSubject.onNext("sixth"); // 발행 안됨.
        behaviorSubject.subscribe(result -> mLogger.d("testBehaviorSubject1", "third subscriber: " + result)); // 완료 이후엔 아무것도 발행되지 않음.
    }

    /**
     * Error가 발행된 BehaviorSubject에 subscribe하면 source에서 발행한 오류를 그대로 전달한다.
     */
    private void testBehaviorSubject2() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.subscribe(result -> mLogger.d("testBehaviorSubject2", "first subscriber: " + result),
                error -> mLogger.d("testBehaviorSubject2", "first subscriber: " + error.getMessage()));
        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);
        behaviorSubject.onError(new RuntimeException("Error occurred"));
        behaviorSubject.onNext(3);
        behaviorSubject.subscribe(result -> mLogger.d("testBehaviorSubject2", "second subscriber: " + result),
                error -> mLogger.d("testBehaviorSubject2", "second subscriber: " + error.getMessage()));
    }

    /**
     * PublishSubject: subscribe 이후에 source observable이 발행한 항목들만 발행한다.
     */
    private void testPublishSubject1() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.subscribe(result -> mLogger.d("testPublishSubject1", "first subscriber: " + result.toString()));
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.subscribe(result -> mLogger.d("testPublishSubject1", "second subscriber: " + result.toString()));
        publishSubject.onNext(3);
        publishSubject.onNext(4);
        publishSubject.onNext(5);
        publishSubject.onCompleted();
        publishSubject.subscribe(result -> mLogger.d("testPublishSubject1", "third subscriber: " + result.toString()));
    }

    private void testPublishSubject2() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.subscribe(result -> mLogger.d("testPublishSubject2", "first subscriber: " + result.toString()),
                error -> mLogger.d("testPublishSubject2", "first subscriber: " + error.getMessage()));
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.subscribe(result -> mLogger.d("testPublishSubject2", "second subscriber: " + result.toString()),
                error -> mLogger.d("testPublishSubject2", "second subscriber: " + error.getMessage()));
        publishSubject.onNext(3);
        publishSubject.onError(new RuntimeException("Error occurred"));
        publishSubject.onNext(4);
        publishSubject.onNext(5);
        publishSubject.subscribe(result -> mLogger.d("testPublishSubject2", "third subscriber: " + result.toString()),
                error -> mLogger.d("testPublishSubject2", "third subscriber: " + error.getMessage()));
    }

    /**
     * ReplaySubject: 옵저버가 subscribe를 시작한 시점과 관계 없이 source observable이 발행한 모든 항목을 subscriber에게 발행한다.
     */
    private void testReplaySubject1() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(result -> mLogger.d("testReplaySubject1", "first subscriber: " + result.toString()));
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.subscribe(result -> mLogger.d("testReplaySubject1", "second subscriber: " + result.toString()));
        replaySubject.onNext(3);
        replaySubject.onNext(4);
        replaySubject.onNext(5);
        replaySubject.onCompleted();
        replaySubject.subscribe(result -> mLogger.d("testReplaySubject1", "third subscriber: " + result.toString()));
    }

    private void testReplaySubject2() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(result -> mLogger.d("testReplaySubject2", "first subscriber: " + result.toString()),
                error -> mLogger.d("testReplaySubject2", "first subscriber: " + error.getMessage()));
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onError(new RuntimeException("Error occurred"));
        replaySubject.subscribe(result -> mLogger.d("testReplaySubject2", "second subscriber: " + result.toString()),
                error -> mLogger.d("testReplaySubject2", "second subscriber: " + error.getMessage()));
        replaySubject.onNext(3);
        replaySubject.subscribe(result -> mLogger.d("testReplaySubject2", "third subscriber: " + result.toString()),
                error -> mLogger.d("testReplaySubject2", "third subscriber: " + error.getMessage()));
    }
}
