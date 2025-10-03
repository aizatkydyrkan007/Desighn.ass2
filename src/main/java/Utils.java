import java.util.Random;

public class Utils {
    private static final Random rnd = new Random(42);

    public static int[] generateRandomArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(Integer.MAX_VALUE);
        }
        return a;
    }

    public static int[] generateSortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    public static int[] generateReverseSortedArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - i;
        return a;
    }

    public static int[] generateNearlySortedArray(int n, int swaps) {
        int[] a = generateSortedArray(n);
        for (int i = 0; i < swaps && i + 1 < n; i++) {
            int j = rnd.nextInt(n);
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
        return a;
    }
}
