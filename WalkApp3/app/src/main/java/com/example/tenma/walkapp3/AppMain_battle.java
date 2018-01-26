package com.example.tenma.walkapp3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.Random;

/**
 * Created by Tenma and yuuma on 2017/1/24.
 */

public class AppMain_battle extends AppCompatActivity {
    //imageボタンの画像切り替えで使う変数
    ImageButton battle, magic, check, escape;
    //ボタンが二回押されたかの判定で使う変数
    int buttonFlag = 0;
    TextView Hp;
    MediaPlayer bgm;
    //      乱数(HP)
    Random r = new Random();
    Random r1 = new Random();
    int n = r.nextInt(1000) + 100;
    int hp85, hp75, hp65, hp50, hp40, hp25,stmn,hosuustock;
    //    idの番地
    static int number;
    //    格納データ
    SharedPreferences data,pref;
    SharedPreferences.Editor editor1,editor;
    //    オーディオ
    private SoundPool soundPool, soundPool1, soundPool2, soundPool3, soundPool4,soundPool5,soundPool6;
    private int soundId, soundId1, soundId2, soundId3, soundId4,soundId5,soundId6;
    boolean lostflag,lostflag1,gifflag;
    ImageView Hp_Bar;
    Bitmap bmp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_battle);
        //data関連読み出し
        data = getSharedPreferences("ZENKAIDATA", MODE_PRIVATE);
        editor1 = data.edit();
        pref = getSharedPreferences("file", MODE_PRIVATE);
        editor = pref.edit();
        // SharedPreferencesよりデータを読み込む
        number = data.getInt("ZenkaiData", 1);
        stmn = (int)pref.getFloat("stmn", 0);
        Log.d("aaaa", "初回oncreatによってよばれたスタミナ"+stmn);


//        敵画像ファイル
        ImageView iw = (ImageView) findViewById(R.id.npc1);
// ランダム画像のリソースの配列を宣言
        TypedArray typedArray = getResources().obtainTypedArray(R.array.tekicpu);
// ランダムな数値を設定
        int rand = (int) (Math.floor(Math.random() * 3));// ランダムで画像を選択する
        Drawable drawable = typedArray.getDrawable(rand);
        if (number > 355) {

        } else {
            iw.setImageDrawable(drawable);
        }
//        体力
        Hp = (TextView) findViewById(R.id.hp);
        Hp.setTextColor(Color.RED);
//        体力BAR
        hp75 = (n / 100 * 75);
        hp50 = (n / 100 * 50);
        hp25 = (n / 100 * 25);
        hp85 = (n / 100 * 25);
        hp40 = (n / 100 * 25);
        hp65 = (n / 100 * 25);

//        HPバー画像表記
        Hp_Bar = (ImageView) findViewById(R.id.hpbar);
        bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp100);
        Hp_Bar.setImageBitmap(bmp1);
//        敵HP設定
        if (number > 355) {
            n = 100000;
            Hp.setText("HP" + "" + n);
        } else {
            Hp.setText("HP" + "" + n);
        }

        //        アニメーション
        findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.start));
