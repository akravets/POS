package pos.models;

import org.springframework.stereotype.Component;

public enum TaxRate {
    CITY("city"),
    STATE("state"),
    COUNTY("county");

    private String name;

    TaxRate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
