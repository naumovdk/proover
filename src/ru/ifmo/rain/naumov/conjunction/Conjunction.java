package ru.ifmo.rain.naumov.conjunction;

import ru.ifmo.rain.naumov.BinaryOperation;
import ru.ifmo.rain.naumov.Expression;
import ru.ifmo.rain.naumov.FileReader;
import ru.ifmo.rain.naumov.HypothesisSet;

import java.util.ArrayList;
import java.util.List;

public class Conjunction extends BinaryOperation {
    public Conjunction(Expression lhs, Expression rhs) {
        super(lhs, rhs, "&", (a, b) -> a && b);
    }

    @Override
    public List<String> getProof(HypothesisSet hypotheses) {
        if (this.evaluate(hypotheses)) {
            return FileReader.readFile("conjunction/A, B--A&B", lhs, rhs);
        } else {
            if (!lhs.evaluate(hypotheses)) {
                return FileReader.readFile("conjunction/!A--!(A&B)", lhs, rhs);
            } else {
                return FileReader.readFile("conjunction/!B--!(A&B)", lhs, rhs);
            }
        }
    }
}
