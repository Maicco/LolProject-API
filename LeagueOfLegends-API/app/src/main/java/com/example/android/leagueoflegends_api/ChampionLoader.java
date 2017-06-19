package com.example.android.leagueoflegends_api;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ChampionLoader extends AsyncTaskLoader<List<Champion>>
{
    private static final String LOG_TAG = ChampionLoader.class.getSimpleName();
    private String mChampionUrl;

    public ChampionLoader(Context context, String championUrl)
    {
        super(context);
        mChampionUrl = championUrl;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<Champion> loadInBackground()
    {
         if (mChampionUrl == null)
             return null;

        List<Champion> champions = fetchChampionData(mChampionUrl);

        return champions;
    }

    public static List<Champion> fetchChampionData(String championUrl)
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Get the URL
        URL url = HttpConnectionUtils.createUrl(championUrl);
        String jsonResponse = null;

        // Get the JSON from the server
        try
        {
            jsonResponse = HttpConnectionUtils.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request (Champion data). ", e);
        }

        List<Champion> champions = extractFeatureFromJson(jsonResponse);



        return champions;
    }

    private static List<Champion> extractFeatureFromJson(String jsonResponse)
    {
        if(TextUtils.isEmpty(jsonResponse))
            return null;

        List<Champion> champions = new ArrayList<>();

        try
        {
            //Read the JSON
            JsonElement root = new JsonParser().parse(jsonResponse);

            //Get the content of the first map
            JsonObject object = root.getAsJsonObject().get("data").getAsJsonObject();

            //Iterate over this map
            Gson gson = new Gson();
            for (Map.Entry<String, JsonElement> entry : object.entrySet())
            {
                Champion champion = gson.fromJson(entry.getValue(), Champion.class);
                champions.add(champion);
            }
        }
        catch (JsonIOException e)
        {
            Log.e(LOG_TAG, "Problem parsing the ChampionData JSON results. ", e);
        }

        //noinspection Since15
        champions.sort(new Comparator<Champion>() {
            @Override
            public int compare(Champion c1, Champion c2) {
                return c1.getChampionKey().compareTo(c2.getChampionKey());
            }
        });

        return champions;
    }
}