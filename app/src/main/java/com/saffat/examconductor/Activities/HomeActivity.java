package com.saffat.examconductor.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.saffat.examconductor.Activities.JoinExam.StudentJoinExam;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;

public class HomeActivity extends AppCompatActivity {


    LinearLayout login0312ma,join0312;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dashboard);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        login0312ma=findViewById(R.id.facultyButtonMain);
        join0312=findViewById(R.id.studentButtonMain);

        login0312ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(HomeActivity.this, FacultyLogin.class);
                startActivity(i1);
            }
        });
        join0312.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(HomeActivity.this, StudentJoinExam.class);
                startActivity(i1);
            }
        });

        // Register the back press dispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitConfirmation();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Alert!!")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES", (dialog, which) -> {
                    // Exit activity
                    finish();
                })
                .setNegativeButton("NO", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}