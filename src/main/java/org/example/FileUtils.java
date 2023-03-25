package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    private static final String reportsDirName = "reports";
    private static final String reportFileExtension = ".txt";
    private static final DateTimeFormatter cdrDateFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter reportDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String reportDurationFormat = "%d:%02d:%02d";

    static List<Call> readCallsFromFile() throws IOException {
        List<Call> calls = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("cdr.txt"));) {
            while (scanner.hasNextLine()) {
                calls.add(getCallFromLine(scanner.nextLine()));
            }
        }
        return calls;
    }

    static void writeReportsToFiles(List<Report> reports) throws IOException {
        for (Report report : reports) {
            writeFile(report);
        }
    }

    static String reportString(Report report) {
        List<String> callTypes = new ArrayList<>();
        List<String> dateTimesStart = new ArrayList<>();
        List<String> dateTimesEnd = new ArrayList<>();
        List<String> durations = new ArrayList<>();
        List<String> costs = new ArrayList<>();

        String callTypeTitle = "Call type";
        String dateTimeStartTitle = "Start time";
        String dateTimeEndTitle = "End time";
        String durationTitle = "Duration";
        String costTitle = "Cost";

        int callTypeLength = callTypeTitle.length();
        int dateTimeStartLength = dateTimeStartTitle.length();
        int dateTimeEndLength = dateTimeEndTitle.length();
        int durationLength = durationTitle.length();
        int costLength = costTitle.length();

        for (Call call : report.calls) {
            String callType = String.valueOf(call.callType);
            String dateTimeStart = call.dateTimeStart.format(reportDateFormatter);
            String dateTimeEnd = call.dateTimeEnd.format(reportDateFormatter);
            String duration = durationString(call.getDuration());
            String cost = String.valueOf(call.rubCost);

            callTypeLength = Math.max(callType.length(), callTypeLength);
            dateTimeStartLength = Math.max(dateTimeStart.length(), dateTimeStartLength);
            dateTimeEndLength = Math.max(dateTimeEnd.length(), dateTimeEndLength);
            durationLength = Math.max(duration.length(), durationLength);
            costLength = Math.max(cost.length(), costLength);

            callTypes.add(callType);
            dateTimesStart.add(dateTimeStart);
            dateTimesEnd.add(dateTimeEnd);
            durations.add(duration);
            costs.add(cost);
        }

        for (int index = 0; index < report.calls.size(); index++) {
            callTypes.set(
                    index,
                    callTypes.get(index) +
                            emptyString(callTypeLength - callTypes.get(index).length())
            );
            dateTimesStart.set(
                    index,
                    dateTimesStart.get(index) +
                            emptyString(dateTimeStartLength - dateTimesStart.get(index).length())
            );
            dateTimesEnd.set(
                    index,
                    dateTimesEnd.get(index) +
                            emptyString(dateTimeEndLength - dateTimesEnd.get(index).length())
            );
            durations.set(
                    index,
                    durations.get(index) +
                            emptyString(durationLength - durations.get(index).length())
            );
            costs.set(
                    index,
                    costs.get(index) +
                            emptyString(costLength - costs.get(index).length())
            );
        }

        callTypeTitle += emptyString(callTypeLength - callTypeTitle.length());
        dateTimeStartTitle += emptyString(dateTimeStartLength - dateTimeStartTitle.length());
        dateTimeEndTitle += emptyString(dateTimeEndLength - dateTimeEndTitle.length());
        durationTitle += emptyString(durationLength - durationTitle.length());
        costTitle += emptyString(costLength - costTitle.length());

        StringBuilder text = new StringBuilder("Tariff: " + report.tariffType + "\n");
        text.append("Phone number: ").append(report.phoneNumber).append("\n");

        String horisontalSeparator = " | ";
        int separatorsNumber = 6;
        int lineLength = callTypeLength + dateTimeStartLength + dateTimeEndLength + durationLength + costLength +
                separatorsNumber * horisontalSeparator.length();
        String linesSeparator = "–".repeat(lineLength) + "\n";

        text.append(linesSeparator);

        for (int index = 0; index < report.calls.size(); index++) {
            text.append(horisontalSeparator);
            text.append(callTypes.get(index)).append(horisontalSeparator);
            text.append(dateTimesStart.get(index)).append(horisontalSeparator);
            text.append(dateTimesEnd.get(index)).append(horisontalSeparator);
            text.append(durations.get(index)).append(horisontalSeparator);
            text.append(costs.get(index)).append(horisontalSeparator).append("\n");
        }
        text.append(linesSeparator);
        text.append("total cost: ").append(report.totalRUBCost).append(" RUB").append("\n");
        return text.toString();
    }

    private static String emptyString(int spacesCount) {
        return " ".repeat(spacesCount);
    }

    private static String durationString(Duration duration) {
        return String.format(
                reportDurationFormat,
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart()
        );
    }

    private static Call getCallFromLine(String line) {
        List<String> params = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(", ");
            while (rowScanner.hasNext()) {
                params.add(rowScanner.next());
            }
        }
        return callFromParams(params);
    }

    private static void writeFile(Report report) throws IOException {
        String dirName = reportsDirName;

        String fileName = report.phoneNumber + reportFileExtension;
        File dir = new File(dirName);
        File actualFile = new File(dir, fileName);

        Files.createDirectories(Paths.get(dirName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(actualFile));

        writer.write(reportString(report));

        writer.close();
    }

    private static Call callFromParams(List<String> params) {
        return new Call(
                CallType.fromString(params.get(0)),
                params.get(1),
                LocalDateTime.parse(params.get(2), cdrDateFormatter),
                LocalDateTime.parse(params.get(3), cdrDateFormatter),
                TariffType.fromString(params.get(4))
        );
    }
}
