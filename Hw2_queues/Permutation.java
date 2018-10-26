/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue();

        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        Iterator iterator = queue.iterator();

        printKItems(k, iterator);
    }

    private static void printKItems(int k, Iterator iterator) {
        int printNum = 0;
        while (iterator.hasNext() && printNum < k) {
            System.out.println(iterator.next());
            printNum++;
        }
    }
}
