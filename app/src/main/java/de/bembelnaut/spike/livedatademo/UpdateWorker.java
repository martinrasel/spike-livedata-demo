package de.bembelnaut.spike.livedatademo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.math.BigDecimal;

// The worker that do something for the simulation...
public class UpdateWorker extends Worker {
    private BigDecimal stockrate;

    public UpdateWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);

        Data inputData = params.getInputData();
        this.stockrate = new BigDecimal(inputData.getDouble("currency", 0.0));
    }

    @Override
    public Result doWork() {
        Log.i("lddemo","do work");
        stockrate = stockrate.add(new BigDecimal(1.4d));
        Data newStockRate = new Data.Builder().putDouble("stockrate", stockrate.doubleValue()).build();
        return Result.success(newStockRate);
    }
}
