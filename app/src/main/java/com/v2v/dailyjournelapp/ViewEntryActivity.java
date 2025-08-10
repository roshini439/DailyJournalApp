package com.v2v.dailyjournelapp;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewEntryActivity extends AppCompatActivity {

    private TextView viewTitle, viewDate, viewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        viewTitle = findViewById(R.id.viewTitle);
        viewDate = findViewById(R.id.viewDate);
        viewContent = findViewById(R.id.viewContent);

        String raw = getIntent().getStringExtra("entry_raw");
        if (raw != null) {
            String[] parts = raw.split("\\|\\|", 3);
            if (parts.length == 3) {
                viewTitle.setText(parts[0]);
                viewDate.setText(parts[1]);
                viewContent.setText(parts[2]);
            } else {
                viewContent.setText(raw);
            }
        }
    }
}
