package com.hjss.utilities;

public enum Gender {
    Male, Female, Other, Unknown;
    public static Gender fromString(String input) {
        if (input == null || input.isEmpty()) {
            return Gender.Unknown;
        }

        switch (input.trim().toLowerCase()) {
            case "m":
            case "male":
                return Gender.Male;
            case "f":
            case "female":
                return Gender.Female;
            case "o":
            case "other":
                return Gender.Other;
            default:
                return Gender.Unknown;
        }
    }
}
