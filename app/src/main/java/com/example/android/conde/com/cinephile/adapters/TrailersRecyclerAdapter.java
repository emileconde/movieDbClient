package com.example.android.conde.com.cinephile.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.conde.com.cinephile.R;
import com.example.android.conde.com.cinephile.models.Trailer;
import com.example.android.conde.com.cinephile.util.AnimationUtil;
import com.example.android.conde.com.cinephile.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersRecyclerAdapter extends RecyclerView.Adapter<TrailersRecyclerAdapter.TrailerViewHolder> {

    private List<Trailer> mTrailers;
    private Context mContext;
    private int mPreviousPosition = 0;

    public TrailersRecyclerAdapter(List<Trailer> trailers, Context context) {
        mTrailers = trailers;
        mContext = context;

    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerViewHolder holder, int position) {
        final Trailer currentTrailer = mTrailers.get(position);


           holder.mYouTubeThumbnailView.initialize(Constants.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
               @Override
               public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                   youTubeThumbnailLoader.setVideo(currentTrailer.getKey());
                   youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                       @Override
                       public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                           youTubeThumbnailView.setVisibility(View.VISIBLE);
                           youTubeThumbnailLoader.release();
                       }

                       @Override
                       public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                       }
                   });

               }

               @Override
               public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

               }
           });

        if(position > mPreviousPosition){
            AnimationUtil.animateX(holder, true);
        }else {
            AnimationUtil.animateX(holder, false);
        }

        mPreviousPosition = position;

    }



    @Override
    public int getItemCount() {
        return mTrailers != null ? mTrailers.size() : 0;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private YouTubeThumbnailView mYouTubeThumbnailView;
        private FloatingActionButton mPlayTrailerFab;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            mYouTubeThumbnailView = itemView.findViewById(R.id.youtube_thumbnail);
            mPlayTrailerFab = itemView.findViewById(R.id.play_trailer_fab);
            mPlayTrailerFab.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Trailer trailer = mTrailers.get(getAdapterPosition());
            playYoutubeVideo(mContext, trailer.getKey());
        }

        public void playYoutubeVideo(Context context, String id){
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.YOUTUBE_LINK + id));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }}


    }





}
