package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MultipricedPricingStrategy implements PricingStrategy {
    private final Map<SkuId, Multiprice> multipricesBySku;

    public MultipricedPricingStrategy(Map<SkuId, Multiprice> multipricesBySku) {
        this.multipricesBySku = Collections.unmodifiableMap(multipricesBySku);
    }

    public PricingStrategyResult applyStrategy(Basket basket) {
        int price = 0;
        Map<SkuId, Integer> remainingBasket = new HashMap<>();
        Map<SkuId, Integer> consumedBasket = new HashMap<>();

        for (Map.Entry<SkuId, Integer> entry: basket.getBasket().entrySet()) {
            SkuId skuId = entry.getKey();
            if (multipricesBySku.containsKey(skuId)) {
                Multiprice appliableMultiprice = multipricesBySku.get(skuId);
                int appliableMultipriceQuantity = entry.getValue() / appliableMultiprice.quantity;
                if (appliableMultipriceQuantity > 0){
                    price += appliableMultipriceQuantity * appliableMultiprice.price;
                    consumedBasket.put(entry.getKey(), appliableMultipriceQuantity * appliableMultiprice.quantity);
                }
                int remainingQuantity = entry.getValue() % appliableMultiprice.quantity;
                if (remainingQuantity > 0) {
                    remainingBasket.put(entry.getKey(), remainingQuantity);
                }
            }
            else {
                remainingBasket.put(entry.getKey(), entry.getValue());
            }
        }
        return new PricingStrategyResult(price, new Basket(consumedBasket), new Basket(remainingBasket));
    }

    public static class Multiprice {
        private final int quantity;
        private final int price;

        public Multiprice(int quantity, int price) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity cannot be <= 0, received "+quantity);
            }
            else {
                this.quantity = quantity;
            }

            if (price <= 0) {
                throw new IllegalArgumentException("Price cannot be <= 0, received "+price);
            }
            else {
                this.price = price;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Multiprice that = (Multiprice) o;

            if (quantity != that.quantity) return false;
            return price == that.price;
        }

        @Override
        public int hashCode() {
            int result = quantity;
            result = 31 * result + price;
            return result;
        }
    }

    public Map<SkuId, Multiprice> getMultipricesBySku() {
        return multipricesBySku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultipricedPricingStrategy that = (MultipricedPricingStrategy) o;

        return multipricesBySku != null ? multipricesBySku.equals(that.multipricesBySku) : that.multipricesBySku == null;
    }

    @Override
    public int hashCode() {
        return multipricesBySku != null ? multipricesBySku.hashCode() : 0;
    }
}
