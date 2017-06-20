package com.example.android.leagueoflegends_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetSummonerNameActivity extends AppCompatActivity
{
    private static String summonerName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_summoner_name);
    }

    public void sendButton(View view)
    {
        Log.v("class:", "click ok");

        EditText editText = (EditText) findViewById(R.id.edit_text_summoner);
        summonerName = editText.getText().toString();

        if(summonerName.isEmpty())
            Toast.makeText(this, "Insert a summoner name!", Toast.LENGTH_SHORT).show();
        else
        {
            Intent intent = new Intent(SetSummonerNameActivity.this, SearchSummonerActivity.class);
            startActivity(intent);
        }
    }

    public static String getSummonerName()
    {
        return summonerName;
    }
}