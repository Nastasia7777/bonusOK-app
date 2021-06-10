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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.ViewModels.CardViewModel;

public class ProfileFragment extends Fragment {

    protected CardModel cardModel;
    protected ClientModel clientModel;
    protected ImageView imageView;
    protected TextView bonusQuantity;
    protected PreferenceManager prefs;

    private Button edit_profile_btn;
    private TextView user_name;

    public ProfileFragment() { }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit_profile_btn = view.findViewById(R.id.btn_edit_profile);
        user_name = view.findViewById(R.id.user_name_textView);
        imageView = (ImageView)view.findViewById(R.id.qr);
        bonusQuantity = (TextView)view.findViewById(R.id.bonusQuantity);

        prefs = new PreferenceManager(getActivity());
        cardModel = prefs.getCardModel();
        clientModel = prefs.getClientModel();

        bonusQuantity.setText(getBonusQuantity());
        if (clientModel.getName() != null)
            user_name.setText(clientModel.getName() + " " + clientModel.getSurname());

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

    private void createQr(String code){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);

        }catch(Exception e){
            e.printStackTrace();
        }
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