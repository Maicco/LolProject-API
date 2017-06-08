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

public class ServerStatusLoader extends AsyncTaskLoader<List<ServerStatus>>
{
    private static final String LOG_TAG = ServerStatusLoader.class.getSimpleName();
    private String mUrl;
    private static boolean isIncidentStatusActive;
    private static String messageContent = "";
    private static String severity;


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
        List<ServerStatus> status = fetchServerStatusData(mUrl);
        return status;
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
        URL url = HttpConnectionUtils.createUrl(requestUrl);

        // Requests an HTTP connection to the URL and receive a JSON answer
        String jsonResponse = null;
        try
        {
            jsonResponse = HttpConnectionUtils.makeHttpRequest(url);
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
                    // Extracts the incident list
                    for (int n = 0; n < incidents.length(); n++)
                    {
                        // Get the current incident from the incidents array
                        JSONObject currentIncident = incidents.getJSONObject(n);
                        isIncidentStatusActive = currentIncident.getBoolean("active"); // Check if the incident status is active

                        // Get the list of updates from the current incident
                        JSONArray updates = currentIncident.getJSONArray("updates");

                        // Check the updates array length
                        if(updates.length() > 0)
                        {
                            for (int j = 0; j < updates.length(); j++)
                            {
                                // Get the current position [j] of current incident from the updates array
                                JSONObject currentUpdate = updates.getJSONObject(j);
                                messageContent += currentUpdate.getString("content") + "\n"; // Get the message content from incident reason
                                severity = currentUpdate.getString("severity"); // Get the incident severity
                            }
                        }
                    }
                }
                else
                {
                    // Clear the variables to not show the same values again
                    isIncidentStatusActive = false;
                    messageContent = "No info";
                    severity = "None";
                }

                // Create an {@link ServerStatus} object with the data received from the serverStatusJSON object
                ServerStatus singleServerStatus = new ServerStatus(regionName, regionTag, serverApplication, statusServerApplication, isIncidentStatusActive, messageContent, severity);

                // Add the {@link singleServerStatus} object to the {@ArrayList status} list
                status.add(singleServerStatus);
                // Clear the variables to not show the same values again
                isIncidentStatusActive = false;
                messageContent = "";
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