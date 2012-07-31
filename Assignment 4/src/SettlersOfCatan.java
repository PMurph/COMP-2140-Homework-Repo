
public class SettlersOfCatan {
	private Graph gameMap;
	private int numPlayers;
	
	public SettlersOfCatan(Graph inputMap, int numPlayers){
		gameMap = inputMap;
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
	
	public boolean buildSettlement(){
		return false;
	}
}
