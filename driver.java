package sortAlgorithms;

// Amelia York - Program #3
// This driver class utilizes five different sort methods to sort an array.
// Insertion sort searches backwards through the array for the correct place to insert a value.
// Once the location is found, searching stops.
// Bubble sort iterates through the array, moving the largest values to the top with each pass.
// Sorting is complete when it passes through the array without swapping pairs.
// Selection sort searches the array for the smallest item and inserts it at position i.
// i increments with each swap.
// Merge sort divides the array into two halves, then recursively divides further until there's only one item in the array.
// At this point, the individual arrays are re-merged in order.
// Quick sort creates a pivot point where items to the left are smaller and items to the right are larger than the pivot.
// Items are swapped from either side of the pivot until the array is ordered.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class driver {
	
	static int countMerge = 0, countQuick = 0;

	public static void main(String[] args) throws FileNotFoundException {
		
		// setting up scanner to read a file created with a user-supplied file name
		
		Scanner reqPath = new Scanner(System.in); // opens scanner for user input
		System.out.println("What is the input file name, including extension?"); // asks for extension
		String filePath = reqPath.nextLine(); // stores input into var
		Scanner inputFile = new Scanner(new File(filePath)); // creates file and scanner from file path
		
		// reading the first line of the input file to gauge the file size,
		// then creating an array of that size

		int size = inputFile.nextInt(); // gets array size from first line of input file
		int[] array = new int[size]; // creates array at that size
		int index = 0; // set index for building array
		
		// adding values from the file to an array
		
		while(inputFile.hasNextInt()) { // passing through input file
			int nextNum = inputFile.nextInt(); // storing each int in variable
			array[index] = nextNum; // setting each array item to int in file
			index++; // increment index
		}
		
		int[] insertion = new int[size]; // creating five arrays for copying
		int[] bubble = new int[size];
		int[] selection = new int[size];
		int[] merge = new int[size];
		int[] quick = new int[size];
		
		for(int i = 0; i < size; i++) { // copying original array into five arrays
			insertion[i] = array[i];
			bubble[i] = array[i];
			selection[i] = array[i];
			merge[i] = array[i];
			quick[i] = array[i];
		}
		
		System.out.println("Size = " + size); // printing number of ints
		selectionSort(size, selection); // sorting with three non-recursive algorithms
		insertionSort(size, insertion);
		bubbleSort(size, bubble);
		
		mergeSort(merge); // sorting with recursive algorithms
		System.out.println("Merge sort: " + countMerge); // getting merge count
		quickSort(quick, 0, quick.length - 1);
		System.out.println("Quick sort: " + countQuick); // getting quick count
	
		
	}
	
	public static void selectionSort(int index, int[] a) { // takes an int for the size of the array and the array itself
		
		int tmp, min, minIndex, count = 0; // initializing variables
		
		for(int i = 0; i < index; i++) { // iterating over array
			tmp = a[i]; // storing each array item in temporary variable
			min = a[i]; // storing current value
			minIndex = i;
			for(int k = i + 1; k < index; k++) { // searching items after current
				count++; // counting operations
				if(a[k] < min) { // compares each value to current, finds smallest item
					min = a[k]; // sets current to smallest item found
					minIndex = k; // stores index where smallest item was found
				}
			}
			a[i] = min; // moves current
			a[minIndex] = tmp; // swaps value stored in tmp with k
		}
		
		System.out.println("Selection sort: " + count); // prints count
	}
	
	public static void insertionSort(int index, int[] a) {
		
		int count = 0;
		
		for(int i = 1; i < a.length; i++) { // iterates over list
			int tmp = a[i]; // stores each item in a tmp var
			int j;
			for(j = i-1; j >= 0 && tmp < a[j]; j--) { // searches backwards 
				count++; // counting operations
				a[j + 1] = a[j]; // shifts value 
			}
			a[j + 1] = tmp; // moves value stored at tmp over
		}
		
		System.out.println("Insertion sort: " + count); // prints count
	}
	
	public static void bubbleSort(int index, int[] a) {
		boolean sorted = false; 
		int length = index; // stores index in var for decrementing
		int count = 0; // initialize count
		
		while(!sorted) { // iterates over array while not sorted
			sorted = true; // sets to true for final pass
			for (int i = 0; i < length - 1; i++) {
				count++;
				if(a[i] > a [i +1]) { // compares current item with next
					int tmp = a[i]; // stores current
					a[i] = a[i +1]; // swaps current and next
					a[i + 1] = tmp; // sets next to current
					sorted = false;
				}
			}
			length--; // decrementing length with each pass
		}
		
		System.out.println("Bubble sort: " + count); // prints count
	}
	
	public static int[] mergeSort(int[] list) {
        if (list.length <= 1) { // checks for empty list
            return list;
        }
         
        int[] first = new int[list.length / 2]; // divides array into two halves
        int[] last = new int[list.length - first.length];
		
        System.arraycopy(list, 0, first, 0, first.length); // copies first half of array into new array
        System.arraycopy(list, first.length, last, 0, last.length); // copies second half of array into new array
         
        mergeSort(first); // sorts each half recursively
        mergeSort(last);
         
        merge(first, last, list); // merges both halves together, overwriting to original array
        return list;
    }
     
    public static void merge(int[] first, int[] last, int[] result) {
    	
        int index1 = 0; // index of first array, starts at beginning
        int index2 = 0; // index of second array, starts at beginning
        int index3 = 0; // index of merged array, starts at beginning
        
        while (index1 < first.length && index2 < last.length) { // compares items at index1 and index 2, stores smaller at result[index3]
        	countMerge++;
            if (first[index1] < last[index2]) {
                result[index3] = first[index1];
                index1++;
                countMerge++;
            } 
            else { // if item at index2 is bigger than index 1, item at index2 is stored in result array
                result[index3] = last[index2];
                index2++;
                countMerge++;
            }
            index3++;
        }
        
        while(index1 < first.length) { // copy remaining elements from both halves - each half will have already sorted elements
			result[index3++] = first[index1++];
			countMerge++;
		}
		while(index2 < last.length) {
			result[index3++] = last[index2++];
			countMerge++;
		}
		
    }
    
    public static void quickSort(int[] a, int first, int last) {
    	if(first < last) {
    		int pivotIndex = partition(a, first, last); // stores index returned by partition method
    		quickSort(a, first, pivotIndex - 1); // recursively sort first half
    		quickSort(a, pivotIndex + 1, last); // recursively sort second half
    	}
    }
    
    public static int partition(int[] a, int low, int high) { // finding pivot point
    	int right = a[high]; // setting far right
        int pivot = low - 1; // setting pivot point 
        int tmp = 0; 

        for(int i = low; i < high; i++) {
            if(a[i] <= right) { // compares current to right
            	countQuick++; // counts operations
                pivot++;
                tmp = a[pivot]; // swaps pivot point with item
                a[pivot] = a[i];
                a[i] = tmp;
            }
        }
        
        tmp = a[pivot + 1]; // swaps item to right side
        a[pivot + 1] = a[high];
        a[high] = tmp;
        return (pivot + 1); // returns pivot index
    }

}
