package com.example.android.show_box.Activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.show_box.Adaptors.CastListAdapter;
import com.example.android.show_box.Adaptors.ReviewListAdapter;
import com.example.android.show_box.Adaptors.SimilarMovieListAdapter;
import com.example.android.show_box.Adaptors.VideoListAdapter;
import com.example.android.show_box.Config.ConfigURL;
import com.example.android.show_box.Models.Cast;
import com.example.android.show_box.Models.Credits;
import com.example.android.show_box.Models.Genre_POJO;
import com.example.android.show_box.Models.MoreDetails;
import com.example.android.show_box.Models.MovieDetails_POJO;
import com.example.android.show_box.Models.MovieResponse;
import com.example.android.show_box.Models.Reviews;
import com.example.android.show_box.Models.Reviews_POJO;
import com.example.android.show_box.Models.Videos;
import com.example.android.show_box.Models.Videos_POJO;
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

import static com.example.android.show_box.BuildConfig.API_KEY;
import static com.example.android.show_box.Config.ConfigURL.CREDITS;
import static com.example.android.show_box.Config.ConfigURL.POSTER_PATH;
import static com.example.android.show_box.Config.ConfigURL.REVIEWS;
import static com.example.android.show_box.Config.ConfigURL.VIDEOS;


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
    @BindView(R.id.review_tv)
    TextView mReviewTV;


    @BindView(R.id.video_rv)
    RecyclerView mVideoRecyclerView;
    @BindView(R.id.cast_rv)
    RecyclerView mCastRecyclerView;
    @BindView(R.id.review_rv)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.similar_rv)
    RecyclerView mSimilarRecyclerView;

    VideoListAdapter mVideoAdapter;
    CastListAdapter mCastAdapter;
    ReviewListAdapter mReviewAdapter;
    SimilarMovieListAdapter mSimilarAdapter;



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

        if(getIntent().getParcelableExtra("movieList") != null) {
            movie_details = getIntent().getParcelableExtra("movieList");
            movieListDetails_helper();
        }
    }


    public void movieListDetails_helper ()
    {
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
        synopsis.setEllipsize(TextUtils.TruncateAt.END);
        synopsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(synopsis.getMaxLines() == 4){
                    synopsis.setEllipsize(null);
                    synopsis.setMaxLines(100);
                } else {
                    if(synopsis.getMaxLines() == 100){
                        synopsis.setEllipsize(TextUtils.TruncateAt.END);
                        synopsis.setMaxLines(4);
                    }
                }
            }
        });
        rating.setText(user_rating);
        release.setText(outputDate);
        title.setText(title_value);

        network_helper(movie_details.getid());

    }


    private void videoRV(List<Videos_POJO> trailers){
        mVideoAdapter = new VideoListAdapter(this, trailers);
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false));
        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
    }

    private void castRV(List<Cast> cast){
        mCastAdapter = new CastListAdapter(this, cast);
        mCastRecyclerView.setLayoutManager(new GridLayoutManager(this, 1,GridLayoutManager.HORIZONTAL,false));
        mCastRecyclerView.setHasFixedSize(true);
        mCastRecyclerView.setAdapter(mCastAdapter);
    }

    private void reviewRV(List<Reviews_POJO> reviews){
        mReviewAdapter = new ReviewListAdapter(this, reviews);
        mReviewRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewRecyclerView.setNestedScrollingEnabled(false);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
    }

    private void similarRV(List<MovieDetails_POJO> similar){
        mSimilarAdapter = new SimilarMovieListAdapter(this, similar);
        mSimilarRecyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL,false));
        mSimilarRecyclerView.setHasFixedSize(true);
        mSimilarRecyclerView.setAdapter(mSimilarAdapter);
    }

//This is the similar movies recyclerview method helper
    private void similarMovie_network_helper(String id){
        MovieData_Interface apiService = ApiClient.getClient().create(MovieData_Interface.class);
        Call<MovieResponse> call = apiService.getSimilarMovies(id, API_KEY);
        Log.v("url of similar details", call.request().url() + "");
        Log.v("id",id);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        List<MovieDetails_POJO> similarMovies = response.body().getResults();
                        similarRV(similarMovies);
                        Log.d(TAG, "number of similar movies received:" + similarMovies.size());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    private void network_helper(final String id){
        String queries = VIDEOS+","+CREDITS+","+REVIEWS;

        MovieData_Interface apiService = ApiClient.getClient().create(MovieData_Interface.class);
        Call<MoreDetails> call = apiService.getMoreDetails(id, API_KEY, queries);
        Log.v("url of more details", call.request().url() + "");
        Log.v("id", id);
        call.enqueue(new Callback<MoreDetails>() {
            @Override
            public void onResponse(Call<MoreDetails> call, Response<MoreDetails> response) {
                if(response.isSuccessful()){
                    if( response.body() != null) {
                        List<Genre_POJO> moreDetails = response.body().getGenres();
                        String runTime = response.body().getRuntime();
                        String tagLine = response.body().getTagline();
                        String movieStatus = response.body().getStatus();
                        Videos videos = response.body().getVideos();
                        if(videos.getResults().size() != 0) {
                            List<Videos_POJO> trailers = videos.getResults();
                            // A recycler view to set trailers
                            Log.v("Video thumbnail URL", "https://img.youtube.com/vi/" + trailers.get(0).getKey() + "/0.jpg");
                            videoRV(trailers);
                        }

                        Credits credits = response.body().getCredits();
                        if(credits.getCast().size() != 0) {
                            List<Cast> cast = credits.getCast();
                            // A recycler view to set cast
                            Log.v("cast thumbnail URL", POSTER_PATH + cast.get(0).getProfilePath());
                            castRV(cast);
                        }


                        Reviews reviews = response.body().getReviews();
                        if(reviews.getResults().size() != 0) {
                            List<Reviews_POJO> userReviews = reviews.getResults();
                            // A recycler view to set userReviews
                            Log.v("reviews author", userReviews.get(0).getAuthor());
                            reviewRV(userReviews);
                        } else {
                            String reviewDetails = "No Reviews Available";
                            mReviewTV.setText(reviewDetails);
                        }

                        String runtimeInMins = runTime + getString(R.string.mins);
                        runtime.setText(runtimeInMins);
                        tagline.setText(tagLine);
                        status.setText(movieStatus);

                        String genre = "";
                        for(int i = 0; i< moreDetails.size()-1 ; i++){
                           genre += moreDetails.get(i).getName() + ", ";
                        }
                        genre += moreDetails.get(moreDetails.size()-1).getName();

                        genres_types.setText(genre);
                    }
                    similarMovie_network_helper(id);
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
