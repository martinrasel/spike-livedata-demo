package de.bembelnaut.spike.livedatademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import de.bembelnaut.livedatademo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize view model with app context
        StockRateModel stockRateModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(StockRateModel.class);

        // start observing livedata
        stockRateModel.getStockLiveData().observe(this, bigDecimal -> {
            Log.i("lddemo", "Update stockrate to: " + bigDecimal);
        });
    }
}