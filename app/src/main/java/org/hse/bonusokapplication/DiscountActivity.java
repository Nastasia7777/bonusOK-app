package org.hse.bonusokapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.ViewModels.PromoListViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiscountActivity extends AppCompatActivity {

    String tag = "DiscountActivity";
    private String imageFilePath;

    private PromoListViewModel promoListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        //Если есть доступ к Интернету, то обновить акции
        promoListViewModel = ViewModelProviders.of(this).get(PromoListViewModel.class);

        //Если нет доступа, то загрузить акции из кэша
    }

    private void ObserveAnyChange()
    {
        promoListViewModel.getPromos().observe(this, new Observer<List<PromoModel>>() {
            @Override
            public void onChanged(List<PromoModel> promoModels) {
                // observing for any promo data change
            }
        });
    }

    private void searchPromoApi(){
        promoListViewModel.searchPromoApi();
    }

    private void getImageForSaving(ImageView imageView)
    {
        Glide.with(DiscountActivity.this).load("https://sever-press.ru/wp-content/uploads/2019/01/11012019_coffe.jpg").into(imageView);
        try {
            saveImageToCash(imageView);
        } catch (IOException ex) {
            Log.e(tag, "Create file", ex);
        }

    }

    private void saveImageToCash(ImageView img) throws IOException {
        File path = getCacheDir();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File image = File.createTempFile(imageFileName, ".jpg", path);
        imageFilePath = image.getAbsolutePath();
    }

    private void loadImageFromCash(ImageView img) {
        if (imageFilePath != null){
            Glide.with(this).load(imageFilePath).into(img);
        }
    }
}