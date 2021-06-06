package pos.model;

import lombok.Value;

@Value
public class Item {
    String sku;
    String name;
    double price;
    TaxCategory taxCategory;
}
