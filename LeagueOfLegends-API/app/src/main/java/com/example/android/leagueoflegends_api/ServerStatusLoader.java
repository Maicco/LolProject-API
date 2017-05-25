package com.example.android.leagueoflegends_api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ServerStatusLoader extends AsyncTaskLoader<List<ServerStatus>>
{
    private String mUrl;

    public ServerStatusLoader(Context context, String url)
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
    public List<ServerStatus> loadInBackground()
    {
        if(mUrl == null)
            return null;

        // Makes a network request, then decodefies the answer, and extracts an StatusServer list
        List<ServerStatus> status = ServerStatusUtils.fetchServerStatusData(mUrl);
        return status;
    }
}
