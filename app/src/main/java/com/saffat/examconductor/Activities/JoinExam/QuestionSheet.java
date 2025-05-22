package com.saffat.examconductor.Activities.JoinExam;

import static com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.CreateSubjectiveQuestion.convertStringToArray;
import static com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.CreateSubjectiveQuestion.convrtArrayToString;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saffat.examconductor.Activities.HomeActivity;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.QuestionAnswerSaveOnline;
import com.saffat.examconductor.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionSheet extends AppCompatActivity {

    QuestionAnswerSaveOnline questionAnswerSaveOnline;
    TextView cl, sn, sc, fm;
    String studentnm, stdrol, stdid, stdem, nmi, nmp, ncn, nsc, nsn, nfm, nqs;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    public static final String USER = "QuestionAnswer";
    ProgressDialog pd;
    ArrayList<String> questionsAnswer;
    ArrayList<Integer> edtextdata, tvtextdata;
    LinearLayout.LayoutParams layoutParams;
    TextView stdnm, stdrl, stdEm, stdId;
    int cgi = 121212500, count = 0;
    int lllcid = 123456720;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_sheet);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);

        edtextdata = new ArrayList<Integer>();
        tvtextdata = new ArrayList<Integer>();
        questionsAnswer = new ArrayList<String>();
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        stdnm = findViewById(R.id.studentNameOfQuestionShow);
        stdrl = findViewById(R.id.studentRollOfQuestionShow);
        stdEm = findViewById(R.id.studentEmailOfQuestionShow);
        stdId = findViewById(R.id.studentIdOfQuestionShow);
        ll = findViewById(R.id.linearlayoutOfTabLayoutQuestionShow);

        cl = findViewById(R.id.classname_tv_show_qs123);
        sn = findViewById(R.id.subjectname_tv_show_qs123);
        sc = findViewById(R.id.subjectcode_tv_show_qs11123);
        fm = findViewById(R.id.fullMarks_tv_show_qs123);

        Bundle b = getIntent().getExtras();
        studentnm = b.getString("stname2");
        stdrol = b.getString("stroll2");
        stdid = b.getString("stid2");
        stdem = b.getString("stemail2");
        nmi = b.getString("meetid");
        nmp = b.getString("meetpassword");
        ncn = b.getString("classssnm");//ok
        nsc = b.getString("subcccc");//ok
        nsn = b.getString("subnnnn");//ok
        nfm = b.getString("fmmm");//ok
        nqs = b.getString("quesssss");

        cl.setText(ncn);
        sn.setText(nsn);
        sc.setText(nsc);
        fm.setText("Full Marks:" + nfm);

        stdnm.setText("   " + studentnm);
        stdrl.setText("   " + stdrol);
        stdEm.setText("   " + stdem);
        stdId.setText("   " + stdid);

        List<String> questionsTmpr = convertStringToArray(nqs);
        setQuestions(questionsTmpr);

        StringBuffer stemp = new StringBuffer();


        for (String sr : questionsTmpr) {
            stemp.append(sr).append("\n");
        }

        // Register the back press dispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitConfirmation();
            }
        });

    }


    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Alert!!")
                .setMessage("Are you sure you want to Save?")
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

    void setQuestions(List<String> questions) {
        int qno = 0;
        layoutParams.setMargins(20,20,20,20);

        for(String quest:questions) {
            lllcid = lllcid + 1;
            cgi = cgi + 1;
            count = count + 1;
            qno = qno + 1;
            TextView tv = new TextView(this);
            tv.setText(quest);
            tv.setPadding(10, 10, 10, 10);
            tv.setId(cgi);
            ll.addView(tv);
            EditText et = new EditText(this);
            et.setText("Ans" + qno + ". ");
            et.setPadding(20, 20, 20, 10);
            et.setMinLines(1);
            et.setMaxLines(10);
            et.setId(lllcid);
            et.setBackgroundResource(R.drawable.edittext_dynamic_round_bg);
            ll.addView(et, layoutParams);
            tvtextdata.add(cgi);
            edtextdata.add(lllcid);
        }

        Button button=new Button(this);
        button.setText("Submit");
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setPadding(20,20,20,20);
        button.setBackgroundResource(R.drawable.round_bg);
        ll.addView(button,layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSaveonfirmation();
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

    private void showSaveonfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Alert!!")
                .setMessage("Are you sure you want to Log out?")
                .setPositiveButton("YES", (dialog, which) -> {
                    saveDataOfQuestionAnswer();
                })
                .setNegativeButton("NO", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }
    private void saveDataOfQuestionAnswer() {
        int tnp = 123456720;

        for (Integer yu : tvtextdata) {
            tnp = tnp + 1;
            TextView tvvv = ll.findViewById(yu);//if application not Work change ll to my_view
            String question = tvvv.getText().toString();

            EditText eddd = ll.findViewById(tnp);
            String answer = eddd.getText().toString();
            questionsAnswer.add(question);
            questionsAnswer.add(answer);
        }
        String datas = convrtArrayToString(questionsAnswer);

        questionAnswerSaveOnline = new QuestionAnswerSaveOnline(nmi, studentnm, stdrol, stdem, stdid, ncn, nsn, nsc, nfm, datas);
        fireBaseInsertToAnswer();
    }



    private void fireBaseInsertToAnswer() {
        String keyID = mDatabase.push().getKey();
        mDatabase.child(keyID).setValue(questionAnswerSaveOnline);
        Toast.makeText(this, "Answer Submitted", Toast.LENGTH_SHORT).show();
        Intent iuu = new Intent(this, HomeActivity.class);
        startActivity(iuu);
        finishAffinity();
    }

}
