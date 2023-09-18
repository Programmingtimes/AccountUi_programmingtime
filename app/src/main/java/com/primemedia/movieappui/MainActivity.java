package com.primemedia.movieappui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.yesterselga.countrypicker.CountryPicker;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomSheets.OnOkButtonClickListener {
    private CircleImageView profileImageView;

PhoneNumberUtil phoneNumberUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profileImageView = findViewById(R.id.profile_image);
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int selectedImageResId = preferences.getInt("selectedImageResId", R.drawable.prof);
        phoneNumberUtil = PhoneNumberUtil.getInstance();
        profileImageView.setImageResource(selectedImageResId);
        ImageView editIcon = findViewById(R.id.editIcon);

        editIcon.setOnClickListener(view -> {
            BottomSheets bottomSheetFragment = new BottomSheets();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });

    }

    @Override
    public void onOkButtonClick(int imageResId) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("selectedImageResId", imageResId);
        editor.apply();

        profileImageView.setImageResource(imageResId);
    }

    public boolean isValidPhoneNumber(String phoneNumber, String countryCode) {
        try
        {
            String phoneNumberWithCountryCode = "+" + countryCode + phoneNumber;
            Phonenumber.PhoneNumber parsedPhoneNumber = phoneNumberUtil.parse(phoneNumberWithCountryCode, null);
            return phoneNumberUtil.isValidNumberForRegion(parsedPhoneNumber, countryCode);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Phone number is not valid
        }
    }
}


