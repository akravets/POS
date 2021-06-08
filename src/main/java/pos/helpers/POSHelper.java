package pos.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.models.TaxRate;
import pos.services.POSService;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Contains a set of helper methods that otherwise don't fit into specific category
 */
public class POSHelper {
    private final DecimalFormat df;
    private final NumberFormat nf;
    @Autowired
    private POSService service;

    @Autowired
    private Environment environment;

    public POSHelper() {
        df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.CEILING);

        Locale locale = Locale.ENGLISH;

        nf = NumberFormat.getNumberInstance(locale);
// for trailing zeros:
        nf.setMinimumFractionDigits(2);
// round to 2 digits:
        nf.setMaximumFractionDigits(2);
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
    public String formatPrice(double price){
       return nf.format(price);
    }

    public String pad(int padding, String value){
        return String.format("%-" + padding + "s", value);
    }
}
