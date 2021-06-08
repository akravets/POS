package pos.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pos.models.Item;
import pos.models.TaxCategory;
import pos.models.TaxRate;

public class ApplyTaxFunctionTest {
    private static ApplyTaxFunction applyTaxFunction;
    private static Item groceryItem;
    private static Item preparedFoodItem;

    @BeforeAll
    public static void init(){
        applyTaxFunction = new ApplyTaxFunction();

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
        Double result = applyTaxFunction.apply(TaxRate.STATE, groceryItem);
        Assertions.assertEquals(1.00, result.doubleValue());
    }

    @Test
    public void testAppyTaxCountyAndGroceryItem(){
        Double result = applyTaxFunction.apply(TaxRate.COUNTY, groceryItem);
        Assertions.assertEquals(1.00, result.doubleValue());
    }

    @Test
    public void testAppyTaxCityAndGroceryItem(){
        Double result = applyTaxFunction.apply(TaxRate.CITY, groceryItem);
        Assertions.assertEquals(0.02, result.doubleValue());
    }

    @Test
    public void testAppyTaxStateAndPreparedFoodItem(){
        Double result = applyTaxFunction.apply(TaxRate.STATE, preparedFoodItem);
        Assertions.assertEquals(0.07, result.doubleValue());
    }

    @Test
    public void testAppyTaxCountyAndPreparedFoodItem(){
        Double result = applyTaxFunction.apply(TaxRate.COUNTY, preparedFoodItem);
        Assertions.assertEquals(0.01, result.doubleValue());
    }

    @Test
    public void testAppyTaxCityAndPreparedFoodItem(){
        Double result = applyTaxFunction.apply(TaxRate.CITY, preparedFoodItem);
        Assertions.assertEquals(0.02, result.doubleValue());
    }
}
