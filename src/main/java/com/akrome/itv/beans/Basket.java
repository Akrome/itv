package com.akrome.itv.beans;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    private final Map<SkuId, Integer> basket;

    public static final Basket EMPTY = new Basket(new HashMap<>());

    public Basket(Map<SkuId, Integer> basket) {
        this.basket = Collections.unmodifiableMap(basket);
    }

    public Map<SkuId, Integer> getBasket() {
        return basket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Basket basket1 = (Basket) o;

        return basket != null ? basket.equals(basket1.basket) : basket1.basket == null;
    }

    @Override
    public int hashCode() {
        return basket != null ? basket.hashCode() : 0;
    }
}
