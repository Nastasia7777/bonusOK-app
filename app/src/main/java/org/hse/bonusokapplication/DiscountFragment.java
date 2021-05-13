package org.hse.bonusokapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

public class DiscountFragment extends Fragment {

    private String tag = "DISCOUNT FRAGMENT";

    public DiscountFragment() {
    }

    public static DiscountFragment newInstance() {
        return new DiscountFragment();
    }
//
//        private void getImageForSaving(ImageView imageView)
//    {
//        Glide.with(this).load("https://sever-press.ru/wp-content/uploads/2019/01/11012019_coffe.jpg").into(imageView);
//        try {
//            saveImageToCash(imageView);
//        } catch (IOException ex) {
//            Log.e(tag, "Create file", ex);
//        }
//
//    }

//    private void saveImageToCash(ImageView img) throws IOException {
//        File path = File.getCacheDir();
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
//                Locale.getDefault()).format(new Date());
//        String imageFileName = "IMG_" + timeStamp + "_";
//        File image = File.createTempFile(imageFileName, ".jpg", path);
//        imageFilePath = image.getAbsolutePath();
//    }
//
//    private void loadImageFromCash(ImageView img) {
//        if (imageFilePath != null){
//            Glide.with(this).load(imageFilePath).into(img);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discount, container, false);
    }
}