package pos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaxCategory {
    Groceries("Groceries", "g"),
    PreparedFood("Prepared Food", "pf"),
    PrescriptionDrug("Prescription Drug", "pd"),
    NonPrescriptionDrug("NonPrescriptionDrug", "nd"),
    Clothing("Clothing", "c"),
    Other("Other", "o");

    @Getter
    private String name;
    @Getter
    private String code;
}
