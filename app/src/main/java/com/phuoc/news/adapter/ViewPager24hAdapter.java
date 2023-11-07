package com.phuoc.news.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.phuoc.news.fragment.BatDongSanFragment;
import com.phuoc.news.fragment.DuLichFragment;
import com.phuoc.news.fragment.GiaoDucFragment;
import com.phuoc.news.fragment.KhoaHocFragment;
import com.phuoc.news.fragment.SucKhoeFragment;
import com.phuoc.news.fragment.TheThaoFragment;
import com.phuoc.news.fragment.TrangChuFragment;

public class ViewPager24hAdapter extends FragmentStateAdapter {

    public ViewPager24hAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TrangChuFragment();
            case 1:
                return new TheThaoFragment();
            case 2:
                return new SucKhoeFragment();
            case 3:
                return new KhoaHocFragment();
            case 4:
                return new GiaoDucFragment();
            case 5:
                return new DuLichFragment();
            case 6:
                return new BatDongSanFragment();

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
