package com.example.android.leagueoflegends_api;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SummonerAdapter extends ArrayAdapter<SummonerInfo>
{
    private static final String LOG_TAG = SummonerAdapter.class.getSimpleName();

    /**
     * Constructs the adapter
     * @param context is the activity who requests the adapter
     * @param summoners is the {@link SummonerInfo} object
     */
    public SummonerAdapter(Activity context, ArrayList<SummonerInfo> summoners)
    {
        super(context, 0, summoners);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Create the listItemView and check if it already exists
        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.summoner_info_list_item, parent, false);

        // Create the {@link SummonerInfo} object to handle its data for each item
        SummonerInfo currentItem = getItem(position);

        // Create the views to set the text to the views at {@layout summoner_info_list_item}
        TextView queueName = (TextView) listItemView.findViewById(R.id.queue_name_text_view);
        queueName.setText(currentItem.getQueueType());
        TextView leagueName = (TextView) listItemView.findViewById(R.id.league_name_text_view);
        leagueName.setText(currentItem.getLeagueName());
        TextView tierName = (TextView) listItemView.findViewById(R.id.tier_name_text_view);
        tierName.setText(currentItem.getTier());
        TextView tierRank = (TextView) listItemView.findViewById(R.id.tier_level_text_view);
        tierRank.setText(currentItem.getTierRank());
        TextView pdl = (TextView) listItemView.findViewById(R.id.pdl_text_view);
        pdl.setText(Integer.toString(currentItem.getPDL()));
        TextView wins = (TextView) listItemView.findViewById(R.id.wins_number_text_view);
        wins.setText(Integer.toString(currentItem.getWins()));
        TextView losses = (TextView) listItemView.findViewById(R.id.losses_number_text_view);
        losses.setText(Integer.toString(currentItem.getLosses()));

        return listItemView;
    }
}
