package com.example.android.leagueoflegends_api;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ChampionListAdapter extends ArrayAdapter<Champion>
{
    private static String LOG_TAG = ChampionListAdapter.class.getSimpleName();

    public ChampionListAdapter(Activity context, ArrayList<Champion> champions)
    {
        super(context, 0, champions);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if (listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.champion_list_item, parent, false);

        Champion currentItem = getItem(position);

        ImageView championIcon = (ImageView) listItemView.findViewById(R.id.icon_image_view);

        try
        {
            int imageId = R.drawable.class.getField(currentItem.getChampionKey()).getInt(null);
            championIcon.setImageResource(imageId);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        TextView championName = (TextView) listItemView.findViewById(R.id.champion_name_text_view);
        championName.setText(currentItem.getChampionName());
        TextView championTitle = (TextView) listItemView.findViewById(R.id.champion_title_text_view);
        championTitle.setText(currentItem.getChampionTitle());
        TextView championTags = (TextView) listItemView.findViewById(R.id.champion_tag_text_view);
        championTags.setText(Arrays.toString(currentItem.getChampionTags().toArray()));

        return listItemView;
    }*/
}
