package org.example.Tariffs;

import java.util.HashMap;

public enum TariffType {
    MINUTE("03"),
    UNLIMITED("06"),
    SIMPLE("11");

    private final String id;

    TariffType(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    private static final HashMap<String, TariffType> valuesMap = new HashMap<>();

    static {
        for(TariffType value: TariffType.values()) {
            valuesMap.put(value.getID(), value);
        }
    }

    public static TariffType fromString(String string) {
        return valuesMap.get(string);
    }
}
