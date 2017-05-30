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
     * Return a list of {@link Champions} objects that has been built up from parsing a JSON response
     * @param requestUrl is the URL to request the data
     * @return a {@link List} champions object
     */
    public static List<Champions> fetchChampionsData (String requestUrl)
    {
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Create an URL object
        URL url = createUrl(requestUrl);

        // Requests an HTTP connection to the URL and receive a JSON answer
        String jsonResponse = null;
        try
        {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        // Create a Champions list with the result of the data extraction from jsonResponse
        List<Champions> champions = extractFeatureFromJson(jsonResponse);

        // Return the {@link List} champions
        return champions;
    }

    /**
     * Creates the url to request the data from the website
     * @param stringUrl is the URL received
     * @return an URL object from the URL received
     */
    private static URL createUrl(String stringUrl)
    {
        URL url = null;
        try
        {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building the URL", e);
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

        // If the URL is null, then return early
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

            // If the request was not succeed, then read the data entry and decode the answer
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
                Log.e(LOG_TAG, "Error response code: "+ urlConnection.getResponseCode());
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the champions JSON results", e);
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
     * @return the not codify {@link InputStream} on a String
     * @throws IOException
     */
    private static String readFromStream (InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();

        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
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

    /**
     * Create the JSON objects from the data received.
     * Extracts the information needed from the chosen fields
     * @param championsJSON is the full JSON object received from the Riot server
     * @return the List of {@link ServerStatus} objects
     */
    private static List<Champions> extractFeatureFromJson (String championsJSON)
    {
        // Check if the data received is empty, then return early
        if (TextUtils.isEmpty(championsJSON))
            return null;

        List<Champions> champions = new ArrayList<>();

        try
        {
            // Build up a List of Champions objects with the corresponding data
            JSONObject jsonObject = new JSONObject(championsJSON);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            // Extract the specific fields from the array
            for(int i=0; i < dataArray.length(); i++)
            {
                JSONObject currentChampion = dataArray.getJSONObject(i);
                int championId = currentChampion.getInt("id");
                String championKey = currentChampion.getString("key");
                String championName = currentChampion.getString("name");
                String championTitle = currentChampion.getString("title");

                // Create an {@link Champions} object with the data extracted from championsJSON
                Champions champion = new Champions(championId, championKey, championName, championTitle);
                champions.add(champion);
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the Champions JSON results", e);
        }

        return champions;
    }


}