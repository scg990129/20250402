package com.asuscomm.scg990129.elac.hw.math272;

import java.util.BitSet;
import java.util.Iterator;
import java.util.function.IntUnaryOperator;

public class AdvancedBitSet extends BitSet implements Iterable<Integer> {

    public static final int defaultMax = (int) Math.sqrt(997);
    protected IntUnaryOperator next;

    // the range 0 through nbits-1
    public AdvancedBitSet(int nbits) {
        super(nbits);
        this.next = this::nextSetBit;
    }

    public AdvancedBitSet() {
        super(defaultMax);
    }

    @Override // ClearBit=getValue=false, SetBit=getValue=true
    public synchronized Iterator<Integer> iterator() {
        return new BitSetIterator(true, true);
    }

    // ClearBit=targetBoolean=false, SetBit=targetBoolean=true
    public synchronized Iterator<Integer> iterator(boolean targetBoolean, boolean isAscendingOrder) {
        return new BitSetIterator(targetBoolean, isAscendingOrder);
    }

    public synchronized Iterator<Integer> iterator(boolean targetBoolean, int beginIncluded, int endIncluded) {
        return new BitSetIterator(targetBoolean, beginIncluded, endIncluded);
    }

    @Override
    public synchronized void set(int bitIndex) {
        super.set(bitIndex);
    }

    @Override
    public synchronized void clear(int bitIndex) {
        super.clear(bitIndex);
    }

    @Override
    public synchronized boolean get(int bitIndex) {
        return super.get(bitIndex);
    }

    public synchronized BitSet setAndGet(int bitIndex) {
        this.setAndGet(bitIndex, true);
        return this;
    }
    public synchronized BitSet setAndGet(int bitIndex, boolean value) {
        this.set(bitIndex, value);
        return this;
    }
    public synchronized BitSet setAndGet(int... bitIndex) {
        this.setAndGet( true, bitIndex);
        return this;
    }
    public synchronized BitSet setAndGet(boolean value, int... bitIndex) {
        for(int i:bitIndex){
            this.set(i, value);
        }
        return this;
    }

    @Override
    public synchronized int nextSetBit(int fromIndex) {
        return super.nextSetBit(fromIndex);
    }

    @Override
    public synchronized int nextClearBit(int fromIndex) {
        return super.nextClearBit(fromIndex);
    }

    @Override
    public synchronized int previousSetBit(int fromIndex) {
        return super.previousSetBit(fromIndex);
    }

    @Override
    public synchronized int previousClearBit(int fromIndex) {
        return super.previousClearBit(fromIndex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int max = this.previousSetBit(this.size() - 1);
        int digits = max > 0 ? (int) Math.floor(Math.log10(max)) + 1 : 1;
        String format = "%0" + digits + "d ";

        for(int i: this){
            sb.append(String.format(format,i) );
        }

        return sb.toString().trim();
    }

    protected class BitSetIterator implements Iterator<Integer> {
        protected int currentIndex = -1;
        protected IntUnaryOperator next;
        protected boolean isAscendingOrder;
        protected int beginIncluded = 0, endIncluded = AdvancedBitSet.this.size() - 1;

        public BitSetIterator(boolean value, int beginIncluded, int endIncluded){
            if (beginIncluded < 0 || endIncluded < 0){
                throw new IllegalArgumentException("beginIncluded < 0 || endIncluded < 0");
            }

            this.endIncluded = endIncluded;
            this.beginIncluded = beginIncluded;
            this.isAscendingOrder = beginIncluded <= endIncluded;

            this.next = value? isAscendingOrder ? AdvancedBitSet.this::nextSetBit: AdvancedBitSet.this::previousSetBit:
                    isAscendingOrder ? AdvancedBitSet.this::nextClearBit : AdvancedBitSet.this::previousClearBit;

            this.currentIndex = this.next.applyAsInt(beginIncluded);
        }

        public BitSetIterator(boolean value, boolean isAscendingOrder) {
            this(value, isAscendingOrder? 0: AdvancedBitSet.this.size() - 1, isAscendingOrder? AdvancedBitSet.this.size() - 1: 0);
        }

        @Override
        public boolean hasNext() {
            return currentIndex != -1 && isAscendingOrder? (currentIndex >= beginIncluded && currentIndex <= endIncluded)
                    : (currentIndex <= beginIncluded && currentIndex >= endIncluded) ;
        }

        @Override
        public Integer next() {
            int returnValue = currentIndex;
            currentIndex = next.applyAsInt((this.isAscendingOrder) ? currentIndex + 1 : currentIndex - 1);
            return returnValue;
        }
    }
}