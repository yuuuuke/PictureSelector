package com.zwp.homework.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolHelper {

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void RunOnIoThread(Runnable runnable) {
        executorService.execute(runnable);
    }

    public static ThreadPoolHelper getInstance() {
        return HolderClass.newInstance;
    }

    private static class HolderClass {
        private static final ThreadPoolHelper newInstance = new ThreadPoolHelper();
    }
}
