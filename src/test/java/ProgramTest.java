import com.akrome.itv.Program;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ProgramTest {
    @Test
    public void program_test() throws IOException {
        Program p = new Program();
        int result = p.run(new String[]{
                "./testFiles/nonEmptyStandardPricingStrategy.csv",
                "./testFiles/nonEmptyMultipricePricingStrategy.csv",
                "./testFiles/nonEmptyBasket.csv"});
        assertEquals(95, result);
    }
}
