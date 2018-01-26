package com.example.tenma.walkapp3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Tenma on 2017/12/15.
 */

public class AppStatus extends AppCompatActivity{
    int sintyouInt;
    int taizyuuInt;
    String seibetuString;
    ArrayAdapter sintyouAdapter;
    ArrayAdapter taizyuuAdapter;
    ArrayAdapter seibetuAdapter;
    private Spinner sintyouSpinner;
    private Spinner taizyuuSpinner;
    private Spinner seibetuSpinner;
    SharedPreferences data;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        //保存
        data = getSharedPreferences("STATUS",MODE_PRIVATE);
        editor = data.edit();

        // ArrayAdapterを生成。layoutにはspinner_item,中身はそれぞれlist,list2使用
        sintyouAdapter = ArrayAdapter.createFromResource(this, R.array.list, R.layout.spinner_item);
        taizyuuAdapter = ArrayAdapter.createFromResource(this, R.array.list2, R.layout.spinner_item);
        seibetuAdapter = ArrayAdapter.createFromResource(this, R.array.list3, R.layout.spinner_item);

        // グラフィックに「spinner_dropdown_item」使用
        sintyouAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        taizyuuAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        seibetuAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //身長
        sintyouSpinner = (Spinner) findViewById(R.id.spinner);
        sintyouSpinner.setAdapter(sintyouAdapter);

        //体重
        taizyuuSpinner = (Spinner) findViewById(R.id.spinner2);
        taizyuuSpinner.setAdapter(taizyuuAdapter);

        //性別
        seibetuSpinner = (Spinner) findViewById(R.id.spinner3);
        seibetuSpinner.setAdapter(seibetuAdapter);

        // 身長のドロップダウンリストから選んだ場合に、その値を保持。
        sintyouSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                String item = (String) spinner.getSelectedItem();
                sintyouInt = Integer.parseInt(item);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // スピナーでは使用しないようですが、ないといけないので放置
            }
        });

        // 体重のドロップダウンリストから選んだ場合に、その値を保持。
        taizyuuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner2 = (Spinner) parent;
                String item2 = (String) spinner2.getSelectedItem();
                taizyuuInt = Integer.parseInt(item2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 性別のドロップダウンリストから選んだ場合に、その値を保持。
        seibetuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                Spinner spinner3 = (Spinner) parent;
                String item3 = (String) spinner3.getSelectedItem();
                seibetuString = (item3);
            }
            @Override
            public void onNothingSelected(AdapterView parent) {
            }
        });
    }

    @Override
    // 画面が表示される度に実行
    protected void onResume() {
        super.onResume();


        // SharedPreferencesよりデータを読み込む
        int sintyouInt =data.getInt("Sintyou", 0);
        int taizyuuInt = data.getInt("Taizyuu",0);
        String seibetuString = data.getString("seibetu",null);

        // 身長のデータが存在する場合
        if( sintyouInt != 0) {
            sintyouSpinner = (Spinner) findViewById(R.id.spinner);
            sintyouAdapter = (ArrayAdapter) sintyouSpinner.getAdapter();
            int spinnerPosition = sintyouAdapter.getPosition(String.valueOf(sintyouInt));
            sintyouSpinner.setSelection(spinnerPosition);
        }

        // 体重のデータが存在する場合
        if( taizyuuInt != 0 ) {
            taizyuuSpinner = (Spinner) findViewById(R.id.spinner2);
            taizyuuAdapter = (ArrayAdapter) taizyuuSpinner.getAdapter();
            int spinnerPosition2 = taizyuuAdapter.getPosition(String.valueOf(taizyuuInt));
            taizyuuSpinner.setSelection(spinnerPosition2);
        }

        // 性別のデータが存在する場合
        if( seibetuString != null ) {
            seibetuSpinner = (Spinner) findViewById(R.id.spinner3);
            seibetuAdapter = (ArrayAdapter) seibetuSpinner.getAdapter();
            int spinnerPosition3 = seibetuAdapter.getPosition(String.valueOf(seibetuString));
            seibetuSpinner.setSelection(spinnerPosition3);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // putInt("キー値" , value )
        editor.putInt("Sintyou",sintyouInt);
        editor.putInt("Taizyuu",taizyuuInt);
        editor.putString("seibetu",seibetuString);
        editor.commit();

    }
    public void MainBack(View view){

        // putInt("キー値" , value )
        editor.putInt("Sintyou",sintyouInt);
        editor.putInt("Taizyuu",taizyuuInt);
        editor.putString("seibetu",seibetuString);
        editor.commit();

        Intent intent = new Intent(this, AppMain_battle.class);
        //遷移先の画面を起動
        startActivity(intent);

//        finish();
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
