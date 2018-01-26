package com.example.tenma.walkapp3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuuma on 2017/12/18.
 */

public class Scenario_kirikae extends ActivityAddToBGMandSE {
    TextView tv1;
    Timer timer = null;
    Handler handle = new Handler();
    int suuzi = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirikae);
        tv1 = (TextView) findViewById(R.id.dainannsyou);
        tv1.setTextColor(Color.WHITE);
        findViewById(R.id.dainannsyou).startAnimation(AnimationUtils.loadAnimation(this, R.anim.senni6));
        tv1.setText("第"+ suuzi+"章");
        tv1.setTextSize(40.0f);
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new MyTimer(), 5000); // ミリ秒でセット
        }
    }
    class MyTimer extends TimerTask {
        @Override
        public void run() {
            handle.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Scenario_kirikae.this, AppMain_scenario.class);
                    //遷移先の画面を起動
                    startActivity(intent);
                    // アニメーションの設定
                    overridePendingTransition(R.anim.senni5, R.anim.senni6);
                }
            });
        }
    }
}
