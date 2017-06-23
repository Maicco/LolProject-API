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

public class SingleChampionLoader extends AsyncTaskLoader<List<SingleChampion>>
{
    private static final String LOG_TAG = SingleChampionLoader.class.getSimpleName();
    private String singleChampionUrl;

    public SingleChampionLoader(Context context, String singleChampionUrl)
    {
        super(context);
        this.singleChampionUrl = singleChampionUrl;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<SingleChampion> loadInBackground()
    {
        if(singleChampionUrl == null)
            return null;

        List<SingleChampion> singleChampion = fetchSingleChampionData(singleChampionUrl);

        return singleChampion;
    }

    public static List<SingleChampion> fetchSingleChampionData(String championUrl)
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        URL url = HttpConnectionUtils.createUrl(championUrl);
        String jsonResponse = null;

        try
        {
            jsonResponse = HttpConnectionUtils.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request (SingleChampion). ", e);
        }

        List<SingleChampion> singleChampion = extractFeaturesFromJson(jsonResponse);

        return singleChampion;
    }

    private static List<SingleChampion> extractFeaturesFromJson(String jsonResponse)
    {
        if(TextUtils.isEmpty(jsonResponse))
            return null;

        List<SingleChampion> singleChampion = new ArrayList<>();

        try
        {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            String title = jsonObject.getString("title");
            String lore = jsonObject.getString("lore");

            JSONObject info = jsonObject.getJSONObject("info");
            int attack = info.getInt("attack");
            int defense = info.getInt("defense");
            int magic = info.getInt("magic");
            int difficult = info.getInt("difficulty");

            JSONObject passiveObj = jsonObject.getJSONObject("passive");
            String passiveName = passiveObj.getString("name");
            String passiveDescription = passiveObj.getString("sanitizedDescription");

            String tags = jsonObject.getJSONArray("tags").toString();

            String key = jsonObject.getString("key");
            String name = jsonObject.getString("name");

            JSONArray spellsArray = jsonObject.getJSONArray("spells");
            JSONObject spell0 = spellsArray.getJSONObject(0);
            String skillName0 = spell0.getString("name");
            String skillDescription0 = spell0.getString("sanitizedDescription");
            String costBurn0 = spell0.getString("costBurn");
            String cooldownBurn0 = spell0.getString("cooldownBurn");

            JSONObject spell1 = spellsArray.getJSONObject(1);
            String skillName1 = spell1.getString("name");
            String skillDescription1 = spell1.getString("sanitizedDescription");
            String costBurn1 = spell1.getString("costBurn");
            String cooldownBurn1 = spell1.getString("cooldownBurn");

            JSONObject spell2 = spellsArray.getJSONObject(2);
            String skillName2 = spell2.getString("name");
            String skillDescription2 = spell2.getString("sanitizedDescription");
            String costBurn2 = spell2.getString("costBurn");
            String cooldownBurn2 = spell2.getString("cooldownBurn");

            JSONObject spell3 = spellsArray.getJSONObject(3);
            String skillName3 = spell3.getString("name");
            String skillDescription3 = spell3.getString("sanitizedDescription");
            String costBurn3 = spell3.getString("costBurn");
            String cooldownBurn3 = spell3.getString("cooldownBurn");

            singleChampion.add(new SingleChampion(title, attack, defense, magic, difficult, passiveName, passiveDescription,
                    tags, skillName0, skillDescription0, skillName1, skillDescription1, skillName2, skillDescription2,
                    skillName3, skillDescription3, costBurn0, cooldownBurn0, costBurn1, cooldownBurn1, costBurn2, cooldownBurn2,
                    costBurn3, cooldownBurn3, lore, key, name));
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the SingleChampion JSON results. ", e);
        }

        return singleChampion;
    }
}