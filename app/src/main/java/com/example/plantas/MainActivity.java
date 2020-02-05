package com.example.plantas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnI;
    Handler mHandler = new Handler();
    boolean isRunning = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnI = findViewById(R.id.btnJuego);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (isRunning) {
//                    try {
//                        Thread.sleep(10000);
//                        mHandler.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//                                // Write your code here to update the UI.
//                                displayData();
//                            }
//                        });
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                }
//            }
//        }).start();

    }

    public void Avatar(View v) {
        Intent avatar = new Intent(this, avatar.class);
        startActivity(avatar);
    }
//
//    private void displayData() {
//        ConnectivityManager cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo nf=cn.getActiveNetworkInfo();
//        if(nf != null && nf.isConnected()==true)
//        {
//            Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show();
//            //myTextView.setText("Network Available");
//        }
//        else
//        {
//            Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show();
//            //myTextView.setText("Network Not Available");
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        isRunning = false;
//        super.onStop();
//
//    }
}
