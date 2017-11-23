/*
//Next do 残り時間を表示する
//
*/

package com.tool.tetsu2kasen.game02;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import static com.tool.tetsu2kasen.game02.R.color.colorAccent;

public class MainActivity extends Activity  implements SensorEventListener{

    float rand;
    float nsx;
    float nsy;
    float nsz;
    float outleftpad;
    float outrigpad;
    float center;
    float plcenter;
    final float stage=500;
    boolean outed=true;
    final int count=50;//25秒間
    int nwcot=0;
    int mTime;

    final int nmcount =50;

    int taped = 0;
    Handler mHandler;

    boolean Handed=true;

    Timer mTimer;
    private SensorManager manager;


    public void oc(View v){

        //プレイヤーを指定位置に設定する
        txtv.setTranslationX(plcenter);

        //TXを初期化しなおす
        TX=txtv.getTranslationX();

        nst.setVisibility(View.INVISIBLE);
        txtv.setTranslationX(plcenter);
        outed=false;
        mTimer = new Timer(false);
        mTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(outed){cancel();}

                        Random random = new Random();
                        rand= random.nextInt(20);
                        rand=(rand-10)*10;
                        TX=TX+rand;
                        ST(TX);
                        if(nwcot>=50){
                            //Clear
                            nst.setTextColor(Color.YELLOW);
                            nst.setText("くりあです♥");
                            nst.setVisibility(View.VISIBLE);
                            outed=true;
                            nwcot=0;
                            cancel();
                        }
                        nwcot++;
                    }
                });

            }
        },0,500);
    }

    public float ST(float tx){
        if(outed==false) {
            txtv.setTranslationX(tx);
        }
        return 0;
    }
    public int width=0;
    TextView txtv;
    //debugviewのインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ
    TextView dbgv;
    //NOW statusとるお
    TextView nst;
    public float TX=0;
    public float nTX=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //ハンドラー取得すりゅ♥
        mHandler = new Handler();
        //横画面固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Windowマネージャをよびだしゅぅぅぅぅぅぅぅぅ
        WindowManager wm = getWindowManager();
        //Dispのインスタンスを取得すりゅううううう
        Display disp =wm.getDefaultDisplay();
        width = disp.getWidth();
        //センサーマネジャーからインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ(はーと)
        manager =(SensorManager)getSystemService(SENSOR_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbgv= (TextView)findViewById(R.id.textView5);
        txtv =(TextView)findViewById(R.id.myView2);

        nst=(TextView)findViewById(R.id.nowst);

        TX=txtv.getTranslationX();
        center=width/2;
        plcenter=center-10;

        //プレイヤーを指定位置に設定する
        txtv.setTranslationX(plcenter);

        //TXを初期化しなおす
        TX=txtv.getTranslationX();

        outleftpad=(width-stage)/2;
        outrigpad=outleftpad+stage;
    }
    public void aclk(){
        if(outed==false) {
            if (TX < width - 20) {
                TX = TX + 5;
                txtv.setTranslationX(TX);
            }
        }
    }
    public void bclk(){
        //nTX
        if(outed==false) {
            if (TX > 0) {
                TX = TX - 5;
                txtv.setTranslationX(TX);
            }
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        //リスナーの登録解除
        manager.unregisterListener(this);
    }
    @Override
    protected void onResume(){
        super.onResume();
        //リスナーの登録
        List<Sensor> sensors= manager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if(sensors.size()>0){
            Sensor s =sensors.get(0);

            manager.registerListener(this,s,SensorManager.SENSOR_DELAY_GAME);

        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor,int  accuracy){
        //TODO AUTO-generated method Stub
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            nsx=event.values[SensorManager.DATA_X];
            nsy=event.values[SensorManager.DATA_Y];
            //Log.i("AAA",String.valueOf(nsy));

            if(nsy>=4.0f){aclk();}
            else if(nsy<=-4.0f){bclk();}
            dbgv.setText("TX:"+TX);
            if(TX>=outleftpad&&TX<=outrigpad){}else{
                Log.d("AAA", "アウトを検知");
                nst.setTextColor(Color.RED);
                nst.setText("アウトです♥");
                nst.setVisibility(View.VISIBLE);
                //out
                outed=true;
            }
            nsz=event.values[SensorManager.DATA_Z];


        }
    }
}