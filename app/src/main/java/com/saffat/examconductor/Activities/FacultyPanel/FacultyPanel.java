package com.saffat.examconductor.Activities.FacultyPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.QuestionDetails;
import com.saffat.examconductor.MainAdapter;
import com.saffat.examconductor.MainModel;
import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;
import com.saffat.examconductor.RecyclerItemClickListener;
import com.saffat.examconductor.AnswerShowQuestionLV;
import com.saffat.examconductor.ShowQuestionInListView;
import com.saffat.examconductor.ResponseStart;
import com.saffat.examconductor.ResultQuestionLVShow;

import java.util.ArrayList;

public class FacultyPanel extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;

    public static String adminNameDet="";
    public static String adminEmailDet="";
    public static String adminPhoneDet="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_panel);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));


        recyclerView=findViewById(R.id.recyclerview);
        Bundle bundle=getIntent().getExtras();

        if (bundle != null) {
            adminNameDet = bundle.getString("name");
            adminEmailDet = bundle.getString("email");
            adminPhoneDet = bundle.getString("phone");

        }

        Integer[] numImage={R.drawable.ic_name,R.drawable.ic_create,R.drawable.ic_response,
                R.drawable.ic_questions,R.drawable.ic_answers,R.drawable.ic_results};
        String[] numWord={"Details","Create","Response","Questions","Answers","Results"};
        mainModels=new ArrayList<>();
        for (int i=0;i<numImage.length;i++)
        {
            MainModel mainModel=new MainModel(numImage[i],numWord[i]);
            this.mainModels.add(mainModel);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mainAdapter=new MainAdapter(FacultyPanel.this,mainModels);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (position==0)
                        {
                            Intent iiio=new Intent(FacultyPanel.this, FacultyDetail.class);
                            startActivity(iiio);
                        }
                        else if (position==1)
                        {
                            Intent i=new Intent(FacultyPanel.this, QuestionDetails.class);
                            startActivity(i);
                        }
                        else if (position==2)
                        {
                            Intent intent111111=new Intent(FacultyPanel.this, ResponseStart.class);
                            startActivity(intent111111);
                        }
                        else if (position==3)
                        {
                            Intent intent11=new Intent(FacultyPanel.this, ShowQuestionInListView.class);
                            startActivity(intent11);
                        }
                        else if (position==4)
                        {
                            Intent iuyt=new Intent(FacultyPanel.this, AnswerShowQuestionLV.class);
                            startActivity(iuyt);
                        }
                        else
                        {
                            Intent igf=new Intent(FacultyPanel.this, ResultQuestionLVShow.class);
                            startActivity(igf);
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

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
                .setMessage("Are you sure you want to Log out?")
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