package Crypt;

public class LCGRandom {
    private final long a;
    private final long c;
    private final long m;
    private long seed;

    public LCGRandom(long seed) {
        this.seed = seed;
        // These are common LCG parameters (from Numerical Recipes)
        this.a = 1664525;
        this.c = 1013904223;
        this.m = (long) Math.pow(2, 32);
    }

    public long next() {
        seed = (a * seed + c) % m;
        return seed;
    }

    public int nextInt(int bound) {
        return (int)(next() % bound);
    }

    public double nextDouble() {
        return (double) next() / (double) m;
    }
}