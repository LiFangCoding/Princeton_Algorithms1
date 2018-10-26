import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue
    private Item[] a;
    private int n;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        if (n == a.length) {
            resize(2 * a.length);
        }

        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomIdx = StdRandom.uniform(n);
        int end = n - 1;

        return deleteItem(randomIdx, end);
    }

    private Item deleteItem(int idx, int end) {
        Item item = a[idx];
        swap(idx, end);
        a[end] = null;
        n--;

        if (n == a.length / 4 && n > 0) {
            resize(a.length / 2);
        }
        return item;
    }

    // put all elements of size n into the capacity part.
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomIdx = StdRandom.uniform(n);
        return a[randomIdx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void swap(int s, int e) {
        Item temp = a[s];
        a[s] = a[e];
        a[e] = temp;
    }

    private class RandomIterator implements Iterator<Item> {
        Item[] randomList;
        int cur;

        public RandomIterator() {
            cur = 0;
            randomList = (Item[]) new Object[n];

            for (int i = 0; i < n; i++) {
                randomList[i] = a[i];
            }

            StdRandom.shuffle(randomList);
        }

        @Override
        public boolean hasNext() {
            return cur < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item res = randomList[cur];
            cur++;
            return res;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}