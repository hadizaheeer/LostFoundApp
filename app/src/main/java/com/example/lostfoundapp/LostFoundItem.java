package com.example.lostfoundapp;

public class LostFoundItem {
    private int id;
    private String type;
    private String name;
    private String phone;
    private String description;
    private String category;
    private String date;
    private String location;
    private String imageUri;
    private long timestamp;

    public LostFoundItem(int id, String type, String name, String phone, String description,
                         String category, String date, String location, String imageUri, long timestamp) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.category = category;
        this.date = date;
        this.location = location;
        this.imageUri = imageUri;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getImageUri() { return imageUri; }
    public long getTimestamp() { return timestamp; }
}