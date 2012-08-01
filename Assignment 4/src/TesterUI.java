import java.util.Scanner;
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
			System.out.println("Reading file...");
			try{
				gameBoard = parseFile(toParse);
				System.out.println("Done");
				System.out.println();
				
				playGame(new SettlersOfCatan(gameBoard));
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
		String toParse = br.readLine();
		String[] input = null;
		
		while(toParse != null){
			input = toParse.split("\\s+");
			toReturn[Integer.parseInt(input[0])][Integer.parseInt(input[1])] = Integer.parseInt(input[2]);
			toReturn[Integer.parseInt(input[1])][Integer.parseInt(input[0])] = Integer.parseInt(input[2]);
			toParse = br.readLine();
		}
		
		return toReturn;
	}
	
	private static boolean processPlayerInput(SettlersOfCatan soc, String input, int playerNum){
		boolean toReturn = false;
		String[] parsedInput = input.split("\\s+");
		
		if(parsedInput[0].equalsIgnoreCase("road")){
			if(parsedInput.length == 3){
				try{
					if(soc.buildRoad(playerNum, Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[2]))){
						System.out.println("Building a " + soc.getPlayerColour(playerNum) + " road on edge <" + parsedInput[1] + "," + parsedInput[2] + ">");
						toReturn = true;
					}else{
						System.out.println("Cannot build on edge <" + parsedInput[1] + "," + parsedInput[2] + ">");
					}
				}catch(NumberFormatException nfe){
					System.out.println("road command arguments must be integers");
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("road command arguments must be valid vertices");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("road command has the wrong number of arguments");
			}
		}else if(parsedInput[0].equalsIgnoreCase("settlement")){
			if(parsedInput.length == 2){
				try{
					if(soc.buildSettlement(playerNum, Integer.parseInt(parsedInput[1]))){
						System.out.println("Building a " + soc.getPlayerColour(playerNum) + " settlement on vertex " + parsedInput[1]);
						toReturn = true;
					}else{
						System.out.println("Cannot build a settlement on vertex " + parsedInput[1]);
					}
				}catch(NumberFormatException nfe){
					System.out.println("settlement command argument must be an integer");
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("settlement command argument must be a valid vertex");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("settlement command has the wrong numver of arguments");
			}
		}else if(parsedInput[0].equalsIgnoreCase("pass")){
			toReturn = true;
		}else if(parsedInput[0].equalsIgnoreCase("print")){
			soc.printGameBoard();
		}else{
			System.out.println("Invalid Command: " + input);
		}
		
		return toReturn;
	}
	
	private static void playGame(SettlersOfCatan game){
		Scanner playerInput = new Scanner(System.in);
		String input = "";
		boolean turnFinished = false;
		
		while(!input.equalsIgnoreCase("end")){
			for(int i = 1; i < 7 && !input.equalsIgnoreCase("end"); i++ ){
				turnFinished = false;
				while(turnFinished==false && !input.equalsIgnoreCase("end")){
					System.out.print("Player " + i + ": ");
					input = playerInput.nextLine().trim();
				
					if(!input.equalsIgnoreCase("end")){
						if(processPlayerInput(game, input, i)){
							turnFinished = true;
						}
					}
				}
			}
		}
		
		game.printGameBoard();
	}
}
