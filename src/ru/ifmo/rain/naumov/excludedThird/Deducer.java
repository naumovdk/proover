package ru.ifmo.rain.naumov.excludedThird;

import ru.ifmo.rain.naumov.*;
import ru.ifmo.rain.naumov.implication.Implication;

import java.util.ArrayList;
import java.util.List;

public class Deducer {
    private static final List<Axiom> axioms = List.of(
            new Axiom(parse("a->b->a")),
            new Axiom(parse("(a->b)->(a->b->y)->(a->y)")),
            new Axiom(parse("a&b->a")),
            new Axiom(parse("a&b->b")),
            new Axiom(parse("a->b->a&b")),
            new Axiom(parse("a->a|b")),
            new Axiom(parse("b->a|b")),
            new Axiom(parse("(a->y)->(b->y)->(a|b->y)")),
            new Axiom(parse("(a->b)->(a->!b)->!a")),
            new Axiom(parse("!!a->a"))
    );

    private static List<String> contraposition(Implication expression) {
        return FileReader.readFile("excludedThird/AimpB--!Bimp!A", expression.getLhs(), expression.getRhs());
    }

    public static List<String> excludedThird() {
        String s1 = "(A)->(A)|!(A)";
        String s2 = "!(A)->(A)|!(A)";
        String s3 = "(!((A)|!(A))->!(A))->(!((A)|!(A))->!(!(A)))->!(!((A)|!(A)))";
        String s4 = "(!((A)|!(A))->!(!(A)))->!(!((A)|!(A)))";
        String s5 = "!(!((A)|!(A)))";
        String s6 = "!(!((A)|!(A)))->(A)|!(A)";
        String s7 = "(A)|!(A)";

        var res = new ArrayList<String>();
        res.add(s1);
        res.addAll(contraposition((Implication) parse(s1)));
        res.add(s2);
        res.addAll(contraposition((Implication) parse(s2)));
        res.add(s3);
        res.add(s4);
        res.add(s5);
        res.add(s6);
        res.add(s7);
        return res;
    }

    private static Expression parse(String input) {
        Tokenizer tokenizer = new Tokenizer(input);
        Parser parser = new Parser(tokenizer);
        return parser.parse();
    }
}
