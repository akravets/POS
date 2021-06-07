package pos.services;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pos.POSHelper;
import pos.commands.AbstractCommand;
import pos.commands.CommandProvider;
import pos.commands.SKULookupCommand;
import pos.exception.TaxRateNotFoundException;
import pos.models.Item;
import pos.models.TaxRate;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Override
    public List<Item> getItems() throws URISyntaxException, IOException {
        File dataFile = ResourceUtils.getFile("classpath:data.csv");
        Reader reader = Files.newBufferedReader(Paths.get(dataFile.toURI()));
        return new CsvToBeanBuilder(reader).withType(Item.class).build().parse();
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
