package ru.ifmo.rain.naumov.deducer;

import ru.ifmo.rain.naumov.*;
import ru.ifmo.rain.naumov.expression.Implication;
import ru.ifmo.rain.naumov.expression.Expression;
import ru.ifmo.rain.naumov.expression.Negation;
import ru.ifmo.rain.naumov.expression.Variable;
import ru.ifmo.rain.naumov.parser.Parser;
import ru.ifmo.rain.naumov.parser.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Deducer {
    private static final List<Axiom> axioms = new ArrayList<>();

    public static void init() {
        axioms.add(new Axiom(parse("a->b->a")));
        axioms.add(new Axiom(parse("(a->b)->(a->b->y)->(a->y)")));

        axioms.add(new Axiom(parse("a&b->a")));
        axioms.add(new Axiom(parse("a&b->b")));
        axioms.add(new Axiom(parse("a->b->a&b")));

        axioms.add(new Axiom(parse("a->a|b")));
        axioms.add(new Axiom(parse("b->a|b")));
        axioms.add(new Axiom(parse("(a->y)->(b->y)->(a|b->y)")));

        axioms.add(new Axiom(parse("(a->b)->(a->!b)->!a")));
        axioms.add(new Axiom(parse("!!a->a")));

    }

    public static List<String> merge(Expression expression, List<String> proof1, Expression free1, List<String> proof2, Expression free2) {
        List<String> res = deduce(free1, proof1);
        res.addAll(deduce(free2, proof2));
        res.addAll(Objects.requireNonNull(FileReader.readFile("deducer/merge", free1, free2, expression)));
        res.addAll(Objects.requireNonNull(FileReader.readFile("deducer/--Aor!A", free1, free2)));
        res.add(expression.toString());
        return res;
    }

    private static List<String> contraposition(Implication expression) {
        return FileReader.readFile("deducer/contraposition", expression.getLhs(), expression.getRhs());
    }

    public static List<String> deduce(Expression assumption, List<String> proof, Expression... proven) {
        init();
        List<String> newProof = new ArrayList<>();
        // whole context???
        for (Expression p : proven) {
            processAxiom(assumption, p, newProof);
        }
        for (Expression current : proof.stream().map(Deducer::parse).collect(Collectors.toList())) {
            if (isAxiom(current)) {
                processAxiom(assumption, current, newProof);
            } else if (current.equals(assumption)) {
                processAimpA(current, newProof);
            } else {
                Expression deltaJ = findDeltaJ(assumption, current, newProof);
                if (deltaJ != null) {
                    processModusPonens(assumption, deltaJ, current, newProof);
                } else if (current instanceof Variable
                        || current instanceof Negation && ((Negation) current).getArgument() instanceof Variable) {
                    processAxiom(assumption, current, newProof);
                } else {
                    System.err.println("couldnt deduce " + current.toString());
                }
            }
        }
        return newProof;
    }

    private static boolean isAxiom(Expression expression) {
        return axioms.stream().anyMatch(a -> a.matches(expression));
    }

    private static Expression findDeltaJ(Expression assumption, Expression deltaI, List<String> newProof) {
        for (int i = newProof.size() - 1; i >= 0; i--) {
            Expression expression = parse(newProof.get(i));
            if (expression instanceof Implication) {
                Implication current = (Implication) expression; // assumption->deltaL
                if (current.getRhs() instanceof Implication) {
                    Implication deltaL = (Implication) current.getRhs();
                    Expression deltaJ = deltaL.getLhs();
                    if (deltaL.getRhs().equals(deltaI)) {
                        if (contains(new Implication(assumption, deltaJ), newProof)) {
                            return deltaJ;
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean contains(Expression expression, List<String> proof) {
        for (int i = proof.size() - 1; i >= 0; i--) {
            if (parse(proof.get(i)).equals(expression)) {
                return true;
            }
        }
        System.err.println("not found " + expression.toString());
        return false;
    }

    private static void processAxiom(Expression assumption, Expression expression, List<String> newProof) {
        newProof.addAll(Objects.requireNonNull(FileReader.readFile("deducer/--AimpAxiom", assumption, expression)));
    }

    private static void processModusPonens(Expression assumption, Expression deltaJ, Expression deltaI, List<String> newProof) {
        newProof.addAll(Objects.requireNonNull(FileReader.readFile("deducer/ModusPonens", assumption, deltaJ, deltaI)));
    }

    private static void processAimpA(Expression expression, List<String> newProof) {
        newProof.addAll(Objects.requireNonNull(FileReader.readFile("deducer/--AimpA", expression)));
    }

    public static Expression parse(String input) {
        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer);
        return parser.parse();
    }

//    // used once
//    public static List<String> notDisjunction() {
//        var res = new ArrayList<String>();
//        res.addAll(FileReader.readFile("implication/!A, !B--AimpB"));
//        res.addAll(FileReader.readFile("deducer/--AimpA", new Variable("b")));
//        res.addAll(List.of(
//                "(a->b)->(b->b)->(a|b->b)",
//                "(b->b)->(a|b->b)",
//                "a|b->b"));
//        res.addAll(contraposition((Implication) parse("a|b->b")));
//        res.add("!(a|b)");
//        return res;
//    }
//
//    // used once
//    public static List<String> excludedThird() {
//        var res = new ArrayList<String>();
//        res.add("a->a|!b");
//        res.addAll(contraposition((Implication) parse(res.get(0))));
//        res.add("!a->a|!b");
//        res.addAll(contraposition((Implication) parse(res.get(1))));
//        res.addAll(List.of(
//                "(!(a|!a)->!a)->(!(a|!a)->!!a)->!!(a|!a)",
//                "(!(a|!a)->!!a)->!!(a|!a)",
//                "!!(a|!a)",
//                "!!(a|!a)->a|!a",
//                "a|!a"
//        ));
//        return res;
//    }
}
