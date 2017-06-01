package com.example.android.leagueoflegends_api;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChampionsUtils
{
    private static final String LOG_TAG = ChampionsUtils.class.getSimpleName();

    /**
     * https://developer.android.com/reference/android/util/JsonReader.html
     */
    // Create a private constructor because no one can create a ChampionsUtils object
    private ChampionsUtils()
    {

    }

    /**
     * This access all methods to return the {@List Champions}
     */
    public static List<Champions> fetchChampionsData(String requestUrl)
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Creates the URL
        URL url = createUrl (requestUrl);
        // Stores the Riot server response
        List<Champions> champions = null;
        try
        {
            champions = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request ", e);
        }

        return champions;
    }

    /**
     * Creates the URL to request the data
     */
    private static URL createUrl (String stringURL)
    {
        URL url = null;
        try
        {
            url = new URL(stringURL);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

    private static List<Champions> makeHttpRequest(URL url) throws IOException
    {
        List<Champions> jsonResponse = null;

        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try
        {
            // Set up the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readJsonStream(inputStream);
            }
            else
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the JSON result ", e);
        }
        // At the end of retrieving json response
        finally
        {
            if (urlConnection != null)
                urlConnection.disconnect();
            if(inputStream !=  null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static List<Champions> readJsonStream(InputStream inputStream) throws IOException
    {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

        try
        {
            return readChampionArray(jsonReader);
        }
        finally
        {
            jsonReader.close();
        }
    }

    private static List<Champions> readChampionArray(JsonReader jsonReader) throws IOException
    {
        List<Champions> champions = new ArrayList<Champions>();

        jsonReader.beginObject();
        while (jsonReader.hasNext())
        {
            champions.add(readChampion(jsonReader));
        }
        jsonReader.endObject();

        Log.v(LOG_TAG, "Champion Array size: " + champions.size());

        return champions;
    }

    private static Champions readChampion(JsonReader jsonReader) throws IOException
    {
        Champions singleChampion = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext())
        {
            String name = jsonReader.nextName();

            if(name.equals("data"))
                singleChampion = getChampionInfo(jsonReader);
            else
                jsonReader.skipValue();
        }
        jsonReader.endObject();

        return new Champions(singleChampion.getChampionId(), singleChampion.getChampionKey(), singleChampion.getChampionName(),
                singleChampion.getChampionTitle());
    }

    private static Champions getChampionInfo(JsonReader jsonReader) throws IOException
    {
        int championId = -1;
        String championKey = null, championName = null, championTitle = null;

        jsonReader.beginObject();
        while (jsonReader.hasNext())
        {
            String name = jsonReader.nextName();

            if(name.equals("id"))
                championId = jsonReader.nextInt();
            else if(name.equals("key"))
                championKey = jsonReader.nextString();
            else if(name.equals("name"))
                championName = jsonReader.nextString();
            else if(name.equals("title"))
                championTitle = jsonReader.nextString();
            else
                jsonReader.skipValue();
        }
        jsonReader.endObject();

        return new Champions(championId, championKey, championName, championTitle);
    }
}