package ru.ifmo.rain.naumov.deducer;

import ru.ifmo.rain.naumov.expression.BinaryOperation;
import ru.ifmo.rain.naumov.expression.Expression;
import ru.ifmo.rain.naumov.expression.Variable;
import ru.ifmo.rain.naumov.expression.Negation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Axiom {
    private final Expression body;
    private final Map<Variable, Expression> values = new HashMap<>();

    public Axiom(Expression body) {
        this.body = body;
    }

    private boolean matches(Expression subAxiom, Expression subExpression) {
        try {
            if (subAxiom instanceof BinaryOperation) {
                if (Objects.equals(((BinaryOperation) subAxiom).getSign(), ((BinaryOperation) subExpression).getSign())) {
                    return matches(((BinaryOperation) subAxiom).getLhs(), ((BinaryOperation) subExpression).getLhs())
                            && matches(((BinaryOperation) subAxiom).getRhs(), ((BinaryOperation) subExpression).getRhs());
                }
                return false;
            }
            if (subAxiom instanceof Negation) {
                return matches(((Negation) subAxiom).getArgument(), ((Negation) subExpression).getArgument());
            }
            if (subAxiom instanceof Variable) {
                if (values.containsKey(subAxiom)) {
                    return values.get(subAxiom).equals(subExpression);
                }
                values.put((Variable) subAxiom, subExpression);
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean matches(Expression expression) {
        values.clear();
        return matches(body, expression);
    }

    public String toString() {
        return body.toString();
    }
}
