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

/**
 * Helper methods related to requesting and receiving server status data from Riot
 */

public final class ServerStatusUtils
{
    private static final String LOG_TAG = ServerStatusUtils.class.getSimpleName();
    private static boolean isIncidentStatusActive;
    private static String messageContent, severity;

    // Create a private constructor because no one can create a ServerStatusUtils object.
    private ServerStatusUtils()
    {

    }

    /**
     * Return a list of {@link ServerStatus} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<ServerStatus> fetchServerStatusData (String requestUrl)
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
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Create a ServerStatus list with the result of the data extraction from jsonResponse
        List<ServerStatus> status = extractFeatureFromJson(jsonResponse);

        // Return the {@link List} status
        return status;
    }

    /**
     * Creates the url to request the data from the website
     * @param stringUrl is the URL received
     * @return a URL object from the URL received
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
            Log.e(LOG_TAG, "Problem bulding the URL ", e);
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

        // If the URL is null, the return early.
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
             * If the request was not succeed (response code 200), then read the data entry and decodefied the answer
             */
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
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
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

    /**
     * Create the JSON objects from the data received.
     * Extracts the information needed from the chosen fields
     * @param serverStatusJSON is the full JSON object received from the Riot server
     * @return the List of {@link ServerStatus} objects
     */
    private static List<ServerStatus> extractFeatureFromJson (String serverStatusJSON)
    {
        // Check if the data received is empty, then return early
        if(TextUtils.isEmpty(serverStatusJSON))
            return null;

        List<ServerStatus> status = new ArrayList<>();

        try
        {
            // Build up a List of ServerStatus objects with the corresponding data
            JSONObject jsonObject = new JSONObject(serverStatusJSON);
            String regionName = jsonObject.getString("name"); // Get the region server name
            String regionTag = jsonObject.getString("region_tag"); // Get the server name region tag

            JSONArray serverStatusArray = jsonObject.getJSONArray("services"); // Extracts the objects from "services"

            // Extract the specific fields from the array
            for(int i=0; i < serverStatusArray.length(); i++)
            {
                // Get the current position [i] of server application from the services array [serverStatusArray]
                JSONObject currentServerStatus = serverStatusArray.getJSONObject(i);
                // Get the fields from currentServerStatus
                String serverApplication = currentServerStatus.getString("name");
                String statusServerApplication = currentServerStatus.getString("status");
                // Get the list of incidents from the currentServerStatus
                JSONArray incidents = currentServerStatus.getJSONArray("incidents");

                // Check the incident array length
                if(incidents.length() > 0)
                {
                    Log.v(LOG_TAG, "True 1 -- " + incidents.length());
                    // Extracts the incident list
                    for (int n = 0; n < incidents.length(); n++)
                    {
                        // Get the current incident from the incidents array
                        JSONObject currentIncident = incidents.getJSONObject(i);
                        isIncidentStatusActive = currentIncident.getBoolean("active"); // Check if the incident status is active

                        // Get the list of updates from the current incident
                        JSONArray updates = currentIncident.getJSONArray("updates");

                        // Check the updates array length
                        if(updates.length() > 0)
                        {
                            Log.v(LOG_TAG, "True 2 -- " + updates.length());
                            for (int j = 0; j < updates.length(); j++)
                            {
                                // Get the current position [j] of current incident from the updates array
                                JSONObject currentUpdate = updates.getJSONObject(i);
                                messageContent = currentUpdate.getString("content"); // Get the message content from incident reason
                                severity = currentUpdate.getString("severity"); // Get the incident severity
                            }
                        }
                    }
                }
                else
                {
                    // Clear the variables to not show the same values again
                    isIncidentStatusActive = false;
                    messageContent = "There are no info.";
                    severity = "None";
                }

                // Create an {@link ServerStatus} object with the data received from the serverStatusJSON object
                ServerStatus singleServerStatus = new ServerStatus(regionName, regionTag, serverApplication, statusServerApplication, isIncidentStatusActive, messageContent, severity);

                // Add the {@link singleServerStatus} object to the {@ArrayList status} list
                status.add(singleServerStatus);
                // Clear the variables to not show the same values again
                isIncidentStatusActive = false;
                messageContent = null;
                severity = null;
            }
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG, "Problem parsing the ServerStatus JSON results.", e);
        }

        return status;
    }
}