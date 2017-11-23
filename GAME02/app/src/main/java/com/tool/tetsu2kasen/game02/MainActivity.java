/*
//Next do 残り時間を表示する
//
*/

package com.tool.tetsu2kasen.game02;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements SensorEventListener {

    float rand;
    float nsx;
    float nsy;
    float nsz;
    float outleftpad;
    float outrigpad;
    float center;
    float plcenter;
    final int actm=20;//20周期で終了（）

    public int act =0;

    final float stage = 500;
    boolean outed = true;
    final int count = 50;//25秒間
    int nwcot = 0;
    public int mTime;

    final int nmcount = 50;

    float W =0;
    int taped = 0;
    Handler mHandler;//るいぱんこ
    Handler aHandler;//アニメーション用ハンドラ


    boolean Handed = true;

    Timer mTimer;
    Timer aTimer;//アニメーションタイマー

    private SensorManager manager;

    public void clear(){
        //Clear
        nst.setTextColor(Color.YELLOW);
        nst.setText("くりあです♥");
        nst.setVisibility(View.VISIBLE);
        outed = true;
        nwcot = 0;


    }

    public void oc(View v) {

        //カウントを初期化
        nwcot=0;
        //プレイヤーを指定位置に設定する
        txtv.setTranslationX(plcenter);

        //TXを初期化しなおす
        TX = txtv.getTranslationX();

        nst.setVisibility(View.INVISIBLE);
        txtv.setTranslationX(plcenter);
        outed = false;
        mTimer = new Timer(false);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (outed) {
                            cancel();
                        }

                        Random random = new Random();
                        rand = random.nextInt(20);
                        rand = (rand - 10) * 10;
                        ANM(rand);
                        //TX = TX + rand;
                        //↓移動処理
                        //ST(TX);
                        if (nwcot >= 50) {
                            clear();
                            cancel();
                        }
                        nwcot++;
                    }
                });

            }
        }, 0, 500);
    }

    public float ST(float tx) {
        if (outed == false) {
            txtv.setTranslationX(tx);
        }
        return 0;
    }
    public float ANM(float wei){



        final int actm=20;//20周期で終了（）

        W=wei/20;
        float anmct;
        aTimer = new Timer(false);
        aTimer.schedule(new TimerTask() {
            @Override
            public void run() {//ここ等辺でおちる
                aHandler.post(new Runnable() {
                    @Override
                    public void run() {


                        act++;
                        TX=(W*nwcot)+TX;
                        ST(TX);
                        if (act >= actm) {
                            cancel();
                        }
                        nwcot++;
                    }
                });

            }
        }, 0, 25);


        return 0;
    }

    public int width = 0;
    TextView txtv;
    //debugviewのインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ
    TextView dbgv;
    //NOW statusとるお
    TextView nst;
    public float TX = 0;
    public float nTX = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //ハンドラー取得すりゅ♥
        mHandler = new Handler();
        //横画面固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Windowマネージャをよびだしゅぅぅぅぅぅぅぅぅ
        WindowManager wm = getWindowManager();
        //Dispのインスタンスを取得すりゅううううう
        Display disp = wm.getDefaultDisplay();
        width = disp.getWidth();
        //センサーマネジャーからインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ(はーと)
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbgv = (TextView) findViewById(R.id.textView5);
        txtv = (TextView) findViewById(R.id.myView2);

        nst = (TextView) findViewById(R.id.nowst);

        TX = txtv.getTranslationX();
        center = width / 2;
        plcenter = center - 10;

        //プレイヤーを指定位置に設定する
        txtv.setTranslationX(plcenter);

        //TXを初期化しなおす
        TX = txtv.getTranslationX();

        outleftpad = (width - stage) / 2;
        outrigpad = outleftpad + stage;

    }

    public void aclk() {
        if (outed == false) {
            if (TX < width - 20) {
                TX = TX + 5;
                txtv.setTranslationX(TX);
            }
        }
    }

    public void bclk() {
        //nTX
        if (outed == false) {
            if (TX > 0) {
                TX = TX - 5;
                txtv.setTranslationX(TX);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //リスナーの登録解除
        manager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //リスナーの登録
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);

            manager.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO AUTO-generated method Stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            nsx = event.values[SensorManager.DATA_X];
            nsy = event.values[SensorManager.DATA_Y];
            //Log.i("AAA",String.valueOf(nsy));

            if (nsy >= 4.0f) {
                aclk();
            } else if (nsy <= -4.0f) {
                bclk();
            }
            dbgv.setText("TX:" + TX);

            if(!outed) {
                if (TX >= outleftpad && TX <= outrigpad) {
                } else {
                    Log.d("AAA", "アウトを検知");
                    nst.setTextColor(Color.RED);
                    nst.setText("アウトです♥");
                    nst.setVisibility(View.VISIBLE);
                    //out
                    outed = true;
                }
            }
            nsz = event.values[SensorManager.DATA_Z];


        }
    }
}
