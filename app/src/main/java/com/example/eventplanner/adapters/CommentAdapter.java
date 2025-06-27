package com.example.eventplanner.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplanner.R;
import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.enumeration.StatusType;
import com.example.eventplanner.model.review.CommentResponse;

import java.util.List;
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentResponse> comments;
    private OnCommentActionListener listener;

    public interface OnCommentActionListener {
        void onApprove(CommentResponse comment);
        void onDelete(CommentResponse comment);
    }

    public CommentAdapter(List<CommentResponse> comments, OnCommentActionListener listener) {
        this.comments = comments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentResponse comment = comments.get(position);
        holder.itemName.setText(comment.getItem());
        holder.content.setText(comment.getContent());
        holder.user.setText(comment.getUser().getName() + " " + comment.getUser().getLastName());
        holder.status.setText(comment.getStatus().toString());

        holder.editIcon.setOnClickListener(v -> listener.onApprove(comment));
        holder.deleteIcon.setOnClickListener(v -> listener.onDelete(comment));
    }
    @Override
    public int getItemCount() {
        return comments.size();
    }
    public void updateData(List<CommentResponse> newComments) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return comments.size();
            }

            @Override
            public int getNewListSize() {
                return newComments.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Pretpostavljam da CommentResponse ima jedinstveni ID
                return comments.get(oldItemPosition).getId().equals(newComments.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Možeš dodati detaljniju proveru polja po polju, ili jednostavno:
                return comments.get(oldItemPosition).equals(newComments.get(newItemPosition));
            }
        });

        comments.clear();
        comments.addAll(newComments);
        diffResult.dispatchUpdatesTo(this);
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
