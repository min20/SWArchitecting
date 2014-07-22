package sort;

import java.io.File;

public class SorterMain {
	static ISorter sorter = null;
	static int[] NUMS = { 5, 3, 2, 1, 6, 3, 4, 0 };

	static String DIR = "/Users/min/dev/SoftwareArchitecting/workspace/Example01-Sort/SortStrategy.xml";

	public static void main(String args[]) {
		File file = new File(DIR);

		
		sorter = SorterFactory.getSorter(file);
		System.out.print("Before Sorting: ");
		printArray(NUMS);
		
		sorter.sort(NUMS);
		
		System.out.print("After Sorting: ");
		printArray(NUMS);
		
		return;
	}

	private static void printArray(int array[]) {
		for (int num : array) {
			System.out.print(num + " ");
		}

		System.out.println();
	}

}
