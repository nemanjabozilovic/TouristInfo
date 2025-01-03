package com.example.touristinfo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.example.touristinfo.R;
import com.example.touristinfo.data.models.Location;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LocationsActivity extends AppCompatActivity {

    private ListView locationsListView;
    private List<Location> locations;
    private List<Location> allLocations;
    private ArrayAdapter<String> adapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        initializeUIElements();

        String categoryName = getIntent().getStringExtra("categoryName");
        locations = (List<Location>) getIntent().getSerializableExtra("locations");

        if (locations == null) {
            locations = new ArrayList<>();
        }

        allLocations = new ArrayList<>(locations);
        setTitle(categoryName);

        List<String> locationNames = new ArrayList<>();
        for (Location location : locations) {
            locationNames.add(location.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationNames);
        locationsListView.setAdapter(adapter);

        locationsListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Location selectedLocation = locations.get(position);

            Intent intent = new Intent(LocationsActivity.this, DetailActivity.class);
            intent.putExtra("location", selectedLocation);
            startActivity(intent);
        });

        setupSearchFilter();
    }

    private void initializeUIElements() {
        locationsListView = findViewById(R.id.locationsListView);
        searchEditText = findViewById(R.id.searchEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            sortLocations();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void sortLocations() {
        locations.sort(Comparator.comparing(Location::getName));
        updateLocationList();
    }

    private void filterLocations(String filter) {
        if (filter.isEmpty()) {
            locations = new ArrayList<>(allLocations);
        } else {
            locations = allLocations.stream()
                    .filter(location -> location.getName().toLowerCase().contains(filter.toLowerCase()))
                    .collect(Collectors.toList());
        }
        updateLocationList();
    }

    private void updateLocationList() {
        List<String> locationNames = new ArrayList<>();
        for (Location location : locations) {
            locationNames.add(location.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationNames);
        locationsListView.setAdapter(adapter);
    }

    private void setupSearchFilter() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString().toLowerCase().trim();
                filterLocations(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}