package com.example.genshinlevelcalculator.models;

import com.google.gson.annotations.SerializedName;

public class CharacterResultDTO {
    @SerializedName("expWasted")
    public int ExpWasted;

    @SerializedName("items")
    public Material[] Items;

    public CharacterResultDTO(int expWasted, Material[] items) {
        ExpWasted = expWasted;
        Items = items;
    }
}
