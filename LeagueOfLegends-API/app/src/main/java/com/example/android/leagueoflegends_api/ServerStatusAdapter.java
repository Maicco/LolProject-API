package com.example.android.leagueoflegends_api;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ServerStatusAdapter extends ArrayAdapter<ServerStatus>
{
    public ServerStatusAdapter(Activity context, ArrayList<ServerStatus> status)
    {
        super(context, 0, status);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from((getContext())).inflate(R.layout.server_status_list_item, parent, false);

        // Select the item at the List View
        ServerStatus currentItem = getItem(position);

        // Create a TextView object to get and set the circle status settings
        TextView statusCircleTextView = (TextView) listItemView.findViewById(R.id.status_circle);
        // Get the circle status draw
        GradientDrawable statusCircle = (GradientDrawable) statusCircleTextView.getBackground();
        // Get and set the color accordingly to the server status
        int statusColor = getStatusColor(currentItem.getServerStatus());
        statusCircle.setColor(statusColor);

        // Create the TextViews that shows the server region tag and for which server the status is showing
        TextView regionTag = (TextView) listItemView.findViewById(R.id.region_tag);
        regionTag.setText(currentItem.getRegionTag());
        TextView serverApplication = (TextView) listItemView.findViewById(R.id.server_application);
        serverApplication.setText(currentItem.getServerAppName());
        // Create the TextView to show the incident message information
        TextView messageContent = (TextView) listItemView.findViewById(R.id.message_content);
        messageContent.setText(currentItem.getMessageContent());

        return listItemView;
    }

    /**
     * Get the right color for each server status
     * @param status is the current server status
     * @return the color to set as background at the status circle
     */
    private int getStatusColor(String status)
    {
        int statusColorResourceId = R.color.status_warning;

        if(status.equalsIgnoreCase(getContext().getString(R.string.online)))
            statusColorResourceId = R.color.status_online;
        if(status.equalsIgnoreCase(getContext().getString(R.string.offline)))
            statusColorResourceId = R.color.status_offline;

        return ContextCompat.getColor(getContext(), statusColorResourceId);
    }
}