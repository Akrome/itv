package com.akrome.itv.parsing;

import com.akrome.itv.beans.SkuId;
import com.akrome.itv.pricing.MultipricedPricingStrategy;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class MultipricePricingStrategyParser {
    public static MultipricedPricingStrategy parse(String filePath) throws IOException {
        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

        Map<SkuId, MultipricedPricingStrategy.Multiprice> map = new HashMap<>();
        for (CSVRecord record : records) {
            SkuId skuId = new SkuId(record.get(0));
            int price = Integer.parseInt(record.get(1));
            int quantity = Integer.parseInt(record.get(2));
            if (map.containsKey(skuId)) {
                throw new IllegalArgumentException("The specified file contains multiple entries for the same sku");
            }
            else {
                map.put(skuId, new MultipricedPricingStrategy.Multiprice(quantity, price));
            }
        }
        return new MultipricedPricingStrategy(map);
    }
}
