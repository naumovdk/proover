package ru.ifmo.rain.naumov.parser;

public class Disjunction extends BinaryOperation {
    public Disjunction(Expression lhs, Expression rhs) {
        super(lhs, rhs, "|", (a, b) -> a || b);
    }
}
