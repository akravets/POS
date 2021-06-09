package pos.provider;

import lombok.Data;
import pos.models.Item;

import java.util.List;
import java.util.Map;

/**
 * Provides access to {@link Item}s
 */
@Data
public class DataProvider {
    private Map<String, List<Item>> items;
}
