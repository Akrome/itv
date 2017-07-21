package parsing;

import com.akrome.itv.beans.SkuId;
import com.akrome.itv.parsing.MultipricePricingStrategyParser;
import com.akrome.itv.parsing.StandardPricingStrategyParser;
import com.akrome.itv.pricing.MultipricedPricingStrategy;
import com.akrome.itv.pricing.StandardPricingStrategy;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultipricingStrategyParserTest {
    @Test
    public void reading_an_empty() throws IOException {
        MultipricedPricingStrategy empty = MultipricePricingStrategyParser.parse("./testFiles/emptyMultipricePricingStrategy.csv");
        assertTrue(empty.getMultipricesBySku().isEmpty());
    }

    @Test
    public void reading_a_non_empty() throws IOException {
        MultipricedPricingStrategy nonEmpty = MultipricePricingStrategyParser.parse("./testFiles/nonEmptyMultipricePricingStrategy.csv");
        Map<SkuId, MultipricedPricingStrategy.Multiprice> expected = new HashMap<>();
        expected.put(new SkuId("A"), new MultipricedPricingStrategy.Multiprice(3, 130));
        expected.put(new SkuId("B"), new MultipricedPricingStrategy.Multiprice(2, 45));
        assertEquals(new MultipricedPricingStrategy(expected), nonEmpty);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reading_an_illegal_throws() throws IOException {
        MultipricePricingStrategyParser.parse("./testFiles/illegalMultipricePricingStrategy.csv");
    }
}
