package oy.tol.tra;

public class Algorithms {


        public static <T> void reverse(T[] array) {
            int i = 0;
            int j = array.length - 1;
            while (i < j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        public static <T extends Comparable<T>> void sort(T[] array) {
            int n = array.length;
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (array[j].compareTo(array[j + 1]) > 0) {
                        swap(array, j, j + 1);
                    }
        }


        //Swaps two elements in an array.

        public static <T> void swap(T[] array, int i, int j) {
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

