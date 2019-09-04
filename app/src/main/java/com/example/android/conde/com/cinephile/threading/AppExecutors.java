package com.example.android.conde.com.cinephile.threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors mInstance;

    public static AppExecutors getInstance(){
        if(mInstance == null){
            mInstance = new AppExecutors();
        }
        return mInstance;
    }

    private final ScheduledExecutorService mExecutorService = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService getExecutorService(){
        return mExecutorService;
    }
}
