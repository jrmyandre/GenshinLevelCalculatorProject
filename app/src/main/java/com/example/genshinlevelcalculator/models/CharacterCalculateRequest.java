package com.example.genshinlevelcalculator.models;

import com.google.gson.annotations.SerializedName;

public class CharacterCalculateRequest {

    @SerializedName("characterId")
    private String characterId;

    @SerializedName("lang")
    private String lang;

    @SerializedName("params")
    private Params params;


    public CharacterCalculateRequest(String characterId, String lang, Params params) {
        this.characterId = characterId;
        this.lang = lang;
        this.params = params;
    }

    public static class Params {



        @SerializedName("currentLevel")
        private Level currentLevel;

        @SerializedName("intendedLevel")
        private Level intendedLevel;

        @SerializedName("currentTalentLvl")
        private Talents currentTalentLvl;

        @SerializedName("intendedTalentLvl")
        private Talents intendedTalentLvl;


        public Params(Level currentLevel, Level intendedLevel, Talents currentTalentLvl, Talents intendedTalentLvl) {
            this.currentLevel = currentLevel;
            this.intendedLevel = intendedLevel;
            this.currentTalentLvl = currentTalentLvl;
            this.intendedTalentLvl = intendedTalentLvl;
        }

    }

    public static class Level {

        @SerializedName("lvl")
        private int lvl;

        @SerializedName("asc")
        private boolean asc;

        @SerializedName("asclLvl")
        private int asclLvl;


        public Level(int lvl, boolean asc, int asclLvl) {
            this.lvl = lvl;
            this.asc = asc;
            this.asclLvl = asclLvl;
        }
    }

    public static class Talents {

        @SerializedName("aa")
        private int aa;

        @SerializedName("skill")
        private int skill;

        @SerializedName("burst")
        private int burst;


        public Talents(int aa, int skill, int burst) {
            this.aa = aa;
            this.skill = skill;
            this.burst = burst;
        }
    }
}
