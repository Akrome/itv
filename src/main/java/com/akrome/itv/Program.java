package com.akrome.itv;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.parsing.BasketParser;
import com.akrome.itv.parsing.MultipricePricingStrategyParser;
import com.akrome.itv.parsing.StandardPricingStrategyParser;
import com.akrome.itv.pricing.MultipricedPricingStrategy;
import com.akrome.itv.pricing.PricingStrategyResult;
import com.akrome.itv.pricing.StandardPricingStrategy;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        Program p = new Program();
        p.run(args);
    }

    public int run(String[] args) throws IOException {
        String standardPricingStrategyFilePath = args[0];
        StandardPricingStrategy standardPricingStrategy = StandardPricingStrategyParser.parse(standardPricingStrategyFilePath);

        String multipricePricingStrategyFilePath = args[1];
        MultipricedPricingStrategy multipricedPricingStrategy = MultipricePricingStrategyParser.parse(multipricePricingStrategyFilePath);

        String basketFilePath = args[2];
        Basket basket = BasketParser.parse(basketFilePath);

        return computeTotalPrice(standardPricingStrategy, multipricedPricingStrategy, basket);
    }

    private int computeTotalPrice(StandardPricingStrategy standardPricingStrategy,
                                 MultipricedPricingStrategy multipricedPricingStrategy,
                                 Basket basket) {
        PricingStrategyResult afterMultiprice = multipricedPricingStrategy.applyStrategy(basket);
        PricingStrategyResult afterStandard = standardPricingStrategy.applyStrategy(afterMultiprice.getRemainingBasket());
        return afterMultiprice.getPrice()+afterStandard.getPrice();
    }
}
