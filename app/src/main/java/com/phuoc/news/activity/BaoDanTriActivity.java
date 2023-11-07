package com.phuoc.news.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.phuoc.news.R;
import com.phuoc.news.fragment.DuLichFragment;
import com.phuoc.news.fragment.GiaoDucFragment;
import com.phuoc.news.fragment.KhoaHocFragment;
import com.phuoc.news.fragment.SucKhoeFragment;
import com.phuoc.news.fragment.TheThaoFragment;
import com.phuoc.news.fragment.TrangChuFragment;

public class BaoDanTriActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_THE_THAO = 1;
    private static final int FRAGMENT_SUC_KHOE = 2;
    private static final int FRAGMENT_KHOA_HOC = 3;
    private static final int FRAGMENT_GIAO_DUC = 4;
    private static final int FRAGMENT_DU_LICH = 5;
    private static final int FRAGMENT_BDS = 6;

    private int mCurrentFragment = FRAGMENT_HOME;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_dan_tri);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.tb_dantri);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(BaoDanTriActivity.this, mDrawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_dan_tri);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new TrangChuFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new TrangChuFragment());
            }
            mCurrentFragment = FRAGMENT_HOME;

        } else if (id == R.id.nav_the_thao) {
            if (mCurrentFragment != FRAGMENT_THE_THAO) {
                replaceFragment(new TheThaoFragment());
            }
            mCurrentFragment = FRAGMENT_THE_THAO;

        } else if (id == R.id.nav_suc_khoe) {
            if (mCurrentFragment != FRAGMENT_SUC_KHOE) {
                replaceFragment(new SucKhoeFragment());
            }
            mCurrentFragment = FRAGMENT_SUC_KHOE;

        } else if (id == R.id.nav_khoa_hoc) {
            if (mCurrentFragment != FRAGMENT_KHOA_HOC) {
                replaceFragment(new KhoaHocFragment());
            }
            mCurrentFragment = FRAGMENT_KHOA_HOC;

        } else if (id == R.id.nav_giao_duc) {
            if (mCurrentFragment != FRAGMENT_GIAO_DUC) {
                replaceFragment(new GiaoDucFragment());
            }
            mCurrentFragment = FRAGMENT_GIAO_DUC;

        } else if (id == R.id.nav_du_lich) {
            if (mCurrentFragment != FRAGMENT_DU_LICH) {
                replaceFragment(new DuLichFragment());
            }
            mCurrentFragment = FRAGMENT_DU_LICH;

        } else if (id == R.id.nav_bds) {
            if (mCurrentFragment != FRAGMENT_BDS) {
                replaceFragment(new SucKhoeFragment());
            }
            mCurrentFragment = FRAGMENT_BDS;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_baodantri, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}