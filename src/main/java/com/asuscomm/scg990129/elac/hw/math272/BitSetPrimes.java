package com.asuscomm.scg990129.elac.hw.math272;

import java.util.Iterator;
import java.util.concurrent.*;
import java.util.stream.IntStream;

// Integer.MAX_VALUE = 2147483647
public class BitSetPrimes extends AdvancedBitSet{
    private static final long serialVersionUID = 1L;
    private static final int MAX = 997;
    private static final int MIN = 2;
    protected int availableLastIndex = 3;
    private static final int MAX_PARAMETER = (int) Math.sqrt(Integer.MAX_VALUE); // 46340, 2147483647
    protected static final ExecutorService executor = Executors.newWorkStealingPool();
    protected Future<Integer> futurePrimesExplorer = null;
    protected FuturePrimesCallable futurePrimesCallable = null;
    protected static volatile BitSetPrimes bitSetPrimes;

    public static synchronized BitSetPrimes getInstance(){
        return (bitSetPrimes == null)? bitSetPrimes = new BitSetPrimes(): bitSetPrimes;
    }

    protected BitSetPrimes() {
        super(MAX);
        this.setAndGet(2,3,5,7,11,13);
        availableLastIndex = 16;
        this.futurePrimesExplorer = executor.submit(futurePrimesCallable = new FuturePrimesCallable());
    }

    @Override
    public synchronized boolean get(int bitIndex) {
        while (bitIndex > availableLastIndex ){
            try {
                this.availableLastIndex = futurePrimesExplorer.get();
            } catch (InterruptedException | ExecutionException e) {
                executor.submit(futurePrimesCallable = new FuturePrimesCallable());
                continue;
            }
        }
        return super.get(bitIndex);
    }

    protected class FuturePrimesCallable implements Callable<Integer> {
        public Integer call() {
            int flagPrimeIndex = availableLastIndex;
            int tempLastIndex = MAX_PARAMETER > availableLastIndex+1? (availableLastIndex+1)*(availableLastIndex+1)-2:
                    Integer.MAX_VALUE - 1; // int max = 2147483647
               try(IntStream candidate = IntStream.range(availableLastIndex/2,tempLastIndex/2)//8 , 143
                        .parallel().map(i->i*2+1) // for example: 17, 19, 21, 23 .... 287
                        .filter(this::isPrime).peek(BitSetPrimes.this::set)
                        .onClose(this::onClose)){
                   flagPrimeIndex = candidate.max().orElse(BitSetPrimes.this.previousSetBit(BitSetPrimes.this.size()));
                   BitSetPrimes.this.availableLastIndex = MAX_PARAMETER > availableLastIndex+1? (availableLastIndex+1)*(availableLastIndex+1)-1:
                           Integer.MAX_VALUE;
               }
               return flagPrimeIndex;
        }

        protected void onClose(){
            if (availableLastIndex == Integer.MAX_VALUE){
                System.err.printf("Primes done: %s\n", BitSetPrimes.this.toString());
                return;
            }
            if (MAX_PARAMETER > availableLastIndex+1){
                System.err.printf("Primes 1: %s\n", BitSetPrimes.this.toString());
                executor.submit(futurePrimesCallable = new FuturePrimesCallable());
            }else{
                System.err.printf("Primes Last: %s\n", BitSetPrimes.this.toString());
                executor.submit(futurePrimesCallable = new FuturePrimesCallable());
            }
        }

        protected boolean isPrime(int i) {
            int temp;

            for (Iterator<Integer> iterator = BitSetPrimes.this.iterator(true, 2, availableLastIndex);
            iterator.hasNext(); ) {
                temp = iterator.next();
                if (i % temp == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
