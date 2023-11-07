package com.phuoc.news.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phuoc.news.R;
import com.phuoc.news.interfacee.IClickItemDetailsListener;
import com.phuoc.news.interfacee.IClickItemNewListener;
import com.phuoc.news.model.New;
import com.phuoc.news.model.New_Details;

import java.util.List;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ItemDetailViewHolder>{
    private List<New_Details> list;
    private IClickItemDetailsListener iClickItemDetailsListener;

    public ItemDetailAdapter(List<New_Details> list, IClickItemDetailsListener iClickItemDetailsListener) {
        this.list = list;
        this.iClickItemDetailsListener = iClickItemDetailsListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_details, parent, false);
        return new ItemDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDetailViewHolder holder, int position) {
        final New_Details newDetails = list.get(position);
        if (newDetails == null) {
            return;
        }
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvDesc.setText(list.get(position).getDesc());
        holder.tvReleaseDay.setText(list.get(position).getRelease_date());
       // holder.imageView1.setImageURI(Uri.parse(list.get(position).getImage()));

        Glide.with(holder.imageView1)
                .load(list.get(position).getImage())
                .centerCrop()
                .error(R.drawable.user)
                .into(holder.imageView1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemDetailsListener.OnClickItemNewDetails(newDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        TextView tvTitle, tvDesc, tvReleaseDay;
        public ItemDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView1 = itemView.findViewById(R.id.img_details);
            tvTitle = itemView.findViewById(R.id.title_details);
            tvDesc = itemView.findViewById(R.id.desc_details);
            tvReleaseDay = itemView.findViewById(R.id.release_detail);
        }
    }

}
