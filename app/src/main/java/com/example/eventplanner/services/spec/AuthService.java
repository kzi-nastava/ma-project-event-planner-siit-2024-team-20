package com.example.eventplanner.services.spec;

import android.os.StrictMode;

import com.example.eventplanner.model.entities.User;

import retrofit2.Call;
import retrofit2.Response;
public class AuthService {
    private static User currentUser;
    private static String accessToken;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getToken() {
        return accessToken;
    }

    public static void setToken(String accessToken) {
        AuthService.accessToken = accessToken;
    }

    public void getMyInfo(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Call<User> currUserResponseCall = ApiService.getUserService().getCurrentUser();
        try {
            Response<User> response = currUserResponseCall.execute();
            currentUser = response.body();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void logout() {
        accessToken = null;
        currentUser = null;
    }
}
