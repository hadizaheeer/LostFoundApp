package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private Spinner filterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        Button addButton = findViewById(R.id.addButton);
        Button filterButton = findViewById(R.id.filterButton);
        Button showAllButton = findViewById(R.id.showAllButton);
        filterSpinner = findViewById(R.id.filterSpinner);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] categories = {"Electronics", "Pets", "Wallets", "Keys", "Clothing", "Other"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        filterSpinner.setAdapter(spinnerAdapter);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        filterButton.setOnClickListener(v -> {
            String category = filterSpinner.getSelectedItem().toString();
            loadItems(dbHelper.getItemsByCategory(category));
        });

        showAllButton.setOnClickListener(v -> loadItems(dbHelper.getAllItems()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems(dbHelper.getAllItems());
    }

    private void loadItems(ArrayList<LostFoundItem> items) {
        adapter = new ItemAdapter(this, items, item -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("itemId", item.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}