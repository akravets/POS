package pos.models;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * POJO that describes POS item
 */
@NoArgsConstructor
@Data
public class Item implements Comparable<Item> {
    @CsvBindByPosition(position = 0)
    private String sku;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private double price;
    @CsvBindByPosition( position = 3)
    private TaxCategory taxCategory;

    @Override
    public int compareTo(Item item) {
        int i = item.getSku().compareTo(getSku());
        if(i < 0)
            return 1;
        if(i > 0)
            return -1;
        return 0;
    }
}
