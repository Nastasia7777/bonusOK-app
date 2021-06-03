package org.hse.bonusokapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.hse.bonusokapplication.AuthorizationActivity;
import org.hse.bonusokapplication.DiscountActivity;
import org.hse.bonusokapplication.DiscountDescription;
import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class PromoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context context;
    private OnPromoClick onPromoClick;
    private ImageView promoImage;
    private TextView name;
    private TextView description;

    private void loadPhoto(){
        // set photo
    }

    private void loadImageFromCash(String imageFilePath) {
        if (imageFilePath != null){
            Glide.with(context).load(imageFilePath).into(promoImage);
        } else
            try{
                Glide.with(context).load("https://sever-press.ru/wp-content/uploads/2019/01/11012019_coffe.jpg").into(promoImage);
            } catch (Exception e){
                Log.e("glide","exception", e);
            }

    }

    private PromoModel currentPromo;

    public PromoViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        promoImage = itemView.findViewById(R.id.promoImage);
        name = itemView.findViewById(R.id.name);
        description = itemView.findViewById(R.id.description);
        itemView.setOnClickListener(this);
    }

    public void bind(final PromoModel data){
        name.setText(data.getName()+" c "+timeFormat(data.getStartDate())+" по "+timeFormat(data.getEndDate()));
        description.setText(data.getDescription());
        currentPromo = data;
        Glide.with(context).load("https://sever-press.ru/wp-content/uploads/2019/01/11012019_coffe.jpg").into(promoImage);
    }

    private String timeFormat(Date date){
        Locale rus = new Locale("ru", "RU");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.", rus);
        return simpleDateFormat.format(date);
    }

    @Override
    public void onClick(View view) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(activity, DiscountDescription.class);
        intent.putExtra(DiscountDescription.PROMO_NAME, currentPromo.getName());
        intent.putExtra(DiscountDescription.PROMO_DESCRIPTION, currentPromo.getDescription());
        intent.putExtra(DiscountDescription.PROMO_START_DATE, currentPromo.getStartDate());
        intent.putExtra(DiscountDescription.PROMO_END_DATE, currentPromo.getEndDate());
        activity.startActivity(intent);
    }
}
