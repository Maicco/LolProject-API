package com.example.android.leagueoflegends_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private static final String API_KEY = "?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";
    private static final String SERVER_STATUS_URL = "https://br1.api.riotgames.com/lol/status/v3/shard-data?api_key=RGAPI-12f35902-57c8-4cd9-aa2e-ba2f18283cd6";

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
    }
}
