package com.example.android.conde.com.cinephile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.conde.com.cinephile.R;
import com.example.android.conde.com.cinephile.models.Review;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.CommentViewHolder> {

    private List<Review> mReviewList;

    public CommentsRecyclerAdapter(List<Review> reviewList) {
        mReviewList = reviewList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Review currenReview = mReviewList.get(position);
        holder.author.setText(currenReview.getAuthor());
        holder.content.setText(currenReview.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewList != null ? mReviewList.size() : 0;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        private TextView author, content;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_author);
            content = itemView.findViewById(R.id.tv_comment);
        }
    }

}
