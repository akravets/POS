package pos.services;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pos.Application;
import pos.models.Item;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
public class POSServiceTest {
    @Autowired
    public POSService service;

    @SneakyThrows
    @Test
    public void getItems(){
        List<Item> items = service.getItems();
        Assert.assertEquals(items.size(), 11);
    }
}
