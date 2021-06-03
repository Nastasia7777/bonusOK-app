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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditingProfileActivity extends AppCompatActivity {

    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        currentDateTime=(TextView)findViewById(R.id.date);
        setInitialDateTime();


        View saving_date_btn= findViewById(R.id.btn_saving_date);
        View exit_btn = findViewById(R.id.btn_exit);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        saving_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showProfile(); }
        });

        EditText name_text = findViewById(R.id.name);
        View  enter1_profile_btn = findViewById(R.id.btn_saving_date);
            enter1_profile_btn.setEnabled(false);

              name_text.addTextChangedListener(new TextWatcher() {

                   public void afterTextChanged(Editable s) {
                   }

                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   }

                   public void onTextChanged(CharSequence s, int start,
                                             int before, int count) {
                       if (name_text.getText().length() > 0 ) {
                           enter1_profile_btn.setEnabled(true);
                           enter1_profile_btn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                         showProfile();
                        }});
                       }
                   }
               });
    }

     private void showProfile() {
         Intent intent = new Intent(this, ProfileActivity.class);
         startActivity(intent);
     }

      private void showMain() {
         Intent intent = new Intent(this, MainActivity.class);
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

    public void setDate(View v) {
        new DatePickerDialog(EditingProfileActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

}

