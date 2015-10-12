/**
 * Prepared for UVic CSC 115, Spring 2015
 * Assignment #4
 *
 * Tokenize: Michael Zastre & modified by Kurt Dorflinger
 * Rest of implementation: Kurt Dorflinger
 */
import java.util.*;
import java.lang.*;

public class EvalInfixExtra {
    /**
     * First ensure the arithmetic operations plus parentheses
     * are surrounded by spaces. Then go ahead and split up the
     * whole expression using spaces (i.e, the operands will be
     * nicely separated from everything else by at least a single
     * space). This will not work for negative numbers.
     */
    //String symbols and the replacement for the negatives were modified
    public static String[] tokenize(String s) {
        // Note the missing minus character...
        String symbols[] = {"\\(", "\\)", "\\+", "\\-", "\\*", "\\/", "\\^"};

        // First eliminate any quotation marks
        s = s.replaceAll("'", " ");
        s = s.replaceAll("\"", " ");

        // Now all operators or parentheses, surround the character
        // with a single space on either side.
        for (int i = 0; i < symbols.length; i++) {
            String regex = symbols[i];
            String replace = " " + regex + " ";
            s = s.replaceAll(regex, replace);
        }   

        // Special case: If there is a unary minus, then then
        // what appears to the right of the symbol is whitespace
        // and a digit; what appears to the left whitespace
        // and a non-digit symbol.
        s = s.replaceAll("(^|([\\+\\-\\*\\/\\^\\(]))\\s+\\-\\s+(\\d+)", "$1 -$3");

        // Eliminate extra whitespaces at start and end of the
        // transformed string. 
        s = s.trim();

        // Finally, take advantage of the whitespace to create an
        // array of strings where each item in the array is one
        // of the elements in the original string passed in to this
        // method.
        return s.split("\\s+");
    } 

