package com.hjss.utilities;

public class Pair<L, R> {
    private final L obj;
    private final R invalidFlag;

    public Pair(L obj, R invalidFlag) {
        this.obj = obj;
        this.invalidFlag = invalidFlag;
    }

    public L getObj() {
        return obj;
    }

    public R getInvalidFlag() {
        return invalidFlag;
    }

    // Need to include equals(), hashCode() and toString()
}
