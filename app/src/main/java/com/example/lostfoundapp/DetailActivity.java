package com.example.lostfoundapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new DBHelper(this);
        itemId = getIntent().getIntExtra("itemId", -1);

        ImageView detailImageView = findViewById(R.id.detailImageView);
        TextView detailTextView = findViewById(R.id.detailTextView);
        Button removeButton = findViewById(R.id.removeButton);

        LostFoundItem item = dbHelper.getItemById(itemId);

        if (item == null) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            detailImageView.setImageURI(Uri.parse(item.getImageUri()));
        } catch (Exception e) {
            detailImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String postedTime = sdf.format(new Date(item.getTimestamp()));

        String details =
                item.getType() + ": " + item.getName() + "\n\n" +
                        "Category: " + item.getCategory() + "\n" +
                        "Phone: " + item.getPhone() + "\n" +
                        "Date Lost/Found: " + item.getDate() + "\n" +
                        "Location: " + item.getLocation() + "\n" +
                        "Posted: " + postedTime + "\n\n" +
                        "Description:\n" + item.getDescription();

        detailTextView.setText(details);

        removeButton.setOnClickListener(v -> {
            boolean deleted = dbHelper.deleteItem(itemId);

            if (deleted) {
                Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to remove advert", Toast.LENGTH_SHORT).show();
            }
        });
    }
}