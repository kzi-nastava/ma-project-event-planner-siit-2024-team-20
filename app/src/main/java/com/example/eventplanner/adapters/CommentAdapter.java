package com.example.eventplanner.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.Comment;
import com.example.eventplanner.model.enumeration.StatusType;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;

    // Constructor
    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.itemName.setText(comment.getItem());
        holder.content.setText(comment.getContent());
        holder.user.setText(comment.getUser());
        holder.status.setText(comment.getStatus().toString());

        // Click listener za odobravanje (approve)
        holder.editIcon.setOnClickListener(v -> {
            comment.setStatus(StatusType.ACCEPTED);
            notifyItemChanged(position); // Ažuriraj prikaz
        });

        // Click listener za brisanje (delete)
        holder.deleteIcon.setOnClickListener(v -> {
            comment.setStatus(StatusType.DENIED);
            notifyItemChanged(position); // Ažuriraj prikaz
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, content, user, status;
        ImageView editIcon, deleteIcon;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_item_name);
            content = itemView.findViewById(R.id.tv_content);
            user = itemView.findViewById(R.id.tv_user);
            status = itemView.findViewById(R.id.tv_status);
            editIcon = itemView.findViewById(R.id.iv_edit);
            deleteIcon = itemView.findViewById(R.id.iv_delete);
        }
    }
}

