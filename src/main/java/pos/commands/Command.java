package pos.commands;

/**
 * Interface describing a Command.
 * Command is a unit of execution in a terminal.
 */
public interface Command {
    /**
     * Executes this {@link Command}
     */
    public void execute();

    /**
     * Name of the {@link Command}
     * @return Returns name for this {@link Command}
     */
    public String getName();

    /**
     * Description of the {@link Command}
     * @return Returns describtion for this {@link Command}
     */
    public String getDescription();

    /**
     * {@link Command} code
     * @return Returns code for this {@link Command}
     */
    public String getCommandCode();
}
