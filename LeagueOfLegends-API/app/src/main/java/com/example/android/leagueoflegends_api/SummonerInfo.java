package com.example.android.leagueoflegends_api;

public class SummonerInfo
{
    private String[] mTier = new String[3], mTierRank = new String[3];
    private String mPlayerId, mPlayerName;
    private int[] mPDL = new int[3], mWins = new int[3], mLosses = new int[3];

    /**
     * Constructs a new {@link SummonerInfo} object.
     */
    public SummonerInfo(String[] tier, String[] tierRank, String playerId, String playerName, int[] pdl, int[] wins, int[] losses)
    {
        mTier = tier;
        mTierRank = tierRank;
        mPlayerId = playerId;
        mPlayerName = playerName;
        mPDL = pdl;
        mWins = wins;
        mLosses = losses;
    }

    public String[] getTier() {
        return mTier;
    }

    public String[] getTierRank() {
        return mTierRank;
    }

    public String getPlayerId() {
        return mPlayerId;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public int[] getPDL() {
        return mPDL;
    }

    public int[] getWins() {
        return mWins;
    }

    public int[] getLosses() {
        return mLosses;
    }
}
