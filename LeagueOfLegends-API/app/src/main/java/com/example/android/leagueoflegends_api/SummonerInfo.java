package com.example.android.leagueoflegends_api;

public class SummonerInfo
{
    private String mLeagueName, mTier, mQueueType, mTierRank, mPlayerId, mPlayerName;
    private int mPDL, mWins, mLosses;

    /**
     * Constructs a new {@link SummonerInfo} object.
     * @param leagueName
     * @param tier
     * @param queueType
     * @param tierRank
     * @param playerId
     * @param playerName
     * @param pdl
     * @param wins
     * @param losses
     */
    public SummonerInfo(String leagueName, String tier, String queueType, String tierRank, String playerId, String playerName, int pdl, int wins, int losses)
    {
        mLeagueName = leagueName;
        mTier = tier;
        mQueueType = queueType;
        mTierRank = tierRank;
        mPlayerId = playerId;
        mPlayerName = playerName;
        mPDL = pdl;
        mWins = wins;
        mLosses = losses;
    }

    public String getLeagueName() {
        return mLeagueName;
    }

    public String getTier() {
        return mTier;
    }

    public String getQueueType() {
        return mQueueType;
    }

    public String getTierRank() {
        return mTierRank;
    }

    public String getPlayerId() {
        return mPlayerId;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public int getPDL() {
        return mPDL;
    }

    public int getWins() {
        return mWins;
    }

    public int getLosses() {
        return mLosses;
    }
}
