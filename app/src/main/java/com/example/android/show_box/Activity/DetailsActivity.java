package com.example.android.show_box.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.show_box.Config.ConfigURL;
import com.example.android.show_box.Models.MovieDetails_POJO;
import com.example.android.show_box.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {


    @BindView(R.id.movie_poster_iv) ImageView poster;
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.synopsis_tv) TextView synopsis;
    @BindView(R.id.rating_tv) TextView rating;
    @BindView(R.id.release_tv) TextView release;
    @BindView(R.id.backdrop_ll) LinearLayout backdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        MovieDetails_POJO movie_details = getIntent().getParcelableExtra("movieList");
        String title_value = movie_details.getTitle();
        String poster_path = movie_details.getPosterPath();
        String plot_synopsis = movie_details.getOverview();
        String user_rating = movie_details.getVoteAverage();
        String release_date = movie_details.getReleaseDate();
        final String backdrop_path = movie_details.getBackdrop_path();


        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transation));
        poster.setTransitionName("poster");

        Picasso.with(this).load( ConfigURL.POSTER_PATH + poster_path)
                .into(poster);

        Picasso.with(this).load( ConfigURL.BACKDROP_PATH + backdrop_path)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        backdrop.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String outputDate = null;
        try {
            Date date = inputFormat.parse(release_date);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        synopsis.setText(plot_synopsis);
        rating.setText(user_rating);
        release.setText(outputDate);
        title.setText(title_value);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
