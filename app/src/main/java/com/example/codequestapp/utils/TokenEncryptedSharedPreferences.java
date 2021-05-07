package com.example.codequestapp.utils;

import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenEncryptedSharedPreferences {

    private static TokenEncryptedSharedPreferences instance;
    private SharedPreferences sharedPreferences;

    public static TokenEncryptedSharedPreferences getInstance() {
        if (instance == null)
            instance = new TokenEncryptedSharedPreferences();
        return instance;
    }

    private TokenEncryptedSharedPreferences() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    AppContext.getContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public void clearToken() {
        replaceToken(null);
    }

    public void replaceToken(String accessToken) {
        sharedPreferences.edit()
                .putString("auth-token", accessToken)
                .apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString("auth-token", null);
    }
}
