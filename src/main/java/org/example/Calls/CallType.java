package org.example.Calls;

public enum CallType {
    OUTGOING("01"),
    INCOMING("02");

    private final String id;

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
