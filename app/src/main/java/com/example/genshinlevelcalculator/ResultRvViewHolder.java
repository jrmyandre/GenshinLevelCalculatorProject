package com.example.genshinlevelcalculator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultRvViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textViewName;
    TextView textViewAmount;

    public ResultRvViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.rv_imageViewItem);
        this.textViewName = itemView.findViewById(R.id.rv_textViewItemName);
        this.textViewAmount = itemView.findViewById(R.id.rv_textViewItemAmount);
    }

}
