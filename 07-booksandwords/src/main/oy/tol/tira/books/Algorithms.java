package oy.tol.tira.books;

import java.util.Comparator;
import java.util.function.Predicate;

public class Algorithms {
    public static <T> void reverse(T[] array) {
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
    }
// fastsort is the alternative.
//    public static <T extends Comparable<T>> void sort(T[] array) {
//        int i = array.length - 1;
//        for (int j = 0; j <= i - 1; j++) {
//            for (int k = j + 1; k <= i; k++) {
//                if (array[j].compareTo(array[k]) > 0) {
//                    T tmp = array[k];
//                    array[k] = array[j];
//                    array[j] = tmp;
//                }
//            }
//        }
//    }

    //slow linear search
    public static <T> int slowLinearSearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
        for (int index = fromIndex; index < toIndex; index++) {
            if (fromArray[index].equals(aValue)) {
                return index;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int binarySearch(T aValue, T[] fromArray, int fromIndex, int toIndex) {
        while (fromIndex <= toIndex) {
            int midIndex = fromIndex + (toIndex - fromIndex) / 2;
            int compareResult = fromArray[midIndex].compareTo(aValue);
            if (compareResult == 0) {
                return midIndex;
            } else if (compareResult < 0) {
                fromIndex = midIndex + 1;
            } else {
                toIndex = midIndex - 1;
            }
        }
        return -1;
    }

    public static <E extends Comparable<E>> void fastSort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSort(E[] array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = hoarePartition(array, begin, end);
            quickSort(array, begin, partitionIndex);
            quickSort(array, partitionIndex + 1, end);
        }
    }
    private static <E extends Comparable<E>> int hoarePartition(E[] array, int begin, int end) {
        E pivot = array[begin];
        int left = begin - 1;
        int right = end + 1;
        while (true) {
            // Move right pointer to the left, find an element less than or equal to pivot
            do {
                right--;
            } while (array[right].compareTo(pivot) < 0);

            // Move left pointer to the right, find an element greater than pivot
            do {
                left++;
            } while (array[left].compareTo(pivot) > 0);

            // Check if pointers have crossed, and swap if not
            if (left < right) {
                swap(array, left, right);
            } else {
                return right;
            }
        }
    }

    private static <E> void swap(E[] array, int index1, int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static <T> int partitionByRule(T[] array, int count, Predicate<T> rule) {
        // Find first element rules applies to.
        // Index of that element will be in variable index.
        int index = 0;
        for (; index < count; index++) {
            if (rule.test(array[index])) {
                break;
            }
        }
        // If went to the end, nothing was selected so quit here.
        if (index >= count) {
            return count;
        }
        // Then start finding not selected elements starting from next from index.
        // If the element is not selected, swap it with the selected one.
        int nextIndex = index + 1;
        // Until end of array reached.
        while (nextIndex != count) {
            if (!rule.test(array[nextIndex])) {
                swap(array, index, nextIndex);
                // If swapping was done, add to index since now it has non-selected element.
                index++;
            }
            nextIndex++;
        }
        return index;
    }

    public static <T> void sortWithComparator(T[] array, Comparator<T> comparator) {
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped) break;
        }
    }

    // sort in ascending order:

//    private static <E extends Comparable<E>> int partition(E[] array, int begin, int end) {
////      //first method:
////        E pivot = array[end];
////        int i = begin - 1;
////        for (int j = begin; j < end; j++) {
////            if (array[j].compareTo(pivot) <= 0) {
////                i++;
////                swap(array, i, j);
////            }
////        }
////        swap(array, i + 1, end);
////        return i + 1;
//        //second method:
//        E pivot = array[end];
//        int leftPointer = begin;
//        int rightPointer = end;
//        while (leftPointer < rightPointer) {
//            while (leftPointer < rightPointer && array[leftPointer].compareTo(pivot) < 0) {
//                leftPointer++;
//            }
//            while (leftPointer < rightPointer && array[rightPointer].compareTo(pivot) > 0) {
//                rightPointer--;
//            }
//            swap(array, leftPointer, rightPointer);
//        }
//        return leftPointer;
//    }
}