package com.example.genshinlevelcalculator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvViewHolder>{

    Context context;
    List<String> nameList;
    List<String> imagePathList;

    public RvAdapter(Context context, List<String> nameList, List<String> imagePathList
    ) {
        this.context = context;
        this.nameList = nameList;
        this.imagePathList = imagePathList;
    }

    @NonNull
    @Override
    public RvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rv_layout, parent, false);
        return new RvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvViewHolder holder, int position) {
        String name = nameList.get(position);
        name = name.replaceAll("_", " ");
        name = WordUtils.capitalizeFully(name);
        String imagePath = imagePathList.get(position);
        String url = "https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin" + imagePath + "?strip=all&quality=100&w=140";
        holder.textView.setText(name);
        Picasso.get().load(url).into(holder.imageView);

        String id = nameList.get(position);
        String finalName = name;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Data.characterNames.contains(id) ? CharacterCalculatorPage.class : WeaponCalculatorPage.class);
                intent.putExtra("id", id);
                intent.putExtra("name", finalName);
                intent.putExtra("imageUrl", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

}
