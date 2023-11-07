package com.phuoc.news.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.phuoc.news.R;
import com.phuoc.news.adapter.ItemDetailAdapter;
import com.phuoc.news.adapter.ViewPager24hAdapter;
import com.phuoc.news.model.New_Details;

import java.util.List;

public class Bao24hActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager2 mViewPager2;
    private ViewPager24hAdapter mViewPager24hAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao24h);


        initUi();
        initConfigTab();
    }

    private void initConfigTab() {
        mViewPager2.setAdapter(mViewPager24hAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Trang Chủ");
                        break;
                    case 1:
                        tab.setText("Thể Thao");
                        break;
                    case 2:
                        tab.setText("Sức Khỏe");
                        break;
                    case 3:
                        tab.setText("Khoa Học");
                        break;
                    case 4:
                        tab.setText("Giáo Dục");
                        break;
                    case 5:
                        tab.setText("Du Lịch");
                        break;
                    case 6:
                        tab.setText("Bất Động Sản");
                        break;
                }
            }
        }).attach();
    }

    private void initUi() {
        mTabLayout = findViewById(R.id.tab_layout_bao24h);
        mViewPager2 = findViewById(R.id.view_page_2_bao24h);
        mViewPager24hAdapter = new ViewPager24hAdapter(this);

    }


}