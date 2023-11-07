package com.phuoc.news.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phuoc.news.R;
import com.phuoc.news.interfacee.IClickItemNewListener;
import com.phuoc.news.model.New;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<New> newList;
    private IClickItemNewListener iClickItemNewListener;

    public ItemAdapter(List<New> newList, IClickItemNewListener iClickItemNewListener) {
        this.newList = newList;
        this.iClickItemNewListener = iClickItemNewListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        final New newBao = newList.get(position);
        if (newBao == null)
            return;

        holder.imgNew.setImageResource(newList.get(position).getImage());
        holder.txtNew.setText(newList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemNewListener.OnClickItemNew(newBao);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (newList != null) {
            return newList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNew;
        TextView txtNew;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgNew = itemView.findViewById(R.id.imgNew);
            txtNew = itemView.findViewById(R.id.txtNew);
        }
    }
}
