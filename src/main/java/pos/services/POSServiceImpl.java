package pos.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pos.exception.SKUNotFoundException;
import pos.functions.TaxForItemFunction;
import pos.commands.AbstractCommand;
import pos.provider.CommandProvider;
import pos.models.Item;
import pos.models.TaxRate;
import pos.provider.DataProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class POSServiceImpl implements POSService {
    @Autowired
    public CommandProvider commandProvider;

    @Autowired
    DataProvider dataProvider;

    @Override
    public Map<String, List<Item>> getItems() throws URISyntaxException, IOException {
        return dataProvider.getItems();
    }

    @Override
    public Map<String, AbstractCommand> getCommands() {
        return commandProvider.getcommands();
    }

    @Override
    public Optional<AbstractCommand> getCommandByCode(String input) {
        return commandProvider.getCommandByCommandCode(input);
    }

    @Override
    public Optional<List<Item>> findItemBySKU(String sku) throws SKUNotFoundException {
        try {
            final Map<String, List<Item>> items = getItems();

            if (sku.length() < 12) {
                if (sku.length() == 3) {
                    return Optional.ofNullable(items.get(sku.substring(0, 3)));
                }
                final Optional<List<Item>> itemList = Optional.ofNullable(items.get(sku.substring(0, 3)));

                itemList.orElseThrow(() -> new SKUNotFoundException(sku));

                List<Item> list = itemList.get();

                return Optional.ofNullable(list.stream().filter(x -> x.getSku().startsWith(sku)).collect(Collectors.toList()));
            }
            // otherwise get list in our bucket
            List<Item> itemList = items.get(sku.substring(0, 3));

            // iterate through the list until we find our sku
            return Optional.of(itemList.stream().
                    filter(x -> x.getSku().equals(sku))
                    .collect(Collectors.toList()));

        } catch (URISyntaxException | IOException e) {
            log.debug("Error finding sku " + sku, e);
            return Optional.empty();
        }
    }

    @Override
    public Map<Item, Map<TaxRate, Double>> getTaxForEachItem(List<Item> items) {
        return getTaxForItemsByTaxRate(items);
    }

    @Override
    public double getTaxForAllItems(List<Item> items) {
        Double reduce = getTaxForItemsByTaxRate(items).entrySet().stream()
                .flatMap(e -> e.getValue().entrySet().stream()
                        .map(x -> x.getValue()))
                .reduce(0.0, Double::sum);

        DecimalFormat df = new DecimalFormat("#.##");

        return Double.valueOf(df.format(reduce));
    }

    @Override
    public Map<TaxRate, Double> groupTotalTaxByJurisdiction(List<Item> items) {
        Map<Item, Map<TaxRate, Double>> map = getTaxForItemsByTaxRate(items);

        Map<TaxRate, Double> taxMap = new HashMap<>();

        map.entrySet().forEach(x -> {
            Map<TaxRate, Double> value = x.getValue();
            for (Map.Entry<TaxRate, Double> entry : value.entrySet()) {
                TaxRate k = entry.getKey();
                Double v = entry.getValue();

                Double taxValue = taxMap.get(k);

                if (taxValue == null) {
                    taxMap.put(k, v);
                } else {
                    taxMap.put(k, taxValue + v);
                }
            }
        });

        return taxMap;
    }

    private Map<Item, Map<TaxRate, Double>> getTaxForItemsByTaxRate(List<Item> items) {
        TaxForItemFunction function = new TaxForItemFunction();

        Map<Item, Map<TaxRate, Double>> taxMap = new LinkedHashMap<>();

        for (Item item : items) {
            Map<TaxRate, Double> map = new LinkedHashMap<>();
            map.put(TaxRate.CITY, function.apply(TaxRate.CITY, item));
            map.put(TaxRate.STATE, function.apply(TaxRate.STATE, item));
            map.put(TaxRate.COUNTY, function.apply(TaxRate.COUNTY, item));
            taxMap.put(item, map);
        }

        return taxMap;
    }
}
