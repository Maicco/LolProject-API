package com.example.android.leagueoflegends_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the TextView object
        TextView serverStatus = (TextView) findViewById(R.id.server_status);
        // Create the ClickListener for the TextView objects
        serverStatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent serverStatusIntent = new Intent(MainActivity.this, ServerStatusActivity.class);
                startActivity(serverStatusIntent);
            }
        });

        TextView summonerName = (TextView) findViewById(R.id.summoner);
        summonerName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent summonerNameIntent = new Intent(MainActivity.this, SetSummonerNameActivity.class);
                startActivity(summonerNameIntent);
            }
        });

        TextView champions = (TextView) findViewById(R.id.champions);
        champions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent championsIntent = new Intent(MainActivity.this, ChampionsListActivity.class);
                startActivity(championsIntent);
            }
        });
    }
}
