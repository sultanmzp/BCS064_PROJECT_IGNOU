package com.saffat.examconductor;

import static com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel.adminEmailDet;
import static com.saffat.examconductor.Activities.FacultyPanel.CreateQuestion.CreateSubjectiveQuestion.convertStringToArray;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResponseStart extends AppCompatActivity {

    ArrayList<DetailAddInListViewStartResponseAdapter> listofClebrity = new ArrayList<DetailAddInListViewStartResponseAdapter>();
    ResponseStart.CelebrityAdapter1 adapter;
    ListView lstview;
    TextView ct;
    TextView cd;
    DetailAddInListViewStartResponseAdapter celebrityArrayList;
    ImageView ci;
    AlertDialog.Builder startResponseDialog;
    private FirebaseDatabase database1;
    private DatabaseReference userRef;
    public static final String USER = "QuestionSet";

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
        setContentView(R.layout.response_start);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));

        database1 = FirebaseDatabase.getInstance();
        userRef = database1.getReference(USER);

        lstview = findViewById(R.id.listviewStartResponse);
        readSavedData();
        adapter = new ResponseStart.CelebrityAdapter1(this, listofClebrity);
        lstview.setAdapter(adapter);
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                celebrityArrayList = listofClebrity.get(i);
                startResponseDialog = new AlertDialog.Builder(ResponseStart.this);

                View view1 = getLayoutInflater().inflate(R.layout.start_response_dialog, null);
                final TextView qpperS = (TextView) view1.findViewById(R.id.select_paper_when_respons_start_Dialog);
                final Spinner timefg = (Spinner) view1.findViewById(R.id.timeTaker_whenResponseStart_Dialog);
                String[] typ = getResources().getStringArray(R.array.responseType);
                ArrayAdapter<String> adapter1111 = new ArrayAdapter<String>(ResponseStart.this, android.R.layout.simple_list_item_1, typ);
                Button cancelbtm = view1.findViewById(R.id.cancelllllll);
                Button next = view1.findViewById(R.id.startResponseBtnOfDialod);

                qpperS.setText("Exam-Id:" + celebrityArrayList.ecode);
                timefg.setAdapter(adapter1111);
                startResponseDialog.setView(view1);
                final AlertDialog alertDialog = startResponseDialog.create();
                alertDialog.setCanceledOnTouchOutside(false);
                cancelbtm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String timee = timefg.getSelectedItem().toString();
                        if (timee.equalsIgnoreCase("Start") || timee.equalsIgnoreCase("Stop")) {
                            userRef.orderByChild("examID").equalTo(celebrityArrayList.ecode).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        String keyy = ds.getKey();
                                        userRef.child(keyy).child("timeResponse").setValue(timee);
                                        alertDialog.dismiss();
                                        Toast.makeText(ResponseStart.this, "Response is " + timee, Toast.LENGTH_SHORT).show();
                                        finish();
                                        break;//late
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(ResponseStart.this, "Please Enter Response Type", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.show();

            }
        });

    }


    public void readSavedData() {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int image = R.drawable.qspaper;
                StringBuffer stringBuffer12 = new StringBuffer();
                String subnamew = "";
                String subcdo = "";
                String Exam_id_unique = "", Exam_passwrd = "", clsnamew = "", fulllum = "", questionadpter = "";

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("adminEmaill").getValue().equals(adminEmailDet)) {

                        List<String> lst = convertStringToArray(ds.child("questionsSet").getValue(String.class));
                        for (String str : lst) {
                            stringBuffer12.append(str).append("\n\n");
                        }
                        Exam_id_unique = ds.child("examID").getValue(String.class);
                        Exam_passwrd = ds.child("examPassword").getValue(String.class);
                        clsnamew = ds.child("className").getValue(String.class);
                        subcdo = ds.child("subjectCode").getValue(String.class);
                        subnamew = ds.child("subjectName").getValue(String.class);
                        fulllum = ds.child("fullMarks").getValue(String.class);
                        questionadpter = stringBuffer12.toString();
                        image = R.drawable.qspaper;

                        setInListView(subcdo, subnamew, image, Exam_id_unique, Exam_passwrd, clsnamew, fulllum, questionadpter);
                        stringBuffer12.delete(0, stringBuffer12.length());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setInListView(String codoo, String namo, int imojooo, String examCode, String empass, String exnamew, String fmarks, String qsa) {
        listofClebrity.add(new DetailAddInListViewStartResponseAdapter(codoo, namo, imojooo, examCode, empass, exnamew, fmarks, qsa));
    }

    class CelebrityAdapter1 extends BaseAdapter {

        Context context;
        ArrayList<DetailAddInListViewStartResponseAdapter> listofCelebrity;

        public CelebrityAdapter1(Context c, ArrayList<DetailAddInListViewStartResponseAdapter> celebrities) {
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

            DetailAddInListViewStartResponseAdapter celebrityArrayList = listofCelebrity.get(i);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View myview = inflater.inflate(R.layout.customlistviewforquestionshower, null);
            ct = myview.findViewById(R.id.celebrityTitle);
            cd = myview.findViewById(R.id.celebrityDescription);
            ci = myview.findViewById(R.id.celebrityImage);

            ct.setText(celebrityArrayList.scode);
            cd.setText(celebrityArrayList.sname);
            ci.setImageResource(celebrityArrayList.image);

            return myview;
        }
    }

}