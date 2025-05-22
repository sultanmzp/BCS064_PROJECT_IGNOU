package com.saffat.examconductor.Activities.JoinExam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;


public class ExamIdValidator extends AppCompatActivity {

    EditText mid,passcode;
    Button join_btn;
    private FirebaseDatabase database1;
    int cmo=0;
    int flag1=0,flag2=0;
    private DatabaseReference userRef,mDatabase;
    public static final String USER="QuestionSet";
    public static final String USER1="QuestionAnswer";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_id_validator);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database1=FirebaseDatabase.getInstance();
        userRef=database1.getReference(USER);
        mDatabase=database1.getReference(USER1);

        mid=findViewById(R.id.exam_id_et_fragment_join_class_meeting);
        passcode=findViewById(R.id.password_et_fragment_join_class_meeting);
        join_btn=findViewById(R.id.join_btn_fragment_join_class_meeting);

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd=new ProgressDialog(ExamIdValidator.this);
                pd.setMessage("Loading...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                examValidterId();
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
                .setMessage("Are you sure you want to Cancel")
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
    private void examValidterId() {

        final String emailla=mid.getText().toString();
        final String passwordla=passcode.getText().toString();

        if (emailla.isEmpty())
        {
            pd.dismiss();
            mid.setError("Please Enter Meeting ID");

        }
        else
        {
            if (passwordla.isEmpty())
            {
                pd.dismiss();
                passcode.setError("Please Enter Password");

            }
            else
            {

                connectonToFirebaseOnline(emailla,passwordla);
            }
        }

    }

    private void connectonToFirebaseOnline(final String emailla, final String passwordla) {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {


                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        for (DataSnapshot ds11:snapshot1.getChildren()) {
                            Bundle b1 = getIntent().getExtras();
                            if (ds11.child("examIDs").getValue().equals(emailla) && ds11.child("studentEmailm").getValue().equals(b1.getString("stemail"))) {
                                cmo=cmo+1;
                            }
                        }
                        if (cmo==0)
                        {
                            for (DataSnapshot ds:snapshot.getChildren())
                            {
                                if (ds.child("examID").getValue().equals(emailla)&&ds.child("examPassword").getValue().equals(passwordla))
                                {      flag2=flag2+1;

                                    if (ds.child("timeResponse").getValue().equals("Start")) {
                                        studentTransferDataToAnotherActivity(ds);
                                        flag1 = flag1 + 1;
                                        break;
                                    }
                                }
                            }
                            if (flag2==0)
                            {
                                pd.dismiss();
                                showMessage("Error!","Invalid Exam ID/Password");
                            }
                            else if (flag1==0)
                            {
                                pd.dismiss();
                                showMessage("Error!","Admin not Start Response");
                            }
                            else
                            {

                            }

                        }
                        else
                        {
                            pd.dismiss();
                            showMessage("Suggestion","You Already Give Exam\nContact to Examiner");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void studentTransferDataToAnotherActivity(DataSnapshot ds) {
        Bundle b = getIntent().getExtras();
        Intent intent = new Intent(ExamIdValidator.this, QuestionSheet.class);
        intent.putExtra("meetid", ds.child("examID").getValue(String.class));
        intent.putExtra("meetpassword",ds.child("examPassword").getValue(String.class));
        intent.putExtra("classssnm",ds.child("className").getValue(String.class));
        intent.putExtra("subcccc",ds.child("subjectCode").getValue(String.class));
        intent.putExtra("subnnnn", ds.child("subjectName").getValue(String.class));
        intent.putExtra("fmmm", ds.child("fullMarks").getValue(String.class));
        intent.putExtra("quesssss", ds.child("questionsSet").getValue(String.class));

        intent.putExtra("stname2", b.getString("stname"));
        intent.putExtra("stroll2", b.getString("stroll"));
        intent.putExtra("stemail2", b.getString("stemail"));
        intent.putExtra("stid2", b.getString("stid"));
        startActivity(intent);
        mid.setText("");
        passcode.setText("");
        finish();
    }

    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(Message);
        b.setCancelable(true);
        b.setPositiveButton("ok",null);
        b.show();
    }

}