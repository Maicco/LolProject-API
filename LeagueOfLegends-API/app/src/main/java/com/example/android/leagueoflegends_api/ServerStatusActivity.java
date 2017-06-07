package com.example.android.leagueoflegends_api;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ServerStatusActivity extends AppCompatActivity implements LoaderCallbacks<List<ServerStatus>>
{
    //private static final String API_KEY = "?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";
    private static final String SERVER_STATUS_URL = "https://br1.api.riotgames.com/lol/status/v3/shard-data?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";
//    private static final String SERVER_STATUS_URL = "https://br1.api.riotgames.com";
//    private static final String SERVER_QUERY_SETTINGS = "/lol/status/v3/shard-data";
    private ServerStatusAdapter mAdapter;
    private static final int SERVER_STATUS_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_status);

        // Instantiate the adapter
        mAdapter = new ServerStatusAdapter(this, new ArrayList<ServerStatus>());

        // Creates the ListView and set the adapter to it
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(mAdapter);

        // Create the empty TextView and set it to be empty
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        // Create the loading spinner ProgressBar
        mLoadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);

        /**
         * Check the internet status
         */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            // Get a reference to LoaderManager, to interact to other loaders
            LoaderManager loaderManager = getLoaderManager();

            // Start the loader.
            // Give an ID and a null handle.
            // Pass this activity to the LoaderCallback parameter (which is valid because this activity implements a LoaderCallback interface)
            loaderManager.initLoader(SERVER_STATUS_LOADER_ID, null, this);
        }
        else
        {
            mLoadingSpinner.setVisibility(View.GONE); // If there is no internet connection, then set the loading spinner visibility to GONE
            mEmptyStateTextView.setText(R.string.no_internet_connection); // Shows the message that there is no internet connection
        }

        /**
         * This handle the click listener for each item at the list view
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                // Get the current item at the list
                ServerStatus status = mAdapter.getItem(position);

                // TODO
            }
        });
    }

    /**
     * This creates the URL to request the data from the Riot Server
     * @return the {@link ServerStatusLoader} object
     */
    @Override
    public Loader<List<ServerStatus>> onCreateLoader(int id, Bundle args)
    {
        return new ServerStatusLoader(this, SERVER_STATUS_URL);
    }

    @Override
    public void onLoadFinished (Loader<List<ServerStatus>> loader, List<ServerStatus> data)
    {
        // Set the progress spinner to GONE
        mLoadingSpinner.setVisibility(View.GONE);
        // Set the empty text to show a "No data received" message
        mEmptyStateTextView.setText(R.string.no_data_received);
        // Clear the old data from the ServerStatus list
        mAdapter.clear();

        // If there is a valid list of {@link ServerStatus}, then add them to the mAdapter
        // This will call the ListView to be up-to-date
        if(data != null && !data.isEmpty())
            mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset (Loader<List<ServerStatus>> loader)
    {
        mAdapter.clear();
    }
}