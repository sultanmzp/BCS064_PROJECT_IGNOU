package com.saffat.examconductor.Activities.FacultyPanel;

import static com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel.adminEmailDet;
import static com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel.adminNameDet;
import static com.saffat.examconductor.Activities.FacultyPanel.FacultyPanel.adminPhoneDet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.saffat.examconductor.NetworkUtil;
import com.saffat.examconductor.R;

public class FacultyDetail extends AppCompatActivity {

    private View cardName, cardEmail, cardPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_details);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));


        // Cards
        cardName = findViewById(R.id.cardNameUser);
        cardEmail = findViewById(R.id.cardEmailUser);
        cardPhone = findViewById(R.id.cardPhoneUser);

        setCardData(cardName, R.drawable.ic_name, adminNameDet.toString());
        setCardData(cardEmail, R.drawable.ic_email, adminEmailDet);
        setCardData(cardPhone, R.drawable.ic_phone, adminPhoneDet);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            NetworkUtil.showNoInternetDialog(this);
        }
    }

    private void setCardData(View card, int iconRes, String text) {
        ImageView icon = card.findViewById(R.id.infoIcon);
        TextView infoText = card.findViewById(R.id.infoText);

        icon.setImageResource(iconRes);
        infoText.setText(text);
    }

    @Override
    public void onBackPressed() {
        // Finish the activity
        super.onBackPressed();
        finish();
    }
}