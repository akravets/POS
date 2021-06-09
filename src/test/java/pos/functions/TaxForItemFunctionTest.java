package pos.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pos.models.Item;
import pos.models.TaxCategory;
import pos.models.TaxRate;

public class TaxForItemFunctionTest {
    private static TaxForItemFunction taxForItemFunction;
    private static Item groceryItem;
    private static Item preparedFoodItem;

    @BeforeAll
    public static void init(){
        taxForItemFunction = new TaxForItemFunction();

        groceryItem = new Item();
        groceryItem.setName("groceryItem");
        groceryItem.setPrice(1.00);
        groceryItem.setTaxCategory(TaxCategory.g);

        preparedFoodItem = new Item();
        preparedFoodItem.setName("preparedFoodItem");
        preparedFoodItem.setPrice(1.00);
        preparedFoodItem.setTaxCategory(TaxCategory.pf);
    }

    @Test
    public void testAppyTaxStateAndGroceryItem(){
        Double result = taxForItemFunction.apply(TaxRate.STATE, groceryItem);
        Assertions.assertEquals(1.00, result.doubleValue());
    }

    @Test
    public void testAppyTaxCountyAndGroceryItem(){
        Double result = taxForItemFunction.apply(TaxRate.COUNTY, groceryItem);
        Assertions.assertEquals(1.00, result.doubleValue());
    }

    @Test
    public void testAppyTaxCityAndGroceryItem(){
        Double result = taxForItemFunction.apply(TaxRate.CITY, groceryItem);
        Assertions.assertEquals(0.02, result.doubleValue());
    }

    @Test
    public void testAppyTaxStateAndPreparedFoodItem(){
        Double result = taxForItemFunction.apply(TaxRate.STATE, preparedFoodItem);
        Assertions.assertEquals(0.07, result.doubleValue());
    }

    @Test
    public void testAppyTaxCountyAndPreparedFoodItem(){
        Double result = taxForItemFunction.apply(TaxRate.COUNTY, preparedFoodItem);
        Assertions.assertEquals(0.01, result.doubleValue());
    }

    @Test
    public void testAppyTaxCityAndPreparedFoodItem(){
        Double result = taxForItemFunction.apply(TaxRate.CITY, preparedFoodItem);
        Assertions.assertEquals(0.02, result.doubleValue());
    }
}
