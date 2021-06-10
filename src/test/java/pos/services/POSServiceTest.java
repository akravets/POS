package pos.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pos.commands.*;
import pos.exception.SKUNotFoundException;
import pos.functions.TaxForItemFunction;
import pos.models.Item;
import pos.models.TaxCategory;
import pos.models.TaxRate;
import pos.provider.CommandProvider;
import pos.provider.DataProvider;
import pos.utils.POSUtility;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class POSServiceTest {
    @Mock
    CommandProvider commandProvider;

    @Mock
    DataProvider dataProvider;

    @InjectMocks
    private static POSServiceImpl posService;

    private static Item item1;
    private static Item item2;
    private static Item item3;

    @BeforeAll
    public static void init() throws URISyntaxException, IOException {
        posService = mock(POSServiceImpl.class);
        mockItems();
        mockCommands();
    }

    // mocks commands
    private static void mockCommands() {
        // should really better this, this looks bad. May be replace with builder
        AbstractCommand exitCommand = new ExitCommand(null,null);
        AbstractCommand listCommand = new ListItemsCommand(null,null);
        AbstractCommand skuLookupCommand = new SKULookupCommand(null,null, null);
        AbstractCommand totalCommand = new TotalCommand(null,null, null);

        Map<String, AbstractCommand> commandMap = new HashMap<>();
        commandMap.put(exitCommand.getCommandCode(), exitCommand);
        commandMap.put(listCommand.getCommandCode(), listCommand);
        commandMap.put(skuLookupCommand.getCommandCode(), skuLookupCommand);
        commandMap.put(totalCommand.getCommandCode(), totalCommand);

        when(posService.getCommands()).thenReturn(commandMap);
    }

    // mocks objects
    private static void mockItems() throws URISyntaxException, IOException {
        item1 = new Item();
        item1.setSku("028400157827");
        item1.setName("CHEETOS CHED JAL");
        item1.setPrice(3.99);
        item1.setTaxCategory(TaxCategory.g);

        item2 = new Item();
        item2.setSku("028400589864");
        item2.setName("CHEETOS CRUNCHY");
        item2.setPrice(3.99);
        item2.setTaxCategory(TaxCategory.g);

        item3 = new Item();
        item3.setSku("017082112774");
        item3.setName("JK LNK BEEF TERI");
        item3.setPrice(5.99);
        item3.setTaxCategory(TaxCategory.pf);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Map<String, Map<String, Item>> data = POSUtility.groupData(items);

        when(posService.getItems()).thenReturn(data);
    }

    @Test
    public void getItems() throws URISyntaxException, IOException {
        final Map<String, Map<String, Item>> items = posService.getItems();
        assertEquals(2, items.size());
    }

    @Test
    public void getCommands() {
        final Map<String,AbstractCommand> commands = posService.getCommands();
        assertEquals(4,commands.size());
    }


    @Test
    public void findItemBySKU_withOneMatch(){
        when(posService.findItemBySKU("028400157827")).thenCallRealMethod();
        assertEquals(Arrays.asList(new Item[]{item1}), posService.findItemBySKU("028400157827").get());
    }

    @Test
    public void findItemBySKU_withManyMatches(){
        when(posService.findItemBySKU("0284005")).thenCallRealMethod();
        assertEquals(2, posService.findItemBySKU("0284005").get().size());
    }

    @Test
    public void findItemBySKU_withNoMatches(){
        when(posService.findItemBySKU(String.valueOf(Integer.MIN_VALUE))).thenCallRealMethod();

        assertThrows(
                SKUNotFoundException.class,
                () -> posService.findItemBySKU(String.valueOf(Integer.MIN_VALUE))
        );
    }

    @Test
    public void taxForItemFunction_oneItem(){
        Item[] item = new Item[]{item1};

        when(posService.getTaxForEachItem(Arrays.asList(item))).thenCallRealMethod();

        Map<TaxRate, Double> map = new LinkedHashMap<>();
        map.put(TaxRate.CITY,0.08);
        map.put(TaxRate.STATE,0.0);
        map.put(TaxRate.COUNTY,0.0);

        Map<Item, Map<TaxRate, Double>>  itemTaxMap = new LinkedHashMap<>();
        itemTaxMap.put(item1, map);

        assertEquals(itemTaxMap.keySet().iterator().next(), item1);
        assertEquals(itemTaxMap.get(item1), posService.getTaxForEachItem(Arrays.asList(item)).get(item1));
    }

    @Test
    public void taxForItemFunction_twoItem(){
        when(posService.getTaxForEachItem(Arrays.asList(new Item[]{item1, item2}))).thenCallRealMethod();

        Map<TaxRate, Double> map = new LinkedHashMap<>();
        map.put(TaxRate.CITY,0.08);
        map.put(TaxRate.STATE,0.0);
        map.put(TaxRate.COUNTY,0.0);

        Map<TaxRate, Double> map2 = new LinkedHashMap<>();
        map2.put(TaxRate.CITY,0.08);
        map2.put(TaxRate.STATE,0.0);
        map2.put(TaxRate.COUNTY,0.0);

        Map<Item, Map<TaxRate, Double>>  itemTaxMap = new LinkedHashMap<>();
        itemTaxMap.put(item1, map);
        itemTaxMap.put(item2, map);

        assertEquals(2, posService.getTaxForEachItem(Arrays.asList(new Item[]{item1, item2})).size());
    }

    @Test
    public void testGetTaxForAllItems(){
        Item[] items = new Item[]{item1, item3};

        when(posService.getTaxForAllItems(Arrays.asList(items))).thenCallRealMethod();
        double taxForAllItems = posService.getTaxForAllItems(Arrays.asList(items));

        Map<Item, Map<TaxRate, Double>> itemsMap = posService.getTaxForEachItem(Arrays.asList(items));

        Double reduce = itemsMap.entrySet().stream()
                .flatMap(e -> e.getValue().entrySet().stream()
                        .map(x -> x.getValue())
                ).reduce(0.0, Double::sum);

        System.out.println(reduce);
    }

    @Test
    public void groupTotalTaxByJurisdiction(){
        Item[] items = new Item[]{item1, item2};

        when(posService.groupTotalTaxByJurisdiction(Arrays.asList(items))).thenCallRealMethod();

        Map<TaxRate, Double> taxRateDoubleMap = posService.groupTotalTaxByJurisdiction(Arrays.asList(items));
        Double cityTax = taxRateDoubleMap.get(TaxRate.CITY);

        assertEquals(Double.valueOf(0.16),cityTax);
    }

    private Map<Item, Map<TaxRate, Double>> getTaxForItemsByTaxRate(List<Item> items){
        TaxForItemFunction function = new TaxForItemFunction();

        Map<Item, Map<TaxRate, Double>> taxMap = new LinkedHashMap<>();

        for(Item item : items){
            Map<TaxRate, Double> map = new LinkedHashMap<>();
            map.put(TaxRate.CITY, function.apply(TaxRate.CITY, item));
            map.put(TaxRate.STATE, function.apply(TaxRate.STATE, item));
            map.put(TaxRate.COUNTY, function.apply(TaxRate.COUNTY, item));
            taxMap.put(item, map);
        }
        return taxMap;
    }
}
