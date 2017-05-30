package com.example.android.leagueoflegends_api;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChampionsAdapter extends ArrayAdapter<Champions>
{
    private static final String LOG_TAG = ChampionsAdapter.class.getSimpleName();

    public ChampionsAdapter(Activity context, ArrayList<Champions> champions)
    {
        super(context, 0, champions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.champions_list_item, parent, false);

        // Select the item at the List View
        Champions currentItem = getItem(position);

        // Create an ImageView object to get and set the champions images
        ImageView championImage = (ImageView) listItemView.findViewById(R.id.champion_image);
        championImage.setImageResource(getChampionImage(currentItem.getChampionKey()));

        TextView championName = (TextView) listItemView.findViewById(R.id.champion_name);
        championName.setText(currentItem.getChampionName());

        TextView championTitle = (TextView) listItemView.findViewById(R.id.champion_title);
        championTitle.setText(currentItem.getChampionTitle());

        return listItemView;
    }

    private int getChampionImage(String championKey)
    {
        int drawable = Integer.parseInt(getContext().getResources().getDrawable(R.drawable.yasuo).toString());

        return drawable;
    }
}
