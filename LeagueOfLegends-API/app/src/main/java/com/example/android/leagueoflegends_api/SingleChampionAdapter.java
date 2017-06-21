package com.example.android.leagueoflegends_api;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleChampionAdapter extends ArrayAdapter<SingleChampion>
{
    public SingleChampionAdapter(Activity context, ArrayList<SingleChampion> champion)
    {
        super(context, 0, champion);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_champion_list_item, parent, false);

        SingleChampion currentItem = getItem(position);
        // Champion Title
        TextView title = (TextView) listItemView.findViewById(R.id.sc_title_text_view);
        title.setText(currentItem.getTitle());

        // Champion Image
        ImageView championImage = (ImageView) listItemView.findViewById(R.id.sc_image_view);
        //championImage.setImageResource(R.drawable.annie_full);
        // Exceptions handler
        try
        {
            // Get the Champion Key name and convert it to a resource id to get the image
            int imageId = R.drawable.class.getField(currentItem.getKey()).getInt(null);
            championImage.setImageResource(imageId);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        // Champion Stats
        ProgressBar attack = (ProgressBar) listItemView.findViewById(R.id.sc_attack_progress_bar);
        attack.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.attack_bar_color), PorterDuff.Mode.SRC_IN);
        attack.setProgress(currentItem.getAttack());
        ProgressBar defense = (ProgressBar) listItemView.findViewById(R.id.sc_defense_progress_bar);
        defense.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.defense_bar_color), PorterDuff.Mode.SRC_IN);
        defense.setProgress(currentItem.getDefense());
        ProgressBar magic = (ProgressBar) listItemView.findViewById(R.id.sc_magic_progress_bar);
        magic.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.magic_bar_color), PorterDuff.Mode.SRC_IN);
        magic.setProgress(currentItem.getMagic());
        ProgressBar difficult = (ProgressBar) listItemView.findViewById(R.id.sc_difficult_progress_bar);
        difficult.getProgressDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.difficult_bar_color), PorterDuff.Mode.SRC_IN);
        difficult.setProgress(currentItem.getDifficult());

        // Skill Icons
        ImageView passive = (ImageView) listItemView.findViewById(R.id.sc_skill_passive_image_view);
        passive.setImageResource(R.drawable.annie_passive);
        ImageView skill0 = (ImageView) listItemView.findViewById(R.id.sc_skill_0_image_view);
        skill0.setImageResource(R.drawable.annie_q);
        ImageView skill1 = (ImageView) listItemView.findViewById(R.id.sc_skill_1_image_view);
        skill1.setImageResource(R.drawable.annie_w);
        ImageView skill2 = (ImageView) listItemView.findViewById(R.id.sc_skill_2_image_view);
        skill2.setImageResource(R.drawable.annie_e);
        ImageView skill3 = (ImageView) listItemView.findViewById(R.id.sc_skill_3_image_view);
        skill3.setImageResource(R.drawable.annie_r1);

        // Attributes/tags and champion story
        TextView tags = (TextView) listItemView.findViewById(R.id.sc_tags_text_view);
        tags.setText(currentItem.getTags());
        TextView lore = (TextView) listItemView.findViewById(R.id.sc_lore_text_view);
        lore.setText(currentItem.getLore());

        return listItemView;
    }
}
