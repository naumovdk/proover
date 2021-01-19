package ru.ifmo.rain.naumov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return '(' + name + ')';
    }

    @Override
    public String toPrefix() { return name; }

    @Override
    public boolean evaluate(HypothesisSet values) {
        return values.get(name);
    }

    @Override
    public List<String> getProof(HypothesisSet hypotheses) {
        return new ArrayList<>(Collections.singletonList(name));
    }

    @Override
    public List<String> getWholeProof(HypothesisSet hypotheses) {
        return getProof(hypotheses);
    }

    @Override
    public boolean equals(Expression other) {
        return other instanceof Variable
                && ((Variable) other).getName().equals(name);
    }

    public String getName() {
        return name;
    }
}
