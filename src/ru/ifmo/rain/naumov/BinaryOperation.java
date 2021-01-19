package ru.ifmo.rain.naumov;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class BinaryOperation implements Expression {
    protected final Expression lhs;
    protected final Expression rhs;
    private final String sign;
    private final BiFunction<Boolean, Boolean, Boolean> calculate;

    public BinaryOperation(Expression lhs, Expression rhs, String sign, BiFunction<Boolean, Boolean, Boolean> calculate) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.sign = sign;
        this.calculate = calculate;
    }

    @Override
    public boolean evaluate(HypothesisSet values) {
        return calculate.apply(lhs.evaluate(values), rhs.evaluate(values));
    }

    public List<String> getWholeProof(HypothesisSet hypotheses) {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(lhs.getWholeProof(hypotheses));
        result.addAll(rhs.getWholeProof(hypotheses));
        result.addAll(getProof(hypotheses));
        return result;
    }

    @Override
    public boolean equals(Expression other) {
        return other instanceof BinaryOperation
                && ((BinaryOperation) other).sign.equals(sign)
                && lhs.equals(((BinaryOperation) other).lhs)
                && rhs.equals(((BinaryOperation) other).rhs);
    }

    @Override
    public String toPrefix() {
        return "(" + this.sign + "," + lhs.toPrefix() + "," + rhs.toPrefix() + ")";
    }

    @Override
    public String toString() {
        return "(" + lhs.toString() + sign + rhs.toString() + ")";
    }

    public String getSign() {
        return sign;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }
}
