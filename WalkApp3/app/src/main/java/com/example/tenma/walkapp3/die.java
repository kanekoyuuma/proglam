package com.example.tenma.walkapp3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
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
import android.graphics.Matrix;
import android.widget.ImageView;
/**
 * Created by yuuma on 2017/12/18.
 */

public class die extends AppCompatActivity {
    TextView tv1;
    private SoundPool soundPool;
    private int soundId;
    MediaPlayer bgm;
    private ImageView imageView,imageView1,imageView2,imageView3;
    protected void onResume() {
        super.onResume();
//        soundPool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
//        soundId = soundPool.load(getApplicationContext(), R.raw.gameover3, 1);

         imageView = (ImageView) findViewById(R.id.gazou);
         imageView1 = (ImageView) findViewById(R.id.gazou1);
         imageView2 = (ImageView) findViewById(R.id.gazou2);
        imageView3 = (ImageView) findViewById(R.id.gazou3);
//        imageView3.setImageResource(R.drawable.maou);



        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),
                R.drawable.mobu1);
        Bitmap bitmap11 = BitmapFactory.decodeResource(getResources(),
                R.drawable.mobu2);
        Bitmap bitmap111 = BitmapFactory.decodeResource(getResources(),
                R.drawable.mobu3);

        // 画像の横、縦サイズを取得
        int imageWidth = bitmap1.getWidth();
        int imageHeight = bitmap1.getHeight();

        int imageWidth1 = bitmap11.getWidth();
        int imageHeight1 = bitmap11.getHeight();

        int imageWidth2 = bitmap111.getWidth();
        int imageHeigh2 = bitmap111.getHeight();
        // Matrix インスタンス生成
        Matrix matrix = new Matrix();
        Matrix matrix1 = new Matrix();
        Matrix matrix2 = new Matrix();

        // 画像中心を基点に90度回転
        matrix.setRotate(50, imageWidth/2, imageHeight/2);
        matrix1.setRotate(150, imageWidth/2, imageHeight/2);
        matrix2.setRotate(200, imageWidth/2, imageHeight/2);

        // 90度回転したBitmap画像を生成
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1, 0, 0,
                imageWidth, imageHeight, matrix, true);

        Bitmap bitmap22 = Bitmap.createBitmap(bitmap11, 0, 0,
                imageWidth1, imageHeight1, matrix1, true);

        Bitmap bitmap222 = Bitmap.createBitmap(bitmap111, 0, 0,
                imageWidth2, imageHeigh2, matrix2, true);

        imageView.setImageBitmap(bitmap2);
        imageView1.setImageBitmap(bitmap22);
        imageView2.setImageBitmap(bitmap222);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirikae);
        //リソースファイルから再生
        bgm = MediaPlayer.create(this, R.raw.fanfare1);
        bgm.start();
        bgm.setLooping(true);
//        soundPool.play(soundId, 1f, 1f, 0, 0, 1);    //音の大きさは0fから1fで調整できる
        tv1 = (TextView) findViewById(R.id.dainannsyou);
        tv1.setTextColor(Color.WHITE);
        findViewById(R.id.dainannsyou).startAnimation(AnimationUtils.loadAnimation(this, R.anim.die));
        findViewById(R.id.gazou).startAnimation(AnimationUtils.loadAnimation(this, R.anim.win));
        findViewById(R.id.gazou1).startAnimation(AnimationUtils.loadAnimation(this, R.anim.win1));
        findViewById(R.id.gazou2).startAnimation(AnimationUtils.loadAnimation(this, R.anim.win2));

        tv1.setTypeface(Typeface.createFromAsset(getAssets(), "DragonQuestFCIntact.ttf"));
        tv1.setText("てきをたおした");
        tv1.setTextSize(40.0f);
    }
    protected void localBgmStop() {
        bgm.pause();
        bgm.release();
        bgm = null;
    }
    public void MainBack(View view){
        Intent intent = new Intent(this, AppMain_scenario.class);
        localBgmStop();
//        Bitmap bitmap1111 = BitmapFactory.decodeResource(getResources(),
//                R.drawable.maou);
//        imageView3.setImageBitmap(bitmap1111);
//        findViewById(R.id.gazou3).startAnimation(AnimationUtils.loadAnimation(this, R.anim.title_name));
        //遷移先の画面を起動
        startActivity(intent);
    }
}
