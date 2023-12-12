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
import com.example.genshinlevelcalculator.models.CharacterCalculateRequest;
import com.example.genshinlevelcalculator.models.CharacterResultDTO;
import com.example.genshinlevelcalculator.models.Material;
import com.example.genshinlevelcalculator.models.WeaponCalculateRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;

public class CharacterCalculatorPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_calculator_page);

        ImageView imageView = findViewById(R.id.imageView);
        TextView nameTextView = findViewById(R.id.textView);
        EditText currentLevel = findViewById(R.id.currentLevel);
        EditText targetLevel = findViewById(R.id.targetLevel);
        CheckBox currentAscend = findViewById(R.id.currentAscend);
        CheckBox targetAscend = findViewById(R.id.targetAscend);
        Button calculateButton = findViewById(R.id.calculateButton);
        EditText attackCurrentLevel = findViewById(R.id.attackCurrentLevel);
        EditText attackTargetLevel = findViewById(R.id.attackTargetLevel);
        EditText skillCurrentLevel = findViewById(R.id.skillCurrentLevel);
        EditText skillTargetLevel = findViewById(R.id.skillTargetLevel);
        EditText burstCurrentLevel = findViewById(R.id.burstCurrentLevel);
        EditText burstTargetLevel = findViewById(R.id.burstTargetLevel);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String id = "";
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

        InputFilter inputFilter2 = (source, start, end, dest, dstart, dend) -> {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (input >= 1 && input <= 10) {
                    return null;
                }
            } catch (NumberFormatException ignored) {
            }
            return "";
        };

        attackCurrentLevel.setFilters(new InputFilter[]{inputFilter2});
        attackTargetLevel.setFilters(new InputFilter[]{inputFilter2});
        skillCurrentLevel.setFilters(new InputFilter[]{inputFilter2});
        skillTargetLevel.setFilters(new InputFilter[]{inputFilter2});
        burstCurrentLevel.setFilters(new InputFilter[]{inputFilter2});
        burstTargetLevel.setFilters(new InputFilter[]{inputFilter2});

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
                int currentAttack = Integer.parseInt(attackCurrentLevel.getText().toString());
                int targetAttack = Integer.parseInt(attackTargetLevel.getText().toString());
                int currentSkill = Integer.parseInt(skillCurrentLevel.getText().toString());
                int targetSkill = Integer.parseInt(skillTargetLevel.getText().toString());
                int currentBurst = Integer.parseInt(burstCurrentLevel.getText().toString());
                int targetBurst = Integer.parseInt(burstTargetLevel.getText().toString());

                CharacterCalculateRequest.Level currentLevelRequest = new CharacterCalculateRequest.Level(currentLevelNumber,
                        currentAscendChecked, checkAscendLevel(currentLevelNumber, currentAscendChecked)
                );
                CharacterCalculateRequest.Level targetLevelRequest = new CharacterCalculateRequest.Level(
                        targetLevelNumber, targetAscendChecked, checkAscendLevel(targetLevelNumber, targetAscendChecked)
                );
                CharacterCalculateRequest.Talents currentTalentsRequest = new CharacterCalculateRequest.Talents(
                        currentAttack, currentSkill, currentBurst
                );
                CharacterCalculateRequest.Talents targetTalentsRequest = new CharacterCalculateRequest.Talents(
                        targetAttack, targetSkill, targetBurst
                );
                CharacterCalculateRequest.Params params = new CharacterCalculateRequest.Params(
                        currentLevelRequest, targetLevelRequest, currentTalentsRequest, targetTalentsRequest
                );
                CharacterCalculateRequest characterCalculateRequest = new CharacterCalculateRequest(
                        finalId, "english", params
                );

                String url = "https://genshin-builds.com/api/genshin/calculate_character_level";
                Gson gson = new Gson();
                String requestBody = gson.toJson(characterCalculateRequest);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson newGson = new Gson();
                                CharacterResultDTO characterResultDTO = newGson.fromJson(response, CharacterResultDTO.class);
                                int expWasted = characterResultDTO.ExpWasted;
                                Material[] materials = characterResultDTO.Items;
                                Intent intent = new Intent(CharacterCalculatorPage.this, ResultPage.class);
                                intent.putExtra("expWasted", expWasted);
                                intent.putExtra("materialList", materials);
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