package com.example.android.show_box.Adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.show_box.Activity.DetailsActivity;
import com.example.android.show_box.Config.ConfigURL;
import com.example.android.show_box.Models.MovieDetails_POJO;
import com.example.android.show_box.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>{


    Context context;
    private List<MovieDetails_POJO> mMovieList;
    private LayoutInflater mInflater;


    public MovieListAdapter(Context context,  List<MovieDetails_POJO> mMovieList  ){
        this.context = context;
        this.mMovieList = mMovieList;
        mInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.movie_list_item,parent,false);
    return new MovieViewHolder(mItemView, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {

        String imagePath = ConfigURL.POSTER_PATH + mMovieList.get(position).getPosterPath();
        Picasso.with(context).load(imagePath)
                .placeholder(R.drawable.ic_action_placeholder_white)
                .into(holder.movieList);
        holder.title.setText(mMovieList.get(position).getTitle());
        holder.movieList.setTransitionName("poster");

        holder.movieList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("movieList", mMovieList.get(position));
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.movieList, "poster");
                context.startActivity(intent, optionsCompat.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
  @BindView(R.id.movie_poster_iv) ImageView movieList;
  @BindView(R.id.home_title_tv)
        TextView title;
  MovieListAdapter mAdapter;

    public MovieViewHolder(View itemView, MovieListAdapter movieListAdapter) {
        super(itemView);
        this.mAdapter = movieListAdapter;
        ButterKnife.bind(this,itemView);
    }
}

public void clear(){
        mMovieList.clear();
        notifyDataSetChanged();
}

public void addAll(List<MovieDetails_POJO> list){
        mMovieList.addAll(list);
        notifyDataSetChanged();
}
}