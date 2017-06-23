package com.example.android.leagueoflegends_api;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to show information about a single champion.
 */
public class SingleChampionActivity extends AppCompatActivity implements LoaderCallbacks<List<SingleChampion>>
{
    private String singleChampionUrl = "https://br1.api.riotgames.com/lol/static-data/v3/champions/" + ChampionsListActivity.champion.getChampionId() + "?locale=en_US&tags=all&api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";
    private SingleChampionAdapter mAdapter;
    private static final int SINGLE_CHAMPION_LOADER_ID = 1;
    private static final String LOG_TAG = SingleChampionActivity.class.getSimpleName();
    private TextView mEmptyStateTextView;
    private ProgressBar mLoadingSpinner;
    // Used on toast
    private Toast toast;
    TextView passiveNameToast;
    TextView passiveDescriptionToast;
    TextView cooldownToast;
    TextView costToast;
    LinearLayout cooldownLinearLayout;
    LinearLayout costLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_champion);

        /**** Custom Toast ****/
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        passiveNameToast = (TextView) layout.findViewById(R.id.toast_passive_name_text_view);
        passiveDescriptionToast = (TextView) layout.findViewById(R.id.toast_passive_description_text_view);
        cooldownToast = (TextView) layout.findViewById(R.id.toast_cooldown_text_view);
        costToast = (TextView) layout.findViewById(R.id.toast_cost_text_view);
        cooldownLinearLayout = (LinearLayout) layout.findViewById(R.id.toast_cooldown_linear_layout);
        costLinearLayout = (LinearLayout) layout.findViewById(R.id.toast_cost_linear_layout);

        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        /********************************/

        mAdapter = new SingleChampionAdapter(this, new ArrayList<SingleChampion>());

        ListView listView = (ListView) findViewById(R.id.single_champion_list_view);
        listView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.single_champion_empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        mLoadingSpinner = (ProgressBar) findViewById(R.id.single_champion_loading_spinner);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
        {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(SINGLE_CHAMPION_LOADER_ID, null, this);
        }
        else
        {
            mLoadingSpinner.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Log.v(SingleChampionActivity.class.getSimpleName(), "Position: "+ position);
                singleChampion = mAdapter.getItem(position);
                Toast.makeText(SingleChampionActivity.this, ""+singleChampion.getPassiveName()+"\n"+singleChampion.getPassiveDescription(), Toast.LENGTH_SHORT).show();
            }
        });*/
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

        if (data != null && !data.isEmpty())
            mAdapter.addAll(data);

        // Set the activity label
        setTitle(mAdapter.getItem(0).getName());
    }

    @Override
    public void onLoaderReset(Loader<List<SingleChampion>> loader)
    {
        mAdapter.clear();
    }

    public void passiveToast(View view)
    {
        String passiveName = mAdapter.getItem(0).getPassiveName();
        String passiveDescription = mAdapter.getItem(0).getPassiveDescription();

        passiveNameToast.setText(passiveName);
        passiveDescriptionToast.setText(passiveDescription);
        cooldownLinearLayout.setVisibility(View.GONE);
        costLinearLayout.setVisibility(View.GONE);

        toast.show();
    }

    public void skill0Toast(View view)
    {
        String skillName = mAdapter.getItem(0).getSkillName0();
        String skillDescription = mAdapter.getItem(0).getSkillDescription0();
        String cooldown = mAdapter.getItem(0).getCooldownBurn0() + " s";
        String cost = mAdapter.getItem(0).getCostBurn0();

        passiveNameToast.setText(skillName);
        passiveDescriptionToast.setText(skillDescription);
        cooldownToast.setText(cooldown);
        costToast.setText(cost);
        cooldownLinearLayout.setVisibility(View.VISIBLE);
        costLinearLayout.setVisibility(View.VISIBLE);

        toast.show();
    }

    public void skill1Toast(View view)
    {
        String skillName = mAdapter.getItem(0).getSkillName1();
        String skillDescription = mAdapter.getItem(0).getSkillDescription1();
        String cooldown = mAdapter.getItem(0).getCooldownBurn1() + " s";
        String cost = mAdapter.getItem(0).getCostBurn1();

        passiveNameToast.setText(skillName);
        passiveDescriptionToast.setText(skillDescription);
        cooldownToast.setText(cooldown);
        costToast.setText(cost);
        cooldownLinearLayout.setVisibility(View.VISIBLE);
        costLinearLayout.setVisibility(View.VISIBLE);

        toast.show();
    }

    public void skill2Toast(View view)
    {
        String skillName = mAdapter.getItem(0).getSkillName2();
        String skillDescription = mAdapter.getItem(0).getSkillDescription2();
        String cooldown = mAdapter.getItem(0).getCooldownBurn2() + " s";
        String cost = mAdapter.getItem(0).getCostBurn2();

        passiveNameToast.setText(skillName);
        passiveDescriptionToast.setText(skillDescription);
        cooldownToast.setText(cooldown);
        costToast.setText(cost);
        cooldownLinearLayout.setVisibility(View.VISIBLE);
        costLinearLayout.setVisibility(View.VISIBLE);

        toast.show();
    }

    public void skill3Toast(View view)
    {
        String skillName = mAdapter.getItem(0).getSkillName3();
        String skillDescription = mAdapter.getItem(0).getSkillDescription3();
        String cooldown = mAdapter.getItem(0).getCooldownBurn3() + " s";
        String cost = mAdapter.getItem(0).getCostBurn3();

        passiveNameToast.setText(skillName);
        passiveDescriptionToast.setText(skillDescription);
        cooldownToast.setText(cooldown);
        costToast.setText(cost);
        cooldownLinearLayout.setVisibility(View.VISIBLE);
        costLinearLayout.setVisibility(View.VISIBLE);

        toast.show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        toast.cancel();
    }
}