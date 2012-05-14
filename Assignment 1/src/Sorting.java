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
					toFill.add(parsedInput[i]);
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
		
		System.arraycopy(inputArray, 0, quickSortArray, 0, inputArray.length);
		System.arraycopy(inputArray, 0, mergeSortArray, 0, inputArray.length);
		System.arraycopy(inputArray, 0, insertionSortArray, 0, inputArray.length);
		System.arraycopy(inputArray, 0, radixSortArray, 0, inputArray.length);
		//TODO: Finish up sorting methods
		insertionSortArray = insertionSort(insertionSortArray);
		quickSortArray = quickSort(quickSortArray, 0, quickSortArray.length);
		mergeSortArray = mergeSort(mergeSortArray, 0, mergeSortArray.length, new String[mergeSortArray.length]);
		radixSortArray = radixSort(radixSortArray);
		for(int i = 0; i < radixSortArray.length; i++)
			System.out.println(radixSortArray[i]);
		
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
		String[] toReturn = null;
		int midV = 0;
		if(maxV > minV+1){
			midV = (maxV - minV) / 2 + minV;
			
			mergeSort(toSort, minV, midV, temp);
			mergeSort(toSort, midV, maxV, temp);
			toReturn = merge(toSort, minV, midV, maxV, temp);
		}
		return toReturn;
	}
	
	private static String[] merge(String[] toSort, int minV, int midV, int maxV, String[] temp)
	{
		int currR = minV;
		int currL = midV;
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
		for(int i = midV; i < maxV; i++)
			toSort[i] = temp[i];
		return toSort;
	}
	
	private static String[] quickSort(String[] toSort, int minV, int maxV){
		int newPivot = randomNum(minV, maxV);
		String tmp = null;
		int big = minV + 1;
		int small = minV + 1;
		
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
		
		for(int i = 0; i < 26; i++){
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
					toTest = 'a';
				}else{
					toTest = tmp.charAt(maxLength - i - 1);
				}
				buckets.get((int)toTest - (int)'a').add(toSort[j]);
			}
			count = 0;
			tmpCount = 0;
			while(count < buckets.size()){
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
			if(toTest[i].compareTo(toTest[i+1]) > 0){
				toReturn = false;
			}
		}
		
		return toReturn;
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
