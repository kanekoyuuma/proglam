package com.example.tenma.walkapp3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuuma on 2017/12/18.
 */

public class Epilogue extends ActivityAddToBGMandSE {
    TextView tv;
    TextView tv1;
    Timer timer = null;
    Handler handle = new Handler();

    //BGM関係
    ////////////////////////////////
    @Override
    // 画面が表示される度に実行
    protected void onResume() {
        super.onResume();

        bgmStart();
    }
    @Override
    protected void onPause() {
        super.onPause();

        bgmPause();
    }
    /////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epilogue);
        tv = (TextView) findViewById(R.id.epilogue_textView);
        tv1 = (TextView) findViewById(R.id.epilogue_1);
        tv.setTextColor(Color.WHITE);
        tv1.setTextColor(Color.WHITE);
        findViewById(R.id.epilogue_textView).startAnimation(AnimationUtils.loadAnimation(this, R.anim.epilogue));
        findViewById(R.id.epilogue_1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.epilogue_1));
        tv.setText(
                "その昔\n\n" +
                        "この世界は大魔王の支配に\n\n" +
                        "より暗黒の時代を迎えていた\n\n" +
                        "しかし勇者クルルァがその身を\n\n" +
                        "引き換えに大魔王を倒し世界は救われた\n\n" +
                        "それから数年後、地上では新たな勇者が\n\n" +
                        "魔界では新たな魔王が同時に生まれた\n\n" +
                        "この物語はそんな二人の数奇な運命を追う\n\n" +
                        "冒険談であるはずが\n" +
                        "\n\n\n\n\n\n\n\n\n"
        );
        tv1.setText("500年がたった");
        tv.setTextSize(16.0f);
        tv1.setTextSize(16.0f);
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new MyTimer(), 25000); // ミリ秒でセット
        }
    }
    public void AppStart(View view){
        Intent intent = new Intent(this, SccrenTitle.class);
        //遷移先の画面を起動
        startActivity(intent);
        timer.cancel();



    }
    class MyTimer extends TimerTask {
        @Override
        public void run() {
            handle.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Epilogue.this, SccrenTitle.class);
                    //遷移先の画面を起動
                    startActivity(intent);
                    // アニメーションの設定
                    overridePendingTransition(R.anim.senni5, R.anim.senni6);
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
