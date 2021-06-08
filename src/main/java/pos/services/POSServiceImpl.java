package pos.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pos.helpers.POSHelper;
import pos.commands.AbstractCommand;
import pos.provider.CommandProvider;
import pos.exception.TaxRateNotFoundException;
import pos.models.Item;
import pos.models.TaxRate;
import pos.provider.DataProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class POSServiceImpl implements POSService {
    @Autowired
    public CommandProvider commandProvider;

    @Autowired
    public POSHelper posHelper;
    
    @Autowired
    Environment environment;

    @Autowired
    DataProvider dataProvider;

    @Override
    public List<Item> getItems() throws URISyntaxException, IOException {
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
    public Set<Item> findItemBySKU(String sku) {
        try {
            return getItems().stream().filter(x -> {
                return x.getSku().startsWith(sku);
            }).collect(Collectors.toSet());
        } catch (URISyntaxException | IOException e) {
            log.error("Error finding sku " + sku, e);
            return Collections.emptySet();
        }
    }

    @Override
    public double getTaxRateByJurisdiction(String jurisdiction) throws NumberFormatException, TaxRateNotFoundException {
        Optional<TaxRate> taxRate = Arrays.stream(TaxRate.values()).filter(t -> t.getName().equals(jurisdiction)).findFirst();
        TaxRate rate = taxRate.orElseThrow(() -> new TaxRateNotFoundException(jurisdiction));
        return Double.valueOf(environment.getProperty("pos.taxRate." + rate.getName()));
    }
}
