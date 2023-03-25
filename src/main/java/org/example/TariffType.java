package org.example;

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

    public static TariffType fromString(String string) {
        switch (string) {
            case "03":
                return TariffType.MINUTE;
            case "06":
                return TariffType.UNLIMITED;
            case "11":
                return TariffType.SIMPLE;
        }
        return null;
    }
}
