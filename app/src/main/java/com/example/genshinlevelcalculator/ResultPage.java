package com.example.genshinlevelcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.example.genshinlevelcalculator.models.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultPage extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);

        TextView textView = findViewById(R.id.expWasted);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Material[] materials = new Material[0];

        Intent data = getIntent();
        if (data != null){
            Parcelable[] parcelables = data.getParcelableArrayExtra("materialList");
            if (parcelables != null){
                materials = Arrays.copyOf(parcelables, parcelables.length, Material[].class);
            }
            String expWasted = data.getStringExtra("expWasted");
            if (expWasted != null){
                expWasted = "Exp Wasted: " + expWasted;
                textView.setText(expWasted);
            }
        }

        List<String> names = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        List<String> imagePaths = new ArrayList<>();
        for (Material x :
             materials) {
            names.add(x.getName());
            amounts.add(x.getAmount());
            imagePaths.add(x.getImg());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ResultRvAdapter(this, names, imagePaths, amounts));
        
    }
}