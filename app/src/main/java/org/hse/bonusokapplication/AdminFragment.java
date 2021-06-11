package org.hse.bonusokapplication;

import android.content.Intent;
import android.hardware.camera2.CameraCaptureSession;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.hse.bonusokapplication.Models.BonusModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AdminFragment extends Fragment {

    private Button read_qr_btn;
    private Button delete_bonus;
    private Button add_bonus;
    private EditText bonus_count;

    private BonusModel bonusModel;
    private int cardCode = -1;
    private int bonusNumber;
    private PreferenceManager prefs;

    public AdminFragment() { }

    public static AdminFragment newInstance() {return new AdminFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        read_qr_btn = getView().findViewById(R.id.read_qr_code_btn);
        delete_bonus = getView().findViewById(R.id.delete_bonus_btn);
        add_bonus = getView().findViewById(R.id.add_bonus_btn);
        bonus_count = getView().findViewById(R.id.bonus_count_text);

        bonusModel = new BonusModel();
        prefs = new PreferenceManager(getActivity());
        String t = prefs.getToken();
        bonusModel.setAdminId(prefs.getClientModel().getId());

        read_qr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity()).forSupportFragment(AdminFragment.this);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });


        delete_bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardCode == -1)
                {
                    Toast.makeText(getContext(), R.string.skan_qr, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getBonusNumber() > 0) {
                    bonusModel.setBonusQuantity(getBonusNumber());
                    makeDeleteBonusApiCall(bonusModel);
                }
            }
        });

        add_bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardCode == -1)
                {
                    Toast.makeText(getContext(), R.string.skan_qr, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getBonusNumber() > 0) {
                    bonusModel.setBonusQuantity(getBonusNumber());
                    makeAddBonusApiCall(bonusModel);
                }
            }
        });
    }


    public int getBonusNumber (){
        return Integer.parseInt(bonus_count.getText().toString());
    }

    public void makeDeleteBonusApiCall (BonusModel model)
    {
        String TAG = "makeDeleteBonusApiCall";
        ClientApi clientApi = Service.getClientApi();
        Call<Void> responseCall = clientApi.deleteBonus(prefs.getToken(), model);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext(), R.string.bonus_deleted, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), R.string.invalid_bonus_delete, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    public void makeAddBonusApiCall (BonusModel model)
    {
        String TAG = "makeAddBonusApiCall";
        ClientApi clientApi = Service.getClientApi();
        Call<Void> responseCall = clientApi.addBonus(prefs.getToken(), model);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(getContext(), R.string.bonus_added, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), R.string.invalid_bonus_add, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,  resultCode,  data);
        if(intentResult.getContents()!=null){
            try {
                cardCode = Integer.parseInt(intentResult.getContents());
                Log.d("QR", "QR READ CARD_CODE: "+cardCode);
                bonusModel.setCardCode(cardCode);
            }catch(Exception e){
                Log.e("QR", "QR ERROR: ", e);
            }
        }
        else{
            Log.d("QR", "NOTHING");
        }
    }


}