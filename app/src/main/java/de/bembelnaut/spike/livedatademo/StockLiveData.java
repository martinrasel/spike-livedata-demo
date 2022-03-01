package de.bembelnaut.spike.livedatademo;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.math.BigDecimal;

// A livedata object that overwrites livedata aware methods onActivate and onInactivate.
public class StockLiveData extends LiveData<BigDecimal> {
    private StockRateUpdateManager stockManager;

    // Create listener to register it on the manager
    private SimplePriceListener listener = price -> setValue(price);

    public StockLiveData(StockRateUpdateManager stockManager) {
        this.stockManager = stockManager;
    }

    // Methods called when lifecycle state changed
    // Methods register itself on the manager to receive changes (onActive) and
    // remove itself as listener when lifecycle changed to inactive
    @Override
    protected void onActive() {
        Log.i("lddemo", "StockLiveData onActive");
        // Add listener to service, if livedata is active
        stockManager.requestPriceUpdates(listener);
    }

    @Override
    protected void onInactive() {
        Log.i("lddemo", "StockLiveData onInactive");
        // Remove listener from service, if livedata is active
        stockManager.removeUpdates(listener);
    }
}
