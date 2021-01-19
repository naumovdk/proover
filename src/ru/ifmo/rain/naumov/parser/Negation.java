package ru.ifmo.rain.naumov.parser;

import java.util.Map;

public class Negation implements Expression {
    private final Expression argument;

    public Negation(Expression argument) {
        this.argument = argument;
    }

    @Override
    public String toPrefix() {
        return "(" + "!" + argument.toPrefix() + ")";
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return !argument.evaluate(values);
    }
}
