package pos.services;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pos.Application;
import pos.commands.AbstractCommand;
import pos.commands.CommandProvider;
import pos.models.Item;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class POSServiceTest {
    @Autowired
    public POSService service;

    /**
     * Tests getCommands() method
     */
    @Test
    public void getItems() throws URISyntaxException, IOException {
        List<Item> items = service.getItems();
        Assert.assertEquals(items.size(), 11);
    }

    /**
     * Tests getCommands() method
     */
    @Test
    public void getCommands() {
        Set<AbstractCommand> commands = service.getCommands();
        Assert.assertEquals(commands.size(), 2);
    }
}
