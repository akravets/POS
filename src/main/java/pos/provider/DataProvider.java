package pos.provider;

import lombok.Data;
import pos.models.Item;

import java.util.List;

/**
 * Provides access to {@link Item}s
 */
@Data
public class DataProvider {
    private List<Item> items;
}
