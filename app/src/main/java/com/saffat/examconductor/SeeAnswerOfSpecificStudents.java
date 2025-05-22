package com.saffat.examconductor;

import static com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.CreateSubjectiveQuestion.LIST_SEPRATOR;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class SeeAnswerOfSpecificStudents extends AppCompatActivity {

    LinearLayout laui;
    TextView cn,csn,csc,cfm,sn,sr,si,se;
    Button fresultAssign;
    EditText etddd;
    List<String> ls;
    String miid,str,stn,sti,ste;

    int cmo=0;
    ResultDatabaseContainer container;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    public static final String USER="FinalResults";

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
        setContentView(R.layout.specific_student_answer);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database= FirebaseDatabase.getInstance();
        mDatabase=database.getReference(USER);

        laui=findViewById(R.id.answerContainerLayout);
        cn=findViewById(R.id.cn_see);
        csn=findViewById(R.id.sn_see);
        csc=findViewById(R.id.sc_see);
        cfm=findViewById(R.id.fm_see);
        sn=findViewById(R.id.stnn_see);
        sr=findViewById(R.id.stnr_see);
        si=findViewById(R.id.stnID_see);
        se=findViewById(R.id.stnE_see);


        final Bundle bdo=getIntent().getExtras();

        String qasss=bdo.getString("quesansddd");
        cn.setText(bdo.getString("classnameddd"));
        csn.setText(bdo.getString("subjectnameddd"));
        csc.setText(bdo.getString("subjectcodeddd"));
        cfm.setText("Full Marks:"+bdo.getString("fmarksddd"));
        sn.setText("   "+bdo.getString("stdnameddd"));
        sr.setText("   "+bdo.getString("stdrollddd"));
        si.setText("   "+bdo.getString("stdidddd"));
        se.setText("   "+bdo.getString("stdemailddd"));

        Toolbar toolbar=findViewById(R.id.toolbar_of_specific_answer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(bdo.getString("stdnameddd"));
        ls=convertStringToArray11(qasss);
        for (String str:ls)
        {
            TextView textView=new TextView(SeeAnswerOfSpecificStudents.this);
            textView.setText(str+"\n");
            textView.setPadding(10,10,10,10);
            laui.addView(textView);
        }
        miid= bdo.getString("meetddd");
        stn=bdo.getString("stdnameddd");
        str=bdo.getString("stdrollddd");
        ste=bdo.getString("stdemailddd");
        sti=bdo.getString("stdidddd");



        fresultAssign=findViewById(R.id.submit_btn_see);
        etddd=findViewById(R.id.assignedMarks_see);
        fresultAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mrk = etddd.getText().toString();
                if (mrk.isEmpty()) {
                    etddd.setError("Assign Marks");
                }
                else {

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot ds:snapshot.getChildren())
                            {
                                if (ds.child("examID").getValue().equals(miid)&&ds.child("studentRoll").getValue().equals(str)) {

                                    //here you write
                                    updateDialogShow(miid,mrk);
                                    cmo=cmo+1;
                                    break;
                                }

                            }
                            if (cmo==0)
                            {
                                container=new ResultDatabaseContainer(miid,stn,str,ste,sti,mrk);
                                addDataOnFirebase();
                                etddd.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });
    }
    private void addDataOnFirebase() {

        String keyID=mDatabase.push().getKey();
        mDatabase.child(keyID).setValue(container);
        Toast.makeText(this, "Sucessfull..", Toast.LENGTH_SHORT).show();
        finish();

    }
    public static List<String> convertStringToArray11(String str)
    {
        return Arrays.asList(str.split(LIST_SEPRATOR));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_of_specific_answer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd=item.getItemId();
        if (idd==R.id.aboutSpecificAnswer)
        {

            StringBuffer sb=new StringBuffer();
            Bundle b=getIntent().getExtras();

            sb.append("\nExam ID:"+b.getString("meetddd"));
            sb.append("\n\nStudent Name:"+b.getString("stdnameddd"));
            sb.append("\nStudent Roll:"+b.getString("stdrollddd"));
            sb.append("\nStudent Email:"+b.getString("stdemailddd"));
            sb.append("\nStudent ID:"+b.getString("stdidddd"));

            showMessage("Exam Info.",sb.toString());
        }
        else if (idd==android.R.id.home)
        {
            finish();
        }
        else
        {
            return true;
        }
        return true;
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder ab=new AlertDialog.Builder(SeeAnswerOfSpecificStudents.this);
        ab.setTitle(title);
        ab.setMessage(message);
        ab.setCancelable(true);
        ab.setPositiveButton("ok",null);
        ab.show();
    }


    private void updateDialogShow(final String eid, final String newMarks) {
        final Dialog dialog=new Dialog(SeeAnswerOfSpecificStudents.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.update_dialog);

        dialog.setCanceledOnTouchOutside(false);
        Button updateBtn=dialog.findViewById(R.id.btnUpdateDialog);
        Button CancelBtn=dialog.findViewById(R.id.btnCancelUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.orderByChild("examID").equalTo(eid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds:snapshot.getChildren())
                        {
                            String keyy=ds.getKey();
                            mDatabase.child(keyy).child("marksObtained").setValue(newMarks);
                            break;//late
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dialog.dismiss();
                etddd.setText("");

                finish();
            }
        });
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}