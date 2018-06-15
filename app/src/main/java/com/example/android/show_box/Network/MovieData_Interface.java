package com.example.android.show_box.Network;

import com.example.android.show_box.Models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieData_Interface {

    @GET("movie/top_rate")
    Call<MovieResponse> getTopRatedMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("movie/popula")
    Call<MovieResponse> getPopularMovies(@Query("page") int page, @Query("api_key") String apiKey);
}
