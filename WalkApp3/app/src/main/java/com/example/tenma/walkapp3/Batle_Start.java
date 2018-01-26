package com.example.tenma.walkapp3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuuma on 2017/1/25.
 */

public class Batle_Start extends AppCompatActivity {
    TextView tv1;
    private SoundPool soundPool;
    private int soundId;
    MediaPlayer bgm;
    Timer timer = null;
    Handler handle = new Handler();
    protected void onResume() {
        super.onResume();
//        soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
//        soundId = soundPool.load(getApplicationContext(), R.raw.gameover3, 1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirikae);
        //リソースファイルから再生
        bgm = MediaPlayer.create(this, R.raw.child_bully);
        bgm.start();
        bgm.setLooping(true);
//        soundPool.play(soundId, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
        tv1 = (TextView) findViewById(R.id.dainannsyou);
        tv1.setTextColor(Color.WHITE);
        findViewById(R.id.dainannsyou).startAnimation(AnimationUtils.loadAnimation(this, R.anim.die));
        tv1.setTypeface(Typeface.createFromAsset(getAssets(), "DragonQuestFCIntact.ttf"));
        tv1.setText("せんとうかいし");
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
                    localBgmStop();
                    Intent intent = new Intent(Batle_Start.this, AppMain_battle.class);
                    //遷移先の画面を起動
                    startActivity(intent);
                }
            });
        }
    }
    protected void localBgmStop() {
        bgm.pause();
        bgm.release();
        bgm = null;
    }
    protected void onPause() {
        super.onPause();
        bgm.pause();
    }
    public void MainBack(View view){
        Intent intent = new Intent(this, AppMain_battle.class);
        //遷移先の画面を起動
        startActivity(intent);
    }
}
