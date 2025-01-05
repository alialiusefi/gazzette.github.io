---
title: "Implementing Binary heap"
excerpt_separator: "<!--more-->"
categories:
  - Blog
toc: true
tags:
  - heap
  - array
  - java
  - dsa
  - binaryheap
---


## Overview

A **binary heap** is a complete binary tree that satisfies the heap property. There are two main ways to represent binary heaps:

1. **Array representation**
2. **Tree representation**

Heaps are commonly used in task scheduling, implicitly in priority queues or tournament boards.

The heap property allows to get the maximum(minimum) element stored in the heap in `O(1)` operation.

## Array Representation

In a binary heap, the elements are stored in an array such that:
- The parent node is at index `i`.
- The left child of a node at index `i` is located at index `2 * i + 1`.
- The right child of a node at index `i` is located at index `2 * i + 2`.
  
Using the construct above we can traverse the tree structure in an array format.

### Example 1

#### Binary Tree

```
      3
     / \
    1   2
```

> Note: This is **not** a binary search tree, but just a regular binary tree.

#### Array Representation

```
[3, 1, 2]
```

- `3` at index `0` (root node)
- `1` at index `2 * 0 + 1 = 1` (left child)
- `2` at index `2 * 0 + 2 = 2` (right child)

---

### Example 2

#### Array Representation

```
[11, 4, 10, 2, 3, 6, 7]
```

#### Binary Tree

```
        11
      /    \
    4       10
  /   \    /   \
 2     3  6     7
```

> **Note:** Every child of a parent is **less than or equal to** its parent, maintaining a transitive relationship. This is not a binary search tree, where the left child is less than the parent and the right child is greater.

---

## Operations

### 1. **heapify(idx)**

The `heapify` operation maintains the heap property by moving down the tree starting from a given index (`idx`). Depending on whether the heap is a max-heap or min-heap, the operation swaps nodes to maintain the heap property.

- If the heap is a **max-heap**, the node at `idx` is compared with its children, and if either child is larger, it swaps places with the largest child.
- If the heap is a **min-heap**, the node at `idx` is compared with its children, and if either child is smaller, it swaps places with the smallest child.

The operation continues down the tree until the heap property is satisfied or until the node reaches a leaf.

```Java
void heapify(int idx) {
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
```

### 2. **heapifyUp(idx)**

The `heapifyUp` operation maintains the heap property by moving upwards from a given index (`idx`). This function is used when adding an element to the heap.

- To move up the heap, we find the parent index using the formula:  
  `parentIdx = floor((idx - 1) / 2)`.
- The operation continues upwards, swapping the node with its parent until the heap property is restored or the root is reached.

```Java
    void heapifyUp(int idx) {
        int parent = (idx - 1) / 2;
        if (idx > 0 && this.array.get(parent) < this.array.get(idx)) {
            int temp = this.array.get(parent);
            this.array.set(parent, this.array.get(idx));
            this.array.set(idx, temp);
            heapifyUp(parent);
        }
    }
```


### 3. **insert(element)**

To insert an element into the heap while maintaining the heap structure:
- If the element is larger (for a max-heap) or smaller (for a min-heap) than the root, it is added to the root, and the heap is heapified.
- Otherwise, the element is added to the end of the array, and `heapifyUp` is used to maintain the heap property.


```Java
    void insert(int i) {
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
```

### 4. **build(arr)**

To build a heap from an existing array:
- We could insert elements one by one and apply `heapifyUp` after each insertion.
- A more **optimal approach** starts by heapifying nodes starting from the middle of the array. This can be done in `O(n)` time by running `heapify` on all nodes starting from `floor(len(arr) / 2)`.

```Java
    private void build(int[] elements) {
        for (int i : elements) {
            this.array.add(i);
        }
        for (int i = (this.array.size() - 1) / 2; i >= 0; i--) {
            heapify(i);
        }
    }
```

### 5. **delete(idx)**

To remove an element from the heap:
- Swap the element at the given index (`idx`) with the last element.
- Remove the last element from the heap.
- Depending on the size of the last element, either `heapifyUp` or `heapify` is called to restore the heap property.

```Java
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
```

### 6. **search(element)**

- Searching for an element in the heap takes `O(n)` time, by scanning the array.
- Better yet, you can traverse the tree structure by skipping branches that will not lead to the element. 
- Since this is not a binary search tree, we cannot guarantee that node exists at either in left or in right subtree, so the algorithm needs to visit both subtrees. Therefore, the worst time complexity for this operation is `O(n)`.

```Java
int search(int a) {
    List<Integer> queue = new LinkedList<>();
    queue.add(0);
    while (!queue.isEmpty()) {
        int head = queue.removeFirst();
        if (this.array.get(head) == a) {
            return head;
        }
        int leftIdx = (2 * head) + 1;
        if (leftIdx < this.array.size()) {
            queue.add(leftIdx);
        }
        int rightIdx = (2 * head) + 2;
        if (rightIdx < this.array.size()) {
            queue.add(rightIdx);
        }
    }
    return -1;
}
```

---

## Duplicates in Heaps

Heaps handle duplicates by inserting them in the order they are added. You can also customize the heap behavior by using a **comparator**, which allows you to define a custom order.

Full code can be accessed [here](/assets/code/implementing-binary-heap/Solution.java). It's not fully tested, use at your own risk.

## Iterating through a heap, to get an ordered list

A good usage of binary heap is to eventually get an ordered list of elements by removing the root node until its empty. Resulting in what is called a heapsort.


## Resources

1. [Educative - Data Structure Heaps Guide](https://www.educative.io/blog/data-structure-heaps-guide)
2. [Wikipedia - Binary Heap](https://en.wikipedia.org/wiki/Binary_heap)

--- 