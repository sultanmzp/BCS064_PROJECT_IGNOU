package com.saffat.examconductor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class QuestionShowWhenClickOnList extends AppCompatActivity {


    TextView class2018,subject2018,subcode2018,fm2018,question2018;
    String eid2018,epass2018;
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
        setContentView(R.layout.specific_question_show);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        class2018=findViewById(R.id.classname_tv_show_qs);
        subject2018=findViewById(R.id.subjectname_tv_show_qs);
        subcode2018=findViewById(R.id.subjectcode_tv_show_qs11);
        fm2018=findViewById(R.id.fullMarks_tv_show_qs);
        question2018=findViewById(R.id.questions_tv_show_qs);

        Bundle b=getIntent().getExtras();

        String c2=b.getString("examnm");
        String s2=b.getString("subnamm");
        String s21=b.getString("subcodded");
        String f2=b.getString("fmm");
        String q12=b.getString("qsst");

        eid2018=b.getString("metid");
        epass2018=b.getString("metpass");

        Toolbar toolbar=findViewById(R.id.toolbarQuestionShow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(b.getString("subcodded"));

        class2018.setText(c2);
        subject2018.setText(s2);
        subcode2018.setText(s21);
        fm2018.setText("Full Marks: "+f2);
        question2018.setText(q12);
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_of_answers,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd=item.getItemId();
        StringBuffer sb=new StringBuffer();
        Bundle b=getIntent().getExtras();

        sb.append("\nExam ID:"+b.getString("metid"));
        sb.append("\nPasscode:"+b.getString("metpass"));
        sb.append("\n\nClass Name:"+b.getString("examnm"));
        sb.append("\nSubject:"+b.getString("subnamm"));
        sb.append("\nSubject code:"+b.getString("subcodded"));
        sb.append("\nFull Marks:"+b.getString("fmm"));

        if (idd==R.id.aboutOfAnswer)
        {
            showMessage("Exam Info.",sb.toString());

        }
        else if (idd==R.id.shareOfAnswer)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());

            // Start share chooser
            startActivity(Intent.createChooser(shareIntent, "Share via"));
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
        AlertDialog.Builder ab=new AlertDialog.Builder(QuestionShowWhenClickOnList.this);
        ab.setTitle(title);
        ab.setMessage(message);
        ab.setCancelable(true);
        ab.setPositiveButton("OK",null);
        ab.show();
    }
}