package com.example.android.leagueoflegends_api;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SummonerLoader extends AsyncTaskLoader<List<SummonerInfo>>
{
    private static final String LOG_TAG = SummonerLoader.class.getSimpleName();
    private String mSummonerIdUrl;
    private static String summonerInfoUrl_part1 = "https://br1.api.riotgames.com/lol/league/v3/positions/by-summoner/";
    private static String summonerInfoUrl_part2 = "?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";

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

        List<SummonerInfo> summoners = fetchSummonerData(mSummonerIdUrl);

        return summoners;
    }

    /**
     * Method called by the {@link SummonerLoader} class to retrieve the data info
     * @param summonerIdUrl is the url with the summoner name, that is used to retrieve the summoner id
     * @return an {@link List<SummonerInfo>} object
     */
    public static List<SummonerInfo> fetchSummonerData (String summonerIdUrl)
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        /**
         * Request the summoner ID to request the data info
         */
        URL url = HttpConnectionUtils.createUrl(summonerIdUrl);

        String jsonResponse = null;

        try
        {
            jsonResponse = HttpConnectionUtils.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request (Summoner ID). ", e);
        }

        String summonerId = extractSummonerId(jsonResponse);

        String summonerInfoUrl = summonerInfoUrl_part1 + summonerId + summonerInfoUrl_part2;
        Log.v(LOG_TAG, "SummonerInfoUrl: " + summonerInfoUrl);

        /**
         * Request the summoner data info
         */
        jsonResponse = null;
        url = HttpConnectionUtils.createUrl(summonerInfoUrl);

        try
        {
            jsonResponse = HttpConnectionUtils.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request (Summoner data info). ", e);
        }

        List<SummonerInfo> summoner = extractSummonerInfo(jsonResponse);

        return summoner;
    }

    private static String extractSummonerId (String summonerIdJson)
    {
        if (TextUtils.isEmpty(summonerIdJson))
            return null;

        String summonerId = "";

        try
        {
            JSONObject summonerObject = new JSONObject(summonerIdJson);
            summonerId = summonerObject.getString("id");
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the Summoner ID JSON results.", e);
        }

        return summonerId;
    }

    private static List<SummonerInfo> extractSummonerInfo(String summonerInfoJson)
    {
        if (TextUtils.isEmpty(summonerInfoJson))
            return null;

        List<SummonerInfo> summoners = new ArrayList<>();

        try
        {
            JSONArray summonerInfoArray= new JSONArray(summonerInfoJson);

            String[] tier = new String[3], rank = new String[3];
            String playerId, playerName;
            int[] pdl = new int[3], wins = new int[3], losses = new int[3];

            JSONObject jsonObject = summonerInfoArray.getJSONObject(0);
            playerId = jsonObject.getString("playerOrTeamId");
            playerName = jsonObject.getString("playerOrTeamName");

            // Check if the JsonArray is equals 3, else initializes the position 2 for all arrays to be default.
            // Because accordingly to the summonerId, the JSON result can return an JSON Array which the length is 2.
            if(summonerInfoArray.length() == 3)
            {
                for (int i = 0; i < summonerInfoArray.length(); i++)
                {
                    JSONObject currentSummonerInfo = summonerInfoArray.getJSONObject(i);

                    tier[i] = currentSummonerInfo.getString("tier");
                    rank[i] = currentSummonerInfo.getString("rank");
                    pdl[i] = currentSummonerInfo.getInt("leaguePoints");
                    wins[i] = currentSummonerInfo.getInt("wins");
                    losses[i] = currentSummonerInfo.getInt("losses");
                }
            }
            else
            {
                for (int i = 0; i < summonerInfoArray.length(); i++)
                {
                    JSONObject currentSummonerInfo = summonerInfoArray.getJSONObject(i);

                    tier[i] = currentSummonerInfo.getString("tier");
                    rank[i] = currentSummonerInfo.getString("rank");
                    pdl[i] = currentSummonerInfo.getInt("leaguePoints");
                    wins[i] = currentSummonerInfo.getInt("wins");
                    losses[i] = currentSummonerInfo.getInt("losses");
                }

                tier[2] = "Unranked";
                rank[2] = "";
                pdl[2] = 0;
                wins[2] = 0;
                losses[2] = 0;
            }

            SummonerInfo summonerInfo = new SummonerInfo(tier, rank, playerId, playerName, pdl, wins, losses);
            summoners.add(summonerInfo);
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the Summoner Data Info JSON results.", e);
        }

        return summoners;
    }
}
