import java.util.Iterator;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private Node sentinel;
    private int size;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;

        public Node() {

        }

        public Node(Item item) {
            this.item = item;
        }

        public Node(Node prev, Node next) {
            this.prev = prev;
            this.next = next;
        }
    }

    public Deque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void addBetween(Item item, Node prev, Node next) {
        Node node = new Node(prev, next);
        prev.next = node;
        next.prev = node;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        addBetween(item, sentinel, sentinel.next);
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        addBetween(item, sentinel.prev, sentinel);
    }

    private Item remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        size--;
        return node.item;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return remove(sentinel.next);
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return remove(sentinel.prev);
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    private class dequeIterator implements Iterator<Item> {
        private Node cur = sentinel;

        public boolean hasNext() {
            return size > 0;
        }

        public Item next() {
            cur = cur.next;
            return cur.item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

}