package org.hse.bonusokapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.hse.bonusokapplication.Models.PromoModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiscountDescription extends AppCompatActivity {

    private  PromoModel promo;
    public static final String PROMO_NAME = "promo_name";
    public static final String PROMO_DESCRIPTION = "promo_description";
    public static final String PROMO_START_DATE = "promo_start_date";
    public static final String PROMO_END_DATE = "promo_end_date";

    private String promoName;
    private String promoDescription;
    private Date startDate;
    private Date endDate;

    private TextView name;
    private TextView description;
    private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_description);

        promoName = (String) getIntent().getExtras().get(PROMO_NAME);
        promoDescription = (String) getIntent().getExtras().get(PROMO_NAME);
        startDate = (Date) getIntent().getExtras().get(PROMO_START_DATE);
        endDate = (Date) getIntent().getExtras().get(PROMO_END_DATE);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        time = findViewById(R.id.time);
        initData();
    }

    private void initData(){
        name.setText(promoName);
        description.setText(promoDescription);
        time.setText("Акция действует с "+timeFormat(startDate)+" по "+timeFormat(endDate));
    }

    private String timeFormat(Date date){
        Locale rus = new Locale("ru", "RU");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.", rus);
        return simpleDateFormat.format(date);
    }
}

