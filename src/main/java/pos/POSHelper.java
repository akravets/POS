package pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.models.TaxRate;
import pos.services.POSService;

import java.util.Set;

/**
 * Contains a set of helper methods that otherwise don't fit into specific category
 */
public class POSHelper {
    @Autowired
    private POSService service;

    @Autowired
    private Environment environment;

    public POSHelper() {}

    public void printAllCommands(){
        Set<AbstractCommand> commandSet = service.getCommands();

        System.out.println("POS Commands");
        System.out.println("============\n");

        commandSet.forEach(c -> {
            StringBuffer buffer = new StringBuffer();
            buffer.append(c.getName());
            buffer.append(" (");
            buffer.append(c.getCommandCode());
            buffer.append(")");

            String partOne = String.format("%-" + 20 + "s", buffer.toString());

            System.out.println(partOne + c.getDescription());
        });
    }

    public double getTaxRateForTaxRateEnum(TaxRate taxRateEnum) throws NumberFormatException{
        return Double.valueOf(environment.getProperty("pos.tax-rate:" + taxRateEnum.getName()));
    }
}
