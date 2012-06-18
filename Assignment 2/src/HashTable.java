import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;
import java.util.ArrayList;

interface TableADT{
	public void add(Course data);
	
	public boolean isInTable(Course data);
}
public class HashTable implements TableADT{
	public static final int DEFAULT_MAX_SIZE = 263;
	
	private static int nodesScanned = 0;
	private static String collision = "";
	
	private Node listTop = null;
	private int entries;
	
	public HashTable(){
		Node dummy = new Node(-1, null);
		dummy.setNext(dummy);
		listTop = dummy;
		entries = 0;
	}
	
	public int getNumEntries(){
		return entries;
	}
	
	public void add(Course data) {
		int keyToAdd = data.getKey();
		
		nodesScanned = 0;
		collision = "";
		
		if(entries < DEFAULT_MAX_SIZE){
			addAtKey(keyToAdd, data, 0);
		}
	}
	
	private void addAtKey(int key, Course data, int calls){
		Node currNode = listTop;
		Node toAdd = null;
		
		while(currNode.getNext().getKey() != -1 && currNode.getNext().getKey() < key){
			currNode = currNode.getNext();
		}
		
		if(currNode.getNext().getKey() > key || currNode.getNext().getKey() == -1){
			toAdd = new Node(key, data);
			toAdd.setNext(currNode.getNext());
			currNode.setNext(toAdd);
			entries++;
		}else if(currNode.getNext().getKey() == key){
			if(calls < DEFAULT_MAX_SIZE){
				nodesScanned++;
				collision += currNode.getNext().getData().getCourseName() + ",";
				addAtKey(key + data.probeStep(), data, calls + 1);
			}
		}
	}

	public boolean isInTable(Course data) {
		int keyToCheck = data.getKey();
		
		nodesScanned = 0;
		
		return checkTable(keyToCheck, data, 0);
	}
	
	private boolean  checkTable(int key, Course data, int calls){
		Node currNode = listTop;
		boolean toReturn = false;
		while( currNode.getNext().getKey() != -1 && currNode.getNext().getKey() < key ){
			currNode = currNode.getNext();
		}
		
		if(currNode.getNext().getKey() > key || currNode.getNext().getKey() == -1){
			toReturn =  false;
		}else if(currNode.getNext().getKey() == key){
			if( currNode.getNext().getData().equals(data)){
				toReturn = true;
			}else{
				if(calls < DEFAULT_MAX_SIZE){
					nodesScanned++;
					toReturn = checkTable(key + data.probeStep(), data, calls + 1);
				}else{
					toReturn = false;
				}
			}
		}
		
		return toReturn;
	}
	
	//=================================
	//Main Method
	//=================================
	public static void main(String[] args){
		System.out.println("Patrick Murphy 6850006 (murphypc@cc.umanitoba.ca)");
		System.out.println("");
		System.out.print("Reading course");
		
		ArrayList<Course> courses = getAndParseInput();
		HashTable ht = new HashTable();
		
		addCoursesToTable(ht, courses);
		
		System.out.println("Done reading courses");
		
		System.out.print("Reading lookup");
		courses = getAndParseInput();
		lookupCourses(ht, courses);
		System.out.println("Done reading lookup data");
		System.out.println("");
		
		System.out.println("======End of processing======");
	}
	
	private static void lookupCourses(HashTable ht, ArrayList<Course> courses){
		for(int i = 0; i < courses.size(); i++){
			if(ht.isInTable(courses.get(i))){
				System.out.println("Course " + courses.get(i).getCourseName() + " taught by " + courses.get(i).getInstructorName() + " is in the table " +
						"(scanned " + HashTable.nodesScanned + " nodes)");
			}else{
				System.out.println("Course " + courses.get(i).getCourseName() + " taught by " + courses.get(i).getInstructorName() + " is not in the table " +
						"(scanned " + HashTable.nodesScanned + " nodes)");
			}
		}
	}
	
