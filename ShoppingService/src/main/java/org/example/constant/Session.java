package org.example.constant;

import lombok.Getter;

@Getter
public class Session {
    private static String sessionToken;
    private static String sessionAuthId;
    private static String sessionProfileId;


    public static void setSession(String token, String authId, String profileId) {
        sessionToken = token;
        sessionAuthId = authId;
        sessionProfileId = profileId;
    }

    public static String getProfileId() {
        return sessionProfileId;
    }
}