//        コマンドのID指定
        ImageButton ib1 = (ImageButton) findViewById(R.id.main_hukidashi2);
        ImageButton ib2 = (ImageButton) findViewById(R.id.main_hukidashi3);
        ImageButton ib3 = (ImageButton) findViewById(R.id.main_hukidashi4);
        ImageButton ib4 = (ImageButton) findViewById(R.id.main_hukidashi5);

        ib1.setImageResource(R.drawable.main_hukidasi);
        ib2.setImageResource(R.drawable.main_hukidasi);
        ib3.setImageResource(R.drawable.main_hukidasi);
        ib4.setImageResource(R.drawable.main_hukidasi);

        findViewById(R.id.main_hukidashi2).setVisibility(View.INVISIBLE);
        findViewById(R.id.main_hukidashi3).setVisibility(View.INVISIBLE);
        findViewById(R.id.main_hukidashi4).setVisibility(View.INVISIBLE);
        findViewById(R.id.main_hukidashi5).setVisibility(View.INVISIBLE);


    }

    protected void onResume() {
        super.onResume();
        //リソースファイルから再生
        bgm = MediaPlayer.create(this, R.raw.battle);
        bgm.start();
        bgm.setLooping(true);
        // 予め音声データを読み込む
        soundPool1 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId1 = soundPool1.load(getApplicationContext(), R.raw.short_punch1, 1);
        soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(getApplicationContext(), R.raw.mahou, 1);
        soundPool2 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId2 = soundPool2.load(getApplicationContext(), R.raw.mahou, 1);
        soundPool3 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId3 = soundPool3.load(getApplicationContext(), R.raw.kaihuku, 1);
        soundPool4 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId4 = soundPool4.load(getApplicationContext(), R.raw.die, 1);
        soundPool5 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId5 = soundPool5.load(getApplicationContext(), R.raw.select08, 1);
        soundPool6 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
        soundId6 = soundPool6.load(getApplicationContext(), R.raw.select03, 1);
    }

    protected void onPause() {
        super.onPause();
        bgm.pause();
    }

    protected void localBgmStop() {
        bgm.pause();
        bgm.release();
        bgm = null;
    }

    public void StatusStart(View view) {
        soundPool2.play(soundId2, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
        Intent intent = new Intent(this, AppStatus.class);
        //遷移先の画面を起動
        startActivity(intent);
    }

    public void LogStart(View view) {
        soundPool2.play(soundId2, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
        Intent intent = new Intent(this, AppLog.class);
        //遷移先の画面を起動
        startActivity(intent);
    }

        //バトルコマンド
    public void change(View view) {
        ImageView Hp_Bar = (ImageView) findViewById(R.id.hpbar);
        //歩数カウント
        hosuustock = stmn;
        String s = String.valueOf(stmn);
        fullWidthNumberToHalfWidthNumber(s);
        // SharedPreferencesよりデータを読み込む
        number = data.getInt("ZenkaiData", 1);
        stmn = (int)pref.getFloat("stmn", 0);

        TextView tv = (TextView) findViewById(R.id.main_text);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "DragonQuestFCIntact.ttf"));
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(10);

        switch (view.getId()) {
//            バトル選択時の処理
            case R.id.main_battle:
                Log.d("aaaa", "battle" );
stmn+=1000;
                if (buttonFlag == 0) {
                    battle = (ImageButton) findViewById(R.id.main_battle);
                    battle.setImageResource(R.drawable.main_battle2);
                    buttonFlag = 1;

                } else if (buttonFlag != 1) {
                    defolt();
                    buttonFlag = 1;

                } else if (buttonFlag == 1) {
                    if(stmn>=250) {             //スタミナが250以上　またはそれ以外の処理
                        //魔王最強になる(条件処理)
                        if (number > 355) {
                            findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.yureru));
                            soundPool1.play(soundId1, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                            n -= 99999;
                            if (n <= 0) {  //nがゼロ以下の表記になったらゼロを表示
                                n = 0;
                            }
                            Hp.setText("HP" + "" + n);
                            tv.setText("かいしんのいちげき !てきに９９９９９のタ゛メーシ゛");

                            if (n <= 0) {
                                Intent intent = new Intent(this, die.class);
                                soundPool4.play(soundId4, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                                localBgmStop();
                                //遷移先の画面を起動
                                startActivity(intent);

                            } else if (n <= 150) {
                                findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.hureeru));

                            }

                        } else {
                            gifflag=true;
                            Log.d("aaaa", "初期flaf"+gifflag);
                            soundPool1.play(soundId1, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                            if(stmn<250) {
                                stmn = 0;
                            }
                            Log.d("aaaa", "なぐるへる" + stmn);
                            stmn -= 250;
                            tv.setText("まおう のこうけ゛き !てきに１００のタ゛メーシ゛");
                            n -= 100;
                            if (n <= 0) {     //nがゼロ以下の表記になったらゼロを表示
                                n = 0;
                            }
//                            ImageView imageView = (ImageView) findViewById(R.id.gazou3);
//                            GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(imageView);
//                            Glide.with(this).load(R.raw.zangeki1).into(target);
//                            if (gifflag==true){
//                                Log.d("aaaa", "flaf"+gifflag);
//
//                                gifflag=false;
//                            }

                            Hp.setText("HP" + "" + n);
                            Hp_Geage();
                            findViewById(R.id.main_hukidashi2).setVisibility(View.VISIBLE);
                            findViewById(R.id.main_text).setVisibility(View.VISIBLE);
                            //死亡時アクティビティ移動　　HP150以下でアニメーション処理
                            if (n <= 0) {
                                Intent intent = new Intent(this, die.class);
                                soundPool4.play(soundId4, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                                localBgmStop();
                                //遷移先の画面を起動
                                startActivity(intent);

                            } else if (n <= 150) {
                                findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.hureeru));

                            }
                            Log.d("aaaa", "通過A" );
                            lostflag = false;
                            Log.d("aaaa", "通過Aフラグ" + lostflag  );
                        }
                    }
                    else{
                        soundPool5.play(soundId5, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                        tv.setText("たたかえませんあるいてくた゛さい");
                        findViewById(R.id.main_hukidashi2).setVisibility(View.VISIBLE);
                        findViewById(R.id.main_text).setVisibility(View.VISIBLE);
                        lostflag = true;
                    }
                    editor.putFloat("stmn", stmn );
                    editor.apply();
                }


                break;
//魔法選択時の処理
            case R.id.main_magic:
                Log.d("aaaa", "magic" );
                Log.d("aaaa", "すたみな"+stmn );

                if (buttonFlag == 0) {
                    magic = (ImageButton) findViewById(R.id.main_magic);
                    magic.setImageResource(R.drawable.main_magic2);
                    buttonFlag = 2;

                } else if (buttonFlag != 2) {
                    defolt();
                    buttonFlag = 2;

                } else if (buttonFlag == 2) {
                    if (stmn>=500) {
                        if (number > 355) {
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                            findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.yureru));
                            findViewById(R.id.main_hukidashi2).setVisibility(View.VISIBLE);
                            findViewById(R.id.main_text).setVisibility(View.VISIBLE);
                            n -= 99999;
                            if (n <= 0) {  //nがゼロ以下の表記になったらゼロを表示
                                n = 0;
                            }
                            Hp.setText("HP" + "" + n);
                            tv.setText("めら をはなった てきに１１４９１４のタ゛メーシ゛");
                            if (n <= 0) {
                                Intent intent = new Intent(this, die.class);
                                soundPool4.play(soundId4, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                                localBgmStop();
                                //遷移先の画面を起動
                                startActivity(intent);

                            } else if (n <= 150) {
                                findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.hureeru));

                            }
                        }
                        else {
                            soundPool.play(soundId, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                            stmn -= 500;
                            Log.d("aaaa", "分岐処理" );
                            Log.d("aaaa", "スタミナへる"+stmn );
                            findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.yureru));
                            findViewById(R.id.main_hukidashi2).setVisibility(View.VISIBLE);
                            findViewById(R.id.main_text).setVisibility(View.VISIBLE);
                            tv.setText("まおうはヒョウト゛ をはなった てきに３００のタ゛メーシ゛");
                            n -= 300;
                            Hp.setText("HP" + "" + n);
                            if (n <= 0) {  //nがゼロ以下の表記になったらゼロを表示
                                n = 0;
                            }
                            if(stmn<=500) {
                                stmn = 0;
                            }
                            Hp_Geage();
                            if (n <= 0) {
                                findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.hureeru));
                                Intent intent = new Intent(this, die.class);
                                soundPool4.play(soundId4, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                                localBgmStop();
                                //遷移先の画面を起動
                                startActivity(intent);
                            } else if (n <= 150) {
                                findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.hureeru));
                            }
                            Log.d("aaaa", "通過B" );
                            lostflag1 = false;
                            lostflag = true;
                        }
                    } else{
                        soundPool5.play(soundId5, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                        tv.setText("たたかえませんあるいてくた゛さい");
                        hukidasi3_and_maintext();
                        lostflag1 = true;
                    }
                    editor.putFloat("stmn", stmn);
                    editor.apply();
                }

                break;
