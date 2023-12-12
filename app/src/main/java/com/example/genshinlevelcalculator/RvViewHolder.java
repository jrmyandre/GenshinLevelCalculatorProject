package com.example.genshinlevelcalculator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public RvViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = itemView.findViewById(R.id.rv_imageViewItem);
        this.textView = itemView.findViewById(R.id.rv_textViewItem);

    }
}
