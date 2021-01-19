package ru.ifmo.rain.naumov;

import java.util.HashSet;

public class Tokenizer {
    private final String raw;
    private int i;
    private String lastVariableName;

    public HashSet<String> getNames() {
        return names;
    }

    private HashSet<String> names;

    public Tokenizer(String raw) {
        this.raw = raw;
        this.i = 0;
        this.names = new HashSet<>();
    }

    public Token nextToken() {
        if (i == raw.length()) {
            return Token.END;
        }
        switch (raw.charAt(i++)) {
            case '(':
                return Token.OPENING;
            case ')':
                return Token.CLOSING;
            case '&':
                return Token.CONJUNCTION;
            case '|':
                return Token.DISJUNCTION;
            case '-':
                i++;
                return Token.IMPLICATION;
            case '!':
                return Token.NEGATION;
            default:
                skipWhiteSpaces();
                readVariableName();
                return Token.VARIABLE;
        }
    }

    void skipWhiteSpaces() {
        while (i < raw.length() && Character.isWhitespace(raw.charAt(i))) {
            i++;
        }
    }

    void readVariableName() {
        int start = i - 1;
        while (i < raw.length() && isPartOfIdentifier(raw.charAt(i))) {
            i++;
        }
        String name = raw.substring(start, i);
        names.add(name);
        lastVariableName = name;
    }

    boolean isPartOfIdentifier(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '\'';
    }

    public String getLastVariableName() {
        return lastVariableName;
    }
}
