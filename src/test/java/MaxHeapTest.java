import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaxHeapTest {

    @Test
    public void testInsertExtractOrder() {
        MaxHeap h = new MaxHeap(10);
        h.insert(3);
        h.insert(10);
        h.insert(5);
        h.insert(10);

        assertEquals(10, h.extractMax(), "Первое извлечение должно вернуть 10");
        assertEquals(10, h.extractMax(), "Второе извлечение должно вернуть 10 (дубликат)");
        assertEquals(5, h.extractMax(), "Третье извлечение должно вернуть 5");
        assertEquals(3, h.extractMax(), "Четвёртое извлечение должно вернуть 3");
    }

    @Test
    public void testBuildFromArrayProducesDescendingOrder() {
        int[] arr = {1, 5, 3, 8, 2, 9};
        PerformanceTracker perf = new PerformanceTracker();
        MaxHeap h = new MaxHeap(arr, perf);

        int n = arr.length;
        int[] out = new int[n];
        for (int i = 0; i < n; i++) {
            out[i] = h.extractMax();
        }

        for (int i = 0; i < n - 1; i++) {
            assertTrue(out[i] >= out[i + 1],
                    "Элемент " + i + " должен быть >= чем элемент " + (i+1));
        }
    }

    @Test
    public void testEmptyExtractThrows() {
        MaxHeap h = new MaxHeap(2);
        assertThrows(IllegalStateException.class, () -> h.extractMax(),
                "При извлечении из пустой кучи должно быть исключение");
    }

    @Test
    public void testIncreaseKeyMovesElementUp() {
        MaxHeap h = new MaxHeap(10);
        h.insert(1);
        h.insert(2);
        h.insert(3); // heap contains [3,2,1] roughly

        // увеличим ключ элемента с индексом 2 (текущая 1) до 100
        h.increaseKey(2, 100);

        assertEquals(100, h.extractMax(), "После increaseKey увеличенный элемент должен стать максимумом");
    }

    @Test
    public void testInsertResizing() {
        MaxHeap h = new MaxHeap(1);
        // insert more элементов чем начальная ёмкость, чтобы проверить ресайз
        for (int i = 0; i < 50; i++) {
            h.insert(i);
        }
        assertEquals(50, h.size(), "Размер кучи должен быть 50 после вставок");

        // Проверим порядок извлечения — должен быть от 49 до 0
        for (int expected = 49; expected >= 0; expected--) {
            assertEquals(expected, h.extractMax());
        }
    }
}