	private static void addCoursesToTable(HashTable ht, ArrayList<Course> courses){
		for(int i = 0; i < courses.size(); i++){
			if(ht.getNumEntries() < HashTable.DEFAULT_MAX_SIZE && ht.isInTable(courses.get(i)) == false){
				System.out.print("Added course " + courses.get(i).getCourseName() + " taught by " + courses.get(i).getInstructorName() + " ");
				ht.add(courses.get(i));
				if( HashTable.nodesScanned > 0){
					System.out.println("(collision(s) with " + HashTable.collision.substring(0, HashTable.collision.length() - 1) + ")");
				}else{
					System.out.println("(no collision)");
				}
			}else if(ht.isInTable(courses.get(i))){
				System.out.println("Skipping duplicate " + courses.get(i).getCourseName());
			}else{
				System.out.println("Table is full. " + courses.get(i) + " was not added to the table");
			}
		}
	}
	
	private static ArrayList<Course> getAndParseInput(){
		File toParse = getFile();
		BufferedReader br = null;
		ArrayList<Course> courses = new ArrayList<Course>();
		String input = "";
		String[] parsedCourse = new String[5];
		int attrCount = 0;
		
		try{
			br = new BufferedReader(new FileReader(toParse));
			input = br.readLine().trim();
			
			System.out.println(" data from file <" + toParse.getName() + ">");
			
			while(input != null){
				if(input.equals("BEGIN COURSE")){
					attrCount = 0;
				}else if(input.equals("END COURSE")){
					if(attrCount != 5){
						throw new Exception("End of course found prematurely when parsing courses");
					}else{
						courses.add(new Course(parsedCourse[0], parsedCourse[1], parsedCourse[3], parsedCourse[4], Integer.parseInt(parsedCourse[2])));
					}
				}else{
					parsedCourse[attrCount] = input;
					attrCount++;
				}
				input = br.readLine();
				if(input != null){
					input = input.trim();
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return courses;
	}
	
	private static File getFile(){
		JFileChooser jfc = new JFileChooser();
		int chooserVal = 0;
		
		chooserVal = jfc.showOpenDialog(null);
		
		if(chooserVal == JFileChooser.APPROVE_OPTION){
			return jfc.getSelectedFile();
		}else{
			return null;
		}
	}
}

class Course{
	public static final int HASH_CONSTANT = 13;
	
	private int numStudents;
	private String courseName;
	private String instructorName;
	private String lectureTime;
	private String roomNumber;
	
	public Course(String courseName, String instructorName, String lectureTime, String roomNumber, int numStudents){
		this.numStudents = numStudents;
		this.courseName = courseName;
		this.instructorName = instructorName;
		this.lectureTime = lectureTime;
		this.roomNumber = roomNumber;
	}
	
	public int getNumStudents(){
		return numStudents;
	}
		
	public String getCourseName(){
		return courseName;
	}
	
	public String getInstructorName(){
		return instructorName;
	}
	
	public String getLectureTime(){
		return lectureTime;
	}
	
	public String getRoomNumber(){
		return roomNumber;
	}
	
	public int getKey(){
		String toConvert = this.toString();
		int toReturn = 0;
		
		for(int i = 0; i < toConvert.length(); i++){
			toReturn = (toReturn * HASH_CONSTANT + (int)toConvert.charAt(i)) % HashTable.DEFAULT_MAX_SIZE;
		}
		
		return toReturn;
	}
	
	public String toString(){
		return courseName + " " + numStudents + " " + instructorName + " " + lectureTime + " " + roomNumber;
	}
	
	public int probeStep(){
		String toConvert = this.toString();
		int toReturn = 0;
		
		for(int i = 0; i < toConvert.length(); i++){
			toReturn = (toReturn * HASH_CONSTANT + (int)toConvert.charAt(i)) % (HashTable.DEFAULT_MAX_SIZE - 1);
		}
		
		return toReturn + 1;
	}
	
	public boolean equals(Course c){
		return numStudents == c.getNumStudents() && courseName.equals(c.getCourseName()) && instructorName.equals(c.getInstructorName()) &&
				lectureTime.equals(c.getLectureTime()) && roomNumber.equals(c.getRoomNumber());
	}
}

class Node{
	private int key;
	private Course data;
	private Node next;
	
	public Node(int key, Course data){
		this.key = key;
		this.data = data;
	}
	
	public Node getNext(){
		return next;
	}
	
	public void setNext(Node next){
		this.next = next;
	}
	
	public int getKey(){
		return key;
	}
	
	public Course getData(){
		return data;
	}
}
