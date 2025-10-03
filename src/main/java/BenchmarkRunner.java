import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        int[] sizes = {1000, 5000, 10000, 20000};
        String[] inputTypes = {"random", "sorted", "reversed", "nearly-sorted"};
        int trials = 3;

        File dataDir = new File("data");
        if (!dataDir.exists()) dataDir.mkdirs();

        String csvPath = "data/benchmarks.csv";
        try (PrintWriter out = new PrintWriter(new FileWriter(csvPath, false))) {
            out.println("n,input_type,trial,time_seconds,comparisons,swaps,array_reads,array_writes,timestamp");
            for (String inputType : inputTypes) {
                for (int n : sizes) {
                    for (int t = 1; t <= trials; t++) {
                        int[] input;
                        switch (inputType) {
                            case "sorted": input = Utils.generateSortedArray(n); break;
                            case "reversed": input = Utils.generateReverseSortedArray(n); break;
                            case "nearly-sorted": input = Utils.generateNearlySortedArray(n, Math.max(1, n/100)); break;
                            default: input = Utils.generateRandomArray(n);
                        }
                        PerformanceTracker perf = new PerformanceTracker();
                        long t0 = System.nanoTime();
                        MaxHeap h = new MaxHeap(input, perf);
                        long t1 = System.nanoTime();
                        double seconds = (t1 - t0) / 1e9;
                        out.print(perf.toCsvRow(n, seconds, inputType, t));
                        out.println("," + LocalDateTime.now());
                        System.out.println("n=" + n + " type=" + inputType + " trial=" + t + " time=" + seconds);
                    }
                }
            }
        }
        System.out.println("Benchmark finished. CSV: data/benchmarks.csv");
    }
}