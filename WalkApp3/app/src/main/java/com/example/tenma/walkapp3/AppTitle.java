package com.example.tenma.walkapp3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuuma on 2017/12/20.
 */

public class AppTitle extends ActivityAddToBGMandSE{
    Timer timer = null;
    Handler handle = new Handler();

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;

    SharedPreferences.Editor editor2;
    SharedPreferences data;
    SharedPreferences.Editor dateEditor;
    Cursor c;

    //前回の不必要歩数
    private float beforedust;   // 歩数がない → センサーの値
    private float beforestopfirst;  // 前回の値がない場合 → -1

    //Android終了時・起動時の処理
    public final static class mReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            SharedPreferences pref = context.getSharedPreferences("file", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            String action = intent.getAction();
            if(action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                Log.v("testt", "-----SHUTDOWN-----");


//                // ----------------------------------------
//
//                // データを入れる処理
//                editor.putBoolean("yesterday", true);
//                editor.apply();
//
//                //必要ないセンサの累積歩数を入れる（起動時は必ず）
//                float dust = pref.getFloat("runningSensor", 0);
//                //不要歩数の上書き（前回終了時に歩数stepsがある場合）
//                if(beforedust != 0) {
//                    dust = beforedust;
//                }
//                Log.v("testt", "前回の不必要歩数の総和[beforedust]" + beforedust);
//
//                //起動時、ストップボタンを押している状態にする
//                stopfirst = se.values[0];
//                //ストップボタンを押し、加算された状態でアプリ終了した場合
//                if(beforestopfirst > -1) {
//                    //前回、ストップボタンを押した時のセンサの値
//                    stopfirst = beforestopfirst;
//                    Log.v("testt", "※※※ストップを押して終了※※※");
//                    Log.v("testt", "ストップを押した時[beforestopfirst]：" + beforestopfirst);
//
//                    stopsteps = se.values[0] - stopfirst;
//                    dust += stopsteps;
//
//                    //初期化(この処理をしないとスタートボタンを押した時に重複処理になる)
//                    stopfirst = se.values[0];
//
//                    Log.v("testt", "stop中に増えた歩数[stopsteps]：" + stopsteps);
//                    Log.v("testt", "stopstepsを足す[dust(起動時)]：" + dust);
//                    Log.v("testt", "※※※※※※");
//
//                }
//
//                //最初に表示したい歩数の計算
//                steps = se.values[0] - dust;
//
//                //データの記録
//                SetData sd = new SetData(context.getApplicationContext(), steps);
//
//
//                // ----------------------------------------


                Log.v("testt", "runningstartflag " + pref.getBoolean("runningstartflag", true));

                //スタートボタンが押された状態でandroid再起動()
                if(pref.getBoolean("runningstartflag", true)) {

                    Log.v("testt", "takuyaaaaaaaaaaaaaaa");

                    Log.v("testt", "卍卍卍卍卍beforestopfirst(センサ)" + pref.getFloat("runningSensor", 0));

                    Log.v("testt", "スタートが押された状態でシャットダウン");


                    editor.putFloat("beforestopfirst", pref.getFloat("runningSensor", 0));
                    editor.apply();

                    editor.putBoolean("shutdown", true);
                    editor.apply();

                }
                Log.v("testt", "[センサ]" + pref.getFloat("runningSensor", 0));
                Log.v("testt", "[beforestopfirst]" + pref.getFloat("beforestopfirst", -1));
                Log.v("testt", "-----SHUTDOWN-----");

                onstopflag = true;

            }
            if(action.equals("android.intent.action.BOOT_COMPLETED")) {
                Log.v("testt", "BOOT_COMPLETED");
                Toast.makeText(context, "-----BOOT_COMPLETED-----", Toast.LENGTH_SHORT).show();

                if(pref.getBoolean("shutdown", false)) {

                    Log.v("testt", "& shutdownのPreferenceが成功");

                    //shutdownの初期化
                    editor.putBoolean("shutdown", false);
                    editor.apply();

                    //アプリ起動時に動作させるif分に入る
                    editor.putBoolean("bootcompleted", true);
                    editor.apply();

                }
            }
            if(action.equals("android.intent.action.DATE_CHANGED")) {
                Log.v("testt", "DATE_CHANGED DATE_CHANGED DATE_CHANGED");

                // データを入れる処理
                editor.putBoolean("yesterday", true);
                editor.apply();



                // dust更新

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rogo);
        findViewById(R.id.imageView2).startAnimation(AnimationUtils.loadAnimation(this, R.anim.senni6));
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new MyTimer(), 5000); // ミリ秒でセット
        }

        //プリファレンスのインスタンス取得
        //前回不必要歩数の取得（歩数stepsがある場合）
        SharedPreferences pref = getSharedPreferences("file", MODE_PRIVATE);
        beforedust = pref.getFloat("beforedust", 0);
        //ストップの間に加算された歩数があり、終了した場合
        beforestopfirst = pref.getFloat("beforestopfirst", -1);

        //android起動時の処理
        if(pref.getBoolean("bootcompleted", false)) {

            Log.v("testt", "Android起動！！！！！！！！！！！！！！！");

            //Serviceを起動
            Intent intent = new Intent(getApplication(), NotificationService.class);
            startService(intent);

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("bootcompleted", false);
            editor.apply();
        }

    }

    //BGM関係
    ////////////////////////////////
    @Override
    // 画面が表示される度に実行
    protected void onResume() {
        super.onResume();

        //KITKAT以上かつTYPE_STEP_COUNTERが有効ならtrue
        boolean isTarget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER);

        if (isTarget) {
            //TYPE_STEP_COUNTERが有効な場合の処理
            Log.d("hasStepCounter", "STEP-COUNTER is available!!!");
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

            setStepCounterListener();
        } else {
            //TYPE_STEP_COUNTERが無効な場合の処理
            Log.d("hasStepCounter", "STEP-COUNTER is NOT available.");

        }

        bgmStart();
    }

    static boolean onstopflag = false;

    protected  void  onStart() {
        super.onStart();

        if(onstopflag) {
            onstopflag = false;
        }
    }

    protected void onStop() {
        super.onStop();

        SharedPreferences pref = getSharedPreferences("file", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        //onDestroy()時にonStop()が呼び出されてしまうのでflag処理
        if(!onstopflag) {

            Log.v("testt", "-----onStop()が呼ばれました-----");
            Log.v("testt", "[steps]" + steps);
            Log.v("testt", "[stopfirst]" + stopfirst);

            //歩数がある場合の保存
            if(steps > 0) {
                beforedust = dust;
            }else {
                beforedust = pref.getFloat("runningSensor", 0);
            }
            //増やした数を再び保存
            pref = getSharedPreferences("file", MODE_PRIVATE);
            editor = pref.edit();
            editor.putFloat("beforedust", beforedust);
            editor.apply();
            Log.v("testt", "[beforedust]" + beforedust);

            //ストップの間に加算された歩数があり、終了した場合
            if(stopflag) {
                Log.v("testt", "※ストップが押されてる状態でonStop()");
                beforestopfirst = stopfirst;
            }else {
                beforestopfirst = -1;
            }
            //増やした数を再び保存
            editor = pref.edit();
            editor.putFloat("beforestopfirst", beforestopfirst);
            editor.apply();

            Log.v("testt", "[beforestopfirst]" + beforestopfirst);
//            Log.v("testt", "[センサ]" + se.values[0]);
            Log.v("testt", "-----onStop()が呼ばれました[終了]-----");

            onstopflag = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        bgmPause();
    }

    private void setStepCounterListener() {
        if (mStepCounterSensor != null) {
            //ここでセンサーリスナーを登録する
            mSensorManager.registerListener(mStepCountListener, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    private SensorEvent se;
    private int soundId;

    //スタート・ストップ・リセットの状態
    private boolean startflag = true;
    private boolean stopflag = false;

    //現在の歩数
    private float steps = 0;

    //起動時を表す（1度だけ使用）
    private int first = 0;

    //アプリ起動以前に記録された歩数、および不必要歩数の総和
    private float dust = 0;
    //ストップが押された時の[センサの値]
    private float stopfirst = 0;
    //ストップが押されている間の歩数（不必要歩数）
    //(スタートが押された瞬間の[センサの値]) - stopdust で求める
    private float stopsteps = 0;

    private final SensorEventListener mStepCountListener = new SensorEventListener() {

        //センサーから歩数を取得し、表示するメソッド
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            se = sensorEvent;
            Log.v("testt", "[センサ]：" + se.values[0]);

            SharedPreferences pref = getSharedPreferences("file", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            //アプリ起動直後の処理
            //[0歩]もしくは、[前回の累積歩数]を表示
            if(first == 0) {
                firstprocessing();

            }
            //スタートボタンが押されている時
            else if(startflag) {

                //歩数表示を増加させる
                //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
                steps = se.values[0] - pref.getFloat("runningdust", -1);
                //wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww

                //dustをNotificationServiceに渡す
                pref = getSharedPreferences("file", MODE_PRIVATE);
                editor = pref.edit();
                editor.putFloat("runningdust", dust);
                editor.apply();

//                //データの記録
//                SetData sd = new SetData(getApplicationContext(), steps);
            }
            //ストップボタンが押されている時
            else if(stopflag) {

                //歩数表示は変化させず維持する（ストップが押される直前の歩数を表示し続けるだけ）

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };

    public void firstprocessing() {

        //必要ないセンサの累積歩数を入れる（起動時は必ず）
        dust = se.values[0];
        //不要歩数の上書き（前回終了時に歩数stepsがある場合）
        if(beforedust != 0) {
            dust = beforedust;
        }
        Log.v("testt", "前回の不必要歩数の総和[beforedust]" + beforedust);

        //起動時、ストップボタンを押している状態にする
        stopfirst = se.values[0];
        //ストップボタンを押し、加算された状態でアプリ終了した場合
        if(beforestopfirst > -1) {
            //前回、ストップボタンを押した時のセンサの値
            stopfirst = beforestopfirst;
            Log.v("testt", "※※※ストップを押して終了※※※");
            Log.v("testt", "ストップを押した時[beforestopfirst]：" + beforestopfirst);

            stopsteps = se.values[0] - stopfirst;
            dust += stopsteps;

            //初期化(この処理をしないとスタートボタンを押した時に重複処理になる)
            stopfirst = se.values[0];

            Log.v("testt", "stop中に増えた歩数[stopsteps]：" + stopsteps);
            Log.v("testt", "stopstepsを足す[dust(起動時)]：" + dust);
            Log.v("testt", "※※※※※※");

        }

        //最初に表示したい歩数の計算
        steps = se.values[0] - dust;

        //状態の初期化（スタートを押している状態）
        startflag = true;
        stopflag = false;

        //startflagをNotificationServiceに渡す
        SharedPreferences pref = getSharedPreferences("file", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("runningstartflag", startflag);
        editor.apply();

        //Serviceを起動
        Intent intent = new Intent(getApplication(), NotificationService.class);
        startService(intent);

        //初回起動時の処理のため、以降このif文に入らないようにする
        first++;
    }

    private void sendNotification() {

        Intent notificationIntent = new Intent(this, AppTitle.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        Notification.Builder builder = new Notification.Builder(this);

        NotificationManager manager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        builder.setSmallIcon(R.mipmap.icon);

        builder.setContentTitle("すとっぷぼたんがおされてるよ");
        builder.setContentText("きょうのほすう　→　" + (int)steps);

        builder.setDefaults(Notification.PRIORITY_DEFAULT);
        builder.setContentIntent(contentIntent);

        manager.notify(1,builder.build());

    }




    class MyTimer extends TimerTask {
        @Override
        public void run() {
            handle.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(AppTitle.this, Epilogue.class);
                    //遷移先の画面を起動
                    startActivity(intent);
                    // アニメーションの設定
                    overridePendingTransition(R.anim.senni5, R.anim.senni6);
//                    tv1 = (TextView) findViewById(R.id.textView2);
//                    tv1.setText("ん");
//                    tv1.setTextColor(Color.WHITE);

                }
            });
        }
    }
}

