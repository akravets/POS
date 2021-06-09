package pos.utils;


import pos.models.Item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class POSUtility {
    public static Map<String, List<Item>> groupData(List<Item> items){
        return items.stream().collect(Collectors.groupingBy(s -> s.getSku().substring(0, 3)));
    }
}
