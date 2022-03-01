package de.bembelnaut.spike.livedatademo;

import java.math.BigDecimal;

interface SimplePriceListener {
    void onPriceChanged(BigDecimal price);
}
