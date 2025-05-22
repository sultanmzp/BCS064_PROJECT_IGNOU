package com.saffat.examconductor.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;
import com.saffat.examconductor.RegisterDataAdapterHolder;

import java.util.regex.Pattern;

public class FacultyRegister extends AppCompatActivity {


    private static final Pattern PASSWORD_PATTERN=
            Pattern.compile("^"+
                    "(?=.\\S+$)"+
                    ".{6,}"+
                    "$"
            );

    ProgressDialog pd;

    Button register112_btn_activity_register_03112s;
    EditText name113_et_activity_register_03112s,email113_et_activity_register_03112s,
    password113_et_activity_register_03112s,cnfpassword113_et_activity_register_03112s,phoneno12;
    RegisterDataAdapterHolder rda;

    public static final String USER="Admins";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(USER);

    public static final String TAG="register_Activty_03112";
    TextView acl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_register);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));


        register112_btn_activity_register_03112s=findViewById(R.id.registerButtonRegister);
        name113_et_activity_register_03112s=findViewById(R.id.nameInputRegister);
        email113_et_activity_register_03112s=findViewById(R.id.emailInputRegister);
        password113_et_activity_register_03112s=findViewById(R.id.passwordInputRegister);
        cnfpassword113_et_activity_register_03112s=findViewById(R.id.passwordCnfInputRegister);
        phoneno12=findViewById(R.id.phoneInputRegister);
        acl=findViewById(R.id.registerTextRegister);

        register112_btn_activity_register_03112s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String name31=name113_et_activity_register_03112s.getText().toString();
            String email31=email113_et_activity_register_03112s.getText().toString();
            String pass31=password113_et_activity_register_03112s.getText().toString();
            String cnfpass31=cnfpassword113_et_activity_register_03112s.getText().toString();
            String phone31=phoneno12.getText().toString();

            if (name31.isEmpty())
            {
                name113_et_activity_register_03112s.setError("Please Enter Name");

            }
            else
            {
                if (email31.isEmpty())
                {
                    email113_et_activity_register_03112s.setError("Please Enter Valid Email Id");
                }
                else
                {
                    if (pass31.isEmpty())
                    {
                        password113_et_activity_register_03112s.setError("Enter Password");
                    }
                    else
                    {
                        if (cnfpass31.isEmpty())
                        {
                            cnfpassword113_et_activity_register_03112s.setError("Enter Conform Password");
                        }
                        else
                        {
                            if (pass31.equals(cnfpass31))
                            {
                               if (!Patterns.EMAIL_ADDRESS.matcher(email31).matches())
                               {
                                   email113_et_activity_register_03112s.setError("Enter Valid Email");
                               }
                               else
                               {
                                   if (!PASSWORD_PATTERN.matcher(pass31).matches())
                                   {
                                       password113_et_activity_register_03112s.setError("Enter at least 6 character");
                                   }
                                   else
                                   {

                                       pd=new ProgressDialog(FacultyRegister.this);
                                       pd.setMessage("Loading...");
                                       pd.setCanceledOnTouchOutside(false);
                                       pd.show();
                                       rda=new RegisterDataAdapterHolder(name31,email31,phone31,pass31);
                                       registerOnFirebase(email31,pass31);
                                   }
                               }
                            }
                            else
                            {
                                cnfpassword113_et_activity_register_03112s.setError("Enter Valid Password");
                            }
                        }
                    }
                }
            }


            }
        });

        acl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private void registerOnFirebase(String email31, String pass31) {
        // Register with email and password
        mAuth.createUserWithEmailAndPassword(email31,pass31)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get user ID
                        String userId = mAuth.getCurrentUser().getUid();

                        // Store in database
                        mDatabase.child(userId).setValue(rda)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                                        updateUI();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed, Retry", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "User Already Exist", Toast.LENGTH_SHORT).show();
                    }
                });
        pd.dismiss();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    private void updateUI() {

        name113_et_activity_register_03112s.setText("");
        email113_et_activity_register_03112s.setText("");
        phoneno12.setText("");
        password113_et_activity_register_03112s.setText("");
        cnfpassword113_et_activity_register_03112s.setText("");
        sucessDialogShow();

    }
    private void sucessDialogShow() {
        Dialog dialog=new Dialog(FacultyRegister.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sucessfuldialog);
        dialog.setCanceledOnTouchOutside(false);
        Button dlbtn=dialog.findViewById(R.id.btnSucessFul);
        TextView tvbs=dialog.findViewById(R.id.sucessTextview);
        tvbs.setText("Register Successful !!");
        dlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();
    }

}