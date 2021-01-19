package ru.ifmo.rain.naumov.implication;

import ru.ifmo.rain.naumov.*;

import java.util.ArrayList;
import java.util.List;

public class Implication extends BinaryOperation {
    public Implication(Expression lhs, Expression rhs) {
        super(lhs, rhs, "->", (a, b) -> !a || b);
    }

    @Override
    public List<String> getProof(HypothesisSet hypotheses) {
        if (this.evaluate(hypotheses)) {
            return FileReader.readFile("implication/B--AimpB", lhs, rhs);
        } else {
            return FileReader.readFile("implication/!A, B--!(AimpB)", lhs, rhs);
        }
    }
}
