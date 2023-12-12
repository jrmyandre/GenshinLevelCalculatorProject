package com.example.genshinlevelcalculator.models;

import com.google.gson.annotations.SerializedName;

public class WeaponCalculateRequest {
    @SerializedName("weaponId")
    private String WeaponId;

    @SerializedName("lang")
    private String Lang;

    @SerializedName("params")
    private Parameters Params;

    public WeaponCalculateRequest(String weaponId, String lang, Parameters params) {
        WeaponId = weaponId;
        Lang = lang;
        Params = params;
    }

    public static class Parameters {

        @SerializedName("currentLevel")
        private Level CurrentLevel;

        @SerializedName("intendedLevel")
        private Level IntendedLevel;

        public Parameters(Level currentLevel, Level intendedLevel) {
            CurrentLevel = currentLevel;
            IntendedLevel = intendedLevel;
        }
    }

    public static class Level {

        @SerializedName("lvl")
        private int Lvl;

        @SerializedName("asc")
        private boolean Asc;

        @SerializedName("asclLvl")
        private int AsclLvl;

        public Level(int lvl, boolean asc, int asclLvl) {
            Lvl = lvl;
            Asc = asc;
            AsclLvl = asclLvl;
        }
    }
}
