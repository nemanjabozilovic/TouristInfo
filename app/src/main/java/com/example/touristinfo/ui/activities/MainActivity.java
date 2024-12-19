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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView categoriesListView;
    private HashMap<String, List<Location>> categoryLocations;
    private ArrayAdapter<String> adapter;
    private List<String> categories;
    private EditText searchEditText;

    private void initializeUIElements() {
        categoriesListView = findViewById(R.id.categoriesListView);
        searchEditText = findViewById(R.id.searchEditText);

        setupCategoryData();
        setupSearchFilter();
    }

    private void setupCategoryData() {
        categoryLocations = new HashMap<>();

        List<Location> historicalPlaces = new ArrayList<>();
        historicalPlaces.add(new Location("Kalemegdan Fortress", "A historic fortress in Belgrade.", "123-456-789", "8:00 AM - 8:00 PM"));
        historicalPlaces.add(new Location("Petrovaradin Fortress", "Located in Novi Sad, known for its architecture.", "987-654-321", "9:00 AM - 7:00 PM"));
        historicalPlaces.add(new Location("Nis Fortress", "An ancient fortress in the city of Niš.", "111-222-333", "8:00 AM - 6:00 PM"));
        categoryLocations.put("Historical Places", historicalPlaces);

        List<Location> naturalBeauty = new ArrayList<>();
        naturalBeauty.add(new Location("Tara National Park", "A stunning park with breathtaking views.", "555-555-555", "24/7"));
        naturalBeauty.add(new Location("Uvac Canyon", "Famous for its meanders and griffon vultures.", "444-444-444", "8:00 AM - 6:00 PM"));
        naturalBeauty.add(new Location("Fruska Gora", "A beautiful national park near Novi Sad.", "666-666-666", "6:00 AM - 8:00 PM"));
        categoryLocations.put("Natural Beauty", naturalBeauty);

        List<Location> artAndMuseums = new ArrayList<>();
        artAndMuseums.add(new Location("National Museum", "The oldest museum in Serbia.", "333-333-333", "10:00 AM - 6:00 PM"));
        artAndMuseums.add(new Location("Museum of Contemporary Art", "Showcases modern and contemporary art.", "222-222-222", "11:00 AM - 7:00 PM"));
        artAndMuseums.add(new Location("Ethnographic Museum", "A museum featuring Serbian culture and traditions.", "777-777-777", "9:00 AM - 5:00 PM"));
        categoryLocations.put("Art and Museums", artAndMuseums);

        categories = new ArrayList<>(categoryLocations.keySet());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        categoriesListView.setAdapter(adapter);

        categoriesListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            String selectedCategory = categories.get(position);
            List<Location> locations = categoryLocations.get(selectedCategory);

            if (locations != null && !locations.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, LocationsActivity.class);
                intent.putExtra("categoryName", selectedCategory);
                intent.putExtra("locations", new ArrayList<>(locations));
                startActivity(intent);
            }
        });
    }

    private void setupSearchFilter() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString().toLowerCase().trim();
                if (query.isEmpty()) {
                    categories = new ArrayList<>(categoryLocations.keySet());
                } else {
                    categories = categoryLocations.keySet().stream()
                            .filter(category -> category.toLowerCase().contains(query))
                            .collect(Collectors.toList());
                }
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, categories);
                categoriesListView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUIElements();
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
            sortCategories();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void sortCategories() {
        categories.sort(String::compareTo);
        adapter.notifyDataSetChanged();
    }
}