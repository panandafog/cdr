package org.example.Tariffs;

public class TariffsService {

    private static final double defaultMinuteCost = 1.5;
    private static final double simpleMinuteCost = 0.5;
    private static final double unlimitedAdditionalMinuteCost = 1.0;

    private static final long simpleMinutesLimit = 100;
    private static final long unlimitedMinutesLimit = 300;
    private static final long unlimitedBaseCost = 100;


    public static double getMinutesRubCost(long minutes) {
        return minutes * defaultMinuteCost;
    }

    public static double getSimpleMinutesRubCost(long minutes) {
        long simpleMinutes = Math.min(minutes, simpleMinutesLimit);
        return (simpleMinutes * simpleMinuteCost) + getMinutesRubCost(minutes - simpleMinutes);
    }

    public static double getUnlimitedMinutesRubCost(long minutes) {
        long additionalMinutes = Math.max(minutes - unlimitedMinutesLimit, 0);
        return unlimitedBaseCost + additionalMinutes * unlimitedAdditionalMinuteCost;
    }
}
