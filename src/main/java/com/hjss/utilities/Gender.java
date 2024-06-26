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

        return switch (input.trim().toLowerCase()) {
            case "m", "male" -> Gender.Male;
            case "f", "female" -> Gender.Female;
            case "o", "other" -> Gender.Other;
            default -> Gender.Unknown;
        };
    }
    public static List<String> getGenderValues() {
        return Arrays.stream(Gender.values())
                     .map(Enum::name)
                     .collect(Collectors.toList());
    }
}
