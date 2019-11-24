package ua.edu.ucu.utils;

import lombok.Getter;
import lombok.Setter;

import ua.edu.ucu.utils.immutable.ImmutableLinkedList;
import ua.edu.ucu.utils.immutable.TwoWayNode;

import javax.imageio.ImageTranscoder;
import java.util.Iterator;

public class Queue implements Iterable {

    @Getter
    @Setter
    private ImmutableLinkedList dynamicQueue;

    public Queue() {
        dynamicQueue = new ImmutableLinkedList();
    }

    TwoWayNode peek() {
        if (this.getDynamicQueue().getSize() == 0) {
            throw new IndexOutOfBoundsException();
        }
        return (TwoWayNode) this.getDynamicQueue().getFirst();
    }

    Object dequeue() {
        if (this.getDynamicQueue().getSize() == 0) {
            throw new IndexOutOfBoundsException();
        }
        Object result = this.getDynamicQueue().getFirst();
        this.setDynamicQueue(this.getDynamicQueue().removeFirst());
        return result;
    }

    public void enqueue(Object e) {
        this.setDynamicQueue(this.getDynamicQueue().addLast(e));
    }


    @Override
    public Iterator iterator() {
        return new QueueIterator(this);
    }

    public static void main(String[] args)
    {
        // Create Linked List
        Queue myList = new Queue();

        // Add Elements
        myList.enqueue("abc");
        myList.enqueue("mno");
        myList.enqueue("pqr");
        myList.enqueue("xyz");

        // Iterate through the list using For Each Loop
        for (Object string : myList)
            System.out.println(string);
    }
}

class QueueIterator implements Iterator {
    TwoWayNode current;

    QueueIterator(Queue q) {
        current =  q.peek();
    }

    public boolean hasNext() {
        return current != null;
    }

    public Object next() {
        Object data = current.getValue();
        current = current.getNext();
        return data;
    }
}
