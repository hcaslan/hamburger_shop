package com.kerem.utility;

import java.util.UUID;

public class CodeGenerator {
    public static String generateCode() {
        String uuidString = UUID.randomUUID().toString();
        StringBuilder combinedChars = new StringBuilder();

        String[] parts = uuidString.split("-");
        for (String part : parts) {
            combinedChars.append(part.charAt(0));
        }

        return combinedChars.toString();
    }
}
