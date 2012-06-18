import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.management.*;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Sorting {

	private static void processInput(){
		File toParse = null;
		FileReader fr = null;
		BufferedReader br = null;
		String input = null;
		String[] parsedInput = null;
		String[] inputArray = null;
		ArrayList<String> inputArrayList = new ArrayList<String>();
		
		
		try{
			toParse = getFileViaChooser();
			fr = new FileReader(toParse);
			br = new BufferedReader(fr);
			
			input = br.readLine();
			
			while(input != null){
				parsedInput = input.split("[^A-Za-z]+");
				//Chose to remove punctuation
				
				fillArrayList(inputArrayList, parsedInput);
				inputArray = convertALtoArray(inputArrayList);
				
				input = br.readLine();
			}
			sortArray(inputArray);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("===End of Processing===");
	}
	
	private static void fillArrayList(ArrayList<String> toFill, String[] parsedInput){
		for(int i = 0; i < parsedInput.length; i++){
			if(parsedInput[i].matches("[A-Za-z]+")){
					toFill.add(parsedInput[i].toLowerCase());
			}
		}
	}
	
	//Converts an ArrayList to an array
	private static String[] convertALtoArray(ArrayList<String> toConvert){
		String[] toReturn = new String[toConvert.size()];
		
		for(int i = 0; i < toConvert.size(); i++){
			toReturn[i] = toConvert.get(i);
		}
		return toReturn;
	}
	
	private static void sortArray(String[] inputArray){
		String[] quickSortArray = new String[inputArray.length];
		String[] mergeSortArray = new String[inputArray.length];
		String[] insertionSortArray = new String[inputArray.length];
		String[] radixSortArray = new String[inputArray.length];
		
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long startTime, endTime;
		long[] durations = new long[4]; // 0: time for quick sort. 1: time for merge sort. 2: time for insertion sort. 3: time for radix sort
		boolean[] areSorted = new boolean[4]; // 0: did quick sort work? 1: did merge sort work? 2: did insertion sort work? 3: did radix sort work? 
		
		System.arraycopy(inputArray, 0, quickSortArray, 0, inputArray.length);
		System.arraycopy(inputArray, 0, mergeSortArray, 0, inputArray.length);
		System.arraycopy(inputArray, 0, insertionSortArray, 0, inputArray.length);
		System.arraycopy(inputArray, 0, radixSortArray, 0, inputArray.length);
		
		startTime = bean.getCurrentThreadCpuTime();
		try{
			quickSortArray = quickSort(quickSortArray, 0, quickSortArray.length);
		}finally{
			endTime = bean.getCurrentThreadCpuTime();
		}
		durations[0] = endTime - startTime;
		areSorted[0] = isSorted(quickSortArray);
		
		startTime = bean.getCurrentThreadCpuTime();
		try{
			mergeSortArray = mergeSort(mergeSortArray, 0, mergeSortArray.length, new String[mergeSortArray.length]);
		}finally{
			endTime = bean.getCurrentThreadCpuTime();
		}
		durations[1] = endTime - startTime;
		areSorted[1] = isSorted(mergeSortArray);
		
		startTime = bean.getCurrentThreadCpuTime();
		try{
			insertionSortArray = insertionSort(insertionSortArray);
		}finally{
			endTime = bean.getCurrentThreadCpuTime();
		}
		durations[2] = endTime - startTime;
		areSorted[2] = isSorted(insertionSortArray);
		
		startTime = bean.getCurrentThreadCpuTime();
		try{
			radixSortArray = radixSort(radixSortArray);
		}finally{
			endTime = bean.getCurrentThreadCpuTime();
		}
		durations[3] = endTime - startTime;
		areSorted[3] = isSorted(radixSortArray);
		
		printOutput(durations, areSorted);
		/*
		System.out.println();
		System.out.println();
		int count = 0;
		while( count < radixSortArray.length){
			for(int i = 0; i < 30 && count < radixSortArray.length; i++){
				System.out.print(radixSortArray[count] + " ");
				count++;
			}
			System.out.println();
		}
				
		*/
	}
	
	private static String[] insertionSort(String[] toSort){
		String toInsert = null;
		int j = 0;
		
		for(int i = 0 ; i < toSort.length; i++){
			toInsert = toSort[i];
			j = i;
			while( j > 0 && toInsert.compareTo(toSort[j - 1]) < 0 ){
				toSort[j] = toSort[j-1];
				j--;
			}
			toSort[j] = toInsert;
		}
		
		return toSort;
	}
	
	private static String[] mergeSort(String[] toSort, int minV, int maxV, String[] temp){
		String[] toReturn = toSort;
		int midV = 0;
		if(maxV > minV+1){
			midV = (maxV - minV) / 2 + minV;

			toSort = mergeSort(toSort, minV, midV, temp);
			toSort = mergeSort(toSort, midV, maxV, temp);
			toReturn = merge(toSort, minV, midV, maxV, temp);
		}
		return toReturn;
	}

	private static String[] merge(String[] toSort, int minV, int midV, int maxV, String[] temp)
	{
		int currL = minV;
		int currR = midV;
		int currT = minV;
		while(currL < midV && currR < maxV){
			if( toSort[currL].compareTo(toSort[currR]) <= 0){
				temp[currT] = toSort[currL];
				currL++;
			}else{
				temp[currT] = toSort[currR];
				currR++;
			}
			currT++;
		}
		if(currL < midV){
			while(currL < midV ){
				temp[currT] = toSort[currL];
				currT++;
				currL++;
			}
		}else if(currR < maxV){
			while(currR < maxV){
				temp[currT] = toSort[currR];
				currT++;
				currR++;
			}
		}
		for(int i = minV; i < maxV; i++)
			toSort[i] = temp[i];
		return toSort;
	}
	
	private static String[] quickSort(String[] toSort, int minV, int maxV){
		int newPivot = randomNum(minV, maxV);
		String tmp = null;
		int big = minV + 1;
		//int small = minV + 1;
		
		if(maxV > minV + 1){
			tmp = toSort[minV];
			toSort[minV] = toSort[newPivot];
			toSort[newPivot] = tmp;
			newPivot = minV;
			
			for(int i = minV + 1; i < maxV; i++){
				if( toSort[i].compareTo(toSort[newPivot]) < 0 ){
					tmp = toSort[big];
					toSort[big] = toSort[i];
					toSort[i] = tmp;
					big++;
				}
			}
			tmp = toSort[big-1];
			toSort[big - 1] = toSort[newPivot];
			toSort[newPivot] = tmp;
			
			newPivot = big - 1;
			
			quickSort(toSort, minV, newPivot);
			quickSort(toSort, newPivot + 1, maxV);
		}
		
		return toSort;
	}
	
	private static String[] radixSort(String[] toSort){
		int maxLength = 0;
		char toTest = ' ';
		String tmp = "";
		int count = 0;
		int tmpCount = 0;
		ArrayList<ArrayList<String>> buckets = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < 27; i++){
			buckets.add(new ArrayList<String>());
		}
		
		for(int i = 0 ; i < toSort.length; i++){
			if( toSort[i].length() > maxLength){
				maxLength = toSort[i].length();
			}
		}
		
		//Padding strings with lengths smaller than the max length with a's on their right side, so that they will
		//appear before strings of longer length
		for(int i = 0; i < maxLength; i++){
			for(int j = 0; j < toSort.length; j++){
				tmp = toSort[j].toLowerCase();
				if(tmp.length() < maxLength && maxLength - i - 1 > tmp.length()-1){
					toTest = (char)((int)'a'-1);
				}else{
					toTest = tmp.charAt(maxLength - i - 1);
				}
				buckets.get((int)toTest - ((int)'a'-1)).add(toSort[j]);
			}
			count = 0;
			tmpCount = 0;
			while(count < 27){
				for(int j = 0; j < buckets.get(0).size(); j++){
					toSort[tmpCount] = buckets.get(0).get(j);
					tmpCount++;
				}
				buckets.remove(0);
				buckets.add(new ArrayList<String>());
				count++;
			}
		}
		return toSort;
	}
	
	private static boolean isSorted(String[] toTest){
		boolean toReturn = true;
		
		for(int i = 0; i < toTest.length - 1; i++){
			if(toTest[i].toLowerCase().compareTo(toTest[i+1].toLowerCase()) > 0){
				toReturn = false;
				System.out.println(">>>>Not sorted: " + toTest[i] + "   " + toTest[i+1]);
			}
		}
		
		return toReturn;
	}
	
	private static void printOutput(long[] durations, boolean[] areSorted){
		long min = 0;
		int divisor = 1;
		
		min = durations[0];
		for(int i = 1; i < durations.length; i++){
			if( durations[i] < min ){
				min = durations[i];
			}
		}
		
		while(min > 100){
			min = min / 10;
			divisor = divisor * 10;
		}
		
		System.out.println("Patrick Murphy");
		System.out.println("6850006");
		System.out.println("COMP 2140");
		System.out.println("Assignment 1");
		
		System.out.println("");
		
		if(areSorted[0]){
			System.out.printf("%-10s: Success\n", "Quick");
		}else{
			System.out.printf("%-10s: Fail\n", "Quick");
		}
		
		if(areSorted[1]){
			System.out.printf("%-10s: Success\n", "Merge");
		}else{
			System.out.printf("%-10s: Fail\n", "Merge");
		}
		
		if(areSorted[2]){
			System.out.printf("%-10s: Success\n", "Insert");
		}else{
			System.out.printf("%-10s: Fail\n", "Insert");
		}
		
		if(areSorted[3]){
			System.out.printf("%-10s: Success\n", "Radix");
		}else{
			System.out.printf("%-10s: Fail\n", "Radix");
		}
		
		System.out.println();
		
		System.out.printf("%-10s| ", "Quick");
		for(int i = 0; i < durations[0]/divisor && i < 100; i++)
			System.out.print('#');
		System.out.println(" [" + (durations[0]/1000) + "ms]");
		
		System.out.printf("%-10s| ", "Merge");
		for(int i = 0; i < durations[1]/divisor && i < 100; i++)
			System.out.print('#');
		System.out.println(" [" + (durations[1]/1000) + "ms]");
		
		System.out.printf("%-10s| ", "Insert");
		for(int i = 0; i < durations[2]/divisor && i < 100; i++)
			System.out.print('#');
		System.out.println(" [" + (durations[2]/1000) + "ms]");
		
		System.out.printf("%-10s| ", "Radix");
		for(int i = 0; i < durations[3]/divisor && i < 100; i++)
			System.out.print('#');
		System.out.println(" [" + (durations[3]/1000) + "ms]");
		
		System.out.println("");
	}
	
	private static File getFileViaChooser(){
		JFileChooser jfc = new JFileChooser();
		int chooserVal = 0;
		
		chooserVal = jfc.showOpenDialog(null);
		
		if(chooserVal == JFileChooser.APPROVE_OPTION){
			return jfc.getSelectedFile();
		}
		
		return null;
	}
	
	private static int randomNum(int min, int max){
		return (int)(Math.random() * (max - min)) + min;
	}
	
	public static void main(String[] args){
		processInput();
	}
}

/*============================================
Patrick Murphy
6850006
COMP 2140
Assignment 1

Quick     : Success
Merge     : Success
Insert    : Success
Radix     : Success

Quick     | ############################################################## [62400ms]
Merge     | ############### [15600ms]
Insert    | #################################################################################################### [3697223ms]
Radix     | ############################################## [46800ms]

===End of Processing===
*/
