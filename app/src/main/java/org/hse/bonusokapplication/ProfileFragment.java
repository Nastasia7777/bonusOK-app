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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfileFragment extends Fragment {
        ImageView imageView;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         View edit_profile_btn = view.findViewById(R.id.btn_edit_profile);

        imageView = (ImageView)view.findViewById(R.id.qr);
        createQr("1234567891112222");

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
}