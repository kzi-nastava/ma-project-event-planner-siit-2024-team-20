package com.example.eventplanner.services.spec;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class NotificationWebSocketClient {

    private StompClient stompClient;
    private Disposable lifecycleDisposable;
    private Disposable topicDisposable;

    private static final String TAG = "STOMP";

    public interface NotificationListener {
        void onNewNotification(String payload);
    }

    private NotificationListener notificationListener;

    public NotificationWebSocketClient(NotificationListener listener) {
        this.notificationListener = listener;
    }

    public void connect(String jwtToken) {
        String url = "ws://<YOUR_IP>:8080/api/ws/websocket";

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);

        List<StompHeader> headers = new ArrayList<>();
        headers.add(new StompHeader("Authorization", "Bearer " + jwtToken));

        stompClient.connect(headers);

        lifecycleDisposable = stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.d(TAG, "Connection opened");
                    break;
                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Log.d(TAG, "Connection closed");
                    break;
            }
        });

        topicDisposable = stompClient.topic("/user/queue/notifications").subscribe(topicMessage -> {
            String json = topicMessage.getPayload();
            Log.d(TAG, "New notification: " + json);

            if (notificationListener != null) {
                notificationListener.onNewNotification(json);
            }
        });
    }

    public void disconnect() {
        if (lifecycleDisposable != null && !lifecycleDisposable.isDisposed()) {
            lifecycleDisposable.dispose();
        }
        if (topicDisposable != null && !topicDisposable.isDisposed()) {
            topicDisposable.dispose();
        }
        if (stompClient != null) {
            stompClient.disconnect();
        }
    }
}

