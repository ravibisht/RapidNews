package com.stark.rnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.stark.rnews.model.News;

import java.util.List;

public class ViewPageAdapter extends PagerAdapter {

    private static final String TAG = "ViewPageAdapter";
    private List<News> newsList;
    private Context context;
    private LayoutInflater layoutInflater;
    private VerticalViewPager verticalViewPager;

    private int newPosition;
    private float x1, x2;

    public ViewPageAdapter(Context context, List<News> newsList, VerticalViewPager verticalViewPager) {
        this.context = context;
        this.newsList = newsList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.verticalViewPager = verticalViewPager;

        Log.i("TAG", "ViewPageAdapter: Size : " + newsList.size());
    }


    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View itemView = layoutInflater.inflate(R.layout.item_container, container, false);
        ImageView newsImage = itemView.findViewById(R.id.news_image);
        ImageView bottomImage = itemView.findViewById(R.id.bottom_image);
        TextView headLine = itemView.findViewById(R.id.headline);
        TextView description = itemView.findViewById(R.id.description);
        TextView bottomHead = itemView.findViewById(R.id.bottom_head);
        TextView tapHere = itemView.findViewById(R.id.tap_here);


        Glide.with(context)
                .load(newsList.get(position).getImageUrl())
                .centerCrop()
                .into(newsImage);
        Glide.with(context)
                .load(newsList.get(position).getImageUrl())
                .centerCrop()
                .override(12, 12)
                .into(bottomImage);

        headLine.setText(newsList.get(position).getTitle());
        description.setText(newsList.get(position).getDesc());
        bottomHead.setText(newsList.get(position).getBottomHead());
        bottomHead.setSelected(true);


        if (position == 1)

            verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }

                @Override
                public void onPageSelected(int position) {
                    newPosition = position;

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX(); //it will get the location of when user press the screen

                        Log.i(TAG, "onTouch: Action Down : " + x1);
                        break;

                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "onTouch: Action Up : " + x2);

                        x2 = event.getX();  //it will get the location of touch of the when user touch screen after ACTION_DOWN

                        float deltaX = x1 - x2; // it will minus the distance  of first touch and seconds touch of the touch . so we can get the actual distance of the swipe

                        Log.i(TAG, "onTouch: delta : " + deltaX);

                        if (deltaX > 250) { //checking for user must swipe towards right side and distance must be over 100 pixel

                            Intent intent = new Intent(context, NewsDetailActivity.class);

                            if (position == 1)
                                intent.putExtra("newsUrl", newsList.get(0).getNewsUrl());
                            else
                                intent.putExtra("newsUrl", newsList.get(newPosition).getNewsUrl());
                            context.startActivity(intent);
                          //  ((MainActivity) context).overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);

                        } else if (deltaX < -200) {
                            Intent intent = new Intent(context, CategoriesActivity.class);
                            if (position == 1)
                                intent.putExtra("newsUrl", newsList.get(0).getNewsUrl());
                            else
                                intent.putExtra("newsUrl", newsList.get(newPosition).getNewsUrl());

                            context.startActivity(intent);
                            ((MainActivity) context).overridePendingTransition(R.anim.anim_left_in, R.anim.anim_right_out);

                        }
                        break;
                }

                return false;
            }
        });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);

    }


}

