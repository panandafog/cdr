package org.example.Utils;

import org.example.Calls.Call;
import org.example.Calls.CallType;
import org.example.Reports.Report;
import org.example.Tariffs.TariffType;

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
import java.util.function.Consumer;

public class FileUtils {

    private static final String reportsDirName = "reports";
    private static final String reportFileExtension = ".txt";
    private static final DateTimeFormatter cdrDateFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter reportDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String reportDurationFormat = "%02d:%02d:%02d";
    private static final String costFormat = "%,.2f";
    private static final int datetimeStringLength = datetimeString(LocalDateTime.now()).length();

    public static List<Call> readCallsFromFile() throws IOException {
        List<Call> calls = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("cdr.txt"));) {
            while (scanner.hasNextLine()) {
                calls.add(getCallFromLine(scanner.nextLine()));
            }
        }
        return calls;
    }

    public static void writeReportsToFiles(List<Report> reports) throws IOException {
        for (Report report : reports) {
            writeFile(report);
        }
    }

    static void reportStrings(Report report, Consumer<String> rowHandler) {
        String callTypeTitle = "Call type";
        String dateTimeStartTitle = "Start time";
        String dateTimeEndTitle = "End time";
        String durationTitle = "Duration";
        String costTitle = "Cost";

        int callTypeLength = Math.max(callTypeTitle.length(), CallType.stringLength);
        int dateTimeStartLength = Math.max(dateTimeStartTitle.length(), datetimeStringLength);
        int dateTimeEndLength = Math.max(dateTimeEndTitle.length(), datetimeStringLength);
        int durationLength = durationTitle.length();
        int costLength = costTitle.length();

        double maxCost = 0.0;
        Duration maxDuration = Duration.ZERO;

        for (Call call : report.calls) {
            maxCost = Math.max(call.rubCost, maxCost);

            Duration duration = call.getDuration();
            if (duration.compareTo(maxDuration) > 0) {
                maxDuration = duration;
            }
        }
        costLength = Math.max(costString(maxCost).length(), costLength);
        durationLength = Math.max(durationString(maxDuration).length(), durationLength);

        rowHandler.accept("Tariff: " + report.tariffType + "\n");
        rowHandler.accept("Phone number: ");
        rowHandler.accept(report.phoneNumber);
        rowHandler.accept("\n");

        String horisontalSeparator = " | ";
        int separatorsNumber = 6;
        int lineLength = callTypeLength + dateTimeStartLength + dateTimeEndLength + durationLength + costLength +
                separatorsNumber * horisontalSeparator.length();
        String linesSeparator = "–".repeat(lineLength) + "\n";

        rowHandler.accept(linesSeparator);

        rowHandler.accept(horisontalSeparator);
        rowHandler.accept(stringWithLength(callTypeTitle, callTypeLength));
        rowHandler.accept(horisontalSeparator);
        rowHandler.accept(stringWithLength(dateTimeStartTitle, dateTimeStartLength));
        rowHandler.accept(horisontalSeparator);
        rowHandler.accept(stringWithLength(dateTimeEndTitle, dateTimeEndLength));
        rowHandler.accept(horisontalSeparator);
        rowHandler.accept(stringWithLength(durationTitle, durationLength));
        rowHandler.accept(horisontalSeparator);
        rowHandler.accept(stringWithLength(costTitle, costLength));
        rowHandler.accept(horisontalSeparator);
        rowHandler.accept("\n");

        rowHandler.accept(linesSeparator);

        for (Call call : report.calls) {
            rowHandler.accept(horisontalSeparator);
            rowHandler.accept(stringWithLength(call.callType.toString(), callTypeLength));
            rowHandler.accept(horisontalSeparator);
            rowHandler.accept(stringWithLength(datetimeString(call.dateTimeStart), dateTimeStartLength));
            rowHandler.accept(horisontalSeparator);
            rowHandler.accept(stringWithLength(datetimeString(call.dateTimeEnd), dateTimeEndLength));
            rowHandler.accept(horisontalSeparator);
            rowHandler.accept(stringWithLength(durationString(call.getDuration()), durationLength));
            rowHandler.accept(horisontalSeparator);
            rowHandler.accept(stringWithLength(costString(call.rubCost), costLength));
            rowHandler.accept(horisontalSeparator);
            rowHandler.accept("\n");
        }

        rowHandler.accept(linesSeparator);
        rowHandler.accept("total cost: ");
        rowHandler.accept(String.valueOf(report.totalRUBCost));
        rowHandler.accept(" RUB");
        rowHandler.accept("\n");
    }

    private static String stringWithLength(String string, long length) {
        return String.format("%1$" + length + "s", string);
    }

    private static String datetimeString(LocalDateTime dateTime) {
        return dateTime.format(reportDateFormatter);
    }

    private static String costString(double cost) {
        return String.format(costFormat, cost);
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
        List<String> params = new ArrayList<>();
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
        Consumer<String> rowConsumer = (row) -> {
            try {
                writer.write(row);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        reportStrings(report, rowConsumer);

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
