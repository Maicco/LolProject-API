package com.example.android.leagueoflegends_api;

import android.util.JsonReader;
import android.util.Log;

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

public final class HttpConnectionUtils
{
    private static final String LOG_TAG = HttpConnectionUtils.class.getSimpleName();

    private HttpConnectionUtils(){}

    /**
     * Creates the url to request the data from the website
     * @param stringUrl is the URL received
     * @return an URL object from the URL received
     */
    public static URL createUrl(String stringUrl)
    {
        URL url = null;
        try
        {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building the URL, ", e);
        }

        return url;
    }

    /**
     * Establish the "connection" with the website to receive the data
     * @param url is the URL received on the method's call
     * @return a String that contains the data received
     * @throws IOException
     */
    public static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try
        {
            // Set up the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /*miliseconds*/);
            urlConnection.setConnectTimeout(15000 /* miliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /**
             * If the request was not succeed (response code 200), then read the data entry and decode the answer
             */
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the server JSON results, ", e);
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
     * @param inputStream is the encrypted data (in binary)
     * @return the decrypted {@link InputStream} as a String
     * @throws IOException
     */
    public static String readFromStream (InputStream inputStream) throws IOException
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


    /*******************************************************************************/

    /**
     * Establish the "connection" with the website to receive the data
     * @param url is the URL received on the method's call
     * @return a String that contains the data received
     * @throws IOException
     */
    public static List<Champion> makeHttpRequestJsonReader(URL url) throws IOException
    {
        List<Champion> champions = new ArrayList<>();

        // If the URL is null, then return early.
        if(url == null)
            return champions;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try
        {
            // Set up the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /*miliseconds*/);
            urlConnection.setConnectTimeout(15000 /* miliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /**
             * If the request was not succeed (response code 200), then read the data entry and decode the answer
             */
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                inputStream = urlConnection.getInputStream();
                champions = readFromJsonReader(inputStream);
            }
            else
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the server JSON results, ", e);
        }
        finally
        {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }

        return champions;
    }

    public static List<Champion> readFromJsonReader(InputStream inputStream) throws IOException
    {
        JsonReader reader = new JsonReader (new InputStreamReader(inputStream, "UTF-8"));
        try
        {
            return readArrayData(reader);
        }
        finally
        {
            reader.close();
        }
    }

    public static List<Champion> readArrayData(JsonReader reader) throws IOException
    {
        List<Champion> champions = new ArrayList<>();
        JSONObject dataJson = null;

        reader.beginObject();
        while (reader.hasNext())
        {
            String name = reader.nextName();
        }
        reader.endObject();

        return champions;
    }
}