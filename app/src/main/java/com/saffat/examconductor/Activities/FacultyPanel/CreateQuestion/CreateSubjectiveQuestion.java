package com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion;

import static com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel.adminEmailDet;

import java.util.Random;
import java.util.UUID;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.QuestionSaveAdpt;
import com.saffat.examconductor.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CreateSubjectiveQuestion extends AppCompatActivity {

    TextView cnam, snam, scode, fmar, adq;
    Button undo;
    int count = 0;
    LinearLayout ll;
    ArrayList<String> questions;
    public static final String LIST_SEPRATOR = "_,_";
    String datas = "";
    //    DD4YouConfig dd4YouConfig;
    QuestionSaveAdpt questionSaveAdpt;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    public static final String USER = "QuestionSet";
    LinearLayout.LayoutParams layoutParams;
    public static final String TAG = "QuestionInputSubjectiveType";
    private List<View> questionViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_subjective_question);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database= FirebaseDatabase.getInstance();
        mDatabase=database.getReference(USER);

        cnam = findViewById(R.id.tvCourseCreate);
        snam = findViewById(R.id.tvSubjectCreate);
        scode = findViewById(R.id.tvCodeCreate);
        fmar = findViewById(R.id.tvMarksCreate);
        adq = findViewById(R.id.btnAddQuestionCreate);
        ll = findViewById(R.id.questionListCreate);

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Bundle bundle=getIntent().getExtras();
        Toolbar toolbar = findViewById(R.id.toolbarCreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        assert bundle != null;
        toolbar.setTitle(bundle.getString("subject_code"));

        questions = new ArrayList<String>();

        setCSSFM();

        adq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count=count+1;

                layoutParams.setMargins(20,20,20,20);
                EditText et=new EditText(getApplicationContext());
                et.setText(count + ". ");
                et.setId(count);
                et.setPadding(20,20,20,10);
                et.setMinLines(1);
                et.setMaxLines(10);
                et.setFocusable(true);//late add
                et.setTextColor(Color.parseColor("#000000")); //late add
                et.setBackgroundResource(R.drawable.edittext_dynamic_round_bg);
                ll.addView(et,layoutParams);

                questionViews.add(et); //for undo
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

    public void setCSSFM() {
        Bundle bundle=getIntent().getExtras();
        String classname=bundle.getString("exam_name_1");
        String subjectname=bundle.getString("subject_name");
        String subjectCode=bundle.getString("subject_code");
        String fullMarks=bundle.getString("full_marks");

        cnam.setText(classname);
        snam.setText(subjectname);
        scode.setText(subjectCode);
        fmar.setText("Full Marks:"+fullMarks);
    }

    public void saveEditTextDataQPaper() {
        Bundle b=getIntent().getExtras();
        String name111=b.getString("exam_name_1");
        String code111=b.getString("subject_code");
        String snam111=b.getString("subject_name");
        String fmar111=b.getString("full_marks");

        for (int i=0;i<count;i++)
        {
            EditText editText=findViewById((i+1));
            String question=editText.getText().toString();
            questions.add(question);
        }
        datas=convrtArrayToString(questions);
        String ExamId=UUID.randomUUID().toString().replace("-", "").substring(0, 10);//unique id generate
        Random random = new Random();

        String ExamPassword=String.valueOf(1000000 + random.nextInt(9000000));

        questionSaveAdpt=new QuestionSaveAdpt(ExamId,ExamPassword,name111,snam111,code111,fmar111,datas,adminEmailDet,"other");
        insertIntoFireBase();
    }

    private void insertIntoFireBase() {
        String keyID=mDatabase.push().getKey();
        mDatabase.child(keyID).setValue(questionSaveAdpt);
        Toast.makeText(getApplicationContext(), "Question Saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), FacultyPanel.class));
        finish();
        finishAffinity();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    public static String convrtArrayToString(ArrayList<String> array) {
        StringBuffer stringBuilder = new StringBuffer();
        for (String str : array) {
            stringBuilder.append(str).append(LIST_SEPRATOR);
        }
        stringBuilder.setLength(stringBuilder.length() - LIST_SEPRATOR.length());
        return stringBuilder.toString();
    }

    public static List<String> convertStringToArray(String str) {
        return Arrays.asList(str.split(LIST_SEPRATOR));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question_inputsubjective_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd = item.getItemId();
        if (idd == R.id.save_Answer78) {
            if (!questionViews.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Alert!!")
                        .setMessage("Are you sure you want save")
                        .setPositiveButton("YES", (dialog, which) -> {
                            // save activity
                            saveEditTextDataQPaper();
                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .setCancelable(false)
                        .show();

            } else {
                Toast.makeText(this, "No question added", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (idd == R.id.undo_Answer78) {
            if (!questionViews.isEmpty()) {
                View lastQuestion = questionViews.remove(questionViews.size() - 1);
                ll.removeView(lastQuestion);
                count--;
            } else {
                Toast.makeText(this, "Nothing to undo", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (idd == android.R.id.home) {
            showExitConfirmation();
        } else {
            return true;
        }
        return true;
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Alert!!")
                .setMessage("Are you sure you want to Exit?")
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

}
