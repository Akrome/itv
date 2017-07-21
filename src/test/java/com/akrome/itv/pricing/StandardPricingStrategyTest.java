package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StandardPricingStrategyTest {

    @Test
    public void apply_standardpricing_to_empty_basket_changes_nothing() {
        Basket basket = given_an_empty_basket();
        StandardPricingStrategy standardPricingStrategy = given_a_test_standardpricing_strategy();
        PricingStrategyResult result = when_we_apply_the_pricing_strategy_to_the_basket(standardPricingStrategy, basket);
        then_the_result_is_empty(result);
        then_the_basket_is(given_an_empty_basket(), basket);
    }

    @Test
    public void apply_multipricing_to_matching_basket_works_correctly() {
        Basket basket = given_a_matching_test_basket();
        StandardPricingStrategy standardPricingStrategy = given_a_test_standardpricing_strategy();
        PricingStrategyResult result = when_we_apply_the_pricing_strategy_to_the_basket(standardPricingStrategy, basket);
        then_the_result_is(350, basket.getBasket(), new HashMap<>(), result);
        then_the_basket_is(given_a_matching_test_basket(), basket);
    }

    @Test(expected = IllegalArgumentException.class)
    public void apply_standardpricing_to_unknown_sku_throws_exception() {
        Basket basket = given_a_non_matching_test_basket();
        StandardPricingStrategy standardPricingStrategy = given_a_test_standardpricing_strategy();
        when_we_apply_the_pricing_strategy_to_the_basket(standardPricingStrategy, basket);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_zero_price_standardpricing_throws_exception() {
        Map<SkuId, Integer> map = new HashMap<>();
        map.put(new SkuId("A"), 0);
        new StandardPricingStrategy(map);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_negative_price_standardpricing_throws_exception() {
        Map<SkuId, Integer> map = new HashMap<>();
        map.put(new SkuId("A"), -1);
        new StandardPricingStrategy(map);
    }

    private Basket given_an_empty_basket() {
        return Basket.EMPTY;
    }

    private StandardPricingStrategy given_a_test_standardpricing_strategy() {
        Map<SkuId, Integer> map = new HashMap<>();
        map.put(new SkuId("A"), 50);
        map.put(new SkuId("B"), 30);
        map.put(new SkuId("C"), 20);
        map.put(new SkuId("D"), 15);
        return new StandardPricingStrategy(map);
    }

    private PricingStrategyResult when_we_apply_the_pricing_strategy_to_the_basket(PricingStrategy pricingStrategy, Basket basket) {
        return pricingStrategy.applyStrategy(basket);
    }

    private void then_the_result_is_empty(PricingStrategyResult result) {
        assertEquals(0, result.getPrice());
        assertEquals(Basket.EMPTY, result.getConsumedBasket());
        assertEquals(Basket.EMPTY, result.getRemainingBasket());
    }

    private void then_the_basket_is(Basket expectedBasket, Basket actualBasket) {
        assertEquals(expectedBasket, actualBasket);
    }

    private Basket given_a_matching_test_basket() {
        HashMap<SkuId, Integer> map = new HashMap<>();
        map.put(new SkuId("A"), 4);
        map.put(new SkuId("B"), 5);
        return new Basket(map);
    }

    private Basket given_a_non_matching_test_basket() {
        HashMap<SkuId, Integer> map = new HashMap<>();
        map.put(new SkuId("E"), 4);
        map.put(new SkuId("F"), 5);
        return new Basket(map);
    }

    private void then_the_result_is(int price, Map<SkuId, Integer> consumedBasket, Map<SkuId, Integer> remainingBasket, PricingStrategyResult result) {
        assertEquals(price, result.getPrice());
        assertEquals(consumedBasket, result.getConsumedBasket().getBasket());
        assertEquals(remainingBasket, result.getRemainingBasket().getBasket());
    }
}
