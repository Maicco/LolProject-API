package com.example.android.leagueoflegends_api;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchSummonerActivity extends AppCompatActivity implements LoaderCallbacks<List<SummonerInfo>>
{
    private static final String LOG_TAG = SearchSummonerActivity.class.getSimpleName();

    private String summonerName = SetSummonerNameActivity.getSummonerName();
    private String summonerIdUrl = "https://br1.api.riotgames.com/lol/summoner/v3/summoners/by-name/"+summonerName+"?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";

    private SummonerAdapter mAdapter;
    private static final int SUMMONER_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_search);
        Log.v(LOG_TAG, "summonerName: "+summonerName);

        // Instantiate the adapter
        mAdapter = new SummonerAdapter(this, new ArrayList<SummonerInfo>());
        // Creates the ListView and set the adapter to it
        ListView listView = (ListView) findViewById(R.id.summoner_list_view);
        listView.setAdapter(mAdapter);
        // Create the empty TextView and set it to be empty
        mEmptyStateTextView = (TextView) findViewById(R.id.summoner_empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        // Create the loading spinner ProgressBar
        mLoadingSpinner = (ProgressBar) findViewById(R.id.summoner_loading_spinner);

        /**
         * Check the internet Status
         */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            // Get a reference to LoaderManager, to interact to other loaders
            LoaderManager loaderManager = getLoaderManager();
            // Start the loader. Give an ID and a null handle.
            // Pass this activity to the LoaderCallback parameter
            // (which is valid because this activity implements a LoaderCallback interface)
            loaderManager.initLoader(SUMMONER_LOADER_ID, null, this);
        }
        else
        {
            mLoadingSpinner.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        // This handle the click listener for each item at the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                SummonerInfo summoner = mAdapter.getItem(position);
            }
        });
    }

    @Override
    public Loader<List<SummonerInfo>> onCreateLoader(int id, Bundle args)
    {
        return new SummonerLoader(this, summonerIdUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<SummonerInfo>> loader, List<SummonerInfo> data)
    {
        mLoadingSpinner.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_data_received);
        mAdapter.clear();

        if(data != null && !data.isEmpty())
            mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<SummonerInfo>> loader)
    {
        mAdapter.clear();
    }
}
