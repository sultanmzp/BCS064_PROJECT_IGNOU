package com.saffat.examconductor;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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


public class ShowFinalResult extends AppCompatActivity {


    TextView clsnn,snnn,scnn,fmnn;

    String mid;
    TableLayout finalResultTL;

    private FirebaseDatabase database1;
    private DatabaseReference userRef;
    public static final String USER="FinalResults";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_final_result);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database1=FirebaseDatabase.getInstance();
        userRef=database1.getReference(USER);

        Toolbar toolbar=findViewById(R.id.toolbarAnser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bgh=getIntent().getExtras();
        clsnn=findViewById(R.id.classname_tv_show_qs);
        snnn=findViewById(R.id.subjectname_tv_show_qs);
        scnn=findViewById(R.id.subjectcode_tv_show_qs11);
        fmnn=findViewById(R.id.fullMarks_tv_show_qs);

        finalResultTL = findViewById(R.id.tableLayoutFinalResult);

        clsnn.setText(bgh.getString("clsqqq"));
        snnn.setText(bgh.getString("snqqq"));
        scnn.setText(bgh.getString("scqqq"));
        fmnn.setText("Full Marks:"+bgh.getString("fmqqq"));

        mid=bgh.getString("milf");

        toolbar.setTitle(bgh.getString("snqqq"));
        showResult();
    }

    private void showResult() {
        addRow("S.NO", "Name", "Roll No.", "Marks", true);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 1;

                for (DataSnapshot ds:snapshot.getChildren())
                {
                    if (ds.child("examID").getValue().equals(mid)) {

                        addRow(String.valueOf(count++), ds.child("studentName").getValue().toString(), ds.child("studentRoll").getValue().toString(), ds.child("marksObtained").getValue().toString(), false);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addRow(String sno, String name, String rollNo, String marks, boolean isHeader) {
        TableRow row = new TableRow(this);

        row.addView(createTextView(sno, isHeader));
        row.addView(createTextView(name, isHeader));
        row.addView(createTextView(rollNo, isHeader));
        row.addView(createTextView(marks, isHeader));

        finalResultTL.addView(row);
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(14);

        if (isHeader) {
            textView.setTypeface(null, Typeface.BOLD);
        }

        return textView;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home)
        {
            finish();
        }
        else if (idd==R.id.aboutAnswer)
        {
            Bundle bgh=getIntent().getExtras();
            StringBuffer sbt=new StringBuffer();
            sbt.append("Exam ID:"+bgh.getString("milf")).append("\nPasscode:"+bgh.getString("pal"));
            showMessage("Exam Info.",sbt.toString());
        }
        else
        {
            return true;
        }
        return true;
    }
}