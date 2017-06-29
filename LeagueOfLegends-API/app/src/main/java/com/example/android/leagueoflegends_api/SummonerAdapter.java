package com.example.android.leagueoflegends_api;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        TextView tierName1 = (TextView) listItemView.findViewById(R.id.info_tier1_text_view);
        TextView wins1 = (TextView) listItemView.findViewById(R.id.info_wins1_text_view);
        TextView losses1 = (TextView) listItemView.findViewById(R.id.info_losses1_text_view);
        ImageView tierImage1 = (ImageView) listItemView.findViewById(R.id.info_tier1_image_view); // Tier image
        TextView pdl1 = (TextView) listItemView.findViewById(R.id.info_pdl1_text_view);

        TextView tierName2 = (TextView) listItemView.findViewById(R.id.info_tier2_text_view);
        TextView wins2 = (TextView) listItemView.findViewById(R.id.info_wins2_text_view);
        TextView losses2 = (TextView) listItemView.findViewById(R.id.info_losses2_text_view);
        ImageView tierImage2 = (ImageView) listItemView.findViewById(R.id.info_tier2_image_view); // Tier image
        TextView pdl2 = (TextView) listItemView.findViewById(R.id.info_pdl2_text_view);

        TextView tierName3 = (TextView) listItemView.findViewById(R.id.info_tier3_text_view);
        TextView wins3 = (TextView) listItemView.findViewById(R.id.info_wins3_text_view);
        TextView losses3 = (TextView) listItemView.findViewById(R.id.info_losses3_text_view);
        ImageView tierImage3 = (ImageView) listItemView.findViewById(R.id.info_tier3_image_view); // Tier image
        TextView pdl3 = (TextView) listItemView.findViewById(R.id.info_pdl3_text_view);

        // Attributions
        tierName1.setText(currentItem.getTier()[0]+" "+currentItem.getTierRank()[0]);
        tierName2.setText(currentItem.getTier()[1]+" "+currentItem.getTierRank()[1]);
        tierName3.setText(currentItem.getTier()[2]+" "+currentItem.getTierRank()[2]);
        wins1.setText(String.valueOf(currentItem.getWins()[0]));
        wins2.setText(String.valueOf(currentItem.getWins()[1]));
        wins3.setText(String.valueOf(currentItem.getWins()[2]));
        losses1.setText(String.valueOf(currentItem.getLosses()[0]));
        losses2.setText(String.valueOf(currentItem.getLosses()[1]));
        losses3.setText(String.valueOf(currentItem.getLosses()[2]));
        pdl1.setText(String.valueOf(currentItem.getPDL()[0]));
        pdl2.setText(String.valueOf(currentItem.getPDL()[1]));
        pdl3.setText(String.valueOf(currentItem.getPDL()[2]));

        try
        {
            int imageId = R.drawable.class.getField(currentItem.getTier()[0].toLowerCase()).getInt(null);
            tierImage1.setImageResource(imageId);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        try
        {
            int imageId = R.drawable.class.getField(currentItem.getTier()[1].toLowerCase()).getInt(null);
            tierImage2.setImageResource(imageId);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        try
        {
            int imageId = R.drawable.class.getField(currentItem.getTier()[2].toLowerCase()).getInt(null);
            tierImage3.setImageResource(imageId);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return listItemView;
    }
}
