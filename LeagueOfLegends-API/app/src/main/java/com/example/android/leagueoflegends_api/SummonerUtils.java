package com.example.android.leagueoflegends_api;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SummonerUtils
{
    private static final String LOG_TAG = SummonerUtils.class.getSimpleName();
    private static String summonerInfoUrl_part1 = "https://br1.api.riotgames.com/lol/league/v3/positions/by-summoner/";
    private static String summonerInfoUrl_part2 = "?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";

    private SummonerUtils(){}

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
        URL url = createUrl(summonerIdUrl);

        String jsonResponse = null;

        try
        {
            jsonResponse = makeHttpRequest(url);
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
        url = createUrl(summonerInfoUrl);

        try
        {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request (Summoner data info). ", e);
        }

        List<SummonerInfo> summoner = extractSummonerInfo(jsonResponse);

        return summoner;
    }

    /**
     * Creates the url to request the data from the website
     * @param stringUrl is the URL received
     * @return an URL object from the URL received
     */
    private static URL createUrl (String stringUrl)
    {
        URL url = null;
        try
        {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

    /**
     * Establish the "connection" with the website to receive the data
     * @param url is the URL received on the method's call
     * @return a String that contains the data received
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the summoner data info. ", e);
        }
        finally
        {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    /**
     * Convert a {@link InputStream} in a String that contains all JSON answer from the server.
     * @param inputStream is the codefied data (in binary)
     * @return the decodefied {@link InputStream} on a String
     * @throws IOException
     */
    private static String readFromStream (InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();

        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(("UTF-8")));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null)
            {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
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

            for (int i=0; i<summonerInfoArray.length(); i++)
            {
                JSONObject currentSummonerInfo = summonerInfoArray.getJSONObject(i);

                String leagueName = currentSummonerInfo.getString("leagueName");
                String tier = currentSummonerInfo.getString("tier");
                String queueType = currentSummonerInfo.getString("queueType");
                String rank = currentSummonerInfo.getString("rank");
                String playerId = currentSummonerInfo.getString("playerOrTeamId");
                String playerName = currentSummonerInfo.getString("playerOrTeamName");
                int pdl = currentSummonerInfo.getInt("leaguePoints");
                int wins = currentSummonerInfo.getInt("wins");
                int losses = currentSummonerInfo.getInt("losses");

                SummonerInfo summonerInfo = new SummonerInfo(leagueName, tier, queueType, rank, playerId, playerName, pdl, wins, losses);

                summoners.add(summonerInfo);
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the Summoner Data Info JSON results.", e);
        }

        return summoners;
    }
}
