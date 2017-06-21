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

public class SingleChampionActivity extends AppCompatActivity implements LoaderCallbacks<List<SingleChampion>>
{
    private String singleChampionUrl = "https://br1.api.riotgames.com/lol/static-data/v3/champions/"+ ChampionsListActivity.champion.getChampionId() +"?locale=en_US&tags=all&api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";
    private SingleChampionAdapter mAdapter;
    private static final int SINGLE_CHAMPION_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_champion);

        mAdapter = new SingleChampionAdapter(this, new ArrayList<SingleChampion>());

        ListView listView = (ListView) findViewById(R.id.single_champion_list_view);
        listView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.single_champion_empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        mLoadingSpinner = (ProgressBar) findViewById(R.id.single_champion_loading_spinner);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(SINGLE_CHAMPION_LOADER_ID, null, this);
        }
        else
        {
            mLoadingSpinner.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                SingleChampion sc = mAdapter.getItem(position);
            }
        });
    }

    @Override
    public Loader<List<SingleChampion>> onCreateLoader(int id, Bundle args)
    {
        return new SingleChampionLoader(this, singleChampionUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<SingleChampion>> loader, List<SingleChampion> data)
    {
        mLoadingSpinner.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_data_received);
        mAdapter.clear();

        if(data != null && !data.isEmpty())
            mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset (Loader<List<SingleChampion>> loader)
    {
        mAdapter.clear();
    }
}