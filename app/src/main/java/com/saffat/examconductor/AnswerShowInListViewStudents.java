package com.saffat.examconductor;

import static com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.CreateSubjectiveQuestion.convertStringToArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnswerShowInListViewStudents extends AppCompatActivity {

    ListView lllv;
    ArrayList<ListOfStudentsAnswerTT> listofStudents = new ArrayList<ListOfStudentsAnswerTT>();
    CelebrityAdapter11111 adapter;
    TextView ct;
    TextView cd, ceer;
    ImageView ci;
    String eidroot;

    private FirebaseDatabase database1;
    private DatabaseReference userRef;
    public static final String USER = "QuestionAnswer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_answer_in_listview);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database1 = FirebaseDatabase.getInstance();
        userRef = database1.getReference(USER);

        Bundle bbbb = getIntent().getExtras();
        eidroot = bbbb.getString("mid");
        lllv = findViewById(R.id.listviewofstudentanswered);
        readSavedData();

        adapter = new CelebrityAdapter11111(this, listofStudents);
        lllv.setAdapter(adapter);


        lllv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ListOfStudentsAnswerTT celebrityArrayList = listofStudents.get(i);
                Toast.makeText(AnswerShowInListViewStudents.this, "Hello," + celebrityArrayList.studentname, Toast.LENGTH_SHORT).show();

                Intent iuy = new Intent(AnswerShowInListViewStudents.this, SeeAnswerOfSpecificStudents.class);


                iuy.putExtra("meetddd", celebrityArrayList.mid);
                iuy.putExtra("stdnameddd", celebrityArrayList.studentname);
                iuy.putExtra("stdrollddd", celebrityArrayList.studentroll);
                iuy.putExtra("stdemailddd", celebrityArrayList.studentenail);
                iuy.putExtra("stdidddd", celebrityArrayList.studentid);
                iuy.putExtra("classnameddd", celebrityArrayList.classname);
                iuy.putExtra("subjectnameddd", celebrityArrayList.subjectname);
                iuy.putExtra("subjectcodeddd", celebrityArrayList.subjectcode);
                iuy.putExtra("fmarksddd", celebrityArrayList.fmarkks);
                iuy.putExtra("quesansddd", celebrityArrayList.quans);
                startActivity(iuy);
            }
        });
    }

    //    public void readSavedData()
//    {
//
//
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                StringBuffer stringBuffer12 = new StringBuffer();
//                stringBuffer12.append("");
//                int image=R.drawable.qspaper;
//
//                String Exam_id_unique="",stnm,stnrol,stnem,stdi,clsnm,sunnm,suncoed,fmm,qah;
//
//                for (DataSnapshot ds:snapshot.getChildren())
//                {
//                    if (ds.child("examIDs").getValue().equals(eidroot)) {
//
//                        List<String> lst = convertStringToArray(ds.child("questionAnswer").getValue(String.class));
//                        for (String str : lst) {
//                            stringBuffer12.append(str).append("\n\n");
//                        }
//
//                        Exam_id_unique=ds.child("examIDs").getValue(String.class);
//                        stnm=ds.child("studentName").getValue(String.class);
//                        stnrol=ds.child("studentRoll").getValue(String.class);
//                        stnem=ds.child("studentEmailm").getValue(String.class);
//                        stdi=ds.child("studentIDs").getValue(String.class);
//                        clsnm=ds.child("className").getValue(String.class);
//                        sunnm=ds.child("subjectName").getValue(String.class);
//                        suncoed=ds.child("subjectCode").getValue(String.class);
//                        fmm=ds.child("fullMarks").getValue(String.class);
//                        qah=stringBuffer12.toString();
//
//                        image=R.drawable.ic_answers;
//
//                        setInListViewTT(Exam_id_unique,stnm,stnrol,stnem,stdi,clsnm,sunnm,suncoed,fmm,qah,image);
//                        stringBuffer12.delete(0,stringBuffer12.length());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
    public void readSavedData() {
        Query query = userRef.orderByChild("examIDs").equalTo(eidroot);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long startTime = System.currentTimeMillis();

                new Thread(() -> {
                    List<ListOfStudentsAnswerTT> localList = new ArrayList<>();

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        try {
                            String examID = ds.child("examIDs").getValue(String.class);
                            if (examID == null || !examID.equals(eidroot)) continue;

                            String studentName = ds.child("studentName").getValue(String.class);
                            String studentRoll = ds.child("studentRoll").getValue(String.class);
                            String studentEmail = ds.child("studentEmailm").getValue(String.class);
                            String studentID = ds.child("studentIDs").getValue(String.class);
                            String className = ds.child("className").getValue(String.class);
                            String subjectName = ds.child("subjectName").getValue(String.class);
                            String subjectCode = ds.child("subjectCode").getValue(String.class);
                            String fullMarks = ds.child("fullMarks").getValue(String.class);
                            String questionRaw = ds.child("questionAnswer").getValue(String.class);

                            List<String> questionAnswers = convertStringToArray(questionRaw);
                            StringBuilder qaBuilder = new StringBuilder();

                            for (String q : questionAnswers) {
                                qaBuilder.append(q).append("\n\n");
                            }

                            localList.add(new ListOfStudentsAnswerTT(
                                    examID, studentName, studentRoll, studentEmail,
                                    studentID, className, subjectName, subjectCode,
                                    fullMarks, qaBuilder.toString(), R.drawable.ic_answers
                            ));
                        } catch (Exception e) {
                            Log.e("DataParseError", "Error parsing DataSnapshot", e);
                        }
                    }

                    // Update UI on main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        listofStudents.clear();
                        listofStudents.addAll(localList);
                        // notify adapter if needed
                        adapter.notifyDataSetChanged();  // Make sure your adapter is set

                        long endTime = System.currentTimeMillis();
                        Log.d("Performance", "Processed " + localList.size() + " items in " + (endTime - startTime) + "ms");
                    });

                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
            }
        });


    }

    class CelebrityAdapter11111 extends BaseAdapter {

        Context context;
        ArrayList<ListOfStudentsAnswerTT> listofCelebrity;

        public CelebrityAdapter11111(Context c, ArrayList<ListOfStudentsAnswerTT> celebrities) {
            this.context = c;
            this.listofCelebrity = celebrities;
        }

        @Override
        public int getCount() {
            return listofCelebrity.size();
        }

        @Override
        public Object getItem(int i) {
            return this.listofCelebrity.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ListOfStudentsAnswerTT celebrityArrayList = listofCelebrity.get(i);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View myview = inflater.inflate(R.layout.student_answer_custom_listview, null);
            ct = myview.findViewById(R.id.studentnameinlistview);
            cd = myview.findViewById(R.id.studentrollinlistview);
            ceer = myview.findViewById(R.id.studentemailinlistview);
            ci = myview.findViewById(R.id.celebrityImage111);

            ct.setText(celebrityArrayList.studentname);
            cd.setText(celebrityArrayList.studentroll);
            ceer.setText(celebrityArrayList.studentenail);
            ci.setImageResource(celebrityArrayList.image);

            return myview;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

}