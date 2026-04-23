package com.example.libraryapp;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    Context context;
    List<Book> list;

    public BookAdapter(Context context, List<Book> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, student, status;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            author = v.findViewById(R.id.author);
            student = v.findViewById(R.id.student);
            status = v.findViewById(R.id.status);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book b = list.get(position);

        holder.title.setText("📖 " + b.bookTitle);
        holder.author.setText("✍️ " + b.authorName);
        holder.student.setText("👤 " + b.studentName);

        if (b.status.equals("Returned")) {
            holder.status.setText("✅ Returned");
            holder.status.setTextColor(0xFF2E7D32);
        } else {
            holder.status.setText("⏳ Pending");
            holder.status.setTextColor(0xFFC62828);
        }

        // Explicit Intent — card click pe DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("ID", b.id);
            intent.putExtra("STUDENT", b.studentName);
            intent.putExtra("TITLE", b.bookTitle);
            intent.putExtra("AUTHOR", b.authorName);
            intent.putExtra("ISSUE", b.issueDate);
            intent.putExtra("RETURN", b.returnDate);
            intent.putExtra("STATUS", b.status);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
