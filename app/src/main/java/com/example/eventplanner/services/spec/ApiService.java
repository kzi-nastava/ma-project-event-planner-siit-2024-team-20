package com.example.eventplanner.services.spec;

import com.example.eventplanner.services.ICommentService;
import com.example.eventplanner.services.IEventService;
import com.example.eventplanner.services.IEventTypeService;
import com.example.eventplanner.services.INotificationService;
import com.example.eventplanner.services.IProductService;
import com.example.eventplanner.services.IReportService;
import com.example.eventplanner.services.IServiceService;
import com.example.eventplanner.services.IUserService;

public class ApiService {
    public static final String BASE_URL = "http://192.168.100.8:8080/api/";
    public static IUserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(IUserService.class);
    }
    public static IEventTypeService getEventTypeService(){
        return RetrofitClient.getClient(BASE_URL).create(IEventTypeService.class);
    }
    public static IEventService getEventService(){
        return RetrofitClient.getClient(BASE_URL).create(IEventService.class);
    }
    public static IProductService getProductService(){
        return RetrofitClient.getClient(BASE_URL).create(IProductService.class);
    }
    public static IServiceService getServiceService(){
        return RetrofitClient.getClient(BASE_URL).create(IServiceService.class);
    }
    public static ICommentService getCommentService(){
        return RetrofitClient.getClient(BASE_URL).create(ICommentService.class);
    }
    public static IReportService getReportService(){
        return RetrofitClient.getClient(BASE_URL).create(IReportService.class);
    }
    public static INotificationService getNotificationService(){
        return RetrofitClient.getClient(BASE_URL).create(INotificationService.class);
    }
}
