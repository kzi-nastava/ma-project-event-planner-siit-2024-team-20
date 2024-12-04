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
import com.google.android.material.button.MaterialButtonToggleGroup;

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

        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleGroup);

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked){
                    if (checkedId == R.id.option1) {
                        FragmentsTool.to(new EoRegistrationFragment(), RegistrationActivity.this, false);
                    } else if (checkedId == R.id.option2) {
                        FragmentsTool.to(new SppRegistrationFragment(), RegistrationActivity.this, false);
                    }
                }
            }
        });
    }
}