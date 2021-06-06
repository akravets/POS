package pos.commands;

public class ListItemsCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return "List";
    }

    @Override
    public String getDescription() {
        return "List items";
    }

    @Override
    public String getCommandCode() {
        return "l";
    }
}
