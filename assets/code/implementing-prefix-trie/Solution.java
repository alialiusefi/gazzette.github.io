import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Solution {
    public static void main(String[] args) {
        PrefixTree tree = new PrefixTree();
        tree.insert("example");
        tree.insert("apple");
        tree.insert("app");
        System.out.println(tree.containsWord("example"));
        System.out.println(tree.containsWord("exampl"));
        System.out.println(tree.containsWord("app"));
        System.out.println(tree.prefixSearch("example"));
        System.out.println(tree.prefixSearch("app"));
    }   

    /**
     * A prefix tree is a tree data stracture that stores words
     */
    static class PrefixTree {
        static class Pair {
            StringBuilder first;
            Node second;

            Pair(StringBuilder first, Node second) {
                this.first = first;
                this.second = second;
            }
        }
        static class Node {
            Character value;
            boolean isWord;
            HashMap<Character, Node> next;

            Node(Character value, boolean isWord) {
                this.value = value;
                this.isWord = isWord;
                this.next = new HashMap<>();
            }
        }

        Node root;

        PrefixTree() {
            this.root = new Node(null, false);
        }

        void insert(String word) {
            if (word.length() < 1) {
                return;
            }
            Node head = this.root;
            for (int i = 0; i < word.length(); i++) {
                Character ch = word.charAt(i);
                Node next = head.next.get(ch);
                if (next == null) {
                    Node node = new Node(ch, i == word.length() - 1);
                    head.next.put(ch, node);
                    head = node;
                } else if (i == word.length() - 1) {
                    next.isWord = true;
                    head = next;
                } else {
                    head = next;
                }
            }
        }

        boolean containsWord(String word) {
            Optional<Node> last = search(word);
            if (last.isEmpty()) {
                return false;
            } else {
                return last.get().isWord;
            }
        }

        private Optional<Node> search(String word) {
            Node head = this.root;
            int index = 0;
            while (index < word.length()) {
                Node next = head.next.get(word.charAt(index));
                if (next == null) {
                    return Optional.empty();
                } else if (index == word.length() - 1) {
                    return Optional.of(next);
                } else {
                    head = next;
                    index++;
                }
            }
            return Optional.empty();
        }

        List<String> prefixSearch(String prefix) {
            List<String> result = new ArrayList<>();
            Optional<Node> maybeLastNode = search(prefix);
            if (maybeLastNode.isEmpty()) {
                return result;
            }
            Node lastNode = maybeLastNode.get();

            Deque<Pair> queue = new LinkedList<>();
            queue.add(new Pair(new StringBuilder(prefix.substring(0, prefix.length() - 1)), lastNode));

            while (!queue.isEmpty()) {
                Pair curr = queue.pop();
                StringBuilder newString = new StringBuilder(curr.first).append(curr.second.value);
                if (curr.second.isWord) {                    
                    result.add(newString.toString());
                }
                Collection<Node> nexts = curr.second.next.values();
                for (Node i : nexts) {
                    queue.add(new Pair(newString, i));
                }
            }

            return result;
        }
    }
}
