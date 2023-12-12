package com.example.genshinlevelcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.genshinlevelcalculator.models.WeaponCalculateRequest;
import com.example.genshinlevelcalculator.models.Material;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;

public class WeaponCalculatorPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_calculator_page);

        ImageView imageView = findViewById(R.id.imageView);
        TextView nameTextView = findViewById(R.id.textView);
        EditText currentLevel = findViewById(R.id.currentLevel);
        EditText targetLevel = findViewById(R.id.targetLevel);
        CheckBox currentAscend = findViewById(R.id.currentAscend);
        CheckBox targetAscend = findViewById(R.id.targetAscend);
        Button calculateButton = findViewById(R.id.calculateButton);

        String id = "";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Gson gson = new Gson();

        Intent data = getIntent();
        if (data != null){
            id = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            String imageUrl = data.getStringExtra("imageUrl");

            Picasso.get().load(imageUrl).into(imageView);
            nameTextView.setText(name);
        }

        InputFilter inputFilter = (source, start, end, dest, dstart, dend) -> {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (input >= 1 && input <= 90) {
                    return null;
                }
            } catch (NumberFormatException ignored) {
            }
            return "";
        };

        currentLevel.setFilters(new InputFilter[]{inputFilter});
        targetLevel.setFilters(new InputFilter[]{inputFilter});

        checkCanAscend(currentLevel, currentAscend);
        checkCanAscend(targetLevel, targetAscend);

        String finalId = id;
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean currentAscendChecked = currentAscend.isChecked();
                boolean targetAscendChecked = targetAscend.isChecked();



                int currentLevelNumber = Integer.parseInt(currentLevel.getText().toString());
                int targetLevelNumber = Integer.parseInt(targetLevel.getText().toString());
                WeaponCalculateRequest.Level currentLevelRequest = new WeaponCalculateRequest.Level(currentLevelNumber,
                        currentAscendChecked, checkAscendLevel(currentLevelNumber, currentAscendChecked)
                );
                WeaponCalculateRequest.Level targetLevelRequest = new WeaponCalculateRequest.Level(
                        targetLevelNumber, targetAscendChecked, checkAscendLevel(targetLevelNumber, targetAscendChecked)
                );
                WeaponCalculateRequest.Parameters parameters = new WeaponCalculateRequest.Parameters(
                        currentLevelRequest, targetLevelRequest
                );
                WeaponCalculateRequest weaponCalculateRequest = new WeaponCalculateRequest(finalId, "english", parameters);

                String url = "https://genshin-builds.com/api/genshin/calculate_weapon_level";
                String requestBody = gson.toJson(weaponCalculateRequest);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson newGson = new Gson();
                                Material[] materialList = newGson.fromJson(response, Material[].class);
                                Intent intent = new Intent(WeaponCalculatorPage.this, ResultPage.class);
                                intent.putExtra("materialList", materialList);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VolleyError", "Error: " + error.toString());
                                NetworkResponse networkResponse = error.networkResponse;
                                if (networkResponse != null) {
                                    Log.e("VolleyError", "Status code: " + networkResponse.statusCode);
                                }

                            }
                        }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            return requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                requestQueue.add(stringRequest);


            }
        });


    }

    private void checkCanAscend(EditText editText, CheckBox checkBox) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String inputText = editable.toString().trim();
                if (!inputText.isEmpty()) {
                    int inputValue = Integer.parseInt(inputText);
                    if (isValidInput(inputValue)){
                        checkBox.setEnabled(true);
                    }
                    else {
                        checkBox.setEnabled(false);
                        checkBox.setChecked(false);
                    }
                }
            }
            private boolean isValidInput(int input) {
                return input == 20 || input == 40 || input == 50 || input == 60 || input == 70 || input == 80;
            }
        });
    }
    private Integer checkAscendLevel(Integer level, boolean ascended){
        int ascLvl;
        if (level > 80){
            ascLvl = 6;
        } else if (level > 70) {
            if (level == 80 && ascended){
                ascLvl = 6;
            }
            else {
                ascLvl = 5;
            }
        } else if (level > 60) {
            if (level == 70 && ascended){
                ascLvl = 5;
            }
            else {
                ascLvl = 4;
            }
        } else if (level > 50) {
            if (level == 60 && ascended){
                ascLvl = 4;
            }
            else {
                ascLvl = 3;
            }
        } else if (level > 40) {
            if (level == 50 && ascended){
                ascLvl = 3;
            }
            else {
                ascLvl = 2;
            }
        } else if (level > 20) {
            if (level == 40 && ascended){
                ascLvl = 2;
            }
            else {
                ascLvl = 1;
            }
        } else if (level == 20 && ascended) {
            ascLvl = 1;
        }
        else {
            ascLvl = 0;
        }
        return ascLvl;
    }
}

