package pos.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pos.commands.*;
import pos.exception.TaxRateNotFoundException;
import pos.models.Item;
import pos.models.TaxCategory;
import pos.provider.CommandProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class POSServiceTest {
    private static POSService posService;

    @BeforeAll
    public static void init() throws URISyntaxException, IOException {
        posService = mock(POSService.class);
        mockItems();
        mockCommands();
    }

    // mocks commands
    private static void mockCommands() {
        AbstractCommand exitCommand = new ExitCommand(null,null);
        AbstractCommand listCommand = new ListItemsCommand(null,null);
        AbstractCommand skuLookupCommand = new SKULookupCommand(null,null, null);
        AbstractCommand totalCommand = new TotalCommand(null,null, null);

        Map<String, AbstractCommand> commandMap = new HashMap<>();
        commandMap.put(exitCommand.getCommandCode(), exitCommand);
        commandMap.put(listCommand.getCommandCode(), listCommand);
        commandMap.put(skuLookupCommand.getCommandCode(), skuLookupCommand);
        commandMap.put(totalCommand.getCommandCode(), totalCommand);

        CommandProvider provider =  new CommandProvider(commandMap);

        when(posService.getCommands()).thenReturn(provider.getcommands());
    }

    // mocks objects
    private static void mockItems() throws URISyntaxException, IOException {
        Item item1 = new Item();
        item1.setSku("028400157827");
        item1.setName("CHEETOS CHED JAL");
        item1.setPrice(3.99);
        item1.setTaxCategory(TaxCategory.g);

        Item item2 = new Item();
        item2.setSku("028400589864");
        item2.setName("CHEETOS CRUNCHY");
        item2.setPrice(3.99);
        item2.setTaxCategory(TaxCategory.g);

        Item item3 = new Item();
        item3.setSku("017082112774");
        item3.setName("JK LNK BEEF TERI");
        item3.setPrice(5.99);
        item3.setTaxCategory(TaxCategory.g);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Map<String, List<Item>> data = items.stream().collect(Collectors.groupingBy(s -> s.getSku().substring(0, 3)));

        when(posService.getItems()).thenReturn(data);
    }

    @Test
    public void getItems() throws URISyntaxException, IOException {
        final Map<String, List<Item>> items = posService.getItems();

        List<Item> finalList = new ArrayList<>();

        // flat lists in items map into one list
        for (List<Item> list : items.values()) {
            list.stream()
                    .forEach(finalList::add);
        }

        assertEquals(3, items.size());
    }

    @Test
    public void getCommands() {
        final Map<String,AbstractCommand> commands = posService.getCommands();
        assertEquals(4,commands.size());
    }


    @Test
    public void findItemBySKU_withOneMatch(){
        final List<Item> items = posService.findItemBySKU("028400589864");
        assertEquals(items.size(), 1);
    }

    @Test
    public void findItemBySKU_withManyMatches(){
        final List<Item> items = posService.findItemBySKU("0284005");
        assertEquals(items.size(), 2);
    }

    @Test
    public void findItemBySKU_withNoMatches(){
        final List<Item> items = posService.findItemBySKU(String.valueOf(Integer.MIN_VALUE));
        assertEquals(items.size(), 0);
    }

    @Test
    public void getTaxRateByJurisdicion_positiveMatch() throws TaxRateNotFoundException {
        double rate = posService.getTaxRateByJurisdiction("city");
        Assertions.assertEquals(rate, 2.0);
    }

    @Test
    public void getTaxRateByJurisdicion_negativeMatch(){
        Assertions.assertThrows(TaxRateNotFoundException.class, () -> {
            posService.getTaxRateByJurisdiction(UUID.randomUUID().toString());
        });
    }
}
