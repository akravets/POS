package pos.services;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.commands.CommandProvider;
import pos.models.CommandEnum;
import pos.models.Item;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class POSServiceImpl implements POSService {
    @Autowired
    public CommandProvider commandProvider;

    @Override
    public List<Item> getItems() throws URISyntaxException, IOException {
        File dataFile = ResourceUtils.getFile("classpath:data.csv");
        Reader reader = Files.newBufferedReader(Paths.get(dataFile.toURI()));
        return new CsvToBeanBuilder(reader).withType(Item.class).build().parse();
    }

    @Override
    public Set<AbstractCommand> getCommands() {
        return commandProvider.getcommands();
    }

    @Override
    public Optional<AbstractCommand> getCommandByCode(String commandCode) {
        return Optional.empty();
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
}
