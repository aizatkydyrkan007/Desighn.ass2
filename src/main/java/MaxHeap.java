import java.util.*;

public class MaxHeap {
    private int[] heap;
    private int size;
    private PerformanceTracker perf;
    private Map<Integer, Set<Integer>> positionMap = new HashMap<>();

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

        positionMap.clear();
        for (int i = 0; i < size; i++) {
            addPosition(heap[i], i);
        }

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

    private void addPosition(int value, int index) {
        positionMap.computeIfAbsent(value, k -> new HashSet<>()).add(index);
    }

    private void removePosition(int value, int index) {
        Set<Integer> set = positionMap.get(value);
        if (set != null) {
            set.remove(index);
            if (set.isEmpty()) positionMap.remove(value);
        }
    }

    private int anyPositionOf(int value) {
        Set<Integer> set = positionMap.get(value);
        if (set == null || set.isEmpty()) return -1;
        return set.iterator().next();
    }

    private void updatePositionAfterSwap(int value, int oldIndex, int newIndex) {
        // Called when a value moves from oldIndex -> newIndex
        removePosition(value, oldIndex);
        addPosition(value, newIndex);
    }

    public void insert(int value) {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2 + 1];
            System.arraycopy(heap, 0, newHeap, 0, heap.length);
            heap = newHeap;
            // positions do not change after array copy
        }
        heap[size] = value;
        perf.countArrayWrite();
        addPosition(value, size);
        siftUp(size++);
    }

    public int extractMax() {
        if (size == 0) throw new IllegalStateException("Heap empty");
        int max = heap[0];
        // remove max from position map (index 0)
        removePosition(max, 0);

        if (size == 1) {
            size = 0;
            return max;
        }

        int lastIndex = size - 1;
        int movedValue = heap[lastIndex];

        heap[0] = movedValue;
        perf.countArrayWrite();

        removePosition(movedValue, lastIndex);
        addPosition(movedValue, 0);

        size--;
        siftDown(0);
        return max;
    }

    public void increaseKey(int oldValue, int newValue) {
        int index = anyPositionOf(oldValue);
        if (index == -1) {
            throw new NoSuchElementException("Value not found in heap");
        }
        if (newValue < heap[index]) {
            throw new IllegalArgumentException("new key smaller than current value");
        }

        removePosition(oldValue, index);
        heap[index] = newValue;
        perf.countArrayWrite();
        addPosition(newValue, index);

        siftUp(index);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parentIndex = parent(i);
            perf.countComparison();
            if (heap[parentIndex] >= heap[i]) break;
            swap(parentIndex, i);
            i = parentIndex;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int leftIndex = left(i), rightIndex = right(i), largestIndex = i;
            if (leftIndex < size) {
                perf.countComparison();
                if (heap[leftIndex] > heap[largestIndex]) largestIndex = leftIndex;
            }
            if (rightIndex < size) {
                perf.countComparison();
                if (heap[rightIndex] > heap[largestIndex]) largestIndex = rightIndex;
            }
            if (largestIndex == i) break;
            swap(i, largestIndex);
            i = largestIndex;
        }
    }

    private void swap(int i, int j) {
        if (i == j) return;
        int vi = heap[i];
        int vj = heap[j];

        int tmp = vi;
        heap[i] = vj;
        heap[j] = tmp;

        removePosition(vi, i);
        addPosition(vi, j);

        removePosition(vj, j);
        addPosition(vj, i);

        perf.countArrayRead(); perf.countArrayRead();
        perf.countArrayWrite(); perf.countArrayWrite();
        perf.countSwap();
    }

    public int size() { return size; }
    public PerformanceTracker getPerf() { return perf; }
}
