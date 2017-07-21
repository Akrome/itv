package parsing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;
import com.akrome.itv.parsing.BasketParser;
import com.akrome.itv.parsing.MultipricePricingStrategyParser;
import com.akrome.itv.parsing.StandardPricingStrategyParser;
import com.akrome.itv.pricing.StandardPricingStrategy;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StandardPricingStrategyParserTest {
    @Test
    public void reading_an_empty() throws IOException {
        StandardPricingStrategy empty = StandardPricingStrategyParser.parse("./testFiles/emptyStandardPricingStrategy.csv");
        assertTrue(empty.getPrices().isEmpty());
    }

    @Test
    public void reading_a_non_empty() throws IOException {
        StandardPricingStrategy nonEmpty = StandardPricingStrategyParser.parse("./testFiles/nonEmptyStandardPricingStrategy.csv");
        Map<SkuId, Integer> expected = new HashMap<>();
        expected.put(new SkuId("A"), 50);
        expected.put(new SkuId("B"), 30);
        expected.put(new SkuId("C"), 20);
        expected.put(new SkuId("D"), 15);
        assertEquals(new StandardPricingStrategy(expected), nonEmpty);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reading_an_illegal_throws() throws IOException {
        StandardPricingStrategyParser.parse("./testFiles/illegalStandardPricingStrategy.csv");
    }
}
