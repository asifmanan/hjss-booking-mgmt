package com.hjss.utilities;

public enum BookingStatus {
    Active, Cancelled, Attended, Void;
    public static BookingStatus fromString(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        return switch (input.trim().toLowerCase()) {
            case "active" -> BookingStatus.Active;
            case "cancelled" -> BookingStatus.Cancelled;
            case "attended" -> BookingStatus.Attended;
            default -> BookingStatus.Void;
        };
    }
}
