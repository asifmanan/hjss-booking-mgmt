package com.hjss.utilities;

public class Pair<L, R> {
    private L obj;
    private R invalidFlag;

    public Pair(){

    }
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

    public void setInvalidFlag(R value) {
        this.invalidFlag = value;
    }
    public void setObject(L value) {
        this.obj = value;
    }

    // Need to include equals(), hashCode() and toString()
}
