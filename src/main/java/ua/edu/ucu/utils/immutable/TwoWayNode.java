package ua.edu.ucu.utils.immutable;

import lombok.Setter;
import lombok.Getter;

public final class TwoWayNode {
    @Setter @Getter
    private Object value;
    @Setter @Getter
    private TwoWayNode previous = null;
    @Setter @Getter
    private TwoWayNode next = null;

    TwoWayNode(Object val) {
        this.setValue(val);
    }

    TwoWayNode(Object val, TwoWayNode prev, TwoWayNode next) {
        this.setNext(next);
        this.setPrevious(prev);
        this.setValue(val);
    }

    TwoWayNode copy() {
        Object val = this.getValue();
        TwoWayNode res = new TwoWayNode(val, previous, next);
        res.setPrevious(this.getPrevious());
        res.setNext(this.getNext());
        return res;
    }
}
