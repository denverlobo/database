/*
Same rules as before. Create a function for whatever data you're generating. We'll call that function in main() at the end and supply it with whatever extra data it might need for foreign keys.

If you need any particular data for your function, start out by writing an assumption above the function for the data you want to have -- including the format you want it in -- then write your code using that assumed data passed in as an argument.

Ex:
//Assuming a list of department names are passed in the following
//format: ["CSE", "ECE", ...]
public void generateInstructors(List<String> departmentNames) {
    // Generate instructor data to file "instructors.txt"
}
*/
package database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class NameGenerator {

	private static String[] Beginning = { "Kr", "Ca", "Ra", "Mrok", "Cru", "Ray", "Bre", "Zed", "Drak", "Mor", "Jag",
			"Mer", "Jar", "Mjol", "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro", "Mar", "Luk" };
	private static String[] Middle = { "air", "ir", "mi", "sor", "mee", "clo", "red", "cra", "ark", "arc", "miri",
			"lori", "cres", "mur", "zer", "marac", "zoir", "slamar", "salmar", "urak" };
	private static String[] End = { "d", "ed", "ark", "arc", "es", "er", "der", "tron", "med", "ure", "zur", "cred",
			"mur" };

	private static String[] heading = { "B", "A", "X", "T", "Cr", "Mnfy", "Bre ", "Zed ", "Drak", "Mor", "Jag", "Mer",
			"Jar", "Mjol", "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", " Cro", "M ar", "L uk" };
	private static String[] words = { "d", "ed", "ark ", "of ", " arc", "es", "e r", "de r", "tron", "m ed", "ur e",
			"zu r", "cred", "mur" };
	private static String[] space = { " ", "-", " & " };
	private static String[] ending = { "ce", "d", "le", "ark", "arc", "ers", "at", "tugy", "ion", "tron", "med", "ure",
			"ose", "cred", "fy", "ass", "ck", "uk", "mur" };

	private static Random rand = new Random();

	public static String generateName() {

		return Beginning[rand.nextInt(Beginning.length)] + Middle[rand.nextInt(Middle.length)]
				+ End[rand.nextInt(End.length)];

	}

	public static String generateTitle() {

		return heading[rand.nextInt(heading.length)] + words[rand.nextInt(words.length)]
				+ space[rand.nextInt(space.length)] + ending[rand.nextInt(ending.length)];

	}
}

public class DataGenerator {
	public static int randInt(int min, int max) {
		Random random = new Random();
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	static String newLine = System.getProperty("line.separator");
	static FileWriter file;
	static String output;
	static int scaleFactor = 40000;

	static String[] depts = { "CSE", "MTH", "CHM", "BIO", "HUM", "ECE", "SWE", "PSY" };
	static int[] building = new int[8];
	static int[][] roomNumber = new int[20][15];
	static Map<Integer, ArrayList<Integer>> courseMap = new HashMap<Integer,ArrayList<Integer>>();
	static Map<Integer, ArrayList<Integer>> teachMap = new HashMap<Integer,ArrayList<Integer>>();

	
	public static void generateDepartment() {
		try {
			file = new FileWriter("Department.txt");
			/* Dept table */
			output = new String();

			for (int i = 0; i < 8; i++) {
				building[i] = randInt(1, 20);
				output = depts[i] + "|" + building[i] + "|" + randInt(500000, 2000000);
				//System.out.println(output);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Department table error");
			e.printStackTrace();
		}
	}

	public static void generateClassroom() {
		try {
			file = new FileWriter("Classroom.txt");
			/* Classroom table */
			String output = new String();

			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 15; j++) {
					roomNumber[i][j] = j+1;//randInt(1, 15);
					output = i + 1 + "|" + roomNumber[i][j] + "|" + randInt(15, 200);
					//System.out.println(output);
					file.write(output + newLine);
				}

			}
			file.close();
		} catch (IOException e) {
			System.out.println("ClassRoom table error");
			e.printStackTrace();
		}
	}

	public static void generateTimeSlot() {
		try {
			file = new FileWriter("Timeslot.txt");
			String startTime, endTime;
			String day;
			for (int i = 1; i <= 17; i++) {
				if (randInt(1, 2) % 2 == 0) {
					day = "MWF";

					startTime = String.valueOf((randInt(1, 2) % 2 == 0) ? randInt(8, 12) : randInt(1, 5));
					endTime = String.valueOf(Integer.parseInt(startTime) + 1);
				} else {
					int temp = randInt(1, 7) % 7;
					day = "TR";
					startTime = (temp == 0) ? "8"
							: ((temp == 1) ? "9:30"
									: (temp == 2) ? "11"
											: (temp == 3) ? "12:30" : (temp == 4) ? "2" : (temp == 5) ? "3:30" : "5");
					endTime = (temp == 0) ? "9:15"
							: ((temp == 1) ? "10:45"
									: (temp == 2) ? "12:15"
											: (temp == 3) ? "1:45"
													: (temp == 4) ? "3:15" : (temp == 5) ? "4:45" : "6:15");
				}
				output = i + "|" + day + "|" + startTime + "|" + endTime;
				//System.out.println(output);
				file.write(output + newLine);

			}
			file.close();
		} catch (IOException e) {
			System.out.println("Timeslot table error");
			e.printStackTrace();
		}
	}

