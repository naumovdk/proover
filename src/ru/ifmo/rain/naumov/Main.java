package ru.ifmo.rain.naumov;

import ru.ifmo.rain.naumov.parser.Expression;
import ru.ifmo.rain.naumov.parser.Parser;
import ru.ifmo.rain.naumov.parser.Tokenizer;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tokenizer tokenizer = new Tokenizer(stringBuilder.toString());
        Parser parser = new Parser(tokenizer);
        Expression result = parser.parse();
        System.out.println(result.toPrefix());
    }
}
