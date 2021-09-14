import java.io.*;
import java.util.*;

/**
 * OperationFunctions 
 * Interface 
 * <br>
 * This interface contains all the methods for the
 * operation of the program
 *
 * @author Warren Bradley
 * @version 1.00, 05 Sept 2021
 */

public interface OperationFunctions {

	/**
	 *
	 * retrieveInt Method. 
	 * <br>
	 * The method is used to make sure the user enters an integer
	 *
	 * @param sc Scanner object for receiving user input
	 * @since version 1.00
	 */
	public static int retrieveInt(Scanner sc) {

		int userChoice;

		while (true) {

			String choice = sc.nextLine();

			try {
				userChoice = Integer.parseInt(choice);
				break;
			} catch (Exception e) {
				System.out.println("Error: You did not enter an integer");
			}

		}
		return userChoice;
	}
	
	/**
	 *
	 * displayProjects Method. 
	 * <br>
	 * The method displays all the currently active projects	 
	 *
	 * @param projects Arraylist containing all the active projects
	 * @since version 1.00
	 */
	public static void displayProjects(ArrayList<Project> projects) {

		if (projects.size() == 0) {
			System.out.println("There are no projects in the system.\n");
			System.out.println("Please create a project first.\n");
		} else {
			// Counter variable for displaying the options of the projects
			int count = 1;

			// Displaying all the available projects
			System.out.println("\nHere are the current projects in our system:\n");

			for (Project proj : projects) {
				System.out.println(String.format("Option %s)", count));
				System.out.println(proj);
				count += 1;
			}
		}
	}
	
	/**
	 *
	 * displayOverdue Method. 
	 * <br>
	 * The method displays all the overdue projects	in the system 
	 *
	 * @param projects Arraylist containing all the active projects
	 * @since version 1.00
	 */	
	public static void displayOverdue(ArrayList<Project> projects) throws Exception {

		// Counter variable for displaying the options of the projects
		int count = 1;

		// Displaying all the available projects
		System.out.println("\nHere are the overdue projects in our system:\n");

		for (Project proj : projects) {

			// Checking if the project is overdue
			String check = Dates.checkDate(proj);

			// if it comes back as overdue, display the project
			if (check == "overdue") {

				System.out.println(String.format("Option %s)", count));
				System.out.println(proj);
				count += 1;
			}
		}

		if (count == 1) {
			System.out.println("\nThere are no overdue projects currently in the system.\n\n");
		}

	}

	/**
	 *
	 * newPerson Method. 
	 * <br>
	 * The method creates a new object from the Person class 
	 *
	 * @param sc Scanner object to allow user input
	 * @param personRole String to indicate the person type (Architect,
	 * Contractor or Customer)
	 * @return returns a Person object of the specified role
	 * @since version 1.00
	 */		
	public static Person newPerson(Scanner sc, String personRole) {

		// Requesting the required information for the person from the user
		System.out.println(String.format("\nWhat is the %s's name?: ", personRole));
		String name = sc.nextLine();
		System.out.println("\n\nWhat is their number?: ");
		String number = sc.nextLine();
		System.out.println("\n\nWhat is their email?: ");
		String email = sc.nextLine();
		System.out.println("\n\nWhat is their address?: ");
		String address = sc.nextLine();

		// Creating the new object from the class Person
		Person newPerson = new Person(name, personRole, number, email, address);

		return newPerson;

	}

	/**
	 *
	 * newProject Method. 
	 * <br>
	 * The method creates a new object from the Project class  and stores it in the Company class
	 *
	 * @param sc Scanner object to allow user input
	 * @param company Company object which the project are stored in	 * 
	 * @since version 1.00
	 */	
	public static void newProject(Company company, Scanner sc) {

		ArrayList<Person> architects = company.getArch();
		ArrayList<Person> contractors = company.getContr();

		if (architects.size() == 0 || contractors.size() == 0) {

			System.out.println("\n\nError!: Sorry you can't create a new project yet.");
			System.out.println("\nThere are either no architects or contractors in the system.\n");
			return;
		}

		// Determining the project number
		int projNum = company.getLastProjNum() + 1;
		company.updateLastProjNum(projNum);

		System.out.println(String.format("\nThis new project will be project number %s", projNum));
		System.out.print("-------------------------------------------\n");

		// Requesting the required information for the customer
		Person customer = newPerson(sc, "customer");

		// Asking the user for relevant information about the project
		System.out.println("\n\nWhat is the building type being built?: ");
		String buildingType = sc.nextLine();
		System.out.println("\n\nWhat is the name of the project?: ");
		String name = sc.nextLine();

		// Setting the project name if they don't give one
		// Sets the project name to "Building type + Customer Surname"
		if (name == "" || name.toLowerCase() == "none") {
			String[] customerNames = customer.getName().split(" ");
			name = String.format("%s %s", buildingType, customerNames[1]);
		}

		// Asking the rest of the project information from the user
		System.out.println("\n\nWhat is the address of the build site?: ");
		String address = sc.nextLine();
		System.out.println("\n\nWhat is the erf number of the project?: ");
		int erf = retrieveInt(sc);
		System.out.println("\n\nWhat is the final price of the project?: ");
		int price = retrieveInt(sc);
		System.out.println("\n\nHow much has the customer paid so far?: ");
		int totalPaid = retrieveInt(sc);
		sc.nextLine();
		// Asking the user for the project date in the correct format
		String deadline = Dates.checkDateFormat(sc);

		// Asking the user to choose the architect
		displayPersonGroup(architects, "architects");
		int choice = 0;

		System.out.println("\n\nType the number of the architect you'd like to allocate to this project.: ");
		choice = retrieveInt(sc);

		Person architect = architects.get(choice - 1);

		// Asking the user to choose the contractor
		displayPersonGroup(contractors, "contractors");

		System.out.println("\n\nType the number of the contractor you'd like to allocate to this project.: ");
		choice = retrieveInt(sc);

		Person contractor = contractors.get(choice - 1);

		// Creating the project object with all the information given
		Project project = new Project(projNum, name, buildingType, address, erf, price, totalPaid, deadline, architect,
				contractor, customer);

		// Adding the project to the company
		company.addProj(project);
	}

	/**
	 *
	 * dispayPersonGroup Method. 
	 * <br>
	 * The method displays the list of all people in a person type group 
	 *
	 * @param group  Arraylist of the person type (architects or contractors)
	 * @param groupName String indicating group type, for the print outs mentioning the group
	 * 
	 * @since version 1.00
	 */	
	public static void displayPersonGroup(ArrayList<Person> group, String groupName) {

		int count = 1;

		System.out.print("-------------------------------------------\n");
		System.out.println(String.format("Here are the available %s in our system:\n", groupName));
		System.out.print("-------------------------------------------\n\n");

		for (Person contr : group) {
			System.out.println(String.format("Option %s)", count));
			System.out.println(contr);
			count += 1;

		}
	}

	/**
	 *
	 * saveProgram Method. 
	 * <br>
	 * The method saves the company class containing all the projects and people to a .txt file
	 *
	 * @param company Company class object containing the stored projects and people	
	 * 
	 * @since version 1.00
	 */	
	public static void saveProgram(Company company) {

		try {
			FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(company);

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");

		}

	}

	/**
	 *
	 * openProgram Method. 
	 * <br>
	 * The method retrieves the company class containing all the projects and people from a .txt file	 
	 * @since version 1.00
	 */	
	public static Company openProgram() {

		Company company = new Company("Poised");

		try {

			FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			company = (Company) oi.readObject();

			oi.close();
			fi.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return company;

	}

}