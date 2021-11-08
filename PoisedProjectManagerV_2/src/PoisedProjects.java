import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * PoisedProjects main class <br>
 * This class is where the main program is run from
 *
 * @author Warren Bradley
 * @version 1.00, 05 Sept 2021
 */

public class PoisedProjects implements OperationFunctions {

	/**
	 *
	 * main Method. <br>
	 * This method is where the main program is run and executed from
	 * 
	 * @param args String array
	 * @since version 1.00
	 */

	public static void main(String[] args) throws Exception {

		// Opening message to program
		System.out.print("Welcome to Poised's Project Managment\n\n");

		// Creating the Company object called poised
		Company poised = new Company("Poised");

		try {
			Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
					"swordfish");
			poised = database.openProgram(poised);
			database.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Opening the scanner for user input
		Scanner sc = new Scanner(System.in);

		// Starting the program loop
		while (true) {

			// Displaying the main menu
			System.out.print("____________________________________________\n");
			System.out.print("\t\tMAIN MENU:\n\n");
			System.out.print("1)Enter new Architect\n");
			System.out.print("2)Enter new Contractor\n");
			System.out.print("3)Create project\n");
			System.out.print("4)Display all active projects\n");
			System.out.print("5)Close program\n\n");

			System.out.print("Please choose an option from above (Type just the number):\n");

			// Requesting which an option choice from the user
			int userChoice = 0;

			userChoice = OperationFunctions.retrieveInt(sc);

			// If user chooses to create a new architect
			if (userChoice == 1) {

				System.out.print("____________________________________________\n");

				// Calling the new user method with person role 'architect' as input
				Person newPerson = OperationFunctions.newPerson(poised, sc, "architect");
				poised.addArch(newPerson);

				// Adding the architect to the database
				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.savePerson(newPerson);
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// If user chooses to create a new contractor
			else if (userChoice == 2) {

				System.out.print("____________________________________________\n");

				// Calling the new user method with person role 'contractor' as input
				Person newPerson = OperationFunctions.newPerson(poised, sc, "contractor");
				poised.addContr(newPerson);

				// Adding the contractor to the database
				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.savePerson(newPerson);
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// If user chooses to create a new project
			else if (userChoice == 3) {

				System.out.print("____________________________________________\n");

				Project newProject = OperationFunctions.newProject(poised, sc);

				// Adding the project to the company
				poised.addProj(newProject);

				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.saveProject(newProject);
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// If user chooses to see all projects
			else if (userChoice == 4) {

				System.out.print("____________________________________________\n");

				// Making a list of all the available projects
				ArrayList<Project> projects = poised.getProj();

				// Displaying all current projects
				OperationFunctions.displayProjects(projects);

				// Asking the user what they would like to do
				System.out.print("Options:\n\n");
				System.out.print("1)Edit a project\n");
				System.out.print("2)See late projects\n");
				System.out.print("3)Find project by name\n");
				System.out.print("4)Return to main menu\n");

				System.out.println("What would you like to do? (Type option number):\n");
				int choice = OperationFunctions.retrieveInt(sc);

				// If the user chooses to edit a project
				if (choice == 1) {
					// Asking the user to choose a project option
					System.out.println("Which project would you like to edit (Type option number):\n\n");
					choice = OperationFunctions.retrieveInt(sc);

					Project project = projects.get(choice - 1);
					projects.get(choice - 1).editProject(poised, sc, choice, project);
				}
				// If the user chooses to see the overdue projects
				else if (choice == 2) {
					OperationFunctions.displayOverdue(projects);
					System.out.print("-------------------------------------------\n");

				}
				// If user chooses to search for project by name
				else if (choice == 3) {

					// Requesting the project name from user
					System.out.println("Please enter the name of the project you're looking for:\n\n");
					String name = sc.nextLine();
					boolean found = false;

					// Looping through projects to see if any match.
					for (Project project : projects) {

						System.out.println(String.format("%s", project.getName().toLowerCase()));
						// If found then go to edit project menu and change boolean found to true.
						if (name.toLowerCase().equals(project.getName().toLowerCase())) {
							found = true;
							project.editProject(poised, sc, choice, project);
						}
					}
					// If no match found then inform user
					if (found == false) {
						System.out.print("Sorry there was no active project found by that name\n");
					}
					System.out.print("-------------------------------------------\n");
				}
			}
			// Closes the program
			else if (userChoice == 5) {
				break;
			} else {
				System.out.print("\nSorry you have not entered one of the given options!\n");
			}
		}

		// Closing the scanner called sc
		sc.close();

		try {
			Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
					"swordfish");
			database.saveCompany(poised);
			database.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
