package oy.tol.tra;

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
        if (begin >= end) {
            return;
        }
        int partitionIndex = partition(array, begin, end);
        quickSort(array, begin, partitionIndex - 1);
        quickSort(array, partitionIndex + 1, end);
    }

    private static <E extends Comparable<E>> int partition(E[] array, int begin, int end) {

        E pivot = array[end];
        int leftPointer = begin;
        int rightPointer = end;
        while (leftPointer < rightPointer) {
            while (leftPointer < rightPointer && array[leftPointer].compareTo(pivot) < 0) {
                leftPointer++;
            }
            while (leftPointer < rightPointer && array[rightPointer].compareTo(pivot) > 0) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        return leftPointer;
    }

    private static <E> void swap(E[] array, int index1, int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}