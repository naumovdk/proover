package ru.ifmo.rain.naumov.parser;

import java.util.Map;
import java.util.function.BiFunction;

public abstract class BinaryOperation implements Expression {
    private final Expression lhs;
    private final Expression rhs;
    private final String sign;
    private final BiFunction<Boolean, Boolean, Boolean> calculate;

    @Override
    public String toString() {
        return null;
    }

    public String toPrefix() {
        return "(" + this.sign + "," + lhs.toPrefix() + "," + rhs.toPrefix() + ")";
    }

    public BinaryOperation(Expression lhs, Expression rhs, String sign, BiFunction<Boolean, Boolean, Boolean> calculate) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.sign = sign;
        this.calculate = calculate;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> values) {
        return calculate.apply(lhs.evaluate(values), rhs.evaluate(values));
    }
}
