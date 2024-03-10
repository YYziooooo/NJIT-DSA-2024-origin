package oy.tol.tra;

public class Algorithms {

    private Algorithms() {
        // Empty
    }

    /**
     * Performs a binary search on the specified array for the specified value.
     *
     * @param value     The value to search for.
     * @param array     The array to search in.
     * @param fromIndex The index to start searching from.
     * @param toIndex   The index to end searching (inclusive).
     * @return The index of the value if found, otherwise -1.
     */
    public static int binarySearch(int value, Integer[] array, int fromIndex, int toIndex) {
        while (fromIndex <= toIndex) {
            int mid = (fromIndex + toIndex) / 2;
            if (array[mid] == value)
                return mid;
            else if (array[mid] < value)
                fromIndex = mid + 1;
            else
                toIndex = mid - 1;
        }
        return -1;
    }

    /**
     * Sorts the given array in ascending order using bubble sort algorithm.
     *
     * @param array The array to be sorted.
     */
    public static void sort(Integer[] array) {
        boolean swapped;
        int n = array.length;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    /**
     * Performs a binary search on the specified array for the specified value.
     *
     * @param s         The string to search for.
     * @param array     The array to search in.
     * @param fromIndex The index to start searching from.
     * @param toIndex   The index to end searching (inclusive).
     * @return The index of the value if found, otherwise -1.
     */
    public static int binarySearch(String s, String[] array, int fromIndex, int toIndex) {
        while (fromIndex <= toIndex) {
            int mid = (fromIndex + toIndex) / 2;
            int cmp = s.compareTo(array[mid]);
            if (cmp == 0)
                return mid;
            else if (cmp < 0)
                toIndex = mid - 1;
            else
                fromIndex = mid + 1;
        }
        return -1;
    }

    /**
     * Sorts the given array of strings in lexicographical order using quick sort algorithm.
     *
     * @param array The array of strings to be sorted.
     */
    public static void sort(String[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(String[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(String[] array, int low, int high) {
        String pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) < 0) {
                i++;
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        String temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}
