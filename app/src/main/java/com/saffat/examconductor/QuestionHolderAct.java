package com.saffat.examconductor;

import static com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.CreateSubjectiveQuestion.LIST_SEPRATOR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.saffat.examconductor.Activities.HomeActivity;

import java.util.ArrayList;

public class QuestionHolderAct extends AppCompatActivity {

    TextView stnnm, stnrl, stngm, stnid;
    LinearLayout lln;
    int cgi = 121212500, count = 0;
    int lllcid = 123456720;
    Button button;
    StringBuffer stb;
    String studentnm, stdrol, stdid, stdem, questionss, eamniddd, clasnfq, subcoedd, sunnamoo, fmarkssss;
    ArrayList<String> questionsAnswer;
    ArrayList<Integer> edtextdata, tvtextdata;
    String datas;
    QuestionAnswerSaveOnline questionAnswerSaveOnline;

    //    private FirebaseDatabase database;
//    private DatabaseReference mDatabase;
    public static final String USER = "QuestionAnswer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_holder);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

//        database= FirebaseDatabase.getInstance();
//        mDatabase=database.getReference(USER);

        stnnm = findViewById(R.id.studentname_tv_fragment_final);
        stnrl = findViewById(R.id.roll_tv_fragment_final);
        stngm = findViewById(R.id.Email_tv_fragment_final);
        stnid = findViewById(R.id.Idnoo_tv_fragment_final);
        lln = findViewById(R.id.fr_question_cnt);

        edtextdata = new ArrayList<Integer>();
        tvtextdata = new ArrayList<Integer>();
        questionsAnswer = new ArrayList<String>();


        Bundle b22 = getIntent().getExtras();
        studentnm = b22.getString("snm");
        stdrol = b22.getString("roll");
        stdid = b22.getString("id");
        stdem = b22.getString("emmail");
        questionss = b22.getString("qsrr");

        eamniddd = b22.getString("exid");
        clasnfq = b22.getString("classngf");
        subcoedd = b22.getString("scdo");
        sunnamoo = b22.getString("subnc");
        fmarkssss = b22.getString("fmrf");

        stnid.setText("ID : " + stdid);
        stnnm.setText("Student Name : " + studentnm);
        stnrl.setText("Roll No : " + stdrol);
        stngm.setText("Email : " + stdem);
        setQuestions(getApplicationContext(), questionss);
    }

    private void setQuestions(Context questionHolderFr, String questionss) {


        StringBuffer stringBuffer12 = new StringBuffer();

        button = new Button(QuestionHolderAct.this);
        button.setText("Submit");
        button.setPadding(20, 20, 20, 20);
        lln.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataOfQuestionAnswer();
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


    private void saveDataOfQuestionAnswer() {

        Toast.makeText(QuestionHolderAct.this, "Submit..2.", Toast.LENGTH_SHORT).show();

        int tnp = 123456720;
        for (Integer yu : tvtextdata) {
            tnp = tnp + 1;
            TextView tvvv = findViewById(yu);
            String question = tvvv.getText().toString();

            EditText eddd = findViewById(tnp);
            String answer = eddd.getText().toString();
            questionsAnswer.add(question);
            questionsAnswer.add(answer);
        }
        Toast.makeText(this, "Data:\n" + datas, Toast.LENGTH_LONG).show();

        questionAnswerSaveOnline = new QuestionAnswerSaveOnline(eamniddd, studentnm, stdrol, stdem, stdid, clasnfq, sunnamoo, subcoedd, fmarkssss, datas);
        addDataOnFirebase();

    }

    private void addDataOnFirebase() {
        Toast.makeText(this, "Sucessfull..", Toast.LENGTH_SHORT).show();
        Intent iuu = new Intent(QuestionHolderAct.this, HomeActivity.class);
        startActivity(iuu);
    }

    public static String convrtIntArrayToString(ArrayList<Integer> array) {
        StringBuffer stringBuilder = new StringBuffer();
        for (Integer str : array) {
            stringBuilder.append(str).append(LIST_SEPRATOR);
        }
        stringBuilder.setLength(stringBuilder.length() - LIST_SEPRATOR.length());
        return stringBuilder.toString();
    }


}