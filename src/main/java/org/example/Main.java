package org.example;

import org.example.Calls.Call;
import org.example.Reports.Report;
import org.example.Utils.FileUtils;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Call> calls = FileUtils.readCallsFromFile();
        List<Report> reports = Report.makeReports(calls);
        FileUtils.writeReportsToFiles(reports);
    }
}