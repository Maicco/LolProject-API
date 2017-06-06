package com.example.android.leagueoflegends_api;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SetSummonerNameActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_summoner_name);
    }

    public void sendButton(View view)
    {
        Log.v("class:", "click ok");
        Intent intent = new Intent(SetSummonerNameActivity.this, SearchSummonerActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_text_summoner);
        String summonerName = editText.getText().toString();

        //startActivity(intent);
    }
}
