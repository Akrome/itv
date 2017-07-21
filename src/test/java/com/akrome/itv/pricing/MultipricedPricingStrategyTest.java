package com.akrome.itv.pricing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MultipricedPricingStrategyTest {

    @Test
    public void apply_multipricing_to_empty_basket_changes_nothing() {
        Basket basket = given_an_empty_basket();
        MultipricedPricingStrategy multipricedPricingStrategy = given_a_test_multipricing_strategy();
        PricingStrategyResult result = when_we_apply_the_pricing_strategy_to_the_basket(multipricedPricingStrategy, basket);
        then_the_result_is_empty(result);
        then_the_basket_is(given_an_empty_basket(), basket);
    }

    @Test
    public void apply_multipricing_to_non_matching_basket_changes_nothing() {
        Basket basket = given_a_non_matching_test_basket();
        MultipricedPricingStrategy multipricedPricingStrategy = given_a_test_multipricing_strategy();
        PricingStrategyResult result = when_we_apply_the_pricing_strategy_to_the_basket(multipricedPricingStrategy, basket);
        then_the_result_is(0, new HashMap<>(), basket.getBasket(), result);
        then_the_basket_is(given_a_non_matching_test_basket(), basket);
    }
    @Test
    public void apply_multipricing_to_matching_basket_works_correctly() {
        Basket basket = given_a_matching_test_basket();
        MultipricedPricingStrategy multipricedPricingStrategy = given_a_test_multipricing_strategy();
        PricingStrategyResult result = when_we_apply_the_pricing_strategy_to_the_basket(multipricedPricingStrategy, basket);

        Map<SkuId, Integer> expectedConsumed = new HashMap<>();
        expectedConsumed.put(new SkuId("A"), 3);
        expectedConsumed.put(new SkuId("B"), 4);

        Map<SkuId, Integer> expectedRemain = new HashMap<>();
        expectedRemain.put(new SkuId("A"), 1);
        expectedRemain.put(new SkuId("B"), 1);

        then_the_result_is(220, expectedConsumed, expectedRemain, result);
        then_the_basket_is(given_a_matching_test_basket(), basket);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creating_zero_quantity_multiprice_throws_exception(){
        new MultipricedPricingStrategy.Multiprice(0, 12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creating_negative_quantity_multiprice_throws_exception() {
        new MultipricedPricingStrategy.Multiprice(-1, 12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creating_zero_price_multiprice_throws_exception() {
        new MultipricedPricingStrategy.Multiprice(7, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creating_negative_price_multiprice_throws_exception() {
       new MultipricedPricingStrategy.Multiprice(0, -1);
    }


    private Basket given_an_empty_basket() {
        return Basket.EMPTY;
    }

    private MultipricedPricingStrategy given_a_test_multipricing_strategy() {
        Map<SkuId, MultipricedPricingStrategy.Multiprice> map = new HashMap<>();
        map.put(new SkuId("A"), new MultipricedPricingStrategy.Multiprice(3, 130));
        map.put(new SkuId("B"), new MultipricedPricingStrategy.Multiprice(2, 45));
        return new MultipricedPricingStrategy(map);
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
        map.put(new SkuId("C"), 4);
        map.put(new SkuId("D"), 5);
        return new Basket(map);
    }

    private void then_the_result_is(int price, Map<SkuId, Integer> consumedBasket, Map<SkuId, Integer> remainingBasket, PricingStrategyResult result) {
        assertEquals(price, result.getPrice());
        assertEquals(consumedBasket, result.getConsumedBasket().getBasket());
        assertEquals(remainingBasket, result.getRemainingBasket().getBasket());
    }
}
