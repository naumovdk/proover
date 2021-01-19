package ru.ifmo.rain.naumov;


import ru.ifmo.rain.naumov.expression.Expression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    private static final String letters = "abc";

    public static List<String> readFile(String file, Expression... expressions) {
        try {
            List<String> text = Files.readAllLines(Paths.get("resources/" + file));
            int i = 0;
            for (Expression ignored : expressions) {
                int finalI = i;
                text = text.stream()
                        .map(s -> s.replaceAll(String.valueOf(letters.charAt(finalI)), String.valueOf(finalI)))
                        .collect(Collectors.toList());
                i++;
            }
            i = 0;
            for (Expression e : expressions) {
                int finalI = i;
                text = text.stream()
                        .map(s -> s.replaceAll(String.valueOf(finalI), e.toString()))
                        .collect(Collectors.toList());
                i++;
            }
            return text;
        } catch (IOException ignored) {
            System.out.println("brth");
            return null;
        }
    }
}
