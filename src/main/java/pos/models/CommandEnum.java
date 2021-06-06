package pos.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public enum CommandEnum {
    Exit("Exit", "Exits application", "x"),
    List("List", "Lists all available commands", "l"),
    ItemList("ItemList", "Lists all items", "i");

    @Getter
    String name;
    @Getter
    String description;
    @Getter
    String command;
}
