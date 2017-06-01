package com.example.android.leagueoflegends_api;

public class Champions
{
    private int mChampionId;
    private String mChampionKey, mChampionName, mChampionTitle;

    /**
     * Constructs a new {@link Champions} object
     * @param championId is the champion ID
     * @param championName is the champion Name
     * @param championTitle is the champion title
     */
    public Champions (int championId, String championKey,  String championName, String championTitle)
    {
        mChampionId = championId;
        mChampionKey = championKey;
        mChampionName = championName;
        mChampionTitle = championTitle;
    }

    public Champions(){}

    public int getChampionId()
    {
        return mChampionId;
    }

    public String getChampionKey()
    {
        return mChampionKey;
    }

    public String getChampionName()
    {
        return mChampionName;
    }

    public String getChampionTitle()
    {
        return mChampionTitle;
    }
}