//確認選択時の処理
            case R.id.main_check:
                Log.d("aaaa", "check" );

                //矢印がなにも選択していない場合
                if (buttonFlag == 0) {
                    //かくにんが選ばれたらかくにんに矢印
                    check = (ImageButton) findViewById(R.id.main_check);
                    check.setImageResource(R.drawable.main_check2);
                    buttonFlag = 3;

                    //矢印がかくにん以外を選択していた場合
                } else if (buttonFlag != 3) {
                    defolt();
                    buttonFlag = 3;

                    //矢印がかくにんを選んでいた場合
                } else if (buttonFlag == 3) {
                    soundPool6.play(soundId6, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                    Log.d("aaaa", ""+fullWidthNumberToHalfWidthNumber(s));
                    tv.setText("いま の ホ゜イント は "+fullWidthNumberToHalfWidthNumber(s)+" ホ゜イント て゛す");
                    hukidasi3_and_maintext();

                }
                break;
//逃げる選択時の処理
            case R.id.main_escape:
                Log.d("aaaa", "escape" );

                if (buttonFlag == 0) {
                    escape = (ImageButton) findViewById(R.id.main_escape);
                    escape.setImageResource(R.drawable.main_escape2);
                    buttonFlag = 4;
                } else if (buttonFlag != 4) {
                    defolt();
                    buttonFlag = 4;

                    //矢印が逃げるを選択していた場合
                } else if (buttonFlag == 4) {
                    soundPool3.play(soundId3, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                    tv.setText("テ゛フ゛すき゛て にけ゛られない ！！\n\n" +
                            "てきはHPをかいふくした");
                    hukidasi3_and_maintext();
                    Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp100);
                    Hp_Bar.setImageBitmap(bmp1);
                    n = +1000;
                    Hp.setText("HP" + "" + n);
                    findViewById(R.id.npc1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.kaihuku));
                }

                break;

