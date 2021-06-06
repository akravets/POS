package pos.services;

import pos.commands.Command;
import pos.models.CommandEnum;
import pos.models.Item;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

public interface POSService {
    public List<Item> getItems() throws URISyntaxException, IOException;
    public Set<Command> getCommands();
}
