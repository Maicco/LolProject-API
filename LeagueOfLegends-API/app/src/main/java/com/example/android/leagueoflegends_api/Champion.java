package com.example.android.leagueoflegends_api;

import java.util.List;

/**
 * Class used to store the champion data received from Riot's server
 */

public class Champion
{
    private int id;
    private String key, name, title;
    private List<String> tags;

    public Champion() {}

    public int getChampionId() {
        return id;
    }

    public String getChampionKey() {
        return key.toLowerCase();
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
