
public class HashTableTests {
	public static void main(String[] args){
		hashFunctionTests();
		System.out.println("======End of Processing======");
	}
	
	private static void hashFunctionTests(){
		Course c = new Course("", "a", "", "", 1);
		
		System.out.println(c.toString());
	}
}
