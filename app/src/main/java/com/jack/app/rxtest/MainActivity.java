package com.jack.app.rxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jack.app.rxtest.fundamental.FundamentalActivity;
import com.jack.app.rxtest.operator.OperatorActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_fundamental).setOnClickListener(this);
        findViewById(R.id.btn_operator).setOnClickListener(this);
        findViewById(R.id.btn_job_scheduler).setOnClickListener(this);
        findViewById(R.id.btn_rx_binding).setOnClickListener(this);

//        Observable<Integer> observable = Observable.create(subscriber -> {
//            for (Integer i : new Integer[]{1, 2, 3, 4, 5}) {
//                Log.v(TAG, Thread.currentThread().getName() + " : onNext " + i);
//                subscriber.onNext(i);
//            }
//        });
//
//        observable.subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        integer -> Log.v(TAG, Thread.currentThread().getName() + " : call " + integer),
//                        e -> Log.v(TAG, Thread.currentThread().getName() + " : onErrorHandler")
//                );

////        Observable<String> simpleObservable = Observable.create(new Observable.OnSubscribe<String>() {
////            @Override
////            public void call(Subscriber<? super String> subscriber) {
////                subscriber.onNext("Hello RxAndroid");
////                subscriber.onCompleted();
////            }
////        });
//        Observable<String> simpleObservable = Observable.just("Hello RxAndroid");
//
//        simpleObservable.subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Log.e(TAG, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG, "onNext: " + s);
//            }
//        });
//
//        simpleObservable.map(text -> text.length()).subscribe(length -> Log.e(TAG, "length: " + length));
//
//        RxView.clicks(findViewById(R.id.btn_activity_main))
//                .map(event -> new Random().nextInt())
//                .subscribe(value -> {
//                    TextView textView = (TextView) findViewById(R.id.tv_activity_main);
//                    textView.setText(String.valueOf(value));
//                }, throwable -> {
//                    Log.e(TAG, "Error: " + throwable.getMessage());
//                });
//
//        Observable<String> leftObservable = RxView.clicks(findViewById(R.id.btn_activity_main_left)).map(event -> "left");
//        Observable<String> rightObservable = RxView.clicks(findViewById(R.id.btn_activity_main_right)).map(event -> "right");
//        Observable<String> together = Observable.merge(leftObservable, rightObservable);
//        together.subscribe(text -> ((TextView) findViewById(R.id.tv_activity_main)).setText(text));
////        together.map(text -> text.toUpperCase()).subscribe(text -> Toast.makeText(this, text, Toast.LENGTH_SHORT).show());
//
//        Observable<Integer> minuses = RxView.clicks(findViewById(R.id.btn_activity_main_minus)).map(event -> -1);
//        Observable<Integer> pluses = RxView.clicks(findViewById(R.id.btn_activity_main_plus)).map(event -> 1);
//        Observable<Integer> merged = Observable.merge(minuses, pluses);
//        merged.scan(0, (sum, number) -> sum + 1).subscribe(count -> ((TextView) findViewById(R.id.tv_activity_count)).setText(count.toString()));
//        merged.scan(0, (sum, number) -> sum + number).subscribe(number -> ((TextView) findViewById(R.id.tv_activity_number)).setText(number.toString()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fundamental:
                FundamentalActivity.invoke(this);
                break;

            case R.id.btn_operator:
                OperatorActivity.invoke(this);
                break;

            case R.id.btn_job_scheduler:
                JobSchedulerActivity.invoke(this);
                break;

            case R.id.btn_rx_binding:
                RxBindingActivity.invoke(this);
                break;
        }
    }
}