	public static void generateStudent() {
		try {
			file = new FileWriter("Student.txt");
			int[] studentId = new int[scaleFactor * 2500];
			for (int j = 0; j < scaleFactor * 2500; j++) {
				studentId[j] = j + 1;
				output = studentId[j] + "|" + NameGenerator.generateName() + "|" + depts[randInt(0, 7)] + "|"
						+ randInt(1, 130);
				//System.out.println(output);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Student table error");
			e.printStackTrace();
		}
	}

	public static void generateInstructor() {
		try {
			file = new FileWriter("Instructor.txt");
			int deptNo ;
			List<Integer> itemList = new ArrayList<Integer>();
			int[] instructorId = new int[scaleFactor * 100];
			for (int j = 0; j < scaleFactor * 100; j++) {
				instructorId[j] = j + 1;
				deptNo = randInt(0, 7);
				
				output = instructorId[j] + "|" + NameGenerator.generateName() + "|" + depts[randInt(0, 7)] + "|"
						+ randInt(30000, 150000);
				//System.out.println(output);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Student table error");
			e.printStackTrace();
		}
	}

	public static void generateAdvisor() {
		try {
			file = new FileWriter("Advisor.txt");
			int k = 1;
			for (int i = 1; i < (scaleFactor * 2500 * 0.9); i++) {
				output = i + "|" + k;
				if (i % 4 == 0) {
					k++;
				}
				//System.out.println(output);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Advisor table error");
			e.printStackTrace();
		}
	}

	public static void generateCourse() {
		try {
			file = new FileWriter("Courses.txt");
			int[] courseId = new int[(int) (scaleFactor * 100 * 2.5)];
			List<Integer> itemList = new ArrayList<Integer>();
			int deptNo;
			for (int i = 0; i < (int) (scaleFactor * 100 * 2.5); i++) {
				courseId[i] = i + 1;
				deptNo = randInt(0, 7);
				output = courseId[i] + "|" + NameGenerator.generateTitle() + "|" + depts[deptNo] + "|" + randInt(1, 4);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Courses table error");
			e.printStackTrace();
		}
	}

	public static void generatePrereq() {
		try {
			file = new FileWriter("PreReq.txt");
			int[] courseId = new int[(int) (scaleFactor * 100 * 2.5 * 0.9)];

			int[] prereq = new int[(int) (scaleFactor * 100 * 2.5 * 0.9)];
			for (int i = 0; i < (int) (scaleFactor * 100 * 2.5 * 0.9); i++) {
				courseId[i] = i+1;
				prereq[i] = randInt(1, (int) (scaleFactor * 100 * 2.5));
				if (courseId[i] == prereq[i]) {
					prereq[i]++;
				}
				output = courseId[i] + "|" + prereq[i];
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("PreReq table error");
			e.printStackTrace();
		}
	}

	public static void generateSection() {
		try {
			file = new FileWriter("Section.txt");
			int[] courseId = new int[(int) (scaleFactor * 100 * 2.5 * 3)];
			String sem;
			int buildingNum;
			for (int i = 0; i < (int) (scaleFactor * 100 * 2.5); i++) {
				courseId[i] = i+1;

				if (randInt(1, 2) % 2 == 0) {
					sem = "Fall";
				} else {
					sem = "Spring";
				}
				buildingNum = randInt(1, 20);
				output = courseId[i] + "|" + randInt(11, 39) /* SectionID */ + "|" + sem + "|"
						+ randInt(2000, 2015) + "|"
						+ buildingNum /* Building num */ + "|" + /* room */roomNumber[buildingNum - 1][randInt(0, 14)]
						+ "|" + randInt(1, 17);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Section table error");
			e.printStackTrace();
		}
	}

	public static void generateTeaches() { // apply Logic
		try {
			file = new FileWriter("Teaches.txt");
			int[] instructorId = new int[(int) (scaleFactor * 100 * 2.5 * 3 * 1.1)];
			int[] courseId = new int[(int) (scaleFactor * 100 * 2.5 * 3 * 1.1)];
			String sem;
			List<Integer> tempList=null;
			int dept;
			for (int i = 0; i < (int) (scaleFactor * 100 * 2.5 * 3 * 1.1); i++) {
				dept= randInt(0, 7);
				instructorId[i] = i+1;
				courseId[i]=randInt(1, (int)(scaleFactor * 100 * 2.5));
				
				if (randInt(1, 2) % 2 == 0) {
					sem = "Fall";
				} else {
					sem = "Spring";
				}
				output = instructorId[i] + "|" + courseId[i] + "|" + randInt(1, 9) /* SectionID */ + "|" + sem + "|"
						+ randInt(2000, 2015);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Teaches table error");
			e.printStackTrace();
		}
	}

	public static void generateTakes() { // apply Logic
		try {
			file = new FileWriter("Takes.txt");
			String sem, grade = "ABCDEF";
			for (int i = 0; i < (int) (scaleFactor * 100 * 2.5 * 3 * 1.1); i++) {
				int studentId = randInt(1, (int) (scaleFactor * 2500));
				int courseId = randInt(1, (int) (scaleFactor * 100 * 2.5));
				if (randInt(1, 2) % 2 == 0) {
					sem = "Fall";
				} else {
					sem = "Spring";
				}
				output = studentId + "|" + courseId + "|" + randInt(1, 9) /* SectionID */ + "|" + sem + "|"
						+ randInt(2000, 2015) + "|" + grade.charAt(randInt(1, 6) % 6);
				file.write(output + newLine);
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Takes table error");
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		long startTime = System.nanoTime();
		generateDepartment();
		generateClassroom();
		generateTimeSlot();
		generateStudent();
		generateInstructor();
		generateCourse();
		generatePrereq();
		generateTeaches();
		generateTakes();
		generateSection();
		long endTime = System.nanoTime();

		long duration = (long) ((endTime - startTime)/1000000000.0); 
		System.out.println("Data Generated with scale factor "+scaleFactor+" with time taken in " +duration+" seconds");
	}
}



