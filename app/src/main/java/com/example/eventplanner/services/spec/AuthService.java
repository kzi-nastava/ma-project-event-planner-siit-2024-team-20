package com.example.eventplanner.services.spec;

import android.os.StrictMode;
import android.util.Base64;

import com.example.eventplanner.model.entities.User;

import org.json.JSONObject;

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

    public static int getUserIdFromToken() {
        if (accessToken == null) return -1;

        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length != 3) return -1;

            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP));
            JSONObject jsonObject = new JSONObject(payload);
            return jsonObject.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static String getRoleFromToken() {
        if (accessToken == null) return null;

        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length != 3) return null;

            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP));
            JSONObject jsonObject = new JSONObject(payload);

            return jsonObject.getString("role");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void logout() {
        accessToken = null;
        currentUser = null;
    }

}
