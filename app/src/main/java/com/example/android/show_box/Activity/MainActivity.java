package com.example.android.show_box.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.show_box.Adaptors.MovieListAdapter;
import com.example.android.show_box.BuildConfig;
import com.example.android.show_box.Models.MovieDetails_POJO;
import com.example.android.show_box.Models.MovieResponse;
import com.example.android.show_box.Network.ApiClient;
import com.example.android.show_box.Network.ConnectivityReceiver;
import com.example.android.show_box.Network.MovieData_Interface;
import com.example.android.show_box.Network.MyApplication;
import com.example.android.show_box.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    MovieListAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.API_Key)
    TextView apiTV;

    private int sort_id = 1;
    private int NextPage = 1;
    private final static String API_KEY = BuildConfig.API_KEY;

    GridLayoutManager manager;
    private EndlessRecyclerViewScrollListener scrollListener;
    List<MovieDetails_POJO> movies;


    int spanCount;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    //private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Popular Movies");
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_sort));

        Configuration newConfig = getResources().getConfiguration();
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 4;
        } else {
            if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
                spanCount = 2;
            }
        }
        // Manually checking internet connection
        checkConnection();

    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        connection(isConnected);

    }

    // Checking the status
    private void connection(boolean isConnected){
        if(isConnected){
            mProgressBar.setVisibility(VISIBLE);
            movieFeed(sort_id);
            mProgressBar.setVisibility(GONE);
        } else {
            if(mAdapter != null) {
                mAdapter.clear();
            }
            apiTV.setText("Network Not Available");
            apiTV.setTextColor(getResources().getColor(R.color.white));
            Toast.makeText(MainActivity.this, "Network Not Available", Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_sort_popular:
                getSupportActionBar().setTitle("Popular Movies");
                if(movies != null) {
                    movies.clear();
                    mAdapter.notifyDataSetChanged();
                }
                mProgressBar.setVisibility(VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sort_id = 1;
                        NextPage = 1;
                        movieFeed(sort_id);
                        mProgressBar.setVisibility(GONE);
                    }
                }, 1000);

            break;
            case R.id.action_sort_top:
                getSupportActionBar().setTitle("Top Rated");
                if(movies != null) {
                    movies.clear();
                    mAdapter.notifyDataSetChanged();
                }
                mProgressBar.setVisibility(VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sort_id = 2;
                        NextPage = 1;

                        movieFeed(sort_id);
                        mProgressBar.setVisibility(GONE);
                    }
                }, 1000);

            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateMovieList(List<MovieDetails_POJO> movies ) {
        mProgressBar.setVisibility(GONE);
        manager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieListAdapter(this, movies);
        mRecyclerView.setAdapter(mAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                NextPage++;
                movieFeed(sort_id);
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);
        swipeToRefresh();
    }


    private void swipeToRefresh(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                movieFeed(1);
                Log.v("Log of sort_ID", String.valueOf(sort_id));
                if(movies != null) {
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void movieFeed (final int sort_order){
        MovieData_Interface apiService = ApiClient.getClient().create(MovieData_Interface.class);
        Call<MovieResponse> call = null;
        if(API_KEY == "Enter your API key here"){
                  apiTV.setText(R.string.API_key_error);
                  mProgressBar.setVisibility(GONE);
                  swipeToRefresh();
            } else {
            apiTV.setVisibility(GONE);
                 switch (sort_order) {

                        case 1:
                        call = apiService.getPopularMovies(NextPage, API_KEY);
                        Log.v("page of popular movies", String.valueOf(NextPage));
                        break;
                    case 2:
                        call = apiService.getTopRatedMovies(NextPage, API_KEY);
                        Log.v("page of top rated", String.valueOf(NextPage));
                        break;
    }

    Log.d("API Key:", API_KEY);
    Log.d("URL", call.request().url() + "");
    Log.v("sort ID", String.valueOf(sort_id));
    call.enqueue(new Callback<MovieResponse>() {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            Log.v("status code", String.valueOf(response.code()));
            if(response.isSuccessful()) {
                if (movies == null) {
                    movies = response.body().getResults();
                    generateMovieList(movies);
                    Log.d(TAG, "number of movies received:" + movies.size());
                } else {
                    movies.addAll(response.body().getResults());
                    mAdapter.notifyDataSetChanged();
                    Log.d(TAG, "number of movies received1:" + movies.size());
                }
            } else {

                switch (response.code()){
                    case 400:
                        Toast.makeText(MainActivity.this, "Validation failed.", Toast.LENGTH_LONG).show();
                        apiTV.setText("Error 400");
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 401:
                        Toast.makeText(MainActivity.this, "Suspended API key: Access to your account has been suspended, contact TMDb.", Toast.LENGTH_LONG).show();
                        apiTV.setText("Error 401");
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 403:
                        Toast.makeText(MainActivity.this, "Duplicate entry: The data you tried to submit already exists.", Toast.LENGTH_LONG).show();
                        apiTV.setText("Error 403");
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 404:
                        apiTV.setText(R.string.error_404);
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        Toast.makeText(MainActivity.this, "Invalid id: The pre-requisite id is invalid or not found.", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(MainActivity.this, "Internal error: Something went wrong, contact TMDb.", Toast.LENGTH_LONG).show();
                        apiTV.setText("Error 500");
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 501:
                        Toast.makeText(MainActivity.this, "Invalid service: this service does not exist.", Toast.LENGTH_LONG).show();
                        apiTV.setText("Error 501");
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 503:
                        Toast.makeText(MainActivity.this, "Service offline: This service is temporarily offline, try again later.", Toast.LENGTH_LONG).show();
                        apiTV.setText("Error 503");
                        apiTV.setTextColor(getResources().getColor(R.color.white));
                        break;
                        default:
                            Toast.makeText(MainActivity.this, "Service broke status code is: " + response.code() , Toast.LENGTH_LONG).show();
                            apiTV.setText("Error " + response.code());
                            apiTV.setTextColor(getResources().getColor(R.color.white));
                            break;
                }
            }
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            Log.e(TAG, t.toString());
        }
    });
    }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
       connection(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
        if (mListState != null) {
            manager.onRestoreInstanceState(mListState);
        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Save list state
        mListState = manager.onSaveInstanceState();
        state.putParcelable(KEY_RECYCLER_STATE, mListState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        // Retrieve list state and list/item positions
        if(state != null)
            mListState = state.getParcelable(KEY_RECYCLER_STATE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(manager != null) {
                manager.setSpanCount(4);
            }
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if(manager != null) {
                manager.setSpanCount(2);
            }
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }



}
