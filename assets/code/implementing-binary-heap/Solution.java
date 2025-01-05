import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution {

    static class MaxBinaryHeap {
        List<Integer> array;

        MaxBinaryHeap() {
            this.array = new ArrayList<Integer>();
        }

        private int size() {
            return this.array.size();
        }

        // returns index
        private int search(int a) {
            List<Integer> queue = new LinkedList<>();
            queue.add(0);
            while (!queue.isEmpty()) {
                int head = queue.removeFirst();
                if (this.array.get(head) == a) {
                    return head;
                }
                int leftIdx = (2 * head) + 1;
                if (leftIdx < this.array.size() && this.array.get(leftIdx) >= a) {
                    queue.add(leftIdx);
                }
                int rightIdx = (2 * head) + 2;
                if (rightIdx < this.array.size() && this.array.get(rightIdx) >= a) {
                    queue.add(rightIdx);
                }
            }
            return -1;
        }

        private void insert(int i) {
            if (!this.array.isEmpty()) {
                if (this.array.get(0) > i) {
                    this.array.add(i);
                    heapifyUp(this.array.size() - 1);
                } else {
                    this.array.add(0, i);
                    heapify(0);
                }
            } else {
                this.array.add(i);
            }
        }

        private void deleteFromIdx(int idx) {
            int elemToDelete = this.array.get(idx);
            int lastElement = this.array.getLast();
            this.array.set(this.array.size() - 1, elemToDelete);
            this.array.set(idx, lastElement);
            this.array.removeLast();
            if (lastElement > elemToDelete) {
                heapifyUp(idx);
            } else {
                heapify(idx);
            }
            System.out.println(String.format("%s at idx %s was removed.", elemToDelete, idx));
        }

        // moves from top to bottom, mantains the correct structure.
        private void heapify(int idx) {
            int left = 2 * idx + 1;
            int right = 2 * idx + 2;
            int largest = idx;

            if (left < this.array.size() && this.array.get(left) > this.array.get(largest)) {
                largest = left;
            }

            if (right < this.array.size() && this.array.get(right) > this.array.get(largest)) {
                largest = right;
            }

            if (idx != largest) {
                int temp = this.array.get(idx);
                this.array.set(idx, this.array.get(largest));
                this.array.set(largest, temp);
                heapify(largest);
            }
        }

        // moves from bottom to up, maintains the correct structure.
        private void heapifyUp(int idx) {
            int parent = (idx - 1) / 2;
            if (idx > 0 && this.array.get(parent) < this.array.get(idx)) {
                int temp = this.array.get(parent);
                this.array.set(parent, this.array.get(idx));
                this.array.set(idx, temp);
                heapifyUp(parent);
            }
        }

        private void buildSuboptimal(int[] elements) {
            for (int i : elements) {
                insert(i);
            }
        }

        private void build(int[] elements) {
            for (int i : elements) {
                this.array.add(i);
            }
            for (int i = (this.array.size() - 1) / 2; i >= 0; i--) {
                heapify(i);
            }
        }
    }

    public static void main(String[] args) {
        MaxBinaryHeap heap = new MaxBinaryHeap();
        int[] elements = new int[] { 100, 19, 36, 17, 3, 2, 7, 25, 25, 25, 1, 1, 111 };
        heap.build(elements);
        System.out.println(heap.array.toString());

        heap.deleteFromIdx(0);
        System.out.println(heap.array.toString());

        int oneIdx = heap.search(1);
        System.out.println(String.format("Index of 1 is %s", oneIdx));

        int deletedIdx = heap.search(111);
        System.out.println(String.format("Index of 111 is %s", deletedIdx));
    }

}
