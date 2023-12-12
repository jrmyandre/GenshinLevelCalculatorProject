package com.example.genshinlevelcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genshinlevelcalculator.models.Material;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultRvAdapter extends RecyclerView.Adapter<ResultRvViewHolder> {
    Context context;

    List<String> name;
    List<String> imagePath;
    List<Integer> amount;

    public ResultRvAdapter(Context context, List<String> name, List<String> imagePath, List<Integer> amount) {
        this.context = context;
        this.name = name;
        this.imagePath = imagePath;
        this.amount = amount;
    }


    @NonNull
    @Override
    public ResultRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.result_rv_layout, parent, false);
        return new ResultRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRvViewHolder holder, int position) {
        holder.textViewName.setText(name.get(position));
        String url = "https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin" + imagePath.get(position) + "?strip=all&quality=100&w=140";
        Picasso.get().load(url).into(holder.imageView);
        holder.textViewAmount.setText(amount.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}
