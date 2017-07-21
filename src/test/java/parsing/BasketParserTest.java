package parsing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;
import com.akrome.itv.parsing.BasketParser;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BasketParserTest {
    @Test
    public void reading_an_empty() throws IOException {
        Basket empty = BasketParser.parse("./testFiles/emptyBasket.csv");
        assertEquals(Basket.EMPTY, empty);
    }

    @Test
    public void reading_a_non_empty() throws IOException {
        Basket nonEmpty = BasketParser.parse("./testFiles/nonEmptyBasket.csv");
        Map<SkuId, Integer> expected = new HashMap<>();
        expected.put(new SkuId("A"), 1);
        expected.put(new SkuId("B"), 2);
        assertEquals(new Basket(expected), nonEmpty);
    }
}
