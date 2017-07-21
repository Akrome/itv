package com.akrome.itv.parsing;

import com.akrome.itv.beans.Basket;
import com.akrome.itv.beans.SkuId;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class BasketParser {
    public static Basket parse(String filePath) throws IOException {
        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

        Map<SkuId, Integer> map = new HashMap<>();
        for (CSVRecord record : records) {
            SkuId skuId = new SkuId(record.get(0));
            int quantity = Integer.parseInt(record.get(1));
            map.put(skuId, quantity+map.getOrDefault(skuId, 0));
        }
        return new Basket(map);
    }
}
