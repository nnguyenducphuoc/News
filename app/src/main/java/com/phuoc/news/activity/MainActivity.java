package com.phuoc.news.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.phuoc.news.R;
import com.phuoc.news.adapter.ItemAdapter;
import com.phuoc.news.interfacee.IClickItemNewListener;
import com.phuoc.news.model.New;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    List<New> newList;
    CircleImageView circleImgAvt;
    RecyclerView recyclerView;

    ItemAdapter itemAdapter;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        initNewList();
        initRecycleView();
        showUserInfo();
    }


    private void initRecycleView() {

        itemAdapter = new ItemAdapter(newList, new IClickItemNewListener() {
            @Override
            public void OnClickItemNew(New newBao) {
                switch (newBao.getName()) {
                    case "Báo 24h":
                        intent = new Intent(MainActivity.this, Bao24hActivity.class);
                        startActivity(intent);
                        break;
                    case "Báo Dân Trí":
                        intent = new Intent(MainActivity.this, BaoDanTriActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        return;
                }
            }
        });
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initNewList() {
        newList = new ArrayList<>();
        newList.add(new New(R.drawable.bao24, "Báo 24h"));
        newList.add(new New(R.drawable.dan_tri, "Báo Dân Trí"));
        newList.add(new New(R.drawable.vnexpress_logo, "Báo VNExpress"));
    }

    private void initUi() {
        circleImgAvt = findViewById(R.id.img_circle_img);
        recyclerView = findViewById(R.id.recycle_view_main);
    }

    private void showUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        Uri photoUrl = user.getPhotoUrl();
        Glide.with(this).load(photoUrl).error(R.drawable.user).into(circleImgAvt);
        // circleImgAvt.setImageURI(photoUrl);
    }
}