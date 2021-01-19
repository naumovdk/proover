package ru.ifmo.rain.naumov;

import ru.ifmo.rain.naumov.conjunction.Conjunction;
import ru.ifmo.rain.naumov.disjunction.Disjunction;
import ru.ifmo.rain.naumov.implication.Implication;
import ru.ifmo.rain.naumov.negation.Negation;

public class Parser {
    private final Tokenizer tokenizer;
    private Token lastToken;

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Expression parse() {
        return parseImplication();
    }

    private Expression parseImplication() {
        Expression operand = parseDisjunction();
        if (lastToken == Token.IMPLICATION) {
            return new Implication(operand, parseImplication());
        }
        return operand;
    }

    private Expression parseDisjunction() {
        Expression operand = parseConjunction();
        while (lastToken == Token.DISJUNCTION) {
            operand = new Disjunction(operand, parseConjunction());
        }
        return operand;
    }

    private Expression parseConjunction() {
        Expression operand = parseNegation();
        while (lastToken == Token.CONJUNCTION) {
            operand = new Conjunction(operand, parseNegation());
        }
        return operand;
    }

    private Expression parseNegation() {
        Expression operand = parseVariable();
        while (lastToken == Token.NEGATION) {
            operand = new Negation(parseNegation());
        }
        return operand;
    }

    private Expression parseVariable() {
        lastToken = tokenizer.nextToken();
        switch (lastToken) {
            case VARIABLE:
                lastToken = tokenizer.nextToken();
                return new Variable(tokenizer.getLastVariableName());
            case OPENING:
                Expression e = parse();
                lastToken = tokenizer.nextToken();
                return e;
            case CLOSING:
            default:
                return null;
        }
    }
}
// a | b | c & d
// ! ! ! a
// !A&!B->!(A|B)
// P1'->!QQ->!R10&S|!T&U&V