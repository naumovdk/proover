package ru.ifmo.rain.naumov.parser;

public class Tokenizer {
    private final String raw;
    private int i;
    private String lastVariableName;

    public Tokenizer(String raw) {
        this.raw = raw;
        this.i = 0;
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
                lastVariableName = readVariableName();
                return Token.VARIABLE;
        }
    }

    void skipWhiteSpaces() {
        while (i < raw.length() && Character.isWhitespace(raw.charAt(i))) {
            i++;
        }
    }

    String readVariableName() {
        int start = i - 1;
        while (i < raw.length() && isPartOfIdentifier(raw.charAt(i))) {
            i++;
        }
        return raw.substring(start, i);
    }

    boolean isPartOfIdentifier(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '\'';
    }

    public String getLastVariableName() {
        return lastVariableName;
    }
}
