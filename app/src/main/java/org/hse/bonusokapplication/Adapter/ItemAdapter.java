package org.hse.bonusokapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.R;

import java.util.ArrayList;
import java.util.List;

public final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<PromoModel> dataList = new ArrayList<>();
    private OnPromoClick onPromoClick;

    public ItemAdapter(OnPromoClick onItemClick) {
        this.onPromoClick = onItemClick;
    }

    public ItemAdapter(Context context, List<PromoModel> movieList, OnPromoClick clickListener) {
        this.context = context;
        this.dataList = movieList;
        this.onPromoClick = clickListener;
    }

    public void setMovieList(List<PromoModel> movieList) {
        this.dataList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("DataList", dataList.size() + " 1");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_discount, parent, false);
        return new PromoViewHolder(contactView, context);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PromoModel data = dataList.get(position);
        if (holder instanceof PromoViewHolder) {
            Log.d("onBindViewHolder", "ViewHolder");
            ((PromoViewHolder) holder).bind(data);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<PromoModel> list) {
        this.dataList = list;
    }

}