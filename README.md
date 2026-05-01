📱 Lost & Found App (SIT305 Task 7.1P)


📌 Overview
The Lost & Found App is an Android mobile application developed using Java and SQLite. The app allows users to create, view, filter, and manage lost or found item adverts. It provides a simple and effective way to help reconnect lost items with their owners.


🎯 Features
✅ Create Advert
Users can create a new lost or found item post
Required fields:
Item type (Lost / Found)
Category
Name
Phone number
Description
Date
Location
Image upload is mandatory


🖼️ Image Upload
Users must upload an image for each advert
Images are selected using the Android document picker
Persistent URI permissions are used to ensure images remain accessible


🗂️ Category Filtering
Users can filter items by category:
Electronics
Pets
Wallets
Keys
Clothing
Other
Implemented using SQLite queries


🕒 Timestamp
Each advert is automatically assigned a timestamp
Displayed in a readable date-time format


📋 View Items
All adverts are displayed in a RecyclerView
Each item shows:
Image
Title
Category
Timestamp
Location


🔍 Item Details
Clicking an item opens a detailed view
Displays all stored information including the image


❌ Remove Advert
Users can delete an advert once the item is found
Removes the record from the SQLite database


🛠️ Technologies Used
Java
Android Studio
SQLite (SQLiteOpenHelper)
RecyclerView
Android Activity Result API
View-based UI (XML layouts)


🗄️ Database Structure
Table: items
Column	Type
id	INTEGER (Primary Key)
type	TEXT
name	TEXT
phone	TEXT
description	TEXT
category	TEXT
date	TEXT
location	TEXT
imageUri	TEXT
timestamp	INTEGER


🧠 Key Implementation Details
SQLite is used for local data persistence
RecyclerView is used for efficient list rendering
Category filtering is implemented via SQL queries
Image URIs are stored as strings in the database
Persistent URI permissions are used to prevent security exceptions when loading images


🎥 Demo Video
Watch the demonstration here:
👉 https://youtu.be/4I__nz1skvU


📂 Project Structure
com.example.lostfoundapp
│
├── MainActivity.java
├── AddItemActivity.java
├── DetailActivity.java
├── DBHelper.java
├── LostFoundItem.java
└── ItemAdapter.java


▶️ How to Run
Open the project in Android Studio
Sync Gradle files
Run the app on an emulator or physical device
Create a new advert and test all features


🧪 Testing
The following functionalities were tested:
Input validation (empty fields, missing image)
Image upload and display
Data storage and retrieval from SQLite
Category filtering
Navigation between screens
Item deletion
Timestamp display
