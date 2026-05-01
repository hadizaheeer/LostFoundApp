package com.example.lostfoundapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, descriptionEditText, dateEditText, locationEditText;
    private RadioGroup typeRadioGroup;
    private Spinner categorySpinner;
    private ImageView selectedImageView;
    private Uri selectedImageUri;
    private DBHelper dbHelper;

    private final ActivityResultLauncher<String[]> imagePicker =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) {
                    try {
                        getContentResolver().takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    selectedImageUri = uri;
                    selectedImageView.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DBHelper(this);

        typeRadioGroup = findViewById(R.id.typeRadioGroup);
        categorySpinner = findViewById(R.id.categorySpinner);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        locationEditText = findViewById(R.id.locationEditText);
        selectedImageView = findViewById(R.id.selectedImageView);

        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        Button saveButton = findViewById(R.id.saveButton);

        String[] categories = {"Electronics", "Pets", "Wallets", "Keys", "Clothing", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        categorySpinner.setAdapter(adapter);

        uploadImageButton.setOnClickListener(v -> imagePicker.launch(new String[]{"image/*"}));

        saveButton.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        int selectedTypeId = typeRadioGroup.getCheckedRadioButtonId();

        if (selectedTypeId == -1) {
            Toast.makeText(this, "Please select Lost or Found", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedTypeId);

        String type = selectedRadioButton.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        long timestamp = System.currentTimeMillis();

        boolean inserted = dbHelper.insertItem(
                type,
                name,
                phone,
                description,
                category,
                date,
                location,
                selectedImageUri.toString(),
                timestamp
        );

        if (inserted) {
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
        }
    }
}