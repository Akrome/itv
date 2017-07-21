package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;

import java.util.Map;

public interface PricingStrategy {
    PricingStrategyResult applyStrategy(Basket skusQuantityBySkuId);
}
