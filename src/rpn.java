import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class rpn {
    public static void main(String[] args) {
        try {
            File file = new File("/home/phoenix/IdeaProjects/compilers-rpn/src/Calc1.stk");
            Scanner sc = new Scanner(file);
            Stack<String> stack = new Stack<String>();
            while (sc.hasNextLine()) {
                String aux = sc.nextLine();
                if (aux.equals("+") || aux.equals("-") || aux.equals("*") || aux.equals("/")) {
                    int operandA = Integer.parseInt(stack.pop());
                    int operandB = Integer.parseInt(stack.pop());
                    switch (aux) {
                        case "+":
                            stack.push(Integer.toString(operandA + operandB));
                            break;
                        case "-":
                            stack.push(Integer.toString(operandA - operandB));
                            break;
                        case "*":
                            stack.push(Integer.toString(operandA * operandB));
                            break;
                        case "/":
                            stack.push(Integer.toString(operandA / operandB));
                            break;
                    }
                } else {
                    stack.push(aux);
                }
            }
            System.out.println(stack.pop());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
