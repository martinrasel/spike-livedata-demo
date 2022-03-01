package de.bembelnaut.spike.livedatademo;

import android.app.Application;

import androidx.lifecycle.ViewModel;

public class StockRateModel extends ViewModel {

    private StockLiveData stockLiveData;

    public StockRateModel(Application context) {
        // create a service that produce a livedata object. The service will do some background work.
        StockRateUpdateManager stockManager = new StockRateUpdateManager("1", context);
        stockLiveData = new StockLiveData(stockManager);
    }

    public StockLiveData getStockLiveData() {
        return stockLiveData;
    }
}
