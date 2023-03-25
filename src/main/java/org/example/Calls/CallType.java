package org.example.Calls;

import org.example.Tariffs.TariffType;

public enum CallType {
    OUTGOING("01"),
    INCOMING("02");

    private final String id;
    public static int stringLength = 0;
    static {
        for(TariffType value: TariffType.values()) {
            stringLength = Math.max(stringLength, String.valueOf(value).length());
        }
    }

    CallType(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public static CallType fromString(String string) {
        switch (string) {
            case "01":
                return CallType.OUTGOING;
            case "02":
                return CallType.INCOMING;
        }
        return null;
    }
}
