package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MouseActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Button leftClick;
    Button rightClick;
    TextView mousePad;

    //private boolean isConnected=false;
    private boolean mouseMoved=false;

    private float initX =0;
    private float initY =0;
    private float disX =0;
    private float disY =0;

    String isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this; //save the context to show Toast messages

        Intent intent = getIntent();
        isConnected = intent.getExtras().getString("isConnected");


        // Ссылка на все кнопки
        leftClick = (Button)findViewById(R.id.leftClick);
        rightClick = (Button)findViewById(R.id.rightClick);


        leftClick.setOnClickListener(this);
        rightClick.setOnClickListener(this);

        //Ссылка на TextView как mousepad
        mousePad = (TextView)findViewById(R.id.mousePad);

        //Захват нажатия и движения пальцами
        mousePad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isConnected.equals("true") && ChooseActivity.out != null){
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            //save X and Y positions when user touches the TextView
                            initX =event.getX();
                            initY =event.getY();
                            mouseMoved=false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            disX = event.getX()- initX; //Mouse movement in x direction
                            disY = event.getY()- initY; //Mouse movement in y direction
                            /*set init to new position so that continuous mouse movement
                            is captured*/
                            initX = event.getX();
                            initY = event.getY();
                            if(disX !=0|| disY !=0){
                                ChooseActivity.out.println(disX +","+ disY); //send mouse movement to server
                            }
                            mouseMoved=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            //consider a tap only if usr did not move mouse after ACTION_DOWN
                            if(!mouseMoved){
                                ChooseActivity.out.println(Constants.MOUSE_LEFT_CLICK);
                            }
                    }
                }
                return true;
            }
        });
    }


    //Нажатия кнопок
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftClick:
                if (isConnected.equals("true") && ChooseActivity.out != null) {
                    //MSocket.sendInfo(Constants.PLAY);
                    ChooseActivity.out.println(Constants.MOUSE_LEFT_CLICK);//send "play" to server
                }
                break;
            case R.id.rightClick:
                if (isConnected.equals("true") && ChooseActivity.out != null) {
                    ChooseActivity.out.println(Constants.MOUSE_RIGHT_CLICK); //send "next" to server
                }
                break;
        }

    }

}