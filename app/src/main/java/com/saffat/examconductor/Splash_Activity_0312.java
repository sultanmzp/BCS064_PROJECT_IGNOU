package com.saffat.examconductor;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.saffat.examconductor.Activities.HomeActivity;

public class Splash_Activity_0312 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent=new Intent(Splash_Activity_0312.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
}