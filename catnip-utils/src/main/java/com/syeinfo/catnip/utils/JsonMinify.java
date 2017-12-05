package com.syeinfo.catnip.utils;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonMinify {

    private static final int EOF = -1;

    private PushbackInputStream in;
    private OutputStream out;
    private int currChar;
    private int nextChar;
    private int line;
    private int column;

    public enum Action {
        OUTPUT_CURR, DELETE_CURR, DELETE_NEXT
    }

    public JsonMinify() {
        this.in = null;
        this.out = null;
    }

    public String minify(String json) {
        InputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            minify(in, out);
            return out.toString("UTF-8").trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void minify(InputStream in, OutputStream out) throws IOException, UnterminatedRegExpLiteralException,
            UnterminatedCommentException,
            UnterminatedStringLiteralException {

        // Initialize
        this.in = new PushbackInputStream(in);
        this.out = out;
        this.line = 0;
        this.column = 0;
        currChar = '\n';
        action(Action.DELETE_NEXT);

        // Process input
        while (currChar != EOF) {
            switch (currChar) {

                case ' ':
                    if (isAlphanum(nextChar)) {
                        action(Action.OUTPUT_CURR);
                    } else {
                        action(Action.DELETE_CURR);
                    }
                    break;

                case '\n':
                    switch (nextChar) {
                        case '{':
                        case '[':
                        case '(':
                        case '+':
                        case '-':
                            action(Action.OUTPUT_CURR);
                            break;
                        case ' ':
                            action(Action.DELETE_NEXT);
                            break;
                        default:
                            if (isAlphanum(nextChar)) {
                                action(Action.OUTPUT_CURR);
                            } else {
                                action(Action.DELETE_CURR);
                            }
                    }
                    break;

                default:
                    switch (nextChar) {
                        case ' ':
                            if (isAlphanum(currChar)) {
                                action(Action.OUTPUT_CURR);
                                break;
                            }
                            action(Action.DELETE_NEXT);
                            break;
                        case '\n':
                            switch (currChar) {
                                case '}':
                                case ']':
                                case ')':
                                case '+':
                                case '-':
                                case '"':
                                case '\'':
                                    action(Action.OUTPUT_CURR);
                                    break;
                                default:
                                    if (isAlphanum(currChar)) {
                                        action(Action.OUTPUT_CURR);
                                    } else {
                                        action(Action.DELETE_NEXT);
                                    }
                            }
                            break;
                        default:
                            action(Action.OUTPUT_CURR);
                            break;
                    }
            }
        }
        out.flush();
    }

    private void action(Action action) throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException,
            UnterminatedStringLiteralException {

        // Process action
        switch (action) {

            case OUTPUT_CURR:
                out.write(currChar);
            case DELETE_CURR:
                currChar = nextChar;

                if (currChar == '\'' || currChar == '"') {
                    for ( ; ; ) {
                        out.write(currChar);
                        currChar = get();
                        if (currChar == nextChar) {
                            break;
                        }
                        if (currChar <= '\n') {
                            throw new UnterminatedStringLiteralException(line, column);
                        }
                        if (currChar == '\\') {
                            out.write(currChar);
                            currChar = get();
                        }
                    }
                }

            case DELETE_NEXT:
                nextChar = next();
                if (nextChar == '/'
                        && (currChar == '(' || currChar == ',' || currChar == '=' || currChar == ':')) {
                    out.write(currChar);
                    out.write(nextChar);
                    for ( ; ; ) {
                        currChar = get();
                        if (currChar == '/') {
                            break;
                        } else if (currChar == '\\') {
                            out.write(currChar);
                            currChar = get();
                        } else if (currChar <= '\n') {
                            throw new UnterminatedRegExpLiteralException(line, column);
                        }
                        out.write(currChar);
                    }
                    nextChar = next();
                }
        }
    }

    private boolean isAlphanum(int c) {
        return ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')
                || c == '_' || c == '$' || c == '\\' || c > 126);
    }

    private int get() throws IOException {
        int c = in.read();

        if (c == '\n') {
            line++;
            column = 0;
        } else {
            column++;
        }

        if (c >= ' ' || c == '\n' || c == EOF) {
            return c;
        }

        if (c == '\r') {
            column = 0;
            return '\n';
        }

        return ' ';
    }

    private int peek() throws IOException {
        int lookaheadChar = in.read();
        in.unread(lookaheadChar);
        return lookaheadChar;
    }

    private int next() throws IOException, UnterminatedCommentException {
        int c = get();

        if (c == '/') {
            switch (peek()) {

                case '/':
                    for ( ; ; ) {
                        c = get();
                        if (c <= '\n') {
                            return c;
                        }
                    }

                case '*':
                    get();
                    for ( ; ; ) {
                        switch (get()) {
                            case '*':
                                if (peek() == '/') {
                                    get();
                                    return ' ';
                                }
                                break;
                            case EOF:
                                throw new UnterminatedCommentException(line, column);
                        }
                    }

                default:
                    return c;
            }

        }
        return c;
    }

    public static class UnterminatedCommentException extends Exception {
        public UnterminatedCommentException(int line, int column) {
            super("Unterminated comment at line " + line + " and column " + column);
        }
    }

    public static class UnterminatedStringLiteralException extends Exception {
        public UnterminatedStringLiteralException(int line, int column) {
            super("Unterminated string literal at line " + line + " and column " + column);
        }
    }

    public static class UnterminatedRegExpLiteralException extends Exception {
        public UnterminatedRegExpLiteralException(int line, int column) {
            super("Unterminated regular expression at line " + line + " and column " + column);
        }
    }

}
