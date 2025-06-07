package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ViewsPerSubscriberReducer {
    public static void run(String inputFilePath, String outputFilePath) throws IOException {
        System.out.println("Reducer: Calculating average subscriber count by channel ID (Top 50)...");

        Map<String, List<Long>> map = new HashMap<>();

        // Read CSV input
        try (Reader reader = new FileReader(inputFilePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                String channelId = record.get("channelId");
                String subscriberCountStr = record.get("subscriberCount").replaceAll(",", "").trim();

                if (!subscriberCountStr.isEmpty()) {
                    try {
                        long subscriberCount = Long.parseLong(subscriberCountStr);
                        map.computeIfAbsent(channelId, k -> new ArrayList<>()).add(subscriberCount);
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid subscriber count: " + subscriberCountStr);
                    }
                }
            }
        }

        // Calculate averages
        Map<String, Double> averageMap = new HashMap<>();
        for (Map.Entry<String, List<Long>> entry : map.entrySet()) {
            double avg = entry.getValue().stream().mapToLong(Long::longValue).average().orElse(0.0);
            averageMap.put(entry.getKey(), avg);
        }

        // Sort by average subscriber count descending and limit to top 50
        List<Map.Entry<String, Double>> sortedTop50 = averageMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(50)
                .collect(Collectors.toList());

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Top 50 Channels");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Channel ID");
        header.createCell(1).setCellValue("Average Subscriber Count");

        // Write data rows and print on terminal
        int rowNum = 1;
        System.out.printf("%-5s %-30s %-25s%n", "Rank", "Channel ID", "Average Subscriber Count");
        System.out.println("--------------------------------------------------------------------------");

        int rank = 1;
        for (Map.Entry<String, Double> entry : sortedTop50) {
            String channelId = entry.getKey();
            double avg = entry.getValue();

            // Print to terminal
            System.out.printf("%-5d %-30s %-25.2f%n", rank, channelId, avg);

            // Write to Excel
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(channelId);
            row.createCell(1).setCellValue(avg);

            rank++;
        }
    }
}