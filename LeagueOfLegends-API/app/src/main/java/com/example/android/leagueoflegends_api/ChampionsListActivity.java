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
import java.util.Comparator;
import java.util.List;

/**
 * The Champion List Activity itself
 */

public class ChampionsListActivity extends AppCompatActivity implements LoaderCallbacks<List<Champion>>
{
    private static final String LOG_TAG = ChampionsListActivity.class.getSimpleName();
    private String championUrl = "https://br1.api.riotgames.com/lol/static-data/v3/champions?champListData=tags&tags=info&dataById=true&api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";
    private ChampionListAdapter mAdapter;
    private static final int SUMMONER_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mAdapter = new ChampionListAdapter(this, new ArrayList<Champion>());
        mAdapter.sort(new Comparator<Champion>() {
            @Override
            public int compare(Champion c1, Champion c2) {
                return c1.getChampionKey().compareToIgnoreCase(c2.getChampionKey());
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champions_list);

        ListView listView = (ListView) findViewById(R.id.champions_list_view);
        listView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.champions_empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        mLoadingSpinner = (ProgressBar) findViewById(R.id.champions_loading_spinner);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(SUMMONER_LOADER_ID, null, this);
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
                Champion champion = mAdapter.getItem(position);
            }
        });
    }

    @Override
    public Loader<List<Champion>> onCreateLoader(int id, Bundle args)
    {
        return new ChampionLoader(this, championUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Champion>> loader, List<Champion> data)
    {
        mLoadingSpinner.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_data_received);
        mAdapter.clear();

        if (data != null && !data.isEmpty())
            mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Champion>> loader)
    {
        mAdapter.clear();
    }
}