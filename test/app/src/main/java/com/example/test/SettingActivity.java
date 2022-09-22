package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;

    Button circle;
    Button cross;
    Button square;
    Button triangle;
    Button start;
    Button menu;
    Button up;
    Button right;
    Button down;
    Button left;
    Button rb;
    Button lb;
    Button lt;
    Button rt;

    CheckBox W;
    CheckBox A;
    CheckBox S;
    CheckBox D;
    CheckBox I;
    CheckBox J;
    CheckBox K;
    CheckBox L;
    CheckBox Q;
    CheckBox E;
    CheckBox _R;
    CheckBox T;
    CheckBox Y;
    CheckBox O;
    CheckBox P;
    CheckBox F;
    CheckBox G;
    CheckBox H;
    CheckBox Z;
    CheckBox X;
    CheckBox C;
    CheckBox V;
    CheckBox B;
    CheckBox N;
    CheckBox M;
    CheckBox ESCAPE;
    CheckBox ENTER;
    CheckBox CONTROL;
    CheckBox ALT;
    CheckBox SPACE;
    CheckBox UP;
    CheckBox DOWN;
    CheckBox RIGHT;
    CheckBox LEFT;

    String tag;


    String isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this; //save the context to show Toast messages

        //Ссылка на все кнопки
        circle = (Button) findViewById(R.id.setting_circle);
        cross = (Button) findViewById(R.id.setting_cross);
        square = (Button) findViewById(R.id.setting_square);
        triangle = (Button) findViewById(R.id.setting_triangle);
        start = (Button) findViewById(R.id.setting_start);
        menu = (Button) findViewById(R.id.setting_menu);
        up = (Button) findViewById(R.id.setting_up);
        right = (Button) findViewById(R.id.setting_right);
        down = (Button) findViewById(R.id.setting_down);
        left = (Button) findViewById(R.id.setting_left);
        rb = (Button) findViewById(R.id.setting_rb);
        lb = (Button) findViewById(R.id.setting_lb);
        lt = (Button) findViewById(R.id.setting_lt);
        rt = (Button) findViewById(R.id.setting_rt);

        circle.setOnClickListener(this);
        cross.setOnClickListener(this);
        square.setOnClickListener(this);
        triangle.setOnClickListener(this);
        start.setOnClickListener(this);
        menu.setOnClickListener(this);
        up.setOnClickListener(this);
        right.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        rb.setOnClickListener(this);
        lb.setOnClickListener(this);
        lt.setOnClickListener(this);
        rt.setOnClickListener(this);

        W = (CheckBox)findViewById(R.id.W);
        A = (CheckBox)findViewById(R.id.A);
        S = (CheckBox)findViewById(R.id.S);
        D = (CheckBox)findViewById(R.id.D);
        I = (CheckBox)findViewById(R.id.I);
        J = (CheckBox)findViewById(R.id.J);
        K = (CheckBox)findViewById(R.id.K);
        L = (CheckBox)findViewById(R.id.L);
        Q = (CheckBox)findViewById(R.id.Q);
        E = (CheckBox)findViewById(R.id.E);
        _R = (CheckBox)findViewById(R.id.R);
        T = (CheckBox)findViewById(R.id.T);
        Y = (CheckBox)findViewById(R.id.Y);
        O = (CheckBox)findViewById(R.id.O);
        P = (CheckBox)findViewById(R.id.P);
        F = (CheckBox)findViewById(R.id.F);
        G = (CheckBox)findViewById(R.id.G);
        H = (CheckBox)findViewById(R.id.H);
        Z = (CheckBox)findViewById(R.id.Z);
        X = (CheckBox)findViewById(R.id.X);
        C = (CheckBox)findViewById(R.id.C);
        V = (CheckBox)findViewById(R.id.V);
        B = (CheckBox)findViewById(R.id.B);
        N = (CheckBox)findViewById(R.id.N);
        M = (CheckBox)findViewById(R.id.M);
        ESCAPE = (CheckBox)findViewById(R.id.ESCAPE);
        ENTER = (CheckBox)findViewById(R.id.ENTER);
        CONTROL = (CheckBox)findViewById(R.id.CONTROL);
        ALT = (CheckBox)findViewById(R.id.ALT);
        SPACE = (CheckBox)findViewById(R.id.SPACE);
        UP = (CheckBox)findViewById(R.id.UP);
        DOWN = (CheckBox)findViewById(R.id.DOWN);
        LEFT = (CheckBox)findViewById(R.id.LEFT);
        RIGHT = (CheckBox)findViewById(R.id.RIGHT);

        Intent intent = getIntent();
        isConnected = intent.getExtras().getString("isConnected");
        //Toast.makeText(context,isConnected,Toast.LENGTH_LONG).show();

    }


    //Загрузка и выгрузка чекеров
    @Override
    public void onClick(View v) {
        tag = ""+v.getTag();
        SharedPreferences sharedPref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        boolean isW = sharedPref.getBoolean(tag+" W", false);//first value - preference name, second value - default value if checkbox not found
        boolean isA = sharedPref.getBoolean(tag+" A", false);
        boolean isS = sharedPref.getBoolean(tag+" S", false);
        boolean isD = sharedPref.getBoolean(tag+" D", false);
        boolean isI = sharedPref.getBoolean(tag+" I", false);
        boolean isJ = sharedPref.getBoolean(tag+" J", false);
        boolean isK = sharedPref.getBoolean(tag+" K", false);
        boolean isL = sharedPref.getBoolean(tag+" L", false);
        boolean isQ = sharedPref.getBoolean(tag+" Q", false);
        boolean isE = sharedPref.getBoolean(tag+" E", false);
        boolean isR = sharedPref.getBoolean(tag+" R", false);
        boolean isT = sharedPref.getBoolean(tag+" T", false);
        boolean isY = sharedPref.getBoolean(tag+" Y", false);
        boolean isO = sharedPref.getBoolean(tag+" O", false);
        boolean isP = sharedPref.getBoolean(tag+" P", false);
        boolean isF = sharedPref.getBoolean(tag+" F", false);
        boolean isG = sharedPref.getBoolean(tag+" G", false);
        boolean isH = sharedPref.getBoolean(tag+" H", false);
        boolean isZ = sharedPref.getBoolean(tag+" Z", false);
        boolean isX = sharedPref.getBoolean(tag+" X", false);
        boolean isC = sharedPref.getBoolean(tag+" C", false);
        boolean isV = sharedPref.getBoolean(tag+" V", false);
        boolean isB = sharedPref.getBoolean(tag+" B", false);
        boolean isN = sharedPref.getBoolean(tag+" N", false);
        boolean isM = sharedPref.getBoolean(tag+" M", false);
        boolean isESCAPE = sharedPref.getBoolean(tag+" ESCAPE", false);
        boolean isENTER = sharedPref.getBoolean(tag+" ENTER", false);
        boolean isCONTROL = sharedPref.getBoolean(tag+" CONTROL", false);
        boolean isALT = sharedPref.getBoolean(tag+" ALT", false);
        boolean isSPACE = sharedPref.getBoolean(tag+" SPACE", false);
        boolean isUP = sharedPref.getBoolean(tag+" UP", false);
        boolean isRIGHT = sharedPref.getBoolean(tag+" RIGHT", false);
        boolean isDOWN = sharedPref.getBoolean(tag+" DOWN", false);
        boolean isLEFT = sharedPref.getBoolean(tag+" LEFT", false);

        W.setChecked(isW);
        A.setChecked(isA);
        S.setChecked(isS);
        D.setChecked(isD);
        I.setChecked(isI);
        J.setChecked(isJ);
        K.setChecked(isK);
        L.setChecked(isL);
        Q.setChecked(isQ);
        E.setChecked(isE);
        _R.setChecked(isR);
        T.setChecked(isT);
        Y.setChecked(isY);
        O.setChecked(isO);
        P.setChecked(isP);
        F.setChecked(isF);
        G.setChecked(isG);
        H.setChecked(isH);
        Z.setChecked(isZ);
        X.setChecked(isX);
        C.setChecked(isC);
        V.setChecked(isV);
        B.setChecked(isB);
        N.setChecked(isN);
        M.setChecked(isM);
        ESCAPE.setChecked(isESCAPE);
        ENTER.setChecked(isENTER);
        CONTROL.setChecked(isCONTROL);
        ALT.setChecked(isALT);
        SPACE.setChecked(isSPACE);
        UP.setChecked(isUP);
        DOWN.setChecked(isDOWN);
        LEFT.setChecked(isLEFT);
        RIGHT.setChecked(isRIGHT);

    }

    public void onClickCheck(View v) {
        SharedPreferences Preference = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = Preference.edit();
        editor.putBoolean(tag+" "+((CheckBox) v).getText().toString(), ((CheckBox) v).isChecked());
        editor.apply();
    }

}
