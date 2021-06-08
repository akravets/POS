package pos.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.models.TaxRate;
import pos.services.POSService;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

/**
 * Contains a set of helper methods that otherwise don't fit into specific category
 */
public class POSHelper {
    private final DecimalFormat df;
    @Autowired
    private POSService service;

    @Autowired
    private Environment environment;

    public POSHelper() {
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
    }

    /**
     * Prints all commands to the console
     */
    public void printAllCommands() {
        Map<String, AbstractCommand> commandMap = service.getCommands();

        System.out.println("POS Commands");
        System.out.println("============\n");

        commandMap.values().stream().forEach(c -> {
            StringBuffer buffer = new StringBuffer();
            buffer.append(c.getName());
            buffer.append(" (");
            buffer.append(c.getCommandCode());
            buffer.append(")");

            String partOne = String.format("%-" + 20 + "s", buffer.toString());

            System.out.println(partOne + c.getDescription());
        });
    }

    /**
     * Formats double in form #.##
     * @param price Price to be formatted
     * @return Returns formatted price
     */
    public double formatPrice(double price){
       return Double.valueOf(df.format(price));
    }
}
