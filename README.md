# Smart Pantry Manager

An Android application built in Java that helps users reduce food waste by tracking the ingredients they have at home. The app uses strict matching logic to suggest recipes that can be cooked using ONLY the ingredients currently in the user's pantry.

## Features
* Pantry management (Add, Read, Update, Delete items)
* Pre-loaded database with 15 recipes
* Strict-matching algorithm for recipe suggestions
* Simple and clean pink-themed user interface

## Database
This app uses **SQLite** via `SQLiteOpenHelper`. This was chosen because it is a lightweight, local storage solution that does not require an internet connection, making the app fully functional offline.
