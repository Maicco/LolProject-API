package com.example.android.leagueoflegends_api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class SummonerLoader extends AsyncTaskLoader<List<SummonerInfo>>
{
    private String mSummonerIdUrl;

    public SummonerLoader(Context context, String summonerIdUrl)
    {
        super(context);
        mSummonerIdUrl = summonerIdUrl;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<SummonerInfo> loadInBackground()
    {
        if(mSummonerIdUrl == null)
            return null;

        List<SummonerInfo> summoners = SummonerUtils.fetchSummonerData(mSummonerIdUrl);

        return summoners;
    }
}
