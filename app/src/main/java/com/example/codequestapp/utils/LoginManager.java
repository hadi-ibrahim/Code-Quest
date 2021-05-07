package com.example.codequestapp.utils;

public class LoginManager {


    private static LoginManager instance;
    private static TokenEncryptedSharedPreferences tokenEncryptedSharedPreferences;

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    private LoginManager() {
        tokenEncryptedSharedPreferences = TokenEncryptedSharedPreferences.getInstance();
    }

    public boolean isLoggedIn() {
        return tokenEncryptedSharedPreferences.getAuthToken() != null;
    }

    public void logout() {
        tokenEncryptedSharedPreferences.clearToken();
    }

    public void login(String token) {
        tokenEncryptedSharedPreferences.replaceToken(token);
    }

}
