import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

class Trace<T> {

    protected final List<T> history;
    protected final T curr;

    protected Trace(T seed) {
        this.curr = seed;
        this.history = new ArrayList<>();
    }

    protected Trace(T curr, List<T> history) {
        this.curr = curr;
        this.history = history;
    }

    @SafeVarargs
    public static <T> Trace<T> of(T seed, T... history) {
        List<T> newHistory = new ArrayList<>();
        Collections.addAll(newHistory, history);
        return new Trace<>(seed, newHistory);
    }

    public Trace<T> map(Transformer<? super T, ? extends T> f) {
        List<T> newHistory = new ArrayList<>();
        newHistory.addAll(this.history);
        T v = f.transform(curr);
        newHistory.add(curr);
        return new Trace<T>(v, newHistory);
    }

    public T get() {
        return curr;
    }

    public List<T> history() {
        return history;
    }

    public Trace<T> back(int n) {
        List<T> newHistory = new ArrayList<>();
        newHistory.addAll(this.history);

        int steps = Math.min(n, history.size());
        T newCurr = newHistory.get(newHistory.size() - steps);
        for (int i = 0; i < steps && newHistory.size() > 0; i++) {
            newHistory.remove(newHistory.size() - 1);
        }
        return new Trace<T>(newCurr, newHistory);
    }

    public Trace<T> flatMap(Transformer<? super T, ? extends Trace<? extends T>> f) {
        Trace<? extends T> newTrace = f.transform(curr);
        T newCurr = newTrace.get();
        List<T> newHistory = new ArrayList<T>();
        newHistory.addAll(this.history);
        newHistory.add(this.curr);
        newHistory.addAll(newTrace.history);
        return new Trace<T>(newCurr, newHistory);
    }

    public boolean equals(Object c) {
        if (c == this) {
            return true;
        }
        if (!(c instanceof Trace)) {
            return false;
        }

        Trace<?> t = (Trace<?>)c;
        if (!t.get().equals(this.get())) {
            return false;
        }
        if (!t.history().equals(this.history())) {
            return false;
        }
        return true;
    }

    public String toString() {
        String s = "" + curr + " (history: [";
        for (T h : history) {
            s += h + ", ";
        }
        s = s.substring(0, s.length() - 2);
        s += "])";
        return s;
        // return "[" + get() + "]";
    }
}

// vim:sw=4
