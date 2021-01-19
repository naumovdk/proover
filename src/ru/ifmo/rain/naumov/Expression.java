package ru.ifmo.rain.naumov;

import java.util.List;

public interface Expression {
    String toString();
    String toPrefix();
    boolean evaluate(HypothesisSet values);
    List<String> getProof(HypothesisSet hypotheses);
    List<String> getWholeProof(HypothesisSet hypotheses);
    boolean equals(Expression other);
}
