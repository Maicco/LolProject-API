package com.example.android.leagueoflegends_api;

public class ServerStatus
{
    private String mServerName, mRegionTag, mServerAppName, mServerStatus;
    private boolean mIncidentStatus;
    private String mMessageContent, mSeverity;

    /**
     * Constructs a new {@link ServerStatus} object.
     * @param serverName is the server name
     * @param regionTag is the server short name
     * @param serverAppName is the server application, like game server or store server
     * @param serverStatus is the server status
     * @param incidentStatus is the server incident current status
     * @param messageContent is the incident message report
     * @param severity is the incident level
     */
    public ServerStatus(String serverName, String regionTag, String serverAppName, String serverStatus, boolean incidentStatus, String messageContent, String severity)
    {
        mServerName = serverName;
        mRegionTag = regionTag;
        mServerAppName = serverAppName;
        mServerStatus = serverStatus;
        mIncidentStatus = incidentStatus;
        mMessageContent = messageContent;
        mSeverity = severity;
    }

    public String getServerName() {
        return mServerName;
    }

    public String getRegionTag() {
        return mRegionTag;
    }

    public String getServerAppName() {
        return mServerAppName;
    }

    public String getServerStatus() {
        return mServerStatus;
    }

    public boolean isIncidentStatus() {
        return mIncidentStatus;
    }

    public String getMessageContent() {
        return mMessageContent;
    }

    public String getSeverity() {
        return mSeverity;
    }
}
