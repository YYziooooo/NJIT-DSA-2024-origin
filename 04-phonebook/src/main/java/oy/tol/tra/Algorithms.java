package oy.tol.tra;

import java.util.Comparator;

public class Algorithms {

    // Implementation of the quicksort algorithm
    public static <T extends Comparable<T>> void fastSort(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        fastSort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void fastSort(T[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);
            fastSort(array, left, pivotIndex - 1);
            fastSort(array, pivotIndex + 1, right);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int left, int right) {
        T pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, right);
        return i + 1;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Implementation of partitionByRule method
    public static <T> int partitionByRule(T[] array, int length, PartitionRule<T> rule) {
        int left = 0;
        int right = length - 1;
        while (left <= right) {
            // Find the first element that does not satisfy the rule from the left side
            while (left <= right && rule.test(array[left])) {
                left++;
            }
            // Find the first element that satisfies the rule from the right side
            while (left <= right && !rule.test(array[right])) {
                right--;
            }
            // Swap the elements if they are in the wrong order
            if (left <= right) {
                swap(array, left, right);
                left++;
                right--;
            }
        }
        // Return the index where the partition occurs
        return left;
    }

    // Implementation of sortWithComparator method
    public static <T> void sortWithComparator(T[] array, Comparator<T> comparator) {
        fastSortWithComparator(array, comparator, 0, array.length - 1);
    }

    private static <T> void fastSortWithComparator(T[] array, Comparator<T> comparator, int left, int right) {
        if (left < right) {
            int pivotIndex = partitionWithComparator(array, comparator, left, right);
            fastSortWithComparator(array, comparator, left, pivotIndex - 1);
            fastSortWithComparator(array, comparator, pivotIndex + 1, right);
        }
    }

    private static <T> int partitionWithComparator(T[] array, Comparator<T> comparator, int left, int right) {
        T pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, right);
        return i + 1;
    }

    // Functional interface for defining partition rule
    public interface PartitionRule<T> {
        boolean test(T element);
    }
}
