package ua.edu.ucu.utils.immutable;

import lombok.Getter;

import java.util.Arrays;

public final class ImmutableLinkedList implements ImmutableList {

    @Getter
    private TwoWayNode start;
    @Getter
    private TwoWayNode end;
    @Getter
    private int size;

    public ImmutableLinkedList() {
        start = new TwoWayNode(null);
        end = new TwoWayNode(null);
        size = 0;
    }

    private ImmutableLinkedList(TwoWayNode starting, TwoWayNode ending, int newsize) {
        start = starting;
        end = ending;
        size = newsize;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        Object[] newArr = {e};
        return this.addAll(this.size, newArr);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        Object[] cArr = {e};
        return this.addAll(index, cArr);
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return this.addAll(this.size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index > this.getSize()) {
            throw new IndexOutOfBoundsException();
        }
        int newSize = this.getSize() + c.length;
        if (c.length == 0) {
            return new ImmutableLinkedList(this.getStart(), this.getEnd(), this.getSize());
        } else {
            if (this.getSize() == 0) {
                TwoWayNode start = new TwoWayNode(c[0]);
                TwoWayNode cur = iter(c, start);
                return new ImmutableLinkedList(start, cur, newSize);
            } else if (this.getSize() == 1) {
                TwoWayNode oldStart = this.getStart().copy();
                if (index == 0) {
                    TwoWayNode toStartWith = new TwoWayNode(c[0]);
                    TwoWayNode curAfterIteratedC = iter(c, toStartWith);
                    curAfterIteratedC.setNext(oldStart);
                    oldStart.setPrevious(curAfterIteratedC);
                    return new ImmutableLinkedList(toStartWith, oldStart, newSize);
                } else {
                    TwoWayNode toStartWith = new TwoWayNode(c[0]);
                    TwoWayNode curAfterIteratedC = iter(c, toStartWith);
                    oldStart.setNext(toStartWith);
                    toStartWith.setPrevious(oldStart);
                    return new ImmutableLinkedList(oldStart, curAfterIteratedC, newSize);
                }
            } else {
                TwoWayNode current = this.getStart().copy();
                if (index == 0) {
                    TwoWayNode toStartWith = new TwoWayNode(c[0]);
                    TwoWayNode curAfterIteratedC = iter(c, toStartWith);
                    curAfterIteratedC.setNext(current);
                    current.setPrevious(curAfterIteratedC);
                    TwoWayNode newEnd = getTwoWayNode(0, current, 0);
                    return new ImmutableLinkedList(toStartWith, newEnd, newSize);
                }
                TwoWayNode copyStart = current;
                current = getTwoWayNode(-1, current, -this.getSize() + index - 1);

                if (current.getNext() != null) {
                    TwoWayNode nextInsertCopy = current.getNext().copy();

                    TwoWayNode toStartWith = new TwoWayNode(c[0], current, nextInsertCopy);
                    current.setNext(toStartWith);
                    TwoWayNode curAfterIteratedC = iter(c, toStartWith);
                    curAfterIteratedC.setNext(nextInsertCopy);
                    nextInsertCopy.setPrevious(current);
                    current = curAfterIteratedC;

                    current = current.getNext();
                    TwoWayNode end = getTwoWayNode(index, current, 0);
                    return new ImmutableLinkedList(copyStart, end, newSize);
                } else {
                    TwoWayNode toStartWith = new TwoWayNode(c[0]);
                    current.setNext(toStartWith);
                    TwoWayNode newCur = iter(c, toStartWith);
                    newCur.setPrevious(current);
                    return new ImmutableLinkedList(copyStart, newCur, newSize);
                }
            }
        }
    }

    private TwoWayNode iter(Object[] c, TwoWayNode startNode) {
        TwoWayNode curNode = startNode;
        for (int i = 1; i < c.length; i++) {
            TwoWayNode newNode = new TwoWayNode(c[i]);
            newNode.setPrevious(curNode);
            curNode.setNext(newNode);
            curNode = newNode;
        }
        return curNode;
    }


    @Override
    public Object get(int index) {
        if (index >= this.getSize() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        TwoWayNode current = this.getStart();
        while (i != index) {
            current = current.getNext();
            i += 1;
        }
        return current.getValue();
    }

    private TwoWayNode getTwoWayNode(int i, TwoWayNode current, int window) {
        int starting_from = i + 1;
        TwoWayNode nodeToChange = current;
        while (starting_from != this.getSize() + window) {
            TwoWayNode nextCopy = nodeToChange.getNext().copy();
            nodeToChange.setNext(nextCopy);
            nextCopy.setPrevious(nodeToChange);
            nodeToChange = nodeToChange.getNext();
            starting_from += 1;
        }
        return nodeToChange;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index > this.getSize() - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            TwoWayNode current = this.getStart().getNext().copy();
            TwoWayNode copyStart = current;
            copyStart.setPrevious(null);
            current = getTwoWayNode(0, current, -1);
            return new ImmutableLinkedList(copyStart, current, this.getSize() - 1);
        }
        TwoWayNode current = this.getStart().copy();
        TwoWayNode copyStart = current;
        current = getTwoWayNode(-1, current, -this.getSize() + index - 1);
        if (index == this.getSize() - 1) {
            return new ImmutableLinkedList(copyStart, current, this.getSize() - 1);
        }
        TwoWayNode copyNext = current.getNext().getNext().copy();
        current.setNext(copyNext);
        if (copyNext.getClass() != TwoWayNode.class) {
            copyNext.setPrevious(current);
        }
        current = getTwoWayNode(index - 1, current, -1);
        return new ImmutableLinkedList(copyStart, current, this.getSize() - 1);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index > this.getSize() - 1 || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        TwoWayNode current = this.getStart().copy();
        TwoWayNode copyStart = current;
        current = getTwoWayNode(-1, current, -this.getSize() + index);
        TwoWayNode curChange = current;
        curChange.setValue(e);
        current = getTwoWayNode(index, current, 0);
        return new ImmutableLinkedList(copyStart, current, this.getSize());
    }


    @Override
    public int indexOf(Object e) {
        int i = 0;
        TwoWayNode current = this.getStart();
        while (i != this.getSize()) {
            if (current.getValue().equals(e)) {
                return i;
            }
            i += 1;
        }
        return -1;
    }

    @Override
    public int size() {
        return this.getSize();
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.getSize() == 0;
    }

    public ImmutableLinkedList addFirst(Object e) {
        return this.add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return this.add(e);
    }

    public Object getFirst() {
        return this.getStart();
    }

    public Object getLast() {
        return this.getEnd().getValue();
    }

    public ImmutableLinkedList removeFirst() {
        return this.remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return this.remove(this.getSize() - 1);
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.getSize()];
        TwoWayNode curr = this.getStart();
        int i = 0;
        while (i != this.getSize()) {
            result[i] = curr.getValue();
            curr = curr.getNext();
            i += 1;
        }
        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray()).substring(1, Arrays.toString(this.toArray()).length() - 1);
    }

}
