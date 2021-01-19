package ru.ifmo.rain.naumov.parser;

public class Conjunction extends BinaryOperation {
    public Conjunction(Expression lhs, Expression rhs) {
        super(lhs, rhs, "&", (a, b) -> a && b);
    }
}
