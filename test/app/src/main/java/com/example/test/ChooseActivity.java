package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ChooseActivity extends AppCompatActivity {
    Context context;


    private boolean isConnected = false;
    private boolean mouseMoved = false;
    static PrintWriter out;

    static Socket socket;
    String ip;

    ConnectPhoneTask connectPhoneTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent intent = getIntent();
        ip = intent.getExtras().getString("ip_address");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this; //save the context to show Toast messages

        //Создание соединения по полученному адресу
        connectPhoneTask = new ConnectPhoneTask();
        connectPhoneTask.execute(ip);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isConnected && out!=null) {
            try {
                out.println("exit"); //tell server to exit
                socket.close(); //close socket
            } catch (IOException e) {
                Log.e("remotedroid", "Error in closing socket", e);
            }
        }
    }

    //Поток для создания соединения с сервером
    public class ConnectPhoneTask extends AsyncTask<String,Void,Boolean> {


        public void closeConnection() {     /* Проверяем сокет. Если он не зарыт, то закрываем его и освобдождаем соединение.*/
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e("LOG_TAG", "Невозможно закрыть сокет: " + e.getMessage());
                } finally {
                    socket = null;
                }
            }
            socket = null;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            InetSocketAddress sa = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(params[0]);
                sa = new InetSocketAddress(serverAddr, Constants.SERVER_PORT);
                closeConnection();
                socket = new Socket();
                socket.connect(sa, 500);

            }
            catch (SocketTimeoutException e) {
                Log.e("remotedroid", "Error while connecting 1", e);
                try {
                    socket.connect(sa, 150);
                } catch (SocketTimeoutException er) {
                    result = false;
                }catch (IOException ex) {
                    result = false;
                }
            }
            catch (IOException e) {
                Log.e("remotedroid", "Error while connecting 2", e);
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            isConnected = result;
            Toast.makeText(context,isConnected?"Подключено!":"Ошибка при подключении",Toast.LENGTH_LONG).show();
            try {
                if(isConnected) {
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); //create output stream to send data to server
                } else {
                    Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                    //Clear all activities and start new task
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }catch (IOException e){
                Log.e("remotedroid", "Error while creating OutWriter", e);
                Toast.makeText(context,"Ошибка при подключении",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
//                //Clear all activities and start new task
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        }
    }

    //Загрузка активностей

    public void onMouseClick(View view) {
        Intent intent = new Intent(ChooseActivity.this, MouseActivity.class);
        String temp = "true";
        if (isConnected){
            temp = "true";
        }
        else
            temp = "false";
        intent.putExtra("isConnected", temp);
        startActivity(intent);
    }

    public void onGamepadClick(View view) {
        Intent intent = new Intent(ChooseActivity.this, GamepadActivity.class);
        String temp = "true";
        if (isConnected){
            temp = "true";
        }
        else
            temp = "false";
        intent.putExtra("isConnected", temp);
        startActivity(intent);
    }

    public void onSettingClick(View view) {
        Intent intent = new Intent(ChooseActivity.this, SettingActivity.class);
        String temp = "true";
        if (isConnected){
            temp = "true";
        }
        else
            temp = "false";
        intent.putExtra("isConnected", temp);
        startActivity(intent);
    }
}
