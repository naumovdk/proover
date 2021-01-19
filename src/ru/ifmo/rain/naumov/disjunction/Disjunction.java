package ru.ifmo.rain.naumov.disjunction;

import ru.ifmo.rain.naumov.BinaryOperation;
import ru.ifmo.rain.naumov.Expression;
import ru.ifmo.rain.naumov.FileReader;
import ru.ifmo.rain.naumov.HypothesisSet;

import java.util.ArrayList;
import java.util.List;

public class Disjunction extends BinaryOperation {
    public Disjunction(Expression lhs, Expression rhs) {
        super(lhs, rhs, "|", (a, b) -> a || b);
    }

    @Override
    public List<String> getProof(HypothesisSet hypotheses) {
        if (!this.evaluate(hypotheses)) {
            return FileReader.readFile("disjunction/!A, !B--!(AorB)", lhs, rhs);
        } else if (lhs.evaluate(hypotheses)) {
            return FileReader.readFile("disjunction/A--AorB", lhs, rhs);
        } else {
            return FileReader.readFile("disjunction/B--AorB", lhs, rhs);
        }
    }
}
