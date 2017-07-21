package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;

public interface PricingStrategy {
    PricingStrategyResult applyStrategy(Basket skusQuantityBySkuId);
}
