package com.example.android.leagueoflegends_api;

import java.util.List;

public class Champion
{
    /*private int mChampionId;
    private String mChampionKey, mChampionName, mChampionTitle;
    private ArrayList<String> mChampionTags;*/

    private int id;
    private String key, name, title;
    private List<String> tags;

    /*public Champion(int id, String key, String name, String title, ArrayList<String> tags)
    {
        mChampionId = id;
        mChampionKey = key;
        mChampionName = name;
        mChampionTitle = title;
        mChampionTags = tags;
    }*/

    public Champion(){}

    public int getChampionId() {
        return id;
    }

    public String getChampionKey() {
        return key;
    }

    public String getChampionName() {
        return name;
    }

    public String getChampionTitle() {
        return title;
    }

    public List<String> getChampionTags() {
        return tags;
    }
}
