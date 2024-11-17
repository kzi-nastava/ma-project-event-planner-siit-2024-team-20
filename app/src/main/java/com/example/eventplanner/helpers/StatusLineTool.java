package com.example.eventplanner.helpers;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class StatusLineTool {

    public static void hideStatusBar(Activity activity) {
        if (activity == null) return;

        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }
}
