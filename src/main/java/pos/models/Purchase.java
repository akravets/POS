package pos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Value
@Component
public class Purchase {
    List<Item> items;

    public Purchase() {
        items = new LinkedList<>();
    }
}
