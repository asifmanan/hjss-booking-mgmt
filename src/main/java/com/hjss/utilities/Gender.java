package com.hjss.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public static List<String> getGenderValues() {
        return Arrays.stream(Gender.values())
                     .map(Enum::name)
                     .collect(Collectors.toList());
    }
}
