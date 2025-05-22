package com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;

import java.util.ArrayList;
import java.util.List;

public class CreateObjectiveQuestion extends AppCompatActivity {

    private LinearLayout questionContainer;
    private int questionCount = 0;
    private EditText currentQuestionEditText = null;
    private List<EditText> currentOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_objective_question);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        Toolbar toolbar = findViewById(R.id.toolbarCreateObj);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Question Code");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        questionContainer = findViewById(R.id.questionListCreateObj);

        TextView btnAddQuestion = findViewById(R.id.btnAddQuestionObj);
        TextView btnAddOption = findViewById(R.id.btnAddOptionObj);

        btnAddQuestion.setOnClickListener(v -> addQuestionField());

        btnAddOption.setOnClickListener(v -> {
            if (currentQuestionEditText != null) {
                addOptionField();
            } else {
                Toast.makeText(this, "Add a question first", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addQuestionField() {
        questionCount++;

        // Create EditText for question
        EditText questionET = new EditText(this);
        questionET.setHint(questionCount + ". what is ...?");
        questionET.setTextSize(16);
        questionET.setPadding(16, 16, 16, 16);
        questionET.setBackgroundResource(R.drawable.input_field);
        questionET.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        questionContainer.addView(questionET);

        // Update current question
        currentQuestionEditText = questionET;
        currentOptions.clear(); // Reset option list for new question
    }

    private void addOptionField() {
        // Option: a., b., c., d. based on size
        char optionChar = (char) ('a' + currentOptions.size());

        EditText optionET = new EditText(this);
        optionET.setHint(optionChar + ".");
        optionET.setTextSize(14);
        optionET.setPadding(16, 16, 16, 16);
        optionET.setBackgroundResource(R.drawable.input_field);
        optionET.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        questionContainer.addView(optionET);
        currentOptions.add(optionET);
    }


    // Inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question_menu, menu);
        return true;
    }

    // Handle Save/Undo clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_undo) {
            handleUndo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    private void handleUndo() {
        if (!currentOptions.isEmpty()) {
            // Remove last option
            EditText lastOption = currentOptions.remove(currentOptions.size() - 1);
            questionContainer.removeView(lastOption);
        } else if (currentQuestionEditText != null) {
            // Remove current question
            questionContainer.removeView(currentQuestionEditText);
            currentQuestionEditText = null;

            // Also reset options
            currentOptions.clear();
            if (questionCount > 0) questionCount--;
        } else {
            Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show();
        }
    }

}