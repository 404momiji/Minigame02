package com.tool.tetsu2kasen.game02;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends Activity  implements SensorEventListener{

    float nsx;
    float nsy;
    float nsz;

    int mTime;

    int taped = 0;
    Handler mHandler;

    boolean Handed=true;

    Timer mTimer;
    private SensorManager manager;



    public int width=0;
    TextView txtv;
    public float TX=0;
    public float nTX=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Windowマネージャをよびだしゅぅぅぅぅぅぅぅぅ
        WindowManager wm = getWindowManager();
        //Dispのインスタンスを取得すりゅううううう
        Display disp =wm.getDefaultDisplay();
        width = disp.getWidth();
        //センサーマネジャーからインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ(はーと)
        manager =(SensorManager)getSystemService(SENSOR_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtv =(TextView)findViewById(R.id.myView2);
        TX=txtv.getTranslationX();
    }
    public void aclk(View v){
        if(TX<width-20) {
            TX=TX+50;
            txtv.setTranslationX(TX);
        }
    }
    public void bclk(View v){
        //nTX
        if(TX>0) {
            TX=TX-50;
            txtv.setTranslationX(TX);
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
            nsz=event.values[SensorManager.DATA_Z];


        }
    }
}
