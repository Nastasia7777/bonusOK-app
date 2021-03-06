package org.hse.bonusokapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.hse.bonusokapplication.Adapter.PromoViewHolder;
import org.hse.bonusokapplication.Models.PromoModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiscountDescription extends RootActivity {

    private  PromoModel promo;
    public static final String PROMO_NAME = "promo_name";
    public static final String PROMO_DESCRIPTION = "promo_description";
    public static final String PROMO_START_DATE = "promo_start_date";
    public static final String PROMO_END_DATE = "promo_end_date";
    public static final String PROMO_IMAGE = "promo_image";

    private String promoName;
    private String promoDescription;
    private Date startDate;
    private Date endDate;
    private String promoImage;

    private TextView name;
    private TextView description;
    private TextView time;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_description);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        promoName = (String) getIntent().getExtras().get(PROMO_NAME);
        promoDescription = (String) getIntent().getExtras().get(PROMO_DESCRIPTION);
        startDate = (Date) getIntent().getExtras().get(PROMO_START_DATE);
        endDate = (Date) getIntent().getExtras().get(PROMO_END_DATE);
        promoImage = (String) getIntent().getExtras().get(PROMO_IMAGE);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        time = findViewById(R.id.time);
        image = findViewById(R.id.image);
        initData();
    }

    private void initData(){
        name.setText(promoName);
        description.setText(promoDescription);
        time.setText("?????????? ?????????????????? ?? "+timeFormat(startDate)+" ???? "+timeFormat(endDate));
        if(PromoViewHolder.loadImageBitmap(promoImage)!=null)
        image.setImageBitmap(PromoViewHolder.loadImageBitmap(promoImage));
    }

    private String timeFormat(Date date){
        Locale rus = new Locale("ru", "RU");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.", rus);
        return simpleDateFormat.format(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item); }
    }
}

