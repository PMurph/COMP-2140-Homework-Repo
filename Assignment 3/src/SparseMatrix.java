
//There should be a file TesterUI.java that should be run instead of this one

public class SparseMatrix {
	private Node matrix;
	private int rows;
	private int columns;
	private boolean printAfterSet;
	
	public SparseMatrix(int numRows, int numColumns){
		matrix = new Node(0, 0, 0.0, new Node( 0, numColumns + 1, 0.0, null, null), new Node(numRows + 1, 0, 0.0, null, null));
		rows = numRows;
		columns = numColumns;
		printAfterSet = true;
	}
	
	public int numRows(){
		return rows;
	}
	
	public int numCols(){
		return columns;
	}
	
	public double get(int row, int column) throws Exception{
		double toReturn = 0.0;
		Node currNode = matrix;
		
		if( row <= 0 || row > rows ){
			throw new Exception("row parameter in the get() function must be > 0 and <= the number of rows in the matrix");
		}
		if( column <= 0 || column > columns ){
			throw new Exception("column paramete in the get() function must be > 0 and <= the number of columns in the matrix");
		}
		
		while(currNode.row <= rows && currNode.row != row){
			currNode = currNode.nextInColumn;
		}
		
		if(currNode.row == row){
			while(currNode.column <= columns && currNode.column != column){
				currNode = currNode.nextInRow;
			}
		}
		
		if(currNode.row == row && currNode.column == column){
			toReturn = currNode.value;
		}
		
		return toReturn;
	}
	
	public void set(double value, int row, int column) throws Exception{
		Node rowHeader = matrix;
		Node colHeader = matrix;
		Node toAdd = new Node(row, column, value, null, null);
		Node temp = null;
		boolean  rowExists = false;
		boolean colExists = false;
		
		if( row <= 0 || row > rows ){
			throw new Exception("row parameter in the set() function must be > 0 and <= the number of rows in the matrix");
		}
		if( column <= 0 || column > columns ){
			throw new Exception("column paramete in the set() function must be > 0 and <= the number of columns in the matrix");
		}
		
		while(rowHeader.nextInColumn.row < row){
			rowHeader = rowHeader.nextInColumn;
		}
		
		if(rowHeader.nextInColumn.row == row){
			rowHeader = rowHeader.nextInColumn;
			rowExists = true;
		}else{
			if(value != 0.0){
				toAdd.nextInRow = new Node(row, columns + 1, 0.0, null, null);
				temp = new Node(row, 0, 0.0, toAdd, rowHeader.nextInColumn );
				rowHeader.nextInColumn = temp;
			}
		}
		
		while( colHeader.nextInRow.column < column ){
			colHeader = colHeader.nextInRow;
		}
		
		if(colHeader.nextInRow.column == column){
			colHeader = colHeader.nextInRow;
			colExists = true;
		}else{
			if(value != 0.0){
				toAdd.nextInColumn = new Node(rows+1, column, 0.0, null, null);
				temp = new Node(0, column, 0.0, colHeader.nextInRow, toAdd );
				colHeader.nextInRow = temp;
			}
		}
		
		if(colExists == true && rowExists == true){
			temp = rowHeader;
			while( temp.nextInRow.column < column ){
				temp = temp.nextInRow;
			}
			
			if(temp.nextInRow.column == column){
				if(value == 0.0){
					temp.nextInRow = temp.nextInRow.nextInRow;
				}else{
					temp.nextInRow.value = value;
				}
			}else{
				if(value != 0.0){
					toAdd.nextInRow = temp.nextInRow;
					temp.nextInRow = toAdd;
				}
			}
			
			temp = colHeader;
			while(temp.nextInColumn.row < row){
				temp = temp.nextInColumn;
			}
			
			if(temp.nextInColumn.row == row){
				if(value == 0.0){
					temp.nextInColumn = temp.nextInColumn.nextInColumn;
				}
			}else{
				if(value != 0.0){
					toAdd.nextInColumn = temp.nextInColumn;
					temp.nextInColumn = toAdd;
				}
			}
		}else if(colExists == true){
			temp = colHeader;
			
			while(temp.nextInColumn.row < row){
				temp = temp.nextInColumn;
			}
			
			if(value != 0.0){
				toAdd.nextInColumn = temp.nextInColumn;
				temp.nextInColumn = toAdd;
			}
		}else if(rowExists == true){
			temp = rowHeader;
			while( temp.nextInRow.column < column ){
				temp = temp.nextInRow;
			}
			
			if(value != 0.0){
				toAdd.nextInRow = temp.nextInRow;
				temp.nextInRow = toAdd;
			}
			
		}
		
		if( printAfterSet == true){
			printMatrix();
		}
	}
	
