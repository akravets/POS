package pos.utils;


import pos.models.Item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class POSUtility {
    public static Map<String, List<Item>> groupData(List<Item> items){
        // TODO: Should really use AVL Tree here to guarantee O(log n) search performance because we map approach it's possible that all
        // items will be in one bucket (although not likely), and we'll have O(n) then
        return items.stream().collect(Collectors.groupingBy(s -> s.getSku().substring(0, 3)));
        //return items.stream().collect(Collectors.groupingBy(s -> s.getSku().substring(0, 3), Collectors.toMap(s -> s.getSku(), Function.identity())));
    }
}
