package com.saffat.examconductor.Activities.JoinExam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;

public class StudentJoinExam extends AppCompatActivity {

    EditText roll_etd_activity_join_03112s, idc, name_etd_activity_join_03112s, email_etd_activity_join_03112s;
    Button btn_next_et_activity_join_03112s;
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_join_exam);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        roll_etd_activity_join_03112s = findViewById(R.id.roll_et_activity_join_03112s);
        name_etd_activity_join_03112s = findViewById(R.id.name_et_activity_join_03112s);
        email_etd_activity_join_03112s = findViewById(R.id.email_et_activity_join_03112s);
        idc = findViewById(R.id.idcard_et_activity_join_03112s);

        btn_next_et_activity_join_03112s = findViewById(R.id.button_next_et_activity_join_03112s);

        btn_next_et_activity_join_03112s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                joinInfoGetStudents();
            }
        });

        // Register the back press dispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

    }

    private void joinInfoGetStudents() {
        String wsn = name_etd_activity_join_03112s.getText().toString();
        String wsr = roll_etd_activity_join_03112s.getText().toString();
        String wse = email_etd_activity_join_03112s.getText().toString();
        String wid = idc.getText().toString();

        if (wsn.isEmpty()) {
            name_etd_activity_join_03112s.setError("Please Enter Name");
        } else {
            if (wsr.isEmpty()) {
                roll_etd_activity_join_03112s.setError("Please Enter Roll Number");
            } else {
                if (wse.isEmpty()) {
                    email_etd_activity_join_03112s.setError("Please Enter Email Id");
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(wse).matches()) {
                        email_etd_activity_join_03112s.setError("Enter valid Email");
                    } else {
                        if (wid.isEmpty()) {
                            Toast.makeText(StudentJoinExam.this, "Joining...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentJoinExam.this, ExamIdValidator.class);
                            intent.putExtra("stname", wsn);
                            intent.putExtra("stroll", wsr);
                            intent.putExtra("stemail", wse);
                            intent.putExtra("stid", "null");
                            startActivity(intent);
                            name_etd_activity_join_03112s.setText("");
                            roll_etd_activity_join_03112s.setText("");
                            email_etd_activity_join_03112s.setText("");
                        } else {
                            Toast.makeText(StudentJoinExam.this, "Joining...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StudentJoinExam.this, ExamIdValidator.class);
                            intent.putExtra("stname", wsn);
                            intent.putExtra("stroll", wsr);
                            intent.putExtra("stemail", wse);
                            intent.putExtra("stid", wid);
                            startActivity(intent);

                            name_etd_activity_join_03112s.setText("");
                            roll_etd_activity_join_03112s.setText("");
                            email_etd_activity_join_03112s.setText("");
                            idc.setText("");
                        }
                    }

                }
            }
        }
    }

}