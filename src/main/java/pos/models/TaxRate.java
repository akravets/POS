package pos.models;

import org.springframework.stereotype.Component;

public enum TaxRate {
    CITY("city",2.0),
    STATE("state",6.3),
    COUNTY("county",0.7);

    private String name;
    private double taxRate;

    TaxRate(String name, double taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }

    public String getName() {
        return name;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
