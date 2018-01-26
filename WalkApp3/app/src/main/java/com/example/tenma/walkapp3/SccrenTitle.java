package com.example.tenma.walkapp3;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuuma on 2017/12/18.
 */

public class SccrenTitle extends ActivityAddToBGMandSE {
    Timer timer = null;
    Handler handle = new Handler();
    private SoundPool soundPool;
    private int soundId;

    //BGM関係
    ////////////////////////////////
    @Override
    // 画面が表示される度に実行
    protected void onResume() {
        super.onResume();
        soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(getApplicationContext(), R.raw.start, 1);
        bgmStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        bgmPause();
    }
    /////////////////////////////////


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sccrentitle);
//        アニメーション
        findViewById(R.id.Start).startAnimation(AnimationUtils.loadAnimation(this, R.anim.start));
        findViewById(R.id.TitleName).startAnimation(AnimationUtils.loadAnimation(this, R.anim.title_name));

//        タイマーの時間
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new MyTimer(), 5000); // ミリ秒でセット
        }
    }
    //    AppStratボタンのめそっど
    public void AppStart(View view){
        soundPool.play(soundId, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
        Intent intent = new Intent(this, AppMain_scenario.class);
        //遷移先の画面を起動
        startActivity(intent);
//        タイマーキャンセル
        timer.cancel();


    }
    //    douzyutuメソッド
    public void Start(View view){
        soundPool.play(soundId, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
//        遷移
        Intent intent = new Intent(SccrenTitle.this, AppMain_scenario.class);
        //遷移先の画面を起動
        startActivity(intent);
    }
    //    タイマーのメソッド
    class MyTimer extends TimerTask {
        @Override
        public void run() {
            handle.post(new Runnable() {
                @Override
//                実際にタイマーを起動したときに何をしたいか書くところ
                public void run() {


                    // アニメーションの設定
//                    overridePendingTransition(R.anim.senni5, R.anim.senni6);
                }
            });
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
