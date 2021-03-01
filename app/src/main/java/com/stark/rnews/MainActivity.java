package com.stark.rnews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.rnews.adapter.NewsCategoryAdapter;
import com.stark.rnews.model.News;
import com.stark.rnews.model.ResponseModel;
import com.stark.rnews.newsapi.NewsAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<News> newsList = new ArrayList<>();

    private DatabaseReference databaseReference;
    private VerticalViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verticalViewPager = findViewById(R.id.vertical_view_pager);


       // newsUsingFirebase();

        newsUsingNewsAPI();

    }

    private void newsUsingNewsAPI() {

        Log.i(TAG, "newsUsingNewsAPI: Entering Inside the Method.");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI = retrofit.create(NewsAPI.class);

        Call<ResponseModel> newsCall = newsAPI.getTopHeadlinesByCountry("us", "f50f348d8a1a428084e0dbfcf35b221d");

        newsCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                for (News news : response.body().getNewsList()) {
                    newsList.add(news);
                }
                verticalViewPager.setAdapter(new ViewPageAdapter(MainActivity.this, newsList, verticalViewPager));

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    private void newsUsingFirebase() {
        Intent intent = getIntent();
        String sender = intent.getStringExtra("SENDER");
        if (sender != null && intent.getStringExtra("SENDER").equals(NewsCategoryAdapter.SENDER)) {

            String selectedCategory = intent.getStringExtra(NewsCategoryAdapter.NEWS_CATEGORY);

            databaseReference = FirebaseDatabase.getInstance().getReference(selectedCategory != null ? selectedCategory.trim() : "News");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        newsList.add(snap.getValue(News.class));
                    }

                    verticalViewPager.setAdapter(new ViewPageAdapter(MainActivity.this, newsList, verticalViewPager));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("News");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        newsList.add(dataSnapshot.getValue(News.class));
                    }

                    verticalViewPager.setAdapter(new ViewPageAdapter(MainActivity.this, newsList, verticalViewPager));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    }

}