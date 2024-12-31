package moe.yak.game.minigame01;

/*
//Next do 残り時間を表示する
//
*/



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
import android.widget.ImageView;
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
    float outLeftPad;
    float outRightPad;
    float center;
    float playerPosition;
    int moveCount = 0;

    boolean animated = true;
    public int animationCount = 0;

    final float stage = 500;
    boolean outed = true;
    int nowCount = 0;
    int playerPositionCount = 0;
    public int mTime;
    int resetPosition = 0;

    final int nmcount = 50;

    float W = 0;
    int taped = 0;
    Handler mHandler;//るいぱんこ
    Handler aHandler;//アニメーション用ハンドラ


    boolean Handed = true;

    Timer mTimer;
    Timer aTimer;//アニメーションタイマー

    private SensorManager manager;

    public int width = 0;
    TextView txtv;
    //debugviewのインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ
    TextView dbgv;
    //NOW statusとるお
    TextView clearText;
    TextView nkr;
    ImageView playerImage;
    public float playerNowPositionX = 0;
    public float nTX = 0;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animated = true;
        //ハンドラー取得すりゅ♥
        mHandler = new Handler();
        //バグの原因っぽい（下が抜けてた）
        aHandler = new Handler();

        //横画面固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Windowマネージャをよびだしゅぅぅぅぅぅぅぅぅ
        WindowManager wm = getWindowManager();
        //Dispのインスタンスを取得すりゅううううう
        Display disp = wm.getDefaultDisplay();
        width = disp.getWidth();
        //センサーマネジャーからインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ(はーと)
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        dbgv = (TextView) findViewById(R.id.textView5);
        txtv = (TextView) findViewById(R.id.myView2);
        nkr = (TextView) findViewById(R.id.NOKORI);
        clearText = (TextView) findViewById(R.id.nowst);
        playerImage = (ImageView) findViewById(R.id.imageView4);

        playerNowPositionX = playerImage.getTranslationX();
        //TX = txtv.getTranslationX();
        center = width / 2;
        playerPosition = center - 35;

        //プレイヤーを指定位置に設定する
        //txtv.setTranslationX(plcenter);
        playerImage.setTranslationX(playerPosition);

        //TXを初期化しなおす
        //TX = txtv.getTranslationX();
        playerNowPositionX = playerImage.getTranslationX();

        outLeftPad = (width - stage) / 2;
        outRightPad = outLeftPad + stage;

    }

    public void start(View v) {
        moveCount = 0;

        //カウントを初期化
        nowCount = 0;
        //プレイヤーを指定位置に設定する
        playerImage.setTranslationX(playerPosition);
        //txtv.setTranslationX(plcenter);

        //playerNowPositionXを初期化しなおす
        //playerNowPositionX = txtv.getTranslationX();
        playerNowPositionX = playerImage.getTranslationX();

        clearText.setVisibility(View.INVISIBLE);
        //txtv.setTranslationX(plcenter);
        playerImage.setTranslationX(playerPosition);
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
                        if (animated == true) {
                            if ((nowCount % 3) == 0 && !(nowCount == 0)) {

                                if (moveCount == 0) {
                                    Random bol = new Random();

                                    resetPosition = bol.nextInt(1);
                                    if (resetPosition == 0) {
                                        playerNowPositionX = outLeftPad - 5;
                                        setPlayerPosition(playerNowPositionX);
                                    } else if (resetPosition == 1) {
                                        playerNowPositionX = outRightPad + 5;
                                        setPlayerPosition(playerNowPositionX);
                                    }
                                } else {
                                    moveCount = 0;
                                }
                            }
                            animated = false;
                            Random random = new Random();

                            rand = random.nextInt(20);
                            if (rand == 0) {
                                rand = random.nextInt(20);
                            }
                            nkr.setText(rand + ":RAND");
                            if (10 > rand) {
                                rand = rand * -1;
                            } else if (rand == 10) {
                                rand = 0;
                            } else if (10 < rand) {
                                rand = rand - 10;
                            }
                            animation(rand);
                        }
                        //TX = TX + rand;
                        //↓移動処理
                        //ST(TX);
                        if (nowCount >= 50) {
                            clear();
                            cancel();
                        }
                        nowCount++;
                    }
                });

            }
        }, 0, 500);
    }
    public void clear() {
        //Clear
        clearText.setTextColor(Color.YELLOW);
        clearText.setText("くりあです♥");
        clearText.setVisibility(View.VISIBLE);
        outed = true;
        nowCount = 0;


    }

    public float setPlayerPosition(float tx) {
        if (outed == false) {
            playerImage.setTranslationX(tx);
            //txtv.setTranslationX(tx);
        }
        return 0;
    }

    public float animation(float wei) {
        playerPositionCount = 1;

        animationCount = 1;
        final int actm = 20;//20周期で終了（）

        W = wei / 20;
        float anmct;
        aTimer = new Timer(false);
        aTimer.schedule(new TimerTask() {
            @Override
            public void run() {//ここ等辺でおちる
                aHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        animationCount++;
                        playerNowPositionX = (W * playerPositionCount) + playerNowPositionX;
                        setPlayerPosition(playerNowPositionX);
                        if (animationCount >= actm) {
                            animated = true;
                            cancel();
                        }
                        playerPositionCount++;
                    }
                });

            }
        }, 0, 50);


        return 0;
    }


    public void aclk(float weg) {
        if (outed == false) {
            if (playerNowPositionX < width - 20) {
                playerNowPositionX = playerNowPositionX + weg;
                playerImage.setTranslationX(playerNowPositionX);
                //txtv.setTranslationX(TX);
            }
        }
    }

    public void bclk(float weg) {
        //nTX
        if (outed == false) {
            if (playerNowPositionX > 0) {
                playerNowPositionX = playerNowPositionX + weg;
                playerImage.setTranslationX(playerNowPositionX);
                //txtv.setTranslationX(TX);
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
                moveCount++;
                aclk(nsy);
            } else if (nsy <= -4.0f) {
                moveCount++;
                bclk(nsy);
            }
            dbgv.setText("playerNowPositionX:" + playerNowPositionX);

            if (!outed) {
                if (playerNowPositionX >= outLeftPad - 70 && playerNowPositionX <= outRightPad - 60) {
                } else {
                    Log.d("AAA", "アウトを検知");
                    clearText.setTextColor(Color.RED);
                    clearText.setText("アウトです♥");
                    clearText.setVisibility(View.VISIBLE);
                    //out
                    outed = true;
                }
            }
            nsz = event.values[SensorManager.DATA_Z];


        }
    }
}
