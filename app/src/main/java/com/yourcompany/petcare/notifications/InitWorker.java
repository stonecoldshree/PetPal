package com.yourcompany.petcare.notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class InitWorker extends Worker {
    public InitWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // safe lightweight init at background; do not do heavy UI work here
        return Result.success();
    }
}
