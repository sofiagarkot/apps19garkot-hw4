package ua.edu.ucu.utils.immutable;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.Getter;


public final class ImmutableArrayList implements ImmutableList {

    @Getter
    private ArrayList myArr;

    public ImmutableArrayList() {
        myArr = new ArrayList();
    }

    private ImmutableArrayList(ArrayList arr) {
        myArr = arr;
    }

    @Override
    public ImmutableArrayList add(Object e) {
        Object[] c = {e};
        return this.addAll(this.size(), c);
    }

    @Override
    public ImmutableArrayList add(int index, Object e) {
        Object[] c = {e};
        return this.addAll(index, c);
    }

    public ImmutableArrayList addAll(Object[] c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public ImmutableArrayList addAll(int index, Object[] c) {
        ArrayList copyArr = (ArrayList) this.myArr.clone();
        if (index > copyArr.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Object> newArr = new ArrayList<>();
        if (c.length == 1 && this.size() == 0) {
            newArr.addAll(Arrays.asList(c));
            return new ImmutableArrayList(newArr);
        } else if (this.size() == 1) {
            if (index == 0) {
                newArr.addAll(Arrays.asList(c));
                newArr.add(copyArr.get(0));
            } else {
                newArr.add(copyArr.get(0));
                newArr.addAll(Arrays.asList(c));
            }
        } else {
            int i = 0;
            while (i != copyArr.size()) {
                if (i != index) {
                    newArr.add(copyArr.get(i));
                } else {
                    newArr.addAll(Arrays.asList(c));
                    index = -1;
                    i -= 1;
                }
                i += 1;
            }
            if (index == this.size()) {
                newArr.addAll(Arrays.asList(c));
            }
        }

        return new ImmutableArrayList(newArr);
    }

    @Override
    public Object get(int index) {
        int size = this.myArr.size();
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.myArr.get(index);
    }

    @Override
    public ImmutableArrayList remove(int index) {
        ArrayList copyArr = (ArrayList) this.myArr.clone();
        if (index >= copyArr.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Object> newArr = new ArrayList<>();
        for (int i = 0; i < copyArr.size(); i++) {
            if (i != index) {
                newArr.add(copyArr.get(i));
            }
        }
        return new ImmutableArrayList(newArr);
    }

    @Override
    public ImmutableArrayList set(int index, Object e) {
        ArrayList copyArr = (ArrayList) this.myArr.clone();
        if (index >= copyArr.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Object> newArr = new ArrayList<>();
        for (int i = 0; i < copyArr.size(); i++) {
            if (i != index) {
                newArr.add(copyArr.get(i));
            } else {
                newArr.add(e);
            }
        }
        return new ImmutableArrayList(newArr);
    }

    @Override
    public int indexOf(Object e) {
        ArrayList copyArr = (ArrayList) this.myArr.clone();
        for (int i = 0; i < copyArr.size(); i++) {
            if (copyArr.get(i).equals(e)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return this.myArr.size();
    }

    @Override
    public ImmutableArrayList clear() {
        return new ImmutableArrayList();
    }

    @Override
    public boolean isEmpty() {
        return this.myArr.size() == 0;
    }

    @Override
    public Object[] toArray() {
        ArrayList copyArr = (ArrayList) this.myArr.clone();
        return copyArr.toArray();
    }

    @Override
    public String toString() {
        ArrayList copyArr = (ArrayList) this.myArr.clone();
        return copyArr.toString().substring(1, copyArr.toString().length() - 1);
    }
}
