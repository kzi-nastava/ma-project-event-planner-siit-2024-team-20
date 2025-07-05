package com.example.eventplanner.model.notification;

public class FcmTokenRequest {
        private String token;

        public FcmTokenRequest(String token) {
            this.token = token;
        }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
