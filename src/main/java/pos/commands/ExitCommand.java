package pos.commands;

import pos.models.CommandEnum;

public class ExitCommand implements Command{
    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public String getDescription() {
        return "Exits application";
    }

    @Override
    public String getCommandCode() {
        return "exit";
    }
}
