package com.example.tenma.walkapp3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

/**
 * Created by Tenma and yuuma on 2017/12/15.
 */

public class AppLog extends AppCompatActivity{
    CalendarView calendarView;
    TextView dateDisplay;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        calendarView = (CalendarView) findViewById(R.id.calendarView);

        dateDisplay = (TextView) findViewById(R.id.date_display);
        dateDisplay.setText("日付をタップしてください");
        dateDisplay.setTextSize(20.0f);
        // ～～～～～～～～～～～～～カレンダー～～～～～～～～～～～～～～～～～～～～～～～～～～
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                // ～～～～～～～データベース～～～～～～～～

                // 作成したDataクラスに読み取り専用でアクセス
                HosuukirokuTest hkData = new HosuukirokuTest( getApplicationContext() );
                SQLiteDatabase db = hkData.getReadableDatabase();

                // タップされた年月日
                int kakunoubanngou = (i*10000) + ((i1 + 1) * 100) + i2;

                // SELECT（取得したい列） FROM（対象テーブル）WHERE（条件）※変数を使う場合「 + 変数」文字列結合
                String sql = "SELECT hizuke , hosuu , karori FROM hosuukirokuTable WHERE hizuke=" + kakunoubanngou;

                Log.v("testt","日付:" + String.valueOf( kakunoubanngou ) );

                // SQL文を実行してデータを取得
                try {
                    c = db.rawQuery(sql, null);
                    c.moveToFirst();

                    // データベースから取ってきただけのデータを、使えるように変数へセット
                    String hizukeVal = c.getString(c.getColumnIndex("hizuke"));
                    String hosuuVal = c.getString(c.getColumnIndex("hosuu"));
                    String karoriVal = c.getString(c.getColumnIndex("karori"));

                    dateDisplay.setText((i1 + 1) + "月 " + i2 + "日 のほすうは " + hosuuVal + "歩です。\n" + "消費カロリーは " + karoriVal + "kcalです");
                } catch ( Exception e){
                    dateDisplay.setText((i1 + 1) + "月 " + i2 + "日のデータはありません。");

                } finally {

                    // クローズ処理
                    c.close();
                    db.close();
                }
            }
        });
    }
    public void MainBack(View view){
        finish();
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