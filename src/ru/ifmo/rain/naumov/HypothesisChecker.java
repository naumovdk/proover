package ru.ifmo.rain.naumov;

import ru.ifmo.rain.naumov.deducer.Deducer;
import ru.ifmo.rain.naumov.expression.Expression;
import ru.ifmo.rain.naumov.expression.Negation;
import ru.ifmo.rain.naumov.expression.Variable;

import java.util.*;

public class HypothesisChecker {

    static HypothesisSet findFewestHypothesisSet(Expression expression, Set<String> allVariables, boolean isNegated) {
        //noinspection ComparatorMethodParameterNotUsed
        return getAllSubsets(new ArrayDeque<>(allVariables)).stream()
                .filter((set) -> set.values().stream().allMatch((variable) -> variable == !isNegated))
                .filter((set) -> isSatisfying(expression, set, allVariables))
                .min((invoked, other) -> invoked.keySet().size() < other.keySet().size() ? -1 : 1).orElse(null);
    }

    static List<String> getProof(Expression expression, HypothesisSet hypotheses, Queue<String> unused) {
        if (unused.isEmpty()) {
            return expression.getWholeProof(hypotheses);
        }
        String next = unused.poll();
        HypothesisSet hypothesesCopy = new HypothesisSet(hypotheses);
        Queue<String> unusedCopy = new LinkedList<>(unused);
        hypotheses.put(next, true);
        hypothesesCopy.put(next, false);

//        var res = getProof(expression, hypotheses, unused);
//        res.addAll(getProof(expression, hypothesesCopy, unusedCopy));
//        return res;
        return Deducer.merge(expression,
                getProof(expression, hypotheses, unused), new Variable(next),
                getProof(expression, hypothesesCopy, unusedCopy), new Negation(new Variable(next)));
    }

    private static Set<HypothesisSet> getAllSubsets(Queue<String> unused) {
        Set<HypothesisSet> result;
        if (unused.isEmpty()) {
            result = new HashSet<>();
            result.add(new HypothesisSet());
            return result;
        }
        String next = unused.poll();
        result = getAllSubsets(unused);
        for (Map<String, Boolean> subset : new HashSet<>(result)) {
            HypothesisSet first = new HypothesisSet(subset);
            first.put(next, true);
            result.add(first);
            HypothesisSet second = new HypothesisSet(subset);
            second.put(next, false);
            result.add(second);
        }
        return result;
    }

    private static boolean isSatisfying(Expression expression, HypothesisSet hypotheses, Set<String> allVariables) {
        Set<String> unused = new HashSet<>(allVariables);
        unused.removeAll(hypotheses.keySet());
        if (unused.isEmpty()) {
            return expression.evaluate(hypotheses);
        }

        Queue<String> queuedUnused = new ArrayDeque<>(unused);
        String current = queuedUnused.poll();
        HypothesisSet first = new HypothesisSet(hypotheses);
        HypothesisSet second = new HypothesisSet(hypotheses);
        first.put(current, true);
        second.put(current, false);
        return isSatisfying(expression, first, allVariables) && isSatisfying(expression, second, allVariables);
    }
}
