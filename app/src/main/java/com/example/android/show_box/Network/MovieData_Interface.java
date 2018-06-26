package com.example.android.show_box.Network;

import com.example.android.show_box.Models.MoreDetails;
import com.example.android.show_box.Models.MovieResponse;
import com.example.android.show_box.Models.SimilarMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieData_Interface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoreDetails> getMoreDetails(@Path("id") String id, @Query("api_key") String apiKey, @Query("append_to_response") String queries);

    @GET("movie/{id}/similar")
    Call<SimilarMovies> getSimilarMovies(@Path("id") String id, @Query("api_key") String apiKey);

}
