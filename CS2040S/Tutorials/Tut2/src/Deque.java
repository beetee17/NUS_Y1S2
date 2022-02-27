import java.lang.reflect.Array;

public class Deque<T> {
    /**
     * A Deque (double-ended queue) is an extension of a queue, which allows enqueueing and de-
     * queueing from both ends of the queue. So the operations it would support are enqueue front,
     * dequeue front, enqueue back, dequeue back. How can we implement a Deque with a fixed-size
     * array in Java? (Again, assume that the number of items in the collections never exceed the
     * array’s capacity.
     */
    private int head = -1;
    private int tail = -1;
    private int maxSize;
    private int currSize = 0;
    private T[] items;

    Deque(Class<T> type, int size) {
        this.items = (T[]) Array.newInstance(type, size);
        this.maxSize = size;
    }

    private void incrementHead() {
        if (this.head == maxSize - 1) {
            this.head = 0;
        } else {
            this.head++;
        }
    }

    private void decrementHead() {
        if (this.head == 0) {
            this.head = this.maxSize - 1;
        } else {
            this.head--;
        }
    }

    private void incrementTail() {
        if (this.tail == maxSize - 1) {
            this.tail = 0;
        } else {
            this.tail++;
        }
    }

    private void decrementTail() {
        if (this.tail == 0) {
            this.tail = this.maxSize - 1;
        } else {
            this.tail--;
        }
    }

    public boolean isFull() {
        return currSize == maxSize;
    }

    public boolean isEmpty() {
        return currSize == 0;
    }

    public void enqFront(T item) {
        if (this.isFull()) {
            // throw exception?
            return;
        }
        if (currSize == 0) {
            this.head = 0;
            this.tail = 0;
            this.items[0] = item;
        } else {
            decrementHead();
            this.items[head] = item;
        }
        this.currSize++;
    }

    public void enqBack(T item) {
        if (this.isFull()) {
            // throw exception?
            return;
        }

        if (currSize == 0) {
            this.head = 0;
            this.tail = 0;
            this.items[0] = item;
        } else {
            incrementTail();
            this.items[tail] = item;
        }
        this.currSize++;
    }

    public T deqFront() {
        if (this.isEmpty()) {
            // throw exception?
            return null;
        }
        T item = this.items[this.head];
        incrementHead();
        this.currSize--;

        if (this.isEmpty())  {
            this.head = -1;
            this.tail = -1;
        }
        return item;
    }

    public T deqBack() {
        if (this.isEmpty()) {
            // throw exception?
            return null;
        }
        T item = this.items[this.head];
        decrementTail();
        this.currSize--;

        if (this.isEmpty())  {
            this.head = -1;
            this.tail = -1;
        }
        return item;
    }
}
