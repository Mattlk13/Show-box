package com.example.android.show_box.Config;

public class ConfigURL {
    public static final String BaseURL = "http://api.themoviedb.org/3/";
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w342";
    public static final String BACKDROP_PATH = "http://image.tmdb.org/t/p/w500";
    public static final String VIDEOS = "videos";
    public static final String VIDEOS_PATH = "https://www.youtube.com/watch?v=";
    public static final String VIDEO_THUMBNAIL = "https://img.youtube.com/vi/";
    // Get the custom thumbnail in 320 x 180 small image resolution then "/mqdefault.jpg"
    // Get the custom thumbnail in 480 x 360 standard image resolution "/0.jpg"
    // Get the custom thumbnail in 720p or 1080p HD image resolution "/maxresdefault.jpg"
    public static final String VIDEO_THUMBNAIL_RESOLUTION = "/0.jpg";
    public static final String CREDITS = "credits";
    public static final String REVIEWS = "reviews";
}
