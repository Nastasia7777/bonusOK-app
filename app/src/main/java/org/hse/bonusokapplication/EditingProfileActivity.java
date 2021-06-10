package org.hse.bonusokapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditingProfileActivity extends RootActivity {

    EditText name_text, surname_text, birthday_text, email_text;
    Button save_button, exit_button;
    Calendar dateAndTime = Calendar.getInstance();
    private PreferenceManager prefs;
    private ClientModel client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        name_text = findViewById(R.id.name);
        surname_text = findViewById(R.id.surname);
        birthday_text = findViewById(R.id.date);
        email_text = findViewById(R.id.email);
        save_button = findViewById(R.id.btn_saving_date);
        exit_button = findViewById(R.id.btn_exit);
        save_button.setEnabled(false);

        prefs = new PreferenceManager(this);
        client = new ClientModel();
        client = prefs.getClientModel();

        getClientData();
        setInitialDateTime();

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        name_text.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) { }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (name_text.getText().length() > 0 ) {
                    save_button.setEnabled(true);
                }
            }
        });

        surname_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (surname_text.getText().length() > 0 ) {
                    save_button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        birthday_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (birthday_text.getText().length() > 0 ) {
                    save_button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        email_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email_text.getText().length() > 0 ) {
                    save_button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void saveClientDataOnClick (View view) {
        client.setName(name_text.getText().toString());
        client.setSurname(surname_text.getText().toString());
        client.setEmail(email_text.getText().toString());
        client.setBirthday(dateAndTime.getTime());
        prefs.saveClientModel(client);
        //отправить пост запрос
        makeClientDataPutApiCall(client.getId(), prefs.getToken(), client);
    }

    public void makeClientDataPutApiCall(int id, String token, ClientModel clientModel) {
        String TAG = "makeClientDataApiCall";
        ClientApi clientApi = Service.getClientApi();
        retrofit2.Call<Void> responseCall = clientApi.putClientData(id, token, clientModel);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d(TAG, "the response code is " + response.code());
                }
                else { //code = 403
                    Log.d(TAG, "invalidToken, the response code is " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    private void showProfile() {
        Intent intent = new Intent(this, ProfileFragment.class);
        startActivity(intent);
        this.finish();
    }

    private void showMain() {
        Intent intent = new Intent(this, MainActivity.class);
        finishAll();
        startActivity(intent);
    }

     @Override
    public void onBackPressed() {
    AlertDialog.Builder ab = new AlertDialog.Builder(EditingProfileActivity.this);
    ab.setTitle("Сообщение");
    ab.setMessage("Вы действительно хотите выйти?");
    ab.setPositiveButton("Да", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            prefs.deleteAllPreferences();
            showMain();
        }
    });
    ab.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
        }
    });

    ab.show();
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

    private void getClientData() {
        if (client.getName() != null)
            name_text.setText(client.getName());
        if (client.getSurname() != null)
            surname_text.setText(client.getSurname());
        if (client.getBirthday() != null)
            dateAndTime.setTime(client.getBirthday());
        if (client.getEmail() != null)
            email_text.setText(client.getEmail());
    }

    public void setDate(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditingProfileActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();

    }

    private void setInitialDateTime() {
        birthday_text.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

}

