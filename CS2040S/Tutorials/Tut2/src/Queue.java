public class Queue {
    /**
     * A Queue is a “FIFO” (First In First Out) collection of elements that supports these operations:
     * enqueue: Adds an element to the queue
     * dequeue: Removes the first element that was added to the queue
     * peek: Returns the next item to be dequeued (without removing it)
     * How would you implement a stack and queue with a fixed-size array in Java? (Assume that
     * the number of items in the collections never exceed the array’s capacity.)
     */

    private Object[] queue;
    private int currSize;
    private int maxSize;

    Queue(int size) {
        this.queue = new Object[size];
        this.currSize = 0;
        this.maxSize = size;
    }

    public void enqueue(Object item) {
        if (currSize >= maxSize) {
            // throw exception: Queue is Full
            return;
        }
        this.currSize++;
        this.queue[this.currSize] = item;
    }

    public Object dequeue() {
        if (this.currSize <= 0) {
            // throw exception
        }
        int index = this.currSize - 1;

        Object deqItem = this.queue[index];
        this.queue[index] = null; // removes the item from stack

        this.currSize--;
        return deqItem;
    }

    public Object peek() {
        if (this.currSize <= 0) {
            // throw exception
        }
        return this.queue[this.currSize-1];
    }
}
