package com.example.touristinfo.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touristinfo.R;
import com.example.touristinfo.data.models.Location;

public class DetailActivity extends AppCompatActivity {

    private TextView nameTextView, descriptionTextView, contactTextView, workingHoursTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        contactTextView = findViewById(R.id.contactTextView);
        workingHoursTextView = findViewById(R.id.workingHoursTextView);

        Location location = (Location) getIntent().getSerializableExtra("location");

        if (location != null) {
            nameTextView.setText(location.getName());
            descriptionTextView.setText(location.getDescription());

            contactTextView.setText(getString(R.string.contact_label) + " " + location.getContactPhone());
            workingHoursTextView.setText(getString(R.string.working_hours_label) + " " + location.getWorkingHours());
        }
    }
}