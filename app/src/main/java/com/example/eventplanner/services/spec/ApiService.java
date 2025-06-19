package com.example.eventplanner.services.spec;

import com.example.eventplanner.services.IEventTypeService;
import com.example.eventplanner.services.IUserService;

public class ApiService {

    public static final String BASE_URL = "http://10.0.2.2:8080/api/";
    public static IUserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(IUserService.class);
    }
    public static IEventTypeService getEventTypeService(){
        return RetrofitClient.getClient(BASE_URL).create(IEventTypeService.class);
    }
}
