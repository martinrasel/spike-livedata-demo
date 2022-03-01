package de.bembelnaut.spike.livedatademo;

import android.content.Context;
import android.util.Log;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StockRateUpdateManager {

    private final BigDecimal stockRate;
    private final ArrayList<SimplePriceListener> listenerList;
    private final PeriodicWorkRequest stockRateWorkerRequest;

    // Manager is a simulator to simulate changing events
    // Registered listeners are called on change
    public StockRateUpdateManager(String symbol, Context context) {
        this.stockRate = new BigDecimal(symbol);
        this.listenerList = new ArrayList<SimplePriceListener>();

        // create a work request that starts every second the worker
        stockRateWorkerRequest =
            new PeriodicWorkRequest.Builder(UpdateWorker.class, 1, TimeUnit.SECONDS)
                    // Constraints
                    .build();

        // assign request to work manager
        WorkManager
            .getInstance(context)
            .enqueue(stockRateWorkerRequest);

        // start observing the request and update all listeners
        WorkManager
            .getInstance(context)
            .getWorkInfoByIdLiveData(stockRateWorkerRequest.getId())
            .observeForever(status -> {
                if (status != null && status.getState().isFinished()) {
                    double refreshDataPeriod = status.getOutputData().getDouble("stockrate", stockRate.doubleValue());
                    Log.i("lddemo","on period"+refreshDataPeriod);

                    for (SimplePriceListener simplePriceListener : listenerList) {
                        simplePriceListener.onPriceChanged(new BigDecimal(refreshDataPeriod));
                    };
                }
            });
    }

    public void requestPriceUpdates(SimplePriceListener listener) {
        Log.i("lddemo", "StockManager - requestPriceUpdates");
        listenerList.add(listener);
    }

    public void removeUpdates(SimplePriceListener listener) {
        Log.i("lddemo", "StockManager - removeUpdates");
        listenerList.remove(listener);
    }
}
