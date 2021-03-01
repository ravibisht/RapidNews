package com.stark.rnews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stark.rnews.adapter.NewsCategoryAdapter;
import com.stark.rnews.model.NewsCategory;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    private static final String TAG = "CategoriesActivity";
    private RecyclerView mRecyclerView, mHorizontalRecyclerView;
    private ArrayList<NewsCategory> newsCategoryArrayList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mRecyclerView = findViewById(R.id.news_categories_recy_view);
        mHorizontalRecyclerView = findViewById(R.id.vertical_recy_view);
        newsCategoryArrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("NewsCategory");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    newsCategoryArrayList.add(dataSnapshot.getValue(NewsCategory.class));
                }
                NewsCategoryAdapter newsCategoryAdapter = new NewsCategoryAdapter(CategoriesActivity.this, newsCategoryArrayList);
                mRecyclerView.setAdapter(newsCategoryAdapter);
                mHorizontalRecyclerView.setAdapter(newsCategoryAdapter);
                mHorizontalRecyclerView.setLayoutManager(new LinearLayoutManager(CategoriesActivity.this, RecyclerView.HORIZONTAL, true));
                mRecyclerView.setLayoutManager(new GridLayoutManager(CategoriesActivity.this, 3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.anim_left_out);
    }
}