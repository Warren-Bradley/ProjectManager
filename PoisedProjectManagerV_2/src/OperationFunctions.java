import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * OperationFunctions Interface <br>
 * This interface contains all the methods for the operation of the program
 *
 * @author Warren Bradley
 * @version 2.00, 21 Sept 2021
 */

public interface OperationFunctions {

	/**
	 *
	 * retrieveInt Method. <br>
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
	 * displayProjects Method. <br>
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
	 * displayOverdue Method. <br>
	 * The method displays all the overdue projects in the system
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
	 * newPerson Method. <br>
	 * The method creates a new object from the Person class
	 *
	 * @param sc         Scanner object to allow user input
	 * @param personRole String to indicate the person type (Architect, Contractor
	 *                   or Customer)
	 * @return returns a Person object of the specified role
	 * @since version 1.00
	 */
	public static Person newPerson(Company company, Scanner sc, String personRole) {

		// Determining the person ID
		int id = company.getlastPersonID() + 1;
		company.updatelastPersonID(id);

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
		Person newPerson = new Person(id, name, personRole, number, email, address);

		return newPerson;
	}

	/**
	 *
	 * newProject Method. <br>
	 * The method creates a new object from the Project class and stores it in the
	 * Company class
	 *
	 * @param sc      Scanner object to allow user input
	 * @param company Company object which the project are stored in *
	 * @since version 1.00
	 */
	public static Project newProject(Company company, Scanner sc) {

		ArrayList<Person> architects = company.getArch();
		ArrayList<Person> contractors = company.getContr();

		if (architects.size() == 0 || contractors.size() == 0) {

			System.out.println("\n\nError!: Sorry you can't create a new project yet.");
			System.out.println("\nThere are either no architects or contractors in the system.\n");
			return null;
		}

		// Determining the project number
		int projNum = company.getLastProjNum() + 1;
		company.updateLastProjNum(projNum);

		System.out.println(String.format("\nThis new project will be project number %s", projNum));
		System.out.print("-------------------------------------------\n");

		// Requesting the required information for the customer
		Person customer = newPerson(company, sc, "customer");

		try {
			Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
					"swordfish");
			database.savePerson(customer);
			database.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

		return project;
	}

	/**
	 *
	 * dispayPersonGroup Method. <br>
	 * The method displays the list of all people in a person type group
	 *
	 * @param group     Arraylist of the person type (architects or contractors)
	 * @param groupName String indicating group type, for the print outs mentioning
	 *                  the group
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
	 * dataNewPerson Method. <br>
	 * The method takes in the data from teh database and creates a person object
	 * with it
	 * 
	 * @param results ResultSet object containing the data of an individual person
	 *                relating to the company
	 * 
	 * @since version 2.00
	 */

	public static Person dataNewPerson(ResultSet results) throws SQLException {

		Person newPerson;

		// Pulling the individual attributes of the person
		Integer id = results.getInt("id");
		String name = results.getString("Name");
		String personRole = results.getString("Role");
		String number = results.getString("Phone");
		String email = results.getString("Email");
		String address = results.getString("Address");

		// Creating the new object from the class Person
		newPerson = new Person(id, name, personRole, number, email, address);

		return newPerson;
	}

	/**
	 *
	 * dataNewProject Method. <br>
	 * The method takes in the data from the database and creates a project object
	 * with it
	 * 
	 * @param statement Statement class object representing the open database
	 * 
	 * @param results   ResultSet object containing the data of an individual
	 *                  project relating to the company
	 * 
	 * @since version 2.00
	 */
	public static Project dataNewProject(Statement statement, ResultSet results, int archID, int contrID, int custID,
			ArrayList<Person> arch, ArrayList<Person> contr, ArrayList<Person> cust) throws SQLException {

		if (results == null) {
			return null;
		}

		// Determining the project number
		int projNum = results.getInt("Proj_Num");
		String name = results.getString("Name");
		String buildingType = results.getString("Type");
		String address = results.getString("Address");
		int erf = results.getInt("ERF");
		int price = results.getInt("Price");
		int totalPaid = results.getInt("Price");
		String deadline = results.getString("Deadline");

		// Creating architect object for this individual project
		Person architect = null;

		for (Person person : arch) {
			if (person.getId() == archID) {
				architect = person;
			}
		}

		// Creating contractor object for this individual project
		Person contractor = null;

		for (Person person : contr) {
			if (person.getId() == contrID) {
				contractor = person;
			}
		}

		// Creating customer object for this individual project
		Person customer = null;

		for (Person person : cust) {			
			if (person.getId() == custID) {
				customer = person;
			}
		}

		// Creating the project object with all the information given
		Project project = new Project(projNum, name, buildingType, address, erf, price, totalPaid, deadline, architect,
				contractor, customer);

		return project;
	}

}