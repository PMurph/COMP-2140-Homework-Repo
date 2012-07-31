
public class Graph {
	private int[][] adjacencyMatrix;
	private int[] vertexColours;
	
	public Graph(int[][] inMatrix, int[] inColours){
		adjacencyMatrix = inMatrix;
		vertexColours = inColours;
	}
	
	public int numVertices(){
		return vertexColours.length;
	}
	
	public boolean isUncolouredEdge(int v1, int v2){
		boolean toReturn = false;
		
		if( adjacencyMatrix[v1][v2] == 7){
			toReturn = true;
		}
		
		return toReturn;
	}
	
	public boolean hasPath(int v1, int v2, int playerColour){
		boolean toReturn = false;
		
		if(vertexColours[v1] == playerColour || vertexColours[v2] == playerColour){
			toReturn = true;
		}else{
			for(int i = 0; i < adjacencyMatrix[v1].length; i++){
				if(adjacencyMatrix[v1][i] == playerColour){
					toReturn = true;
				}
			}
			
			for(int i = 0; i < adjacencyMatrix[v2].length; i++){
				if(adjacencyMatrix[v2][i] == playerColour){
					toReturn = true;
				}
			}
		}
		
		return toReturn;
	}
	
	public void setEdgeColour(int v1, int v2, int colour){
		adjacencyMatrix[v1][v2] = colour;
		adjacencyMatrix[v2][v1] = colour;
	}
	
	public void printAMatrix(){
		System.out.println("Adjacency Matrix: ");
		System.out.println("");
		
		for(int i = 0; i < adjacencyMatrix.length; i++){
			for(int j = 0; j < adjacencyMatrix[i].length; j++){
				System.out.print(adjacencyMatrix[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public void printVColours(){
		System.out.println("Vertices: ");
		System.out.println("");
		
		for(int i = 0; i < vertexColours.length; i++){
			System.out.println(vertexColours[i]);
		}
	}
}
