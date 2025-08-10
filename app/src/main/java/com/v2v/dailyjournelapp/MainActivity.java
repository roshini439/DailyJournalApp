package com.v2v.dailyjournelapp;




import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText titleInput, contentInput;
    Button saveButton;
    ListView journalList;
    ArrayList<String> entries;
    ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    String PREFS_NAME = "JournalData";
    String ENTRIES_KEY = "Entries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleInput = findViewById(R.id.titleInput);
        contentInput = findViewById(R.id.contentInput);
        saveButton = findViewById(R.id.saveButton);
        journalList = findViewById(R.id.journalList);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        entries = new ArrayList<>(getSavedEntries());

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entries);
        journalList.setAdapter(adapter);

        saveButton.setOnClickListener(v -> saveEntry());

        journalList.setOnItemClickListener((parent, view, position, id) -> {
            String entry = entries.get(position);
            Intent intent = new Intent(MainActivity.this, ViewEntryActivity.class);
            intent.putExtra("entryData", entry);
            startActivity(intent);
        });
    }

    private void saveEntry() {
        String title = titleInput.getText().toString().trim();
        String content = contentInput.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        String entry = title + " | " + date + " | " + content;

        entries.add(0, entry); // Add on top
        adapter.notifyDataSetChanged();
        saveEntriesToPrefs();

        titleInput.setText("");
        contentInput.setText("");

        Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show();
    }

    private void saveEntriesToPrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(ENTRIES_KEY, new HashSet<>(entries));
        editor.apply();
    }

    private List<String> getSavedEntries() {
        return new ArrayList<>(sharedPreferences.getStringSet(ENTRIES_KEY, new HashSet<>()));
    }
}
