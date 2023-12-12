package com.example.genshinlevelcalculator.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Material implements Parcelable {
    @SerializedName("id")
    private String Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("img")
    private String Img;

    @SerializedName("amount")
    private int Amount;

    @SerializedName("rarity")
    private int Rarity;

    public Material(String id, String name, String img, int amount, int rarity) {
        Id = id;
        Name = name;
        Img = img;
        Amount = amount;
        Rarity = rarity;
    }

    protected Material(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Img = in.readString();
        Amount = in.readInt();
        Rarity = in.readInt();
    }

    public static final Creator<Material> CREATOR = new Creator<Material>() {
        @Override
        public Material createFromParcel(Parcel in) {
            return new Material(in);
        }

        @Override
        public Material[] newArray(int size) {
            return new Material[size];
        }
    };

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getImg() {
        return Img;
    }

    public int getAmount() {
        return Amount;
    }

    public int getRarity() {
        return Rarity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeString(Img);
        parcel.writeInt(Amount);
        parcel.writeInt(Rarity);
    }
}
