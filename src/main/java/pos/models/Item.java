package pos.models;

import com.opencsv.bean.CsvBindByPosition;
import lombok.NoArgsConstructor;

/**
 * POJO that describes POS item
 */
@NoArgsConstructor
public class Item {
    @CsvBindByPosition(position = 0)
    private String sku;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private double price;
    @CsvBindByPosition( position = 3)
    private TaxCategory taxCategory;
}
