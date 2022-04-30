package stacker.rpn.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rpn {
    public static Token numOrFail(String str) {
        Pattern pattern = Pattern.compile("\\d+(.\\d+)?");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return new Token(TokenType.NUM, str);
        } else {
            System.out.println("Error: Unexpected character: " + str.strip());
            System.exit(1);
        }
        return new Token(TokenType.NUM, "this should never happen");
    }
    public static void main(String[] args) {
        try {
            boolean debug = true; // prints token info
            File file = new File("/home/phoenix/IdeaProjects/compilers-rpn/src/Calc1.stk");
            Scanner sc = new Scanner(file);
            Stack<Token> stack = new Stack<>();
            ArrayDeque<Token> queue = new ArrayDeque<>();
            while (sc.hasNextLine()) {
                String aux = sc.nextLine();
                switch (aux) {
                    case "+" -> queue.add(new Token(TokenType.PLUS, aux));
                    case "-" -> queue.add(new Token(TokenType.MINUS, aux));
                    case "/" -> queue.add(new Token(TokenType.SLASH, aux));
                    case "*" -> queue.add(new Token(TokenType.STAR, aux));
                    default -> queue.add(numOrFail(aux));
                }
            }
            queue.add(new Token(TokenType.EOF, "\u001a"));
            if (debug) {
                for (Token token : queue
                ) {
                    System.out.println(token);
                }
            }
            queue.removeLast();
            while (queue.size() > 0) {
                Token aux = queue.remove();
                if (aux.lexeme.equals("+") || aux.lexeme.equals("-") || aux.lexeme.equals("*") || aux.lexeme.equals("/")) {
                    Float operandA = Float.parseFloat(stack.pop().lexeme);
                    Float operandB = Float.parseFloat(stack.pop().lexeme);
                    switch (aux.lexeme) {
                        case "+" -> stack.push(new Token(TokenType.NUM, Float.toString(operandA + operandB)));
                        case "-" -> stack.push(new Token(TokenType.NUM, Float.toString(operandA - operandB)));
                        case "*" -> stack.push(new Token(TokenType.NUM, Float.toString(operandA * operandB)));
                        case "/" -> stack.push(new Token(TokenType.NUM, Float.toString(operandA / operandB)));
                    }
                } else {
                    stack.push(aux);
                }
            }
            System.out.println(stack.pop().lexeme);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}