package com.hjss.utilities;

import java.util.List;
import java.util.Map;

public class TablePrinter {
    private List<String> headers;
    private Map<String, Integer> columnWidths;
    public TablePrinter(List<String> headers,
                        Map<String, Integer> columnWidths){

        this.headers = headers;
        this.columnWidths = columnWidths;

    }
    private static String truncateString(String data, int width) {
        if (data != null && data.length() > width) {
            return data.substring(0, width);
        }
        return data == null ? "" : data;
    }

    public void printHeader() {
        for (String header : this.headers) {
            Integer width = this.columnWidths.get(header);
            if (width == null) {
                width = 10;
            }
            System.out.printf("%-" + width + "s", truncateString(header, width));
        }
        System.out.println();
    }

    public void printRow(List<String> rowData) {
        if (rowData.size() != this.headers.size()) {
            System.out.println("Row data and headers do not match.");
            return;
        }
        for (int i = 0; i < rowData.size(); i++) {
            String data = rowData.get(i);
            String header = this.headers.get(i);
            Integer width = this.columnWidths.get(header);
            if (width == null) {
                width = 10;
            }
            System.out.printf("%-" + width + "s", truncateString(data, width));
        }
        System.out.println();
    }
}