//スタミナ減った時の処理
            case R.id.main_hukidashi2:
                Log.d("aaaa", "hukidasi2" );
//                gifflag=true;
                if (buttonFlag == 0) {
                    buttonFlag = 5;
                    //矢印が逃げる以外を選択していた場合
                } else if (buttonFlag != 5) {
                    Log.d("aaaa", "B!");
                    defolt();
                    buttonFlag = 5;

                } else if (buttonFlag == 5) {
                    Log.d("aaaa", "分岐!");
                    soundPool5.play(soundId5, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
                    if (stmn>=250){
                        if(lostflag == false) {
                            Log.d("aaaa", "分岐B!");
                            tv.setText("こうけ゛きによってすたみなか２５０゛へってしまった\n\n\n   のこり゛" +
                                    fullWidthNumberToHalfWidthNumber(s) +"ほ゜いんとて゛す");
                            findViewById(R.id.gazou3).startAnimation(AnimationUtils.loadAnimation(this, R.anim.kieru));
                            hukidasi3_and_maintext();
                        }
                        else if(lostflag1 == false) {
                            Log.d("aaaa", "分岐A!");
                            tv.setText("こうけ゛きによってすたみなか５００゛へってしまった\n\n\n   のこり゛" +
                                    fullWidthNumberToHalfWidthNumber(s) +"ほ゜いんとて゛す");
                            hukidasi3_and_maintext();
                        }
                    }else if (lostflag1 == true ){
                        Log.d("aaaa", "分岐C!");

                        tv.setText("これいし゛ょうたたかえません");
                        hukidasi3_and_maintext();
                    }
                    else if (lostflag == true ){
                        Log.d("aaaa", "分岐D!");
                        tv.setText("これいし゛ょうたたかえません");
                        hukidasi3_and_maintext();
                    }
                }
break;
//            デフォルトに戻る
            case R.id.main_hukidashi3:
                Log.d("aaaa", "hukidasi 3");
                Log.d("aaaa", "スタミナへる"+stmn );
                findViewById(R.id.main_text1).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_hukidashi3).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_hukidashi5).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_hukidashi2).setVisibility(View.INVISIBLE);
                findViewById(R.id.main_text).setVisibility(View.INVISIBLE);
                //矢印の初期化
                defolt();
                buttonFlag = 0;
                break;

        }
    }
    protected void Hp_Geage(){
        Hp_Bar = (ImageView) findViewById(R.id.hpbar);
        if (n <= hp25) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp25);
            Hp_Bar.setImageBitmap(bmp1);

        } else if (n <= hp40) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp40);
            Hp_Bar.setImageBitmap(bmp1);

        } else if (n <= hp50) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp50);
            Hp_Bar.setImageBitmap(bmp1);

        } else if (n <= hp65) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp65);
            Hp_Bar.setImageBitmap(bmp1);

        } else if (n <= hp75) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp75);
            Hp_Bar.setImageBitmap(bmp1);

        } else if (n <= hp85) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.hp85);
            Hp_Bar.setImageBitmap(bmp1);

        }
    }
    protected void defolt(){
        battle = (ImageButton) findViewById(R.id.main_battle);
        battle.setImageResource(R.drawable.main_battle2);
        magic = (ImageButton) findViewById(R.id.main_magic);
        magic.setImageResource(R.drawable.main_magic1);
        check = (ImageButton) findViewById(R.id.main_check);
        check.setImageResource(R.drawable.main_check1);
        escape = (ImageButton) findViewById(R.id.main_escape);
        escape.setImageResource(R.drawable.main_escape1);
    }
    protected void hukidasi3_and_maintext(){
        findViewById(R.id.main_hukidashi3).setVisibility(View.VISIBLE);
        findViewById(R.id.main_text).setVisibility(View.VISIBLE);
    }
//アンドロイド既存のバックボタンを押しても元のアクティビティに戻せなくする
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

    //String型の数字を全角のString型の数字に変換
    public static String fullWidthNumberToHalfWidthNumber(String str) {
        if (str == null){
            throw new IllegalArgumentException();
        }
        StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ('0' <= c && c <= '9') {
                sb.setCharAt(i, (char) (c - '0' + '０'));
            }
        }
        return sb.toString();
    }
}