package com.jack.app.rxtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jack.app.rxtest.fundamental.FundamentalActivity;
import com.jack.app.rxtest.operator.OperatorActivity;
import com.jack.app.rxtest.rxbinding.RxBindingActivity;
import com.jack.app.rxtest.scheduler.SchedulerActivity;

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
                SchedulerActivity.invoke(this);
                break;

            case R.id.btn_rx_binding:
                RxBindingActivity.invoke(this);
                break;
        }
    }
}
