package com.sujin.hamrobazar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.sujin.hamrobazar.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Using a thread and halt screen for 2 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("welcome",MODE_PRIVATE);
                String terms=sharedPreferences.getString("terms","");
                String safety=sharedPreferences.getString("safety","");
                String ad=sharedPreferences.getString("ad","");

                if ((terms.equals("checked")) && (safety.equals("checked")) &&(ad.equals("checked"))){
                    Intent intent=new Intent(SplashScreenActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(SplashScreenActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);
    }
}

