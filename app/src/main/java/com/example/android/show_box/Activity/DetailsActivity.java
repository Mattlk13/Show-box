package com.example.android.show_box.Activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.show_box.Config.ConfigURL;
import com.example.android.show_box.Models.Genre_POJO;
import com.example.android.show_box.Models.MoreDetails;
import com.example.android.show_box.Models.MovieDetails_POJO;
import com.example.android.show_box.Models.MovieResponse;
import com.example.android.show_box.Network.ApiClient;
import com.example.android.show_box.Network.MovieData_Interface;
import com.example.android.show_box.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.example.android.show_box.BuildConfig.API_KEY;


public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    MovieDetails_POJO movie_details;

    @BindView(R.id.movie_poster_iv) ImageView poster;
    @BindView(R.id.title_tv) TextView title;
    @BindView(R.id.synopsis_tv) TextView synopsis;
    @BindView(R.id.rating_tv) TextView rating;
    @BindView(R.id.release_tv) TextView release;
    @BindView(R.id.runtime_tv) TextView runtime;
    @BindView(R.id.tagline_tv) TextView tagline;
    @BindView(R.id.status_tv) TextView status;
    @BindView(R.id.genres_tv) TextView genres;
    @BindView(R.id.genres_type_tv) TextView genres_types;
    @BindView(R.id.backdrop_ll) LinearLayout backdrop;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        movie_details = getIntent().getParcelableExtra("movieList");
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

        network_helper();

    }





    private void network_helper(){

        MovieData_Interface apiService = ApiClient.getClient().create(MovieData_Interface.class);
        Call<MoreDetails> call = apiService.getMoreDetails(movie_details.getid(), API_KEY);;
        Log.v("id", movie_details.getid());
        call.enqueue(new Callback<MoreDetails>() {
            @Override
            public void onResponse(Call<MoreDetails> call, Response<MoreDetails> response) {
                if(response.isSuccessful()){
                    if( response.body() != null) {
                        List<Genre_POJO> moreDetails = response.body().getGenres();
                        String runTime = response.body().getRuntime();
                        String tagLine = response.body().getTagline();
                        String movieStatus = response.body().getStatus();
                        String budget = response.body().getBudget();
                        String revenue = response.body().getRevenue();

                        runtime.setText(runTime + "mins");
                        tagline.setText(tagLine);
                        status.setText(movieStatus);
                        String genre = "";
                        for(Genre_POJO s : moreDetails){
                            genre +=s + " ";
                        }

                        genres_types.setText(genre.toString());
                    }
                }else {
                    switch (response.code()) {
                        case 400:
                            Toast.makeText(DetailsActivity.this, "Validation failed.", Toast.LENGTH_LONG).show();
                            synopsis.setText("Error 400");
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case 401:
                            Toast.makeText(DetailsActivity.this, "Suspended API key: Access to your account has been suspended, contact TMDb.", Toast.LENGTH_LONG).show();
                            synopsis.setText("Error 401");
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case 403:
                            Toast.makeText(DetailsActivity.this, "Duplicate entry: The data you tried to submit already exists.", Toast.LENGTH_LONG).show();
                            synopsis.setText("Error 403");
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case 404:
                            synopsis.setText(R.string.error_404);
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            Toast.makeText(DetailsActivity.this, "Invalid id: The pre-requisite id is invalid or not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(DetailsActivity.this, "Internal error: Something went wrong, contact TMDb.", Toast.LENGTH_LONG).show();
                            synopsis.setText("Error 500");
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case 501:
                            Toast.makeText(DetailsActivity.this, "Invalid service: this service does not exist.", Toast.LENGTH_LONG).show();
                            synopsis.setText("Error 501");
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                        case 503:
                            Toast.makeText(DetailsActivity.this, "Service offline: This service is temporarily offline, try again later.", Toast.LENGTH_LONG).show();
                            synopsis.setText("Error 503");
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                        default:
                            Toast.makeText(DetailsActivity.this, "Service broke status code is: " + response.code(), Toast.LENGTH_LONG).show();
                            synopsis.setText("Error " + response.code());
                            synopsis.setTextColor(getResources().getColor(R.color.white));
                            break;
                    }
                }

            }

            @Override
            public void onFailure(Call<MoreDetails> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
