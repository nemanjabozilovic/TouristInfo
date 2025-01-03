package com.example.touristinfo.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.touristinfo.R;
import com.example.touristinfo.data.models.Location;

public class DetailFragment extends Fragment {
    private static final String ARG_LOCATION = "location";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDescription = view.findViewById(R.id.textViewDescription);
        TextView textViewContact = view.findViewById(R.id.textViewContact);
        TextView textViewWorkingHours = view.findViewById(R.id.textViewWorkingHours);

        Location location = (Location) getArguments().getSerializable(ARG_LOCATION);
        if (location != null) {
            textViewName.setText(getString(R.string.name_label) + " " + location.getName());
            textViewDescription.setText(getString(R.string.description_label) + " " + location.getDescription());
            textViewContact.setText(getString(R.string.contact_label) + " " + location.getContactPhone());
            textViewWorkingHours.setText(getString(R.string.working_hours_label) + " " + location.getWorkingHours());
        }

        return view;
    }
}