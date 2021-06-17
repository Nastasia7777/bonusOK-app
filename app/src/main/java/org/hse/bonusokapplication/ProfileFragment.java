package org.hse.bonusokapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;

import org.hse.bonusokapplication.Models.PromoModel;

import org.hse.bonusokapplication.ViewModels.CardViewModel;
import org.hse.bonusokapplication.ViewModels.PromoListViewModel;

import java.util.List;
import java.util.Random;

public class ProfileFragment extends Fragment {

    protected CardModel cardModel;
  
    private PromoListViewModel promoListViewModel;
    private int clientId;
    public ClientModel clientModel;
    protected ImageView imageView;
    protected TextView bonusQuantity;
    protected PreferenceManager prefs;

    private Button edit_profile_btn;
    private TextView user_name;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ProfileFragment() { }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         prefs = new PreferenceManager(getActivity());
        cardModel = prefs.getCardModel();
        clientModel = prefs.getClientModel();
        clientId = clientModel.getId();
        edit_profile_btn = view.findViewById(R.id.btn_edit_profile);
        user_name = view.findViewById(R.id.user_name_textView);

        imageView = (ImageView)view.findViewById(R.id.qr);
        bonusQuantity = (TextView)view.findViewById(R.id.bonusQuantity);

        prefs = new PreferenceManager(getActivity());
        cardModel = prefs.getCardModel();
        clientModel = prefs.getClientModel();

        bonusQuantity.setText(getBonusQuantity());
        if (clientModel.getName() != null && clientModel.getSurname() != null)
            user_name.setText(clientModel.getName() + " " + clientModel.getSurname());

        imageView.setImageBitmap(CardViewModel.createQrBitmap(getCardCode()));
        promoListViewModel = ViewModelProviders.of(this).get(PromoListViewModel.class);
        ObserveAnyChange();
        edit_profile_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { showEditProfile(); }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                bonusQuantity.setText(getBonusQuantity());
                if (clientModel.getName() != null && clientModel.getSurname() != null)
                user_name.setText(clientModel.getName() + " " + clientModel.getSurname());
                String s = clientModel.getName();
                String v = clientModel.getSurname();
                //bonusQuantity.invalidate();
                //bonusQuantity.requestLayout();
                user_name.invalidate();
                user_name.requestLayout();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Альтернативный способ
        //  mSwipeRefreshLayout.setColorSchemeColors(
        //  Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

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

    private void ObserveAnyChange()
    {
        promoListViewModel.getPromoListObserver().observe(getActivity(), new Observer<List<PromoModel>>() {
            @Override
            public void onChanged(List<PromoModel> promoModels) {
                // observing for any promo data change
                if(promoModels!=null)
                    for(PromoModel model: promoModels){
                        Log.d("TAG", "onChanged: "+model.getDescription());
                    }
            }
        });
        promoListViewModel.searchPromoApi(clientId);
    }
}