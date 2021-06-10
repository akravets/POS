package pos.provider;

import lombok.Data;
import org.springframework.stereotype.Repository;
import pos.models.Item;

import java.util.List;
import java.util.Map;

/**
 * Provides access to {@link Item}s
 */
@Data
public class DataProvider {
    private Map<String, Map<String, Item>> items;
}
