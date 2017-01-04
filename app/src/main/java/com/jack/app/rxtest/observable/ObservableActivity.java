package com.jack.app.rxtest.observable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jack.app.rxtest.R;
import com.jack.app.rxtest.util.TextViewLogger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Jack on 2017-01-03.
 */

public class ObservableActivity extends AppCompatActivity {

    public static void invoke(Activity activity) {
        Intent intent = new Intent(activity, ObservableActivity.class);
        activity.startActivity(intent);
    }

    private TextViewLogger mLogger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_observable);

        findViewById(R.id.btn_activity_observable_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test1();
            }
        });

        findViewById(R.id.btn_activity_observable_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test2();
            }
        });

        findViewById(R.id.btn_activity_observable_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test3();
            }
        });

        findViewById(R.id.btn_activity_observable_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test4();
            }
        });

        findViewById(R.id.btn_activity_observable_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testWithJava8();
            }
        });

        mLogger = new TextViewLogger((TextView) findViewById(R.id.tv_activity_observable_log), true);
    }

    /**
     * Observable을 생성하고 문자열 'Hello world'를 subscriber로 발행하고 Subscriber는 해당 이벤트를 로그로 출력한다.
     * Subscriber의 onNext는 데이터가 발행될 때 마다 호출이 되고 데이터 발행이 끝이나면 onCompleted가 호출되고 발행중 에러가 발생하면 onError가 호출된다.
     * onCompleted와 onError는 둘중 하나만 호출된다.
     */
    private void test1() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello world");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mLogger.d("test1", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                mLogger.d("test1", "onError: " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                mLogger.d("test1", "onNext: " + s);
            }
        });
    }

    /**
     * Subscriber의 필요한 메서드만 Action interface로 구현할 수 있다.
     */
    private void test2() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello rx");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) { // onNext
                mLogger.d("test2", "action string: " + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) { // onError
                mLogger.d("test2", "action throwable: " + throwable.getMessage());
            }
        }, new Action0() {
            @Override
            public void call() { // onCompleted
                mLogger.d("test2", "action 0");
            }
        });
    }

    /**
     * just: 객체 하나 또는 집합을 Observable로 변환한다. 변환된 Observable은 원본 객체들을 발행(emit)한다. (생성 operator)
     * map: Observable이 발행한 항목에 함수를 적용한다. (변환 operator)
     * filter: 테스트 조건을 만족하는 항목들만 배출한다. (필터링 operator)
     */
    private void test3() {
        Observable.just("Hello map!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer length) {
                        return length % 2 == 0; // 짝수인가?
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer length) {
                        mLogger.d("test3", "length: " + length);
                    }
                });
    }

    /**
     * merge: 복수개의 Observable들이 발행하는 항목들을 merge시켜 하나의 Observable로 만든다. (결합 operator)
     */
    private void test4() {
        Observable observable1 = Observable.just("What is weather like to day?");
        Observable observable2 = Observable.just("It is raining and cold.");

        Observable.merge(observable1, observable2)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String str) {
                        mLogger.d("test4", str);
                    }
                });

        Observable.merge(observable2, observable1)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String str) {
                        mLogger.d("test4", "(reversed) " + str);
                    }
                });
    }

    /**
     * Java8의 lambda와 rxAndroid를 같이 사용하는 예
     */
    private void testWithJava8() {
        Observable.just("Hello rx, hello java8")
                .map(str -> str.length()) // same as (String str) -> {return str.length();}
                // same as (str) -> {return str.length();}
                // same as str -> {return str.length();}
                .subscribe(length -> mLogger.d("testWithJava8", "length: " + length));
    }
}
