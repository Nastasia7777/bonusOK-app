package org.hse.bonusokapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.ViewModels.CardViewModel;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    protected PreferenceManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

         View edit_profile_btn= findViewById(R.id.btn_edit_profile);
        prefs = new PreferenceManager(this);
        imageView = (ImageView)findViewById(R.id.qr);
        imageView.setImageBitmap(CardViewModel.createQrBitmap(getCardCode()));

        edit_profile_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { showEditProfile(); }
        });
    }

     private void showEditProfile() {
         Intent intent = new Intent(this, EditingProfileActivity.class);
         startActivity(intent);
     }


    private String getCardCode(){
        CardModel cardModel = prefs.getCardModel();
        int res = cardModel.getCardCode();
        return Integer.toString(res);
    }
}