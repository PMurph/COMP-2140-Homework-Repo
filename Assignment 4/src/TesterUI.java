import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import javax.swing.JFileChooser;

public class TesterUI {
	public static void main(String[] args){
		File toParse = getFile();
		Graph gameBoard = null;
		
		System.out.println("Patrick Murphy 6850006");
		System.out.println("COMP 2140 Assignment 4");
		System.out.println("");
		
		if(toParse != null){
			try{
				gameBoard = parseFile(toParse);
				gameBoard.printAMatrix();
				gameBoard.printVColours();
				settlersTests(gameBoard);
			}catch(Exception e){
				System.out.println("Error occured when parsing file");
				e.printStackTrace();
			}
		}else{
			System.out.println("No input file selected");
		}
		
		System.out.println("");
		System.out.println("Game Over");
		System.out.println("");
		System.out.println("======End of Processing======");
	}
	
	private static Graph parseFile(File input) throws Exception{
		Graph toReturn = null;
		int numVertices = 0;
		int[][] adjacencyMatrix = null;
		int[] vColours = null;
		BufferedReader br = null;
		
		br = new BufferedReader(new FileReader(input));
		
		numVertices = getNumVertices(br);
		vColours = getColours(br, numVertices);
		adjacencyMatrix = getEdges(br, numVertices);
		
		toReturn = new Graph(adjacencyMatrix, vColours);
		
		return toReturn;
	}
	
	private static File getFile(){
		File toReturn = null;
		JFileChooser jfc = new JFileChooser();
		int response = 0;
		
		response = jfc.showOpenDialog(null);
		
		if(response == JFileChooser.APPROVE_OPTION){
			toReturn = jfc.getSelectedFile();
		}
		
		return toReturn;
	}
	
	private static int getNumVertices(BufferedReader br) throws Exception{
		int toReturn;
		
		toReturn = Integer.parseInt(br.readLine());
		
		return toReturn;
	}
	
	private static int[] getColours(BufferedReader br, int numVertecies) throws Exception{
		int[] toReturn = new int[numVertecies];
		String[] input = null;
		
		for(int i = 0; i < numVertecies; i++){
			input = br.readLine().split("\\s+");
			toReturn[Integer.parseInt(input[0])] = Integer.parseInt(input[1]);
		}
		
		return toReturn;
	}
	
	private static int[][] getEdges(BufferedReader br, int numVertecies) throws Exception{
		int[][] toReturn = new int[numVertecies][numVertecies];
		String[] input = null;
		
		for(int i = 0; i < numVertecies; i++){
			input = br.readLine().split("\\s+");
			toReturn[Integer.parseInt(input[0])][Integer.parseInt(input[1])] = Integer.parseInt(input[2]);
			toReturn[Integer.parseInt(input[1])][Integer.parseInt(input[0])] = Integer.parseInt(input[2]);
		}
		
		return toReturn;
	}
	
	private static void settlersTests(Graph gameBoard){
		SettlersOfCatan soc = new SettlersOfCatan(gameBoard, 2);
		
		try{
			System.out.println("Test 1 Passed: " + (soc.buildRoad(1, 1, 3) == true));
			System.out.println("Test 2 Passed: " + (soc.buildRoad(2, 2, 0) == true));
			System.out.println("Test 3 Passed: " +(soc.buildRoad(1, 0, 2) == false));
			
		    gameBoard.printAMatrix();
		    gameBoard.printVColours();
		}catch(Exception e){
			System.out.println("Tests Failed");
			e.printStackTrace();
		}
	}
}
