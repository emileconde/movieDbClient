package com.example.android.conde.com.cinephile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.conde.com.cinephile.R;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.util.AnimationUtil;
import com.example.android.conde.com.cinephile.util.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayAllMoviesAdapter extends RecyclerView.Adapter<DisplayAllMoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovies;
    private ItemClickListener movieItemClickListener;
    private int mPreviousPosition = 0;

    public DisplayAllMoviesAdapter(Context context, List<Movie> movies, ItemClickListener listener) {
        this.mContext = context;
        this.mMovies = movies;
        movieItemClickListener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.item_display_all,viewGroup,false);
        return new MyViewHolder(view);


        }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.TvTitle.setText(mMovies.get(position).getTitle());
        Glide.with(mContext)
                .load(Constants.BASE_IMAGE_URL+ Constants.PROFILE_IMAGE_SIZE
                        +mMovies.get(position).getPosterPath())
                .placeholder(R.drawable.ic_image_black_32dp)
                .error(R.drawable.ic_broken_image_black_32dp)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(myViewHolder.ImgMovie);
        myViewHolder.tvRating.setText(String.format("\t\t%s", String.valueOf(mMovies.get(position).getVoteAverage())));

        //scrolling down
        if(position > mPreviousPosition){
            AnimationUtil.animateY(myViewHolder, true);
        }else {
            AnimationUtil.animateY(myViewHolder, false);
        }
        mPreviousPosition = position;
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TvTitle;
        private ImageView ImgMovie;
        private TextView tvRating;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            TvTitle = itemView.findViewById(R.id.tv_display_all_movie_title);
            ImgMovie = itemView.findViewById(R.id.iv_display_all_movie_poster);
            tvRating = itemView.findViewById(R.id.tv_display_all_movie_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMovieClick(mMovies.get(getAdapterPosition()), ImgMovie);

                }
            });

        }
    }
}
