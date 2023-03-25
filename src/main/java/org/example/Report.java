package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report {

    String phoneNumber;
    List<Call> calls;
    TariffType tariffType;
    double totalRUBCost;

    public Report(List<Call> calls) {
        phoneNumber = calls.get(0).phoneNumber;
        tariffType = calls.get(0).tariffType;
        this.calls = calls;

        calculateRUBCost();
    }

    public static List<Report> makeReports(List<Call> calls) {
        Map<String, List<Call>> callsMap = new HashMap<>();
        for (Call call: calls) {
            if (!callsMap.containsKey(call.phoneNumber)) {
                callsMap.put(call.phoneNumber, new ArrayList<>());
            }
            callsMap.get(call.phoneNumber).add(call);
        }

        List<Report> reports = new ArrayList<>();
        for (Map.Entry<String, List<Call>> entry : callsMap.entrySet()) {
            reports.add(new Report(entry.getValue()));
        }
        return reports;
    }

    public void calculateRUBCost() {
        totalRUBCost = 0.0;
        long totalMinutes = 0;

        for (Call call: calls) {
            totalMinutes += call.getDuration().toMinutes();
            double newTotalCost = totalRUBCost;

            switch (tariffType) {
                case MINUTE:
                    newTotalCost = TariffsService.getMinutesRubCost(totalMinutes);
                    break;
                case UNLIMITED:
                    newTotalCost = TariffsService.getUnlimitedMinutesRubCost(totalMinutes);
                    break;
                case SIMPLE:
                    newTotalCost = TariffsService.getSimpleMinutesRubCost(totalMinutes);
                    break;
            }

            call.rubCost = newTotalCost - totalRUBCost;
            totalRUBCost = newTotalCost;
        }
    }
}
