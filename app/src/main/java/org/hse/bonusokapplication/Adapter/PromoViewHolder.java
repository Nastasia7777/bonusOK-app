package org.hse.bonusokapplication.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.R;

public final class PromoViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    private OnPromoClick onPromoClick;
    private ImageView promoImage;
    private TextView name;
    private TextView description;

    private void loadPhoto(){
        // set photo
    }

    public PromoViewHolder(View itemView, Context context,  OnPromoClick onPromoClick) {
        super(itemView);
        this.context = context;
        this.onPromoClick = onPromoClick;
        promoImage = itemView.findViewById(R.id.promoImage);
        name = itemView.findViewById(R.id.name);
        description = itemView.findViewById(R.id.description);
    }

    public void bind(final PromoModel data){
        name.setText(data.getName());
        description.setText(data.getDescription());
    }
}
