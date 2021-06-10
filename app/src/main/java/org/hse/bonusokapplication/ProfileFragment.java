package org.hse.bonusokapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.ViewModels.CardViewModel;

public class ProfileFragment extends Fragment {
        ImageView imageView;
        TextView bonusQuantity;
    protected PreferenceManager prefs;
    protected CardModel cardModel;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         View edit_profile_btn = view.findViewById(R.id.btn_edit_profile);
        prefs = new PreferenceManager(getActivity());
        cardModel = prefs.getCardModel();
        imageView = (ImageView)view.findViewById(R.id.qr);
        bonusQuantity = (TextView)view.findViewById(R.id.bonusQuantity);
        bonusQuantity.setText(getBonusQuantity());
        imageView.setImageBitmap(CardViewModel.createQrBitmap(getCardCode()));

        edit_profile_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { showEditProfile(); }
        });
    }
 private void showEditProfile() {
         Intent intent = new Intent(getActivity(), EditingProfileActivity.class);
         startActivity(intent);
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private String getCardCode(){
        int res = cardModel.getCardCode();
        return Integer.toString(res);
    }

    private String getBonusQuantity(){
        int res = cardModel.getBonusQuantity();
        return Integer.toString(res);
    }


}