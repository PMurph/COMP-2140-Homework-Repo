
public class Test {
	public static void main(String[] args){

		/*
		emptyMatrixTests();
		setAndGetTests();
		*/
		matrixMultiplicationTests();
		
		System.out.println("======End of Processing=====");
	}
	
	private static void matrixMultiplicationTests(){
		SparseMatrix sm1 = new SparseMatrix(2,3); 
		SparseMatrix sm2 = new SparseMatrix(3,2);
		
		System.out.println("======Test 9======");
		System.out.println(">Tests whether or not the multiplyBy function works for sparse matricies");
		try{
			//setup matrix 1
			sm1.set(3.0, 1, 1);
			sm1.set(-2.0, 1, 2);
			sm1.set(3.0, 1, 3);
			sm1.set(1.0, 2, 1);
			sm1.set(5.0, 2, 2);
			sm1.set(2.0, 2, 3);
			
			//setup matrix 2
			sm2.set(-1.0, 1, 1);
			sm2.set(1.0, 1, 2);
			sm2.set(2.0, 2, 1);
			sm2.set(0.0, 2, 2);
			sm2.set(3.0, 3, 1);
			sm2.set(1.0, 3, 2);
			
			sm1.multiplyBy(sm2);
		}catch(Exception e){
			System.out.println("Test Passed: False" );
			e.printStackTrace();
		}
		
		System.out.println("======Test 10======");
		System.out.println(">Tests whether or not the multiplyBy function works for a sparse matrix and a 2d array");
		try{
			sm1 = new SparseMatrix(3, 3);
			sm1.set(1.0, 1, 1);
			sm1.set(2.0, 1, 3);
			sm1.set(5.0, 2, 2);
			sm1.set(1.0, 2, 3);
			sm1.set(1.0, 3, 3);
			
			double[][] array2d = new double[3][3];
			array2d[0][0] = 2.0;
			array2d[0][1] = 3.0;
			array2d[0][2] = 4.0;
			array2d[1][0] = 5.0;
			array2d[1][1] = 1.0;
			array2d[1][2] = -1.0;
			array2d[2][0] = -2.0;
			array2d[2][1] = -3.0;
			array2d[2][2] = 4.0;
			
			sm1.multiplyBy(array2d);
		}catch(Exception e){
			System.out.println("Test Passed: False");
			e.printStackTrace();
		}
	}
	
	private static void setAndGetTests(){
		SparseMatrix sm = new SparseMatrix(4, 4);
		double temp = 0.0;
		
		System.out.println("======Test 5======");
		System.out.println(">Tests whether of not a set() call will set the appropriate element in the matrix");
		try{
			sm.set(4.2, 2, 1);
			sm.printMatrix();
			System.out.println("Test Passed: " + (4.2 == sm.get(2, 1)));
		}catch(Exception e){
			System.out.println("Test Passed: False");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Test 6======");
		System.out.println(">Tests whether the set function on a number of border elements in the matrix");
		try{
			sm.set(-1.3, 4, 4);
			sm.set(2.4, 4, 1);
			sm.set(3.14, 1, 4);
			sm.set(2.2, 1, 1);
			sm.printMatrix();
			System.out.println("Test Passed: " + ((4.2 == sm.get(2, 1)) && (3.14 == sm.get(1, 4)) && (-1.3 == sm.get(4, 4)) && (2.4 == sm.get(4,1) && (2.2 == sm.get(1, 1)))));
		}catch(Exception e){
			System.out.println("Test Passed: False");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Test 7======");
		System.out.println(">Tests whether the set function removes nodes if the value 0.0 is passed to it.");
		try{
			sm.set(0.0, 1, 1);
			sm.set(0.0, 4, 4);
			sm.printMatrix();
			System.out.println("Test Passed: " + ((4.2 == sm.get(2, 1)) && (3.14 == sm.get(1, 4)) && (0.0 == sm.get(4, 4)) && (2.4 == sm.get(4,1) && (0.0 == sm.get(1, 1)))));
		}catch(Exception e){
			System.out.println("Test Passed: False");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Test 8======");
		System.out.println(">Tests whether the set function throws the proper exception if passed inapropriate row number");
		try{
			sm.set(4.5, 5, 1);
			System.out.println("Test Passed: False");
		}catch(Exception e){
			sm.printMatrix();
			try{
				System.out.println("Test Passed: " + ((4.2 == sm.get(2, 1)) && (3.14 == sm.get(1, 4)) && (0.0 == sm.get(4, 4)) && (2.4 == sm.get(4,1) && (0.0 == sm.get(1, 1)))));
				e.printStackTrace();
			}catch(Exception i){
				System.out.println("Test Passed: False");
				i.printStackTrace();
			}
		}
	}
	
	private static void emptyMatrixTests(){
		SparseMatrix sm = new SparseMatrix(4, 4);
		double temp = 0.0;
		
		System.out.println("======Test 1======");
		System.out.println(">Tests whether or not a get() call with appropriate parameters on an empty matrix returns 0.0");
		try{
			temp = sm.get(3, 3);
			System.out.println("get(3,3) returned: " + temp);
			System.out.println("Test Passed: " + (temp > 0.2 && temp > -0.2));
		}catch(Exception e){
			System.out.println("Test Passed: False");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Test 2======");
		System.out.println(">Tests whether or no a get() call with an inappropriate row parameter throws an exception");
		try{
			temp = sm.get(5, 3);
			System.out.println("Test Passed: False");
		}catch(Exception e){
			System.out.println("Test Passed: True");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Test 3======");
		System.out.println(">Tests whether or not a get() call with an inappropriate column parameter throws an exception");
		try{
			temp = sm.get(3, 5);
			System.out.println("Test Passed: False");
		}catch(Exception e){
			System.out.println("Test Passed: True");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		
		System.out.println("======Test 4=====");
		System.out.println(">Tests the toString() and printMatrix() methods");
		sm.printMatrix();
	}
}