    public static boolean isBalanced(String expr) {
        int balance = 0;
        //Count up for left parens and down for right parens
        for(int i = 0; i < expr.length(); i++) {
            if(expr.charAt(i) == '(') {
                balance++;
            } else if(expr.charAt(i) == ')') {
                balance--;
            }
        }
        return balance == 0;
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    private static boolean isNumber(String token) {
        //-? checks if there is one "-" or not at all (positive or negative) followed by numbers
        //(.\\d+)? checks if a decimal followed by numbers occurs once or not at all
        return token.matches("-?\\d+(.\\d+)?");
    }

    private static int precedence(String token) {
        //Return 2 if multiplication or division, 1 if addition or subtraction
        if(token.equals("^")) {
            return 3;
        } else if(token.equals("*") || token.equals("/")) {
            return 2;
        } else if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 0;
    }

    private static void handlePrecedence(String token, StringStackRefBased tokenStack, StringListRefBased postFix) throws StringStackException {
        //While there are still tokens that have higher precedence and there arent any parenthesis we can add the operator
        //stack to the postfix expression
        //System.out.println("Handling precedence.");
        while(!tokenStack.isEmpty() && precedence(tokenStack.peek()) > precedence(token) && !tokenStack.peek().equals("(")) {
            postFix.insertTail(tokenStack.pop());
        }
        tokenStack.push(token);
    }
    
    public static StringList toPostfix(String[] tokens) throws StringStackException {
        StringListRefBased postFix = new StringListRefBased();
        StringStackRefBased tokenStack = new StringStackRefBased();
        String token;
        String subString;

        for(int i = 0; i < tokens.length; i++) {
            token = tokens[i];
            //System.out.println(token);
            if(token.equals("(")) {
                tokenStack.push(token);
            //If a paren is closed off then all the operators in that stack are in postfix and need to be added
            } else if(token.equals(")")) {
                while(!tokenStack.peek().equals("(")) {
                    postFix.insertTail(tokenStack.pop());
                }
                tokenStack.pop();
            //If the token is an operator then set up postfix order
            } else if(isOperator(token)) {
                handlePrecedence(token, tokenStack, postFix);
            } else {
                //add number to solution
                postFix.insertTail(token);
            }
        }
        //Empty out the stack into the postfix StringList as it is it postfix order by now
        while(!tokenStack.isEmpty()) {
            postFix.insertTail(tokenStack.pop());
        }

        return postFix;
    }

    private static String calculateToken(String token, Float a, Float b, Float result) throws ArithmeticException {
        //Performs the desired operation based on the operation token
        //0.0f because java assumes 0.0 is double
        result = 0.0f;
        if(token.equals("+")) {
            result = a + b;
        } else if(token.equals("-")) {
            result = (b - a);
        } else if(token.equals("*")) {
            result = (a * b);
        } else if(token.equals("/")) {
            if(a == 0){
                throw new ArithmeticException("<divide by zero>");
            }
            result = (b / a);
        } else if(token.equals("^")) {
            //Can't take a negative power of a negative exponent
            if(a < 1 && a > -1 && b < 0) {
                throw new ArithmeticException("<fractional root of negative number>");
            }
            Double power = Math.pow(b, a);
            result = power.floatValue();
        }
        //Return String since evalStack is a StringStack
        return result.toString();
    }
    public static String evaluatePostfix(String[] tokens) throws StringStackException {
        StringStackRefBased evalStack = new StringStackRefBased();
        //Convert the infix tokens to postfix
        StringList postFix = toPostfix(tokens);
        String token;
        int length = postFix.getLength();
        Float result = new Float(0);
        Float a = new Float(0);
        Float b = new Float(0);

        for(int i = 0; i < length; i++) {
            token = postFix.retrieve(i);
            try {
                //If the token is a number it gets pushed
                if(isNumber(token)) {
                    evalStack.push(token);
                //If the token is an operator we pop from the stack and perform that operation
                } else {
                    a = a.parseFloat(evalStack.pop());
                    b = b.parseFloat(evalStack.pop());
                    evalStack.push(calculateToken(token, a, b, result));
                }
            } catch(StringStackException error) {
                return "<syntax error>";
            } catch(NumberFormatException error) {
                return "<nonFloat>";
            } catch(ArithmeticException error) {
                return error.getMessage();
            }
        }
        return evalStack.pop();
    }

    public static String evaluateExpression(String expr) throws StringStackException {
        String[] tokens = tokenize(expr);
        /*for(String token : tokens) {
            System.out.println(token);
        }*/
        if(!isBalanced(expr)) {
            return "<unbalanced ()>";
        }
        StringStackRefBased balanceStack = new StringStackRefBased();
        try {
            //For each String token in String[] tokens
            for(String token : tokens) {
                //Pop from the stack if there is an operator and push if there is a number.
                //The amount of operators should be numbers - 1
                if(isOperator(token)) {
                    balanceStack.pop();
                } else if(isNumber(token)) {
                    balanceStack.push(token);
                } else if(!token.equals("(") && !token.equals(")")) {
                    return "<nonFloat>";
                }
            }
            //A number should remain on the stack since operators = n-1 so 1 number should not be popped
            if(balanceStack.isEmpty()) {
                return "<syntax error>";
            } else {
                String top = balanceStack.pop();
                //If there are n-1 operators then we can evaluate the expression
                if(isNumber(top) && balanceStack.isEmpty()){
                    return evaluatePostfix(tokens);
                }
            }
            return "<syntax error>";
        } catch (StringStackException error) {
            return "<syntax error>";
        }
    }
 
    public static String testEvalInfix(String expr) {
        //Try to solve the expression and return it for testing purposes
        try {
            return evaluateExpression(expr);
        } catch (StringStackException error) {
            return null;
        }
    }

    public static void main(String args[]) {
        if (args.length < 1) {
            System.err.println("usage: java EvalInfix '<expression>'");
            System.exit(1);
        }
        //Try to solve the expression
        try {
            System.out.println(evaluateExpression(args[0]));
        } catch (StringStackException error) {
            System.err.println(error);
        }

    }
}
