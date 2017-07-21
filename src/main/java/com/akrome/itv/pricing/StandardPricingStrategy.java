package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StandardPricingStrategy implements PricingStrategy {
    private final Map<SkuId, Integer> prices;

    public StandardPricingStrategy(Map<SkuId, Integer> prices) {
        Set<Map.Entry<SkuId, Integer>> invalidPrices = prices.entrySet().stream()
                .filter(entry -> entry.getValue() <= 0).collect(Collectors.toSet());
        if (!invalidPrices.isEmpty()) {
            throw new IllegalArgumentException("Invalid prices detected: "+invalidPrices);
        }
        this.prices = Collections.unmodifiableMap(prices);
    }

    @Override
    public PricingStrategyResult applyStrategy(Basket basket) {
        Set<SkuId> unknownSkuIds = basket.getBasket().keySet().stream()
                .filter(skuId -> !prices.containsKey(skuId)).collect(Collectors.toSet());
        if (!unknownSkuIds.isEmpty()) {
            throw new IllegalArgumentException("Unrecognized SkuIds in basket: "+unknownSkuIds);
        }
        else {
            int price = basket.getBasket().entrySet().stream()
                    .reduce(0,
                            (p, e) -> p + prices.get(e.getKey()) * e.getValue(),
                            (p1, p2) -> p1+p2);
            return new PricingStrategyResult(price, basket, Basket.EMPTY);
        }
    }

    public Map<SkuId, Integer> getPrices() {
        return prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandardPricingStrategy that = (StandardPricingStrategy) o;

        return prices != null ? prices.equals(that.prices) : that.prices == null;
    }

    @Override
    public int hashCode() {
        return prices != null ? prices.hashCode() : 0;
    }
}
