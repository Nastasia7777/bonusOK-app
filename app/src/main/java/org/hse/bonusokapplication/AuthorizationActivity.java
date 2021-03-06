package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Models.DeviceModel;
import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.Repositories.ClientRepository;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;
import org.hse.bonusokapplication.ViewModels.ClientViewModel;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends BaseClientActivity {

    private Button get_code_btn, enter2_profile_btn;
    private EditText phone_input_text, code_input_text;
    private String phone_number, sms_code;
    TextView mTimer;
    //private Boolean user_can_enter = false;
    boolean hasTimerFinished = false;

    private String TAG = "AuthorizationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        get_code_btn = findViewById(R.id.btn_get_code);
        enter2_profile_btn= findViewById(R.id.btn_enter2_profile);
        phone_input_text = findViewById(R.id.phone_input);
        code_input_text = findViewById(R.id.code_input);
        get_code_btn.setEnabled(false);
        enter2_profile_btn.setEnabled(false);
        code_input_text.setEnabled(false);

        //???????????????????????? ???????????? ?????????? ????????????????
        phone_input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //?????????????????? ?????????? ???????????????????? ???????????? ?? ?????????????? ???? ???????? ???????????? ??????????????
                phone_number = phone_input_text.getText().toString();
                if (checkPhoneNumber(phone_number)) get_code_btn.setEnabled(true);
                else get_code_btn.setEnabled(false);
                phone_number = deleteSymbolsFromPhoneNumber(phone_number);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        //???????????????????????? ???????????? ??????-??????
        code_input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //???????????????? ?????????? ??????-????????
                sms_code = code_input_text.getText().toString();
                if (checkCode(sms_code)) enter2_profile_btn.setEnabled(true);
                else enter2_profile_btn.setEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mTimer = findViewById(R.id.resend_code);

        mTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasTimerFinished) {
                    startResendTimer();
                }
            }
        });
    }

    public void startResendTimer ()
    {
        AuthorizationActivity context = this;

        mTimer.setClickable(false);
        mTimer.setFocusable(false);
        get_code_btn.setEnabled(false);
        hasTimerFinished = false;
        //?????????????? ???????????? ?????????????????? ?????????????? ???? 20 ???????????? ?? ?????????? ??????????????
        //?? 1 ?????????????? (???????????? ???????????????? ?? ??????????????????????????):
        new CountDownTimer(30000, 1000) {

            //?????????? ?????????????????? ?????????? ???????????????? ?????????????????? ?????????????? ?? ???????????? ????????????????
            public void onTick(long millisUntilFinished) {
                mTimer.setText("????????????????: "
                        + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTimer.setText("?????????????????? ????????????????");
                mTimer.setTextColor(ContextCompat.getColor(context, R.color.link));
                mTimer.setClickable(true);
                mTimer.setFocusable(true);
                hasTimerFinished = true;
                get_code_btn.setEnabled(true);
            }
        }
                .start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            this.finish();
            return true;
      default:
           return super.onOptionsItemSelected(item); }
    }

    // ???????????????? ??????-??????
    public void getCodeOnClick(View view){
        if (phone_number == null) {
            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        prefs.savePhoneNumber(phone_number);
        observeAnyChangeAboutUserStatus();
        makeAuthCodeApiCall(phone_number);
        get_code_btn.setEnabled(false);
    }

    private void observeAnyChangeAboutUserStatus(){
        clientViewModel.user_is_registered.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isUserReg) {
                if (isUserReg) {
                    Log.d(TAG, "TRUE" );
                    code_input_text.setEnabled(true);
                }
                else {
                Log.d(TAG, "FALSE" );
                    code_input_text.setEnabled(false);
                    Toast.makeText(getApplicationContext(), R.string.user_is_unregistered, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //?????????????????????? ??????-???????? ????????????????????????
    public void makeAuthCodeApiCall (String phone)
    {
       startResendTimer();
        ClientApi clientApi = Service.getClientApi();
        retrofit2.Call<Void> responseCall = clientApi.getAuthCode(phone);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d(TAG, "the response code is " + response.code());
                    clientViewModel.user_is_registered.postValue(true);
                }
                else { //code = 404
                    Log.d(TAG, "the response code is " + response.code());
                    clientViewModel.user_is_registered.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    //???????????????? ???????????? ???????????????????????? ?? ???????????? ?? ??????????????
    public void enterToProfileOnClick (View view) {
        if (phone_number == null) {
            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (sms_code == null) {
            Toast.makeText(this, R.string.input_sms_code, Toast.LENGTH_SHORT).show();
            return;
        }
        observeAnyChangeAboutToken();
        makeTokenApiCall(phone_number, sms_code);
    }

    protected void showProfile(){
        super.showProfile();
        this.finish();
    }
}
