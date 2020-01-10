package com.sujin.hamrobazar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.sujin.hamrobazar.R;
import com.sujin.hamrobazar.termsandconditions.PostingActivity;
import com.sujin.hamrobazar.termsandconditions.SafetyActivity;
import com.sujin.hamrobazar.termsandconditions.TermsActivity;
import com.sujin.hamrobazar.termsandconditions.PostingActivity;
import com.sujin.hamrobazar.termsandconditions.SafetyActivity;
import com.sujin.hamrobazar.termsandconditions.TermsActivity;

public class TermsAndConditionActivity extends AppCompatActivity {

    private CheckBox cbTerms, cbSafety, cbPosting;
    private Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        cbTerms = findViewById(R.id.cbTerms);
        cbSafety = findViewById(R.id.cbSafety);
        cbPosting = findViewById(R.id.cbPosting);
        btnAgree = findViewById(R.id.btnAgree);

        cbTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TermsAndConditionActivity.this, TermsActivity.class));
            }
        });

        cbSafety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TermsAndConditionActivity.this, SafetyActivity.class));
            }
        });


        cbPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TermsAndConditionActivity.this, PostingActivity.class));
            }
        });

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbTerms.isChecked() && cbSafety.isChecked() && cbPosting.isChecked()) {
                    startActivity(new Intent(TermsAndConditionActivity.this, SplashScreenActivity.class));
                }

                else {
                    Toast.makeText(TermsAndConditionActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

