package pos.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pos.Application;
import pos.commands.AbstractCommand;
import pos.models.Item;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class POSServiceTest {
    @Autowired
    public POSService service;


    @Test
    public void getItems() throws URISyntaxException, IOException {
        final List<Item> items = service.getItems();
        assertEquals(items.size(), 11);
    }


    @Test
    public void getCommands() {
        final Set<AbstractCommand> commands = service.getCommands();
        assertEquals(commands.size(), 2);
    }


    @Test
    public void findItemBySKU_withOneMatch(){
        final Set<Item> items = service.findItemBySKU("305730179201");
        assertEquals(items.size(), 1);
    }

    @Test
    public void findItemBySKU_withManyMatches(){
        final Set<Item> items = service.findItemBySKU("305730");
        assertEquals(items.size(), 2);
    }

    @Test
    public void findItemBySKU_withNoMatches(){
        final Set<Item> items = service.findItemBySKU(String.valueOf(Integer.MIN_VALUE));
        assertEquals(items.size(), 0);
    }
}
