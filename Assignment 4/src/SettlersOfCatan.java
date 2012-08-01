
public class SettlersOfCatan {
	private Graph gameMap;
	
	public SettlersOfCatan(Graph inputMap){
		gameMap = inputMap;
	}
	
	public boolean buildSettlement(int playerID, int vertex) throws Exception{
		boolean toReturn = false;
		
		if(gameMap.vertexHasRoad(vertex, playerID) && !gameMap.cityWithinTwoEdges(vertex)){
			toReturn = true;
			gameMap.setVertexColour(vertex, playerID);
		}
		
		return toReturn;
	}
	
	public boolean buildRoad(int playerID, int endPoint1, int endPoint2) throws Exception{
		boolean toReturn = false;
		
		if( endPoint1 < gameMap.numVertices() && endPoint2 < gameMap.numVertices() && gameMap.isUncolouredEdge(endPoint1, endPoint2) 
				&& gameMap.hasPath(endPoint1, endPoint2, playerID)){
			toReturn = true;
			gameMap.setEdgeColour(endPoint1, endPoint2, playerID);
		}
		
		return toReturn;
	}
	
	public String getPlayerColour(int playerID){
		return gameMap.getColour(playerID);
	}
	
	public void printGameBoard(){
		gameMap.printVColours();
		gameMap.printAMatrix();
	}
}
