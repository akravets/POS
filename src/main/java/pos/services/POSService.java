package pos.services;

import org.springframework.stereotype.Service;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.exception.TaxRateNotFoundException;
import pos.models.Item;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Service that exposes various interactions with POS data
 */
@Service
public interface POSService {
    /**
     * Gets list of items
     * @return Return {@link Command}s
     * @throws URISyntaxException
     * @throws IOException
     */
    public Map<String, List<Item>> getItems() throws URISyntaxException, IOException;

    /**
     * Gets {@link Set} of {@link Command}s
     * @return Returns {@link Command}s
     */
    public Map<String,AbstractCommand> getCommands();

    /**
     * Given <code>commandCode</code> returns its {@link Command}
     * @param commandCode code for which {@link Command} to be returned
     * @return Returns {@link Command}
     */
    public Optional<AbstractCommand> getCommandByCode(String commandCode);

    /**
     * Returns either specific {@link Item} (in a {@link List}) that matches this <code>sku</code>, or {@link List}
     * of {@link Item}s if the sku entered is a partial sku
     * @param sku SKU or partial SKU for which {@link Item}s to be returned
     * @return Returns {@link List} of {@link Item}s
     */
    public List<Item> findItemBySKU(String sku);

    /**
     * Gets tax rate for jurisdiction
     * @param jurisdiction
     * @return Returns tax rate
     */
    public double getTaxRateByJurisdiction(String jurisdiction) throws TaxRateNotFoundException;
}
