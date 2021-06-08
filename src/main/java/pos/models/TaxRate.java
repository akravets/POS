package pos.models;

/**
 * Defines tax rates for municipalities
 */
public enum TaxRate {
    CITY("city",2.0),
    STATE("state",6.3),
    COUNTY("county",0.7);

    // tax name
    private String name;

    // tax rate
    private double taxRate;

    /**
     * Constructor
     *
     * @param name tax name
     * @param taxRate tax rate
     */
    TaxRate(String name, double taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }

    /**
     * Get tax name
     * @return Returns tax name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets tax rate
     * @return Returns tax rate
     */
    public double getTaxRate() {
        return taxRate;
    }
}
