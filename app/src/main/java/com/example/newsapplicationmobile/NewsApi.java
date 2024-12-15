package com.example.newsapplicationmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("v2/everything")
    Call<NewsResponse> getNews(
            @Query("q") String query,
            @Query("from") String from,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
