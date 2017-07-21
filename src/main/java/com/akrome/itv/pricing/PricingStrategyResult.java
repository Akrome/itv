package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;

import java.util.Map;

public class PricingStrategyResult {
    private final int price;
    private final Basket consumedBasket;
    private final Basket remainingBasket;

    public PricingStrategyResult(int price, Basket consumedBasket, Basket remainingBasket) {
        this.price = price;
        this.consumedBasket = consumedBasket;
        this.remainingBasket = remainingBasket;
    }

    public int getPrice() {
        return price;
    }

    public Basket getConsumedBasket() {
        return consumedBasket;
    }

    public Basket getRemainingBasket() {
        return remainingBasket;
    }
}
