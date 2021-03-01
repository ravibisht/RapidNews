package com.stark.rnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stark.rnews.MainActivity;
import com.stark.rnews.R;
import com.stark.rnews.model.NewsCategory;

import java.util.ArrayList;
import java.util.List;

public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.ViewHolder> {

    public static final String SENDER = "NewsCategoryAdapter";
    public static final String NEWS_CATEGORY = "news_category";
    private Context mContext;
    private List<NewsCategory> newsCategoryList;


    public NewsCategoryAdapter(Context context, ArrayList<NewsCategory> newsCategoryList) {
        this.mContext = context;
        this.newsCategoryList = newsCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.news_category_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(newsCategoryList.get(position).getImageUrl())
                .into(holder.imageView);

        holder.textView.setText(newsCategoryList.get(position).getCategoryName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("SENDER", SENDER);
                intent.putExtra(NEWS_CATEGORY, newsCategoryList.get(position).getCategoryName());

                Toast.makeText(mContext, "" + newsCategoryList.get(position).getCategoryName(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsCategoryList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private RelativeLayout relativeLayout;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.news_category_layout);
            imageView = itemView.findViewById(R.id.category_image);
            textView = itemView.findViewById(R.id.news_category_name);
            cardView = itemView.findViewById(R.id.news_cardView);


        }
    }
}
