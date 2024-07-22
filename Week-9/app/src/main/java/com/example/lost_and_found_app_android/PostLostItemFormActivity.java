package com.example.lost_and_found_app_android;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class PostLostItemFormActivity extends AppCompatActivity {
    RadioGroup lostOrFoundRadioGroup;
    RadioButton lostItemRadioBtn;
    RadioButton foundItemRadioBtn;
    EditText userName;
    EditText userPhoneNumber;
    EditText itemDescription;
    EditText dateOfPost;
    AutocompleteSupportFragment locationAutocompleteFragment;
    String lostOrFoundPost;
    double itemLocationLat;
    double itemLocationLng;
    String itemLocationName;
    Button savePostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lost_item_form);

        // Widgets
        lostOrFoundRadioGroup = findViewById(R.id.idRadioGroupLostOrFound);
        userName = findViewById(R.id.idNameInput);
        userPhoneNumber = findViewById(R.id.editTextPhone);
        itemDescription = findViewById(R.id.idItemDescription);
        dateOfPost = findViewById(R.id.idEditTextDate);
        savePostButton = findViewById(R.id.idSavePostBtn);
        lostItemRadioBtn = findViewById(R.id.idLostRadioBtn);
        foundItemRadioBtn = findViewById(R.id.idFoundRadioBtn);
        locationAutocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.idAutocompleteLocationFragment);

        lostItemRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lostOrFoundPost = "Lost";
            }
        });

        foundItemRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lostOrFoundPost = "Found";
            }
        });

        /**
         * Google Maps Autocomplete logic
         */

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAWhXUeOuBnND-bYhyd2_CeixffStDGCw4"); // Todo @Jason: REMOVE BEFORE PUSHING
        }

        locationAutocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        locationAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                 itemLocationLat = place.getLatLng().latitude;
                 itemLocationLng = place.getLatLng().longitude;
                 itemLocationName = place.getName();
            }
            @Override
            public void onError(@NonNull Status status) {
                Log.i("Google Maps Autocomplete Error: ", "An error has occurred: " + status);
            }
        });
        savePostButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                try {
                    if (lostOrFoundPost != null) {
                        LostAndFoundModel lostAndFoundPost = new LostAndFoundModel(-1, userName.getText().toString(), Integer.parseInt(userPhoneNumber.getText().toString()), itemDescription.getText().toString(), dateOfPost.getText().toString(), false, itemLocationLat, itemLocationName, itemLocationLng,  lostOrFoundPost);
                        DbHelper dataBaseHelper = new DbHelper(PostLostItemFormActivity.this);
                        boolean success = dataBaseHelper.createLostOrFoundItemRecord(lostAndFoundPost);

                        // Todo @Jason: Create Maps marker using Lat/Lng of location entered on Post activity form.
                        if (success) {
                            Toast.makeText(PostLostItemFormActivity.this, lostOrFoundPost + " item has been posted successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PostLostItemFormActivity.this, "An error has occurred! The item has not been posted.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PostLostItemFormActivity.this, "You must select a category for this post.", Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception e) {
                    Toast.makeText(PostLostItemFormActivity.this, "You must fill out all required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    };
}
