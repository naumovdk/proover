package ru.ifmo.rain.naumov.parser;

import java.util.Map;

public interface Expression {
    String toString();
    String toPrefix();
    boolean evaluate(Map<String, Boolean> values);
}
