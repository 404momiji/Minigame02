package moe.yak.game.minigame01;



import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends SensorGameActivity implements SensorEventListener {

    float rand;

    float nsy;
    int timeSec = 25;
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
    int resetPosition = 0;

    float W = 0;
    Handler mHandler;//るいぱんこ
    Handler aHandler;//アニメーション用ハンドラ
    Timer mTimer;
    Timer aTimer;//アニメーションタイマー
    private SensorManager manager;
    public int width = 0;
    //debugviewのインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ
    RelativeLayout lo;
    TextView dbg_TextView;
    //NOW statusとるお
    TextView clearText;
    TextView Nokori_TextView;
    ImageView playerImage;
    public float playerNowPositionX = 0;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animated = true;

        mHandler = new Handler();
        aHandler = new Handler();

        //横画面固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        lo = findViewById(R.id.base);
        ViewTreeObserver observer = lo.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                () -> {
                    initCalc(lo.getWidth());
                    Log.d("WIDTH",lo.getWidth()+"");
                });

        //センサーマネジャーからインスタンスをしゅとくすりゅぅうぅぅぅぅうぅ(はーと)
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        dbg_TextView =  findViewById(R.id.textView5);
        if(ApplicationController.DBG_MODE){
            dbg_TextView.setVisibility(View.VISIBLE);
        }
        Nokori_TextView = findViewById(R.id.NOKORI);
        clearText = findViewById(R.id.nowst);
        playerImage = findViewById(R.id.imageView4);

        playerNowPositionX = playerImage.getTranslationX();


        //プレイヤーを指定位置に設定する
        playerImage.setTranslationX(playerPosition);


        playerNowPositionX = playerImage.getTranslationX();



    }

    private void initCalc(int _width){
        width = _width;
        center = (float) width / 2;
        playerPosition = center - 70;
        outLeftPad = (width - stage) / 2-110;
        outRightPad = outLeftPad + stage+150;
    }
    public void start(View v) {
        moveCount = 0;

        //カウントを初期化
        nowCount = 0;
        //プレイヤーを指定位置に設定する
        playerImage.setTranslationX(playerPosition);

        //playerNowPositionXを初期化しなおす
        playerNowPositionX = playerImage.getTranslationX();

        clearText.setVisibility(View.INVISIBLE);
        playerImage.setTranslationX(playerPosition);
        outed = false;
        mTimer = new Timer(false);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        if (outed) {
                            cancel();
                        }
                        if (animated) {
                            if ((nowCount % 3) == 0 && !(nowCount == 0)) {

                                if (moveCount == 0) {
                                    Random bol = new Random();

                                    resetPosition = bol.nextInt(3);
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
                            Nokori_TextView.setText("残り:"+(timeSec-nowCount/2) + "秒");
                            if (10 > rand) {
                                rand = rand * -1;
                            } else if (rand == 10) {
                                rand = 0;
                            } else if (10 < rand) {
                                rand = rand - 10;
                            }
                            animate(rand);
                        }
                        //TX = TX + rand;
                        //↓移動処理
                        //ST(TX);
                        if (nowCount >= timeSec*2) {
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

    public void setPlayerPosition(float tx) {
        if (!outed) {
            playerImage.setTranslationX(tx);
        }
    }

    public void animate(float wei) {
        playerPositionCount = 1;

        animationCount = 1;
        final int actm = 20;//20周期で終了（）

        W = wei / 20;
        aTimer = new Timer(false);
        aTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                aHandler.post(() -> {

                    animationCount++;
                    playerNowPositionX = (W * playerPositionCount) + playerNowPositionX;
                    setPlayerPosition(playerNowPositionX);
                    if (animationCount >= actm) {
                        animated = true;
                        cancel();
                    }
                    playerPositionCount++;
                });

            }
        }, 0, 50);


    }


    public void aclk(float weg) {
        if (!outed) {
            if (playerNowPositionX < width - 20) {
                playerNowPositionX = playerNowPositionX + weg;
                playerImage.setTranslationX(playerNowPositionX);
            }
        }
    }

    public void bclk(float weg) {
        if (!outed) {
            if (playerNowPositionX > 0) {
                playerNowPositionX = playerNowPositionX + weg;
                playerImage.setTranslationX(playerNowPositionX);
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

        if (!sensors.isEmpty()) {
            Sensor s = sensors.get(0);
            manager.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
        }
    }




    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //nsx = event.values[SensorManager.DATA_X];
            nsy = event.values[SensorManager.DATA_Y];
            //nsz = event.values[SensorManager.DATA_Y];


            if (nsy >= 4.0f) {
                moveCount++;
                aclk(nsy);
            } else if (nsy <= -4.0f) {
                moveCount++;
                bclk(nsy);
            }
            if(ApplicationController.DBG_MODE) {
                dbg_TextView.setText("playerNowPositionX:" + playerNowPositionX);
            }

            if (!outed) {
                if (!(playerNowPositionX >= outLeftPad - 70) || !(playerNowPositionX <= outRightPad - 60)) {
                    Log.d("AAA", "アウトを検知");
                    clearText.setTextColor(Color.RED);
                    clearText.setText("アウトです♥");
                    clearText.setVisibility(View.VISIBLE);
                    //out
                    outed = true;
                }
            }



        }
    }

}
