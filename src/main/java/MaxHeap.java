public class MaxHeap {
    private int[] heap;
    private int size;
    private PerformanceTracker perf;

    public MaxHeap(int capacity) {
        this.heap = new int[Math.max(1, capacity)];
        this.size = 0;
        this.perf = new PerformanceTracker();
    }

    public MaxHeap(int[] arr, PerformanceTracker perf) {
        this.heap = new int[arr.length];
        System.arraycopy(arr, 0, this.heap, 0, arr.length);
        this.size = arr.length;
        this.perf = perf != null ? perf : new PerformanceTracker();
        buildHeap();
    }

    private void buildHeap() {
        for (int i = parent(size - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }
    private int parent(int i) { return (i - 1) / 2; }

    public void insert(int value) {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2 + 1];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
        }
        heap[size] = value;
        perf.countArrayWrite();
        siftUp(size++);
    }

    public int extractMax() {
        if (size == 0) throw new IllegalStateException("Heap empty");
        int max = heap[0];
        heap[0] = heap[--size];
        perf.countArrayWrite();
        siftDown(0);
        return max;
    }

    public void increaseKey(int index, int newValue) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (newValue < heap[index]) throw new IllegalArgumentException("new key smaller");
        heap[index] = newValue;
        perf.countArrayWrite();
        siftUp(index);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = parent(i);
            perf.countComparison();
            if (heap[p] >= heap[i]) break;
            swap(p, i);
            i = p;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int l = left(i), r = right(i), largest = i;
            if (l < size) {
                perf.countComparison();
                if (heap[l] > heap[largest]) largest = l;
            }
            if (r < size) {
                perf.countComparison();
                if (heap[r] > heap[largest]) largest = r;
            }
            if (largest == i) break;
            swap(i, largest);
            i = largest;
        }
    }

    private void swap(int i, int j) {
        int tmp = heap[i];
        perf.countArrayRead(); perf.countArrayRead();
        heap[i] = heap[j];
        heap[j] = tmp;
        perf.countArrayWrite(); perf.countArrayWrite();
        perf.countSwap();
    }

    public int size() { return size; }
    public PerformanceTracker getPerf() { return perf; }
}