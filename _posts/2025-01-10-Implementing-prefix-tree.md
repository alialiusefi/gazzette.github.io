---
title: "Implementing prefix trie"
excerpt_separator: "<!--more-->"
categories:
- Blog
toc: true
tags:
- tree
- trie
- array
- java
- dsa
- prefix
- search
- map
---

## Introduction:

A prefix trie is a tree data structure that stores strings that is prefix searchable. The nodes of the tree are actually the elements of the alphabet used to create a string. Since its a tree, it contains a root which is actually computed since a start of the string varies.
So, actually we have multiple roots that can be up to `len(alphabet)` in size. At start, a map of `character -> node` is suited to point to which root to the start of the word.

### Tree Representation:

```
    |-> a -> p -> p -> l -> e    
    |-> b -> a -> n -> a -> n -> a
    |-> c -> r -> a -> y -> o -> n
0 --|-> p -> e -> o -> p -> l -> e
    |-> + -> 1 -> 5 -> 5 -> 5 -> 8 -> 0 -> 8 -> 0
    |-> t -> h -> e -> e -> n -> d
```

Different words are stored in their own branches, but if there are common prefixes, the branches then start to diverge:

```
                     | -> a -> t -> i -> o -> N -> s
0 --|-> represent -> | -> e -> r
    |                | -> i -> n -> g
    |                 
    |-> +1800 -> | 5 -> | -> 5 -> 5 -> 8 -> 0 -> 8 -> 0
                 |      | -> 0 -> 5 -> 8 -> 0 -> 8 -> 0
                 |
                 | 6 -> 0 -> 6 -> 0 
```

Every node of the tree will have the following structure:

```java
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
```
The boolean flag is an identifier if the node is the final character of a word. 
As mentioned earlier, the `PrefixTree` has a root node with empty value. So the structure is simply the following:

```java
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
}
```
The pair structure will be used later for in our operations.

## Operations:

### 1. insert(word)
```java
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
```
The above operation insert a word by add the sequence of characters if they doesnt exist, otherwise it marks final character as true.
Since we traverse the word by its characters, the time complexity is `O(len(word))`.

### 2. search(word)
```java
Optional<Node> search(String word) {
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
```
Despite the name of the function named as `search`, it only returns the last node of the word. The way its implemented above is` O(len(word))` operation. and that is okay. This is a function that will be used in other operations such as `prefixSearch`. For exact search,it can be optimized.

### 3. contains(word)

```java
boolean containsWord(String word) {
    Optional<Node> last = search(word);
    if (last.isEmpty()) {
        return false;
    } else {
        return last.get().isWord;
    }
}
```
This is an operation that will use the `search` function to get the last node to then check if its a word or not to satisfy the exactmatching.
This can be optimized to `O(1)` time complexity using a hash table with space tradeoff of `len(words)`.

### 4. prefixSearch(prefix)
```java
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

```
The above operation uses the `search` operation, which returns the last node of the prefix. So, by having access to the last node, we can then search for all the words with the same prefix. Using BFS, where the `lastNode` is the root node, we start to search for nodes with `isWord == true` while remembering our traversing. Pair structure mentioned in intorduction is used to support that.

## Resources:
1. The full code can be access [here](/assets/code/implementing-prefix-trie/Solution.java). It is not tested, so use on your own risk.
2. https://en.wikipedia.org/wiki/Trie

## Todo:
1. Ordering for batch fetching?
2. Ip address prefix?
3. Optimize insert function and contains with hashmap.
4. Delete operation
5. Replace hashtable with ?
