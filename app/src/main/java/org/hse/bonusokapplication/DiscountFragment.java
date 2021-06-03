package org.hse.bonusokapplication;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.hse.bonusokapplication.Adapter.ItemAdapter;
import org.hse.bonusokapplication.Adapter.OnPromoClick;
import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.ViewModels.PromoListViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiscountFragment extends Fragment implements OnPromoClick {

    private String tag = "DISCOUNT FRAGMENT";
    private int clientId;

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

    private RecyclerView recyclerView;
    List<PromoModel> promoList = new ArrayList<>();
    private ItemAdapter adapter;

    private PromoListViewModel promoListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            clientId = bundle.getInt(MenuActivity.CLIENT_ID, 5);
        }
        View v = inflater.inflate(R.layout.fragment_discount, container, false);

        //Если есть доступ к Интернету, то обновить акции
        promoListViewModel = ViewModelProviders.of(this).get(PromoListViewModel.class);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new ItemAdapter(getActivity(), promoList, new OnPromoClick() {
            @Override
            public void OnClick(PromoModel data) {
                Toast.makeText(getActivity(), "Clicked Promo Name is : " +data.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        ObserveAnyChange();
        return v;
    }

    private void ObserveAnyChange(){
        promoListViewModel.getPromoListObserver().observe(getActivity(), new Observer<List<PromoModel>>() {
            @Override
            public void onChanged(List<PromoModel> promoModels) {
                if(promoModels != null) {
                    promoList = promoModels;
                    adapter.setMovieList(promoModels);

                    //Обновить список акций в кэше
                } else {

                }
            }
        });
        promoListViewModel.searchPromoApi(clientId);
    }



    @Override
    public void OnClick(PromoModel data) {
        Toast.makeText(getActivity(), "Clicked Promo Name is : " +data.getName(), Toast.LENGTH_SHORT).show();
    }
}