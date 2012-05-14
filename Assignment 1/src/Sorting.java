import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.management.*;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class Sorting {

	private static void processInput(){
		File toParse = getFileViaChooser();
		FileReader fr = null;
		BufferedReader br = null;
		String input = null;
		String[] parsedInput = null;
		String[] inputArray = null;
		ArrayList<String> inputArrayList = new ArrayList<String>();
		
		
		try{
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
		
		insertionSortArray = insertionSort(insertionSortArray);
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
	
	public static void main(String[] args){
		processInput();
	}
}
