package com.example.android.show_box.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.android.show_box.Models.Videos_POJO;
import com.example.android.show_box.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.show_box.Config.ConfigURL.VIDEOS_PATH;
import static com.example.android.show_box.Config.ConfigURL.VIDEO_THUMBNAIL;
import static com.example.android.show_box.Config.ConfigURL.VIDEO_THUMBNAIL_RESOLUTION;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>{

    Context context;
    private List<Videos_POJO> trailers;
    private LayoutInflater mInflater;


    public VideoListAdapter(Context context, List<Videos_POJO> trailers) {
        this.context = context;
        this.trailers = trailers;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.videos_list_item,parent,false);
        return new VideoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        String thumbnailPath = VIDEO_THUMBNAIL + trailers.get(position).getKey() + VIDEO_THUMBNAIL_RESOLUTION;
        final String videoPath = VIDEOS_PATH + trailers.get(position).getKey();
        Log.v("Adapter Thumbnail", thumbnailPath);

        Picasso.with(context).load(thumbnailPath)
                .placeholder(R.drawable.ic_action_placeholder_white)
                .into(holder.mThumbnail);

        holder.mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class VideoViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.video_thumbnail_ib)
        ImageButton mThumbnail;
        VideoListAdapter mAdapter;

        public VideoViewHolder(View itemView, VideoListAdapter videoListAdapter) {
            super(itemView);
            this.mAdapter = videoListAdapter;
            ButterKnife.bind(this,itemView);
        }
    }
}

