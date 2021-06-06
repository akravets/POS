package pos.models;

import lombok.AllArgsConstructor;

/**
 * {@link Enum} for referencing tax categories
 */
@AllArgsConstructor
public enum TaxCategory {
    g("Groceries"),
    pf("Prepared Foods"),
    pd("Prescription Drugs"),
    nd("Non Prescription Drugs"),
    c("Clothing"),
    o("Other");

    String name;

}