	public String toString(){
		String toReturn = "";
		Node currNode = matrix.nextInColumn;
		
		for(int i = 0; i < rows; i++){
			if(i + 1 == currNode.row){
				toReturn += rowToString(currNode);
				currNode = currNode.nextInColumn;
			}else{
				toReturn += zeroRowToString();
			}
		}
		
		return toReturn;
	}
	
	public void printMatrix(){
		System.out.println(toString());
	}
	
	public void togglePrintAfterSet(){
		printAfterSet = !printAfterSet;
	}
	
	public SparseMatrix multiplyBy(SparseMatrix other) throws Exception{
		SparseMatrix toReturn = null;
		double sum = 0.0;
		
		if( columns != other.rows )
			throw new Exception("The number of rows in this matrix must be equal to the number of columns in the other matrix");
		
		toReturn = new SparseMatrix(rows, other.columns);
		
		for(int i = 1; i <= rows; i++ ){
			for(int j = 1; j <= other.columns; j++){
				sum = 0;
				for(int k = 1; k <= columns; k++){
					sum += get(i, k) * other.get(k, j);
				}
				toReturn.set(sum, i, j);
			}
		}
		
		return toReturn;
	}
	
	public SparseMatrix multiplyBy(double[][] other) throws Exception{
		SparseMatrix toReturn = null;
		double sum = 0.0;
		
		if( columns != other.length )
			throw new Exception("The number of rows in this matrix must be equal to the number of columns in the other matrix");
		
		toReturn = new SparseMatrix(rows, other[0].length);
		
		for(int i = 1; i <= rows; i++ ){
			for(int j = 1; j <= other[0].length; j++){
				sum = 0;
				for(int k = 1; k <= columns; k++){
					sum += get(i, k) * other[k - 1][j - 1];
				}
				toReturn.set(sum, i, j);
			}
		}
		
		return toReturn;
	}
	
	public static void main(String[] args){
		System.out.println("This file only contains the SparseMatrix class. Use the TesterUI file to run the user interface");
	}
	
	private String rowToString(Node rowStart){
		String toReturn = "";
		Node currNode = rowStart.nextInRow;
		
		for(int i = 0; i < columns; i++){
			if( i == columns - 1){
				if( currNode.column == i + 1){
					toReturn += currNode.value + "\n";
					currNode = currNode.nextInRow;
				}else{
					toReturn += "0.0\n";
				}
			}else{
				if( currNode.column == i + 1){
					toReturn += currNode.value + "\t";
					currNode = currNode.nextInRow;
				}else{
					toReturn += "0.0\t";
				}
			}
		}
		
		return toReturn;
	}
	
	private String zeroRowToString(){
		String toReturn = "";
		
		for(int i = 0; i < columns; i++){
			if( i == columns - 1){
				toReturn += "0.0\n";
			}else{
				toReturn += "0.0\t";
			}
		}
		
		return toReturn;
	}
	
	private class Node{
		public int row;
		public int column;
		public double value;
		public Node nextInRow;
		public Node nextInColumn;
		
		public Node(int r, int c, double newValue, Node rNext, Node cNext){
			row = r;
			column = c;
			value = newValue;
			nextInRow = rNext;
			nextInColumn = cNext;
		}
	}
}
