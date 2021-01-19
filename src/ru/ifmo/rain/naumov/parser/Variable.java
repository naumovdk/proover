package ru.ifmo.rain.naumov.parser;

import java.util.Map;

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toPrefix() { return name; }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return values.get(name);
    }
}
