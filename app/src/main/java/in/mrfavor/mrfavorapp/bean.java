package in.mrfavor.mrfavorapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class bean extends Application {


    private static Context context;
    private String TAG = "myApp";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        FontsOverride.setDefaultFont(this, "MONOSPACE", "tcb.ttf");

    }

    public static Context getContext() {
        return context;
    }

}
