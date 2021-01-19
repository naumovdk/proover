package ru.ifmo.rain.naumov.parser;

import java.util.Map;

public class Implication extends BinaryOperation {
    public Implication(Expression lhs, Expression rhs) {
        super(lhs, rhs, "->", (a, b) -> !a || b);
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return false;
    }
}
