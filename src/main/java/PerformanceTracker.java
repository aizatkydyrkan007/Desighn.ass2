public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayReads = 0;
    private long arrayWrites = 0;

    public void countComparison() { comparisons++; }
    public void countSwap() { swaps++; }
    public void countArrayRead() { arrayReads++; }
    public void countArrayWrite() { arrayWrites++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayReads() { return arrayReads; }
    public long getArrayWrites() { return arrayWrites; }

    public String toCsvRow(int n, double seconds, String inputType, int trial) {
        return n + "," + inputType + "," + trial + "," + seconds + "," +
                comparisons + "," + swaps + "," + arrayReads + "," + arrayWrites;
    }

    public void reset() {
        comparisons = swaps = arrayReads = arrayWrites = 0;
    }
}