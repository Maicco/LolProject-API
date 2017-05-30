package com.example.android.leagueoflegends_api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ChampionsLoader extends AsyncTaskLoader<List<Champions>>
{
    private String mUrl;

    public ChampionsLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<Champions> loadInBackground()
    {
        if(mUrl == null)
            return null;

        // Makes a network request, then decodefies the answer, and extracts an StatusServer list
        List<Champions> champions = ChampionsUtils.fetchChampionsData(mUrl);
        return champions;
    }
}