package com.example.eventplanner.activities.startup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.eventplanner.R;
import com.example.eventplanner.fragments.startup.EoRegistrationFragment;
import com.example.eventplanner.fragments.startup.SppRegistrationFragment;
import com.example.eventplanner.helpers.FragmentsTool;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnEOreg = findViewById(R.id.btnEO);
        btnEOreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentsTool.to(new EoRegistrationFragment(), RegistrationActivity.this);
            }
        });
        Button btnSPPreg = findViewById(R.id.btnSPP);
        btnSPPreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentsTool.to(new SppRegistrationFragment(), RegistrationActivity.this);
            }
        });
    }
}