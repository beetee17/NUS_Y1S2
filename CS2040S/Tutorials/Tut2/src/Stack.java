public class Stack {
    /**
     * Just a quick recap, a Stack is a “LIFO” (Last In First Out) collection
     * of elements that supports the following operations:
     * push: Adds an element to the stack
     * pop: Removes the last element that was added to the stack
     * peek: Returns the last element added to the stack (without removing it)
     * How would you implement a stack and queue with a fixed-size array in Java? (Assume that
     * the number of items in the collections never exceed the array’s capacity.)
     */

    private Object[] stack;
    private int maxSize;
    private int currSize;

    Stack(int size) {
        this.maxSize = size;
        this.stack = new Object[size];
        this.currSize = 0;
    }

    public boolean isFull() {
        return this.currSize == maxSize;
    }

    public boolean isEmpty() {
        return this.currSize == 0;
    }

    public void push(Object item) {
        if (this.isFull()) {
            // throw exception?
            return;
        }
        this.stack[currSize] = item;
        this.currSize++;
    }

    public Object pop() {
        if (this.isEmpty()) {
            // throw exception?
            return null;
        }
        int index = this.currSize - 1;

        Object popItem = this.stack[index];
        this.stack[index] = null; // removes the item from stack

        this.currSize--;

        return popItem;
    }

    public Object peek() {
        if (this.isEmpty()) {
            // throw exception?
            return null;
        }
        return this.stack[currSize];
    }

    public static boolean checkParens(String str) {
        /**
         * A set of parentheses is said to be balanced as long as every opening parenthesis “(” is closed
         * by a closing parenthesis “)”. So for example, the strings “()()” and “(())” are balanced but
         * the strings “)(())(” and “((” are not. Using a stack, determine whether a string of parentheses
         * are balanced.
         */

        // Assume only "(" and ")" characters
        int len = str.length();
        Stack stack = new Stack(len);

        for (int i = 0; i < len; i++) {
            String s = String.valueOf(str.charAt(i));
            if (s.equals("(")) {
                stack.push(s);
            } else if (stack.isEmpty()) {
                return false;
            } else {
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String[] testCases = new String[] { "()()", "(())", "(()", "())()", "((())())" };
        for (String str : testCases) {
            System.out.println(checkParens(str));
        }
    }

}
