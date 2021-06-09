package pos.services;

import org.springframework.stereotype.Service;
import pos.commands.AbstractCommand;
import pos.commands.Command;
import pos.exception.TaxRateNotFoundException;
import pos.models.Item;
import pos.models.TaxRate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
    public Optional<List<Item>> findItemBySKU(String sku);

    /**
     * Applies tax rate as defined in {@link TaxRate} for each {@link Item} in <code>items</code>
     * @param items {@link List} of {@link Item}s for which tax to be calculated
     * @return Returns <code>Map<TaxRate, Double></code>
     */
    public Map<Item, Map<TaxRate, Double>> getTaxForEachItem(List<Item> items);

    /**
     * Combines tax for all items across all jurisdictions
     * @param items <code>List</code> of {@link Item}s for which total tax to be calculated
     * @return Returns combined tax
     */
    public double getTaxForAllItems(List<Item> items);

    /**
     * Groups total tax by jurisdiction across all items
     * @param items items <code>List</code> of {@link Item}s for which total tax to be grouped
     * @return Returns <code>Map</code> of <code>TaxRate</code> and <code>Double</code> where value is total tax of each
     * jurisdiction across all items
     */
    public Map<TaxRate, Double> groupTotalTaxByJurisdiction(List<Item> items);
}
