package com.stark.rnews.newsapi;

import com.stark.rnews.model.News;
import com.stark.rnews.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("top-headlines")
    Call<ResponseModel> getTopHeadlinesByCountry(@Query("country") String country, @Query("apiKey") String apiKey);
}
