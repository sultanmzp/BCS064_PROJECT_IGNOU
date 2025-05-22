package com.saffat.examconductor.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;

public class FacultyLogin extends AppCompatActivity {

    EditText et_email_et_login03112s, et_password_et_login03112s;
    Button btn_login_et_login03112s;
    TextView tvreg;
    public static final String USER = "Admins";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(USER);

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_logion);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        et_email_et_login03112s = findViewById(R.id.emailInputLogin);
        et_password_et_login03112s = findViewById(R.id.passwordInputLogin);
        btn_login_et_login03112s = findViewById(R.id.loginButtonLogin);

        tvreg = findViewById(R.id.registerTextLogin);

        btn_login_et_login03112s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd = new ProgressDialog(FacultyLogin.this);
                pd.setMessage("Loading...");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                final String emailla = et_email_et_login03112s.getText().toString();
                final String passwordla = et_password_et_login03112s.getText().toString();

                if (emailla.isEmpty()) {
                    pd.dismiss();
                    et_email_et_login03112s.setError("Please Enter Email ID");
                } else {
                    if (passwordla.isEmpty()) {
                        pd.dismiss();
                        et_password_et_login03112s.setError("Enter Password");
                    } else {
                        // Login
                        mAuth.signInWithEmailAndPassword(emailla, passwordla)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Get user ID
                                        String userId = mAuth.getCurrentUser().getUid();

                                        // Retrieve user details from database
                                        mDatabase.child(userId).get().addOnCompleteListener(dataTask -> {
                                            if (dataTask.isSuccessful() && dataTask.getResult().exists()) {
                                                DataSnapshot snapshot = dataTask.getResult();

                                                // Assign values to variables
                                                String userName = snapshot.child("adminName").getValue(String.class);
                                                String userEmail = snapshot.child("adminEmail").getValue(String.class);
                                                String userPhone = snapshot.child("adminPhoneNo").getValue(String.class);

                                                Intent intent = new Intent(FacultyLogin.this, FacultyPanel.class);
                                                intent.putExtra("email", userEmail);
                                                intent.putExtra("name", userName);
                                                intent.putExtra("phone", userPhone);
                                                et_email_et_login03112s.setText("");
                                                et_password_et_login03112s.setText("");
                                                pd.dismiss();
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "User Not found", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }
                                        });

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    }
                                });
                    }
                }

            }
        });
        tvreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FacultyLogin.this, FacultyRegister.class);
                startActivity(i);
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
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

}