package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GamepadActivity extends AppCompatActivity implements View.OnTouchListener {
    Context context;
    //Button leftClick;
    //Button rightClick;
    //TextView mousePad;

    ImageButton circle;
    ImageButton cross;
    ImageButton square;
    ImageButton triangle;
    ImageButton start;
    ImageButton menu;
    ImageButton up;
    ImageButton right;
    ImageButton down;
    ImageButton left;
    ImageButton rb;
    ImageButton lb;
    ImageButton lt;
    ImageButton rt;

    String trash;

    SharedPreferences sharedPref;

    List<String> keys = Arrays.asList("W", "A", "S", "D", "I", "J", "K", "L", "ESCAPE", "ENTER", "Q",  "E",  "R",  "T",  "Y",  "O",  "P",  "F",  "G",  "H",  "Z",  "X",  "C",  "V",  "B",  "N",  "M",
            "ALT", "CONTROL", "SPACE", "UP", "DOWN", "RIGHT", "LEFT");

    String circleValue;
    String crossValue;
    String squareValue;
    String triangleValue;
    String startValue;
    String menuValue;
    String upValue;
    String rightValue;
    String downValue;
    String leftValue;
    String rbValue;
    String lbValue;
    String ltValue;
    String rtValue;

    private boolean mouseMoved=false;

    private float initX =0;
    private float initY =0;
    private float disX =0;
    private float disY =0;


    String isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamepad);
        //FullScreencall();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this; //save the context to show Toast messages

        Intent intent = getIntent();
        isConnected = intent.getExtras().getString("isConnected");


        //Ссылка на кнопки
        circle = (ImageButton) findViewById(R.id.circle);
        cross = (ImageButton) findViewById(R.id.cross);
        square = (ImageButton) findViewById(R.id.square);
        triangle = (ImageButton) findViewById(R.id.triangle);
        start = (ImageButton) findViewById(R.id.start);
        menu = (ImageButton) findViewById(R.id.menu);
        up = (ImageButton) findViewById(R.id.up);
        right = (ImageButton) findViewById(R.id.right);
        down = (ImageButton) findViewById(R.id.down);
        left = (ImageButton) findViewById(R.id.left);
        rb = (ImageButton) findViewById(R.id.rb);
        lb = (ImageButton) findViewById(R.id.lb);
        lt = (ImageButton) findViewById(R.id.lt);
        rt = (ImageButton) findViewById(R.id.rt);

        circle.setOnTouchListener(this);
        cross.setOnTouchListener(this);
        square.setOnTouchListener(this);
        triangle.setOnTouchListener(this);
        start.setOnTouchListener(this);
        menu.setOnTouchListener(this);
        up.setOnTouchListener(this);
        right.setOnTouchListener(this);
        down.setOnTouchListener(this);
        left.setOnTouchListener(this);
        rb.setOnTouchListener(this);
        lb.setOnTouchListener(this);
        lt.setOnTouchListener(this);
        rt.setOnTouchListener(this);

        sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        circleValue = getButtonStringValue(R.id.circle);
        squareValue = getButtonStringValue(R.id.square);
        triangleValue = getButtonStringValue(R.id.triangle);
        crossValue = getButtonStringValue(R.id.cross);
        startValue = getButtonStringValue(R.id.start);
        menuValue = getButtonStringValue(R.id.menu);
        upValue = getButtonStringValue(R.id.up);
        rightValue = getButtonStringValue(R.id.right);
        downValue = getButtonStringValue(R.id.down);
        leftValue = getButtonStringValue(R.id.left);
        rbValue = getButtonStringValue(R.id.rb);
        lbValue = getButtonStringValue(R.id.lb);
        rtValue = getButtonStringValue(R.id.rt);
        ltValue = getButtonStringValue(R.id.lt);

    }

    //Обработка набор нажатых клавиш
    public String getButtonStringValue(int id){
        ImageButton btn = (ImageButton) findViewById(id);
        String tag = ""+btn.getTag();
        StringBuilder result = new StringBuilder();

        String plus = "";
        for (String temp : keys) {
            if (sharedPref.getBoolean(tag+" "+temp, false)) {
                result.append(plus);
                plus="+";
                result.append(temp);
            }
        }

        return result.toString();
    }


    //Оработка нажатых кнопок
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMask = event.getActionMasked();
        ImageButton val = (ImageButton) findViewById(v.getId());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) val.getLayoutParams();
        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: // первое касание
                params.height -= 10;
                params.width -= 10;
                val.setLayoutParams(params);
                sendInfo(v.getId(), "press");
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: // прерывание последнего касания
                params.height += 10;
                params.width += 10;
                val.setLayoutParams(params);
                sendInfo(v.getId(), "release");
                break;

        }
        return true;
    }

    //Отправка полученной информации на сервер
    public void sendInfo(int _id, String val) {
        if (isConnected != null && isConnected.equals("true") && ChooseActivity.out != null) {
            switch (_id) {
                case R.id.cross:
                    if (crossValue != null && !crossValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + crossValue);//send "play" to server
                    }
                    break;
                case R.id.circle:
                    if (circleValue != null && !circleValue.equals("")) {
                        ChooseActivity.out.println(val + "+" + circleValue); //send "next" to server
                    }
                    break;
                case R.id.square:
                    if (squareValue != null && !squareValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + squareValue);//send "play" to server
                    }
                    break;
                case R.id.triangle:
                    if (triangleValue != null && !triangleValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + triangleValue);//send "play" to server
                    }
                    break;
                case R.id.start:
                    if (startValue != null && !startValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + startValue);//send "play" to server
                    }
                    break;
                case R.id.menu:
                    if (menuValue != null && !menuValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + menuValue);//send "play" to server
                    }
                    break;
                case R.id.lb:
                    if (lbValue != null && !lbValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + lbValue);//send "play" to server
                    }
                    break;
                case R.id.rb:
                    if (rbValue != null && !rbValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + rbValue);//send "play" to server
                    }
                    break;
                case R.id.rt:
                    if (rtValue != null && !rtValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + rtValue);//send "play" to server
                    }
                    break;
                case R.id.lt:
                    if (ltValue != null && !ltValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + ltValue);//send "play" to server
                    }
                    break;
                case R.id.up:
                    if (upValue != null && !upValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + upValue);//send "play" to server
                    }
                    break;
                case R.id.left:
                    if (leftValue != null && !leftValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + leftValue);//send "play" to server
                    }
                    break;
                case R.id.right:
                    if (rightValue != null && !rightValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + rightValue);//send "play" to server
                    }
                    break;
                case R.id.down:
                    if (downValue != null && !downValue.equals("")) {
                        //MSocket.sendInfo(Constants.PLAY);
                        ChooseActivity.out.println(val + "+" + downValue);//send "play" to server
                    }
                    break;
            }
        }
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
