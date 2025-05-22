package com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;

public class QuestionDetails extends AppCompatActivity {

    Button nextqs;
    EditText ename112,scode,sname,fm;
    Spinner spnr;
    String timee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_details);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        nextqs=findViewById(R.id.btnNext);
        ename112=findViewById(R.id.etCourse);
        scode=findViewById(R.id.etCode);
        sname=findViewById(R.id.etSubject);
        fm=findViewById(R.id.etMarks);
        spnr=findViewById(R.id.spinnerType);
        String[] typ= new String[]{"Subjective", "Objective"};
        ArrayAdapter<String> adapter1111=new ArrayAdapter<String>(QuestionDetails.this,android.R.layout.simple_list_item_1,typ);

        spnr.setAdapter(adapter1111);

        nextqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timee=spnr.getSelectedItem().toString();
                validateAndProceedNext();
            }
        });
    }

    private void validateAndProceedNext() {
        String examn=ename112.getText().toString();
        String subcode=scode.getText().toString();
        String subname=sname.getText().toString();
        String FullM=fm.getText().toString();

        if (examn.isEmpty())
        {
            ename112.setError("Please Enter Class Name");
        }
        else
        {
            if (subcode.isEmpty())
            {
                scode.setError("Please Enter Subject Code");
            }
            else
            {
                if (subname.isEmpty())
                {
                    sname.setError("Please Enter Subject Name");
                }
                else
                {
                    if (FullM.isEmpty())
                    {
                        fm.setError("Please Enter Full Marks");
                    }
                    else
                    {

                        if (timee.equalsIgnoreCase("Subjective")) {
                            Toast.makeText(QuestionDetails.this, "sub :" + subname, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(QuestionDetails.this, CreateSubjectiveQuestion.class);

                            i.putExtra("exam_name_1", examn);
                            i.putExtra("subject_code", subcode);
                            i.putExtra("subject_name", subname);
                            i.putExtra("full_marks", FullM);
                            startActivity(i);

                            ename112.setText("");
                            scode.setText("");
                            sname.setText("");
                            fm.setText("");
                        }
                        else if (timee.equalsIgnoreCase("Objective")) {
                            Intent i = new Intent(this, CreateObjectiveQuestion.class);

                            i.putExtra("exam_name_1", examn);
                            i.putExtra("subject_code", subcode);
                            i.putExtra("subject_name", subname);
                            i.putExtra("full_marks", FullM);
                            startActivity(i);

                            ename112.setText("");
                            scode.setText("");
                            sname.setText("");
                            fm.setText("");
                        }
                        else
                        {
                            showMessage("Error","Select Question type");
                        }
                    }
                }
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
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