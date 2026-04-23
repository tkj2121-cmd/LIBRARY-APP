package com.example.libraryapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id           = getIntent().getIntExtra("ID", -1);
        String student   = getIntent().getStringExtra("STUDENT");
        String title     = getIntent().getStringExtra("TITLE");
        String author    = getIntent().getStringExtra("AUTHOR");
        String issue     = getIntent().getStringExtra("ISSUE");
        String returnD   = getIntent().getStringExtra("RETURN");
        String status    = getIntent().getStringExtra("STATUS");

        TextView detailStudent = findViewById(R.id.detailStudent);
        TextView detailTitle   = findViewById(R.id.detailTitle);
        TextView detailAuthor  = findViewById(R.id.detailAuthor);
        TextView detailIssue   = findViewById(R.id.detailIssue);
        TextView detailReturn  = findViewById(R.id.detailReturn);
        TextView detailDays    = findViewById(R.id.detailDays);
        TextView detailFine    = findViewById(R.id.detailFine);
        TextView detailStatus  = findViewById(R.id.detailStatus);
        Button markBtn         = findViewById(R.id.markReturnedBtn);
        Button deleteBtn       = findViewById(R.id.deleteBtn);

        detailStudent.setText("👤 Student: " + student);
        detailTitle.setText("📖 Book: " + title);
        detailAuthor.setText("✍️ Author: " + author);
        detailIssue.setText("📅 Issue Date: " + issue);
        detailReturn.setText("📅 Return Date: " + returnD);

        // Days remaining + Fine calculation
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date returnDate = sdf.parse(returnD);
            Date today = new Date();
            long diff = returnDate.getTime() - today.getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            if (days >= 0) {
                detailDays.setText("⏳ Days Remaining: " + days);
                detailFine.setText("💰 Fine: ₹0");
            } else {
                long overdue = Math.abs(days);
                long fine = overdue * 2; // ₹2 per day
                detailDays.setText("⚠️ Overdue by: " + overdue + " days");
                detailFine.setText("💰 Fine: ₹" + fine + " (₹2/day)");
            }
        } catch (Exception e) {
            detailDays.setText("⏳ Days: N/A");
            detailFine.setText("💰 Fine: N/A");
        }

        // Status
        if (status.equals("Returned")) {
            detailStatus.setText("✅ Status: Returned");
            detailStatus.setTextColor(0xFF2E7D32);
            markBtn.setEnabled(false);
        } else {
            detailStatus.setText("⏳ Status: Pending");
            detailStatus.setTextColor(0xFFC62828);
        }

        // Mark as Returned
        DBHelper dbHelper = new DBHelper(this);
        markBtn.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE books SET status='Returned' WHERE id=" + id);
            Toast.makeText(this, "Marked as Returned!", Toast.LENGTH_SHORT).show();
            finish(); // wapas MainActivity
        });

        // Delete
        deleteBtn.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM books WHERE id=" + id);
            Toast.makeText(this, "Record Deleted!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

