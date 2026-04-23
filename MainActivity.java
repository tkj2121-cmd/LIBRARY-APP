package com.example.libraryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText studentName, bookTitle, authorName, issueDate, returnDate;
    Button addBtn;
    RecyclerView recyclerView;

    ArrayList<Book> list;
    BookAdapter adapter;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentName = findViewById(R.id.studentName);
        bookTitle   = findViewById(R.id.bookTitle);
        authorName  = findViewById(R.id.authorName);
        issueDate   = findViewById(R.id.issueDate);
        returnDate  = findViewById(R.id.returnDate);
        addBtn      = findViewById(R.id.addBtn);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new BookAdapter(this, list);
        recyclerView.setAdapter(adapter);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        loadData();

        addBtn.setOnClickListener(v -> {
            String sn = studentName.getText().toString().trim();
            String bt = bookTitle.getText().toString().trim();
            String an = authorName.getText().toString().trim();
            String id = issueDate.getText().toString().trim();
            String rd = returnDate.getText().toString().trim();

            if (!sn.isEmpty() && !bt.isEmpty() && !an.isEmpty()
                    && !id.isEmpty() && !rd.isEmpty()) {

                db.execSQL("INSERT INTO books(studentName, bookTitle, authorName, " +
                        "issueDate, returnDate, status) VALUES('" +
                        sn+"','"+bt+"','"+an+"','"+id+"','"+rd+"','Pending')");

                studentName.setText("");
                bookTitle.setText("");
                authorName.setText("");
                issueDate.setText("");
                returnDate.setText("");

                list.clear();
                loadData();
                Toast.makeText(this, "Book Issued!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        loadData();
    }

    private void loadData() {
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);
        while (cursor.moveToNext()) {
            list.add(new Book(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            ));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
