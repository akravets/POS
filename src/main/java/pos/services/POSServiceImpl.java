package pos.services;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
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
    public Set<Command> getCommands() {
        return commandProvider.getcommands();
    }
}
