package ru.ifmo.rain.naumov.expression;

import ru.ifmo.rain.naumov.FileReader;
import ru.ifmo.rain.naumov.HypothesisSet;

import java.util.List;

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
    public boolean evaluate(HypothesisSet values) {
        return !argument.evaluate(values);
    }

    @Override
    public List<String> getProof(HypothesisSet hypotheses) {
        if (this.evaluate(hypotheses)) {
            return argument.getProof(hypotheses);
        } else {
            return FileReader.readFile("negation/A--!!A", argument);
        }
    }

    @Override
    public List<String> getWholeProof(HypothesisSet hypotheses) {
        List<String> result = argument.getWholeProof(hypotheses);
        result.addAll(getProof(hypotheses));
        return result;
    }

    @Override
    public boolean equals(Expression other) {
        return other instanceof Negation
                && (((Negation) other).argument.equals(argument));
    }

    @Override
    public String toString() {
        return "!" + argument.toString();
    }

    public Expression getArgument() {
        return argument;
    }
}
