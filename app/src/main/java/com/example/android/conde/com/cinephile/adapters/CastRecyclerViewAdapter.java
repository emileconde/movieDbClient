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
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.util.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CastRecyclerViewAdapter extends
        RecyclerView.Adapter<CastRecyclerViewAdapter.CastViewHolder> {

    private List<Cast> mCastList;
    private Context mContext;
    private ItemClickListener mItemClickListener;
    public CastRecyclerViewAdapter(Context context, List<Cast> castList, ItemClickListener listener) {
        mCastList = castList;
        mContext = context;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false );
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast currentCastMember = mCastList.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_IMAGE_URL+ Constants.PROFILE_IMAGE_SIZE
                        +currentCastMember.getProfile_path())
                .placeholder(R.drawable.ic_image_black_32dp)
                .error(R.drawable.ic_broken_image_black_32dp)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.castImg);

        holder.castName.setText(currentCastMember.getName());

    }

    @Override
    public int getItemCount() {
        return mCastList != null ? mCastList.size() : 0;
    }

    public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView castImg;
        private TextView castName;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImg = itemView.findViewById(R.id.iv_cast_image);
            castName = itemView.findViewById(R.id.tv_cast_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onCastClick(mCastList.get(getAdapterPosition()), castImg);
        }
    }

}
