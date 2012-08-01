
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
	
	public boolean cityWithinTwoEdges(int v){
		boolean toReturn = false;

		if(vertexColours[v] != 7){
			toReturn = true;
		}
		
		for(int i = 0; i < adjacencyMatrix[v].length && toReturn == false; i++){
			if(adjacencyMatrix[v][i] != 0 && vertexColours[i] != 7){
				toReturn = true;
			}
		}
		
		return toReturn;
	}
	
	public boolean isUncolouredEdge(int v1, int v2){
		boolean toReturn = false;
		
		if( adjacencyMatrix[v1][v2] == 7){
			toReturn = true;
		}
		
		return toReturn;
	}
	
	public boolean vertexHasRoad(int v, int playerColour){
		boolean toReturn = false;
		
		for(int i = 0; i < adjacencyMatrix[v].length; i++){
			if(adjacencyMatrix[v][i] == playerColour){
				toReturn = true;
			}
		}
		
		return toReturn;
	}
	
	public boolean hasPath(int v1, int v2, int playerColour){
		boolean toReturn = false;
		
		if(vertexColours[v1] == playerColour || vertexColours[v2] == playerColour){
			toReturn = true;
		}else{
			if(vertexColours[v1] == 7 ){
				for(int i = 0; i < adjacencyMatrix[v1].length; i++){
					if(adjacencyMatrix[v1][i] == playerColour){
						toReturn = true;
					}
				}
			}
			
			if(vertexColours[v2] == 7){
				for(int i = 0; i < adjacencyMatrix[v2].length; i++){
					if(adjacencyMatrix[v2][i] == playerColour){
						toReturn = true;
					}
				}
			}
		}
		
		return toReturn;
	}
	
	public void setVertexColour(int v, int colour){
		vertexColours[v] = colour;
	}
	
	public void setEdgeColour(int v1, int v2, int colour){
		adjacencyMatrix[v1][v2] = colour;
		adjacencyMatrix[v2][v1] = colour;
	}
	
	public void printAMatrix(){
		System.out.println("Edges: ");
		
		System.out.printf("%9s", " ");
		
		for(int i = 0; i < adjacencyMatrix.length; i++){
			System.out.printf("%9d", i);
		}
		
		System.out.println();
		
		for(int i = 0; i < adjacencyMatrix.length; i++){
			System.out.printf("%9d", i);
			for(int j = 0; j < adjacencyMatrix[i].length; j++){
				if(adjacencyMatrix[i][j] == 7 || adjacencyMatrix[i][j] == 0){
					System.out.printf("%9s", " ");
				}else{
					System.out.printf("%9d", adjacencyMatrix[i][j]);
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public void printVColours(){
		System.out.println("Vertices: ");
		
		for(int i = 0; i < vertexColours.length; i++){
			System.out.printf("%9s %s%n", i+":" ,getColour(vertexColours[i]));
		}
		
		System.out.println();
	}
	
	public String getColour(int playerColour){
		String toReturn = "uncoloured";
		
		switch (playerColour){
			case 1: toReturn = "red";
				break;
			case 2: toReturn = "yellow";
				break;
			case 3: toReturn = "green";
				break;
			case 4: toReturn = "blue";
				break;
			case 5: toReturn = "orange";
				break;
			case 6: toReturn = "purple";
				break;
		}
		
		return toReturn;
	}
}
