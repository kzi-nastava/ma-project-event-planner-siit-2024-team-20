package com.example.eventplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.review.CommentViewResponse;

import java.util.List;

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewAdapter.CommentViewHolder> {

    private List<CommentViewResponse> comments;

    public CommentViewAdapter(List<CommentViewResponse> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentViewResponse comment = comments.get(position);
        holder.author.setText(comment.getUser());
        holder.content.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView author, content;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.comment_author);
            content = itemView.findViewById(R.id.comment_content);
        }
    }
}