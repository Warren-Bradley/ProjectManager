import java.util.ArrayList;
import java.util.Formatter;
import java.io.FileWriter;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Project Class <br>
 * This class keeps track of an individual projects stored in the company and
 * all their needed details and methods
 *
 * @author Warren Bradley
 * @version 1.00, 05 Sept 2021
 */

public class Project implements Serializable {

	// Serial version ID
	private static final long serialVersionUID = 1L;

	// Attributes
	int number;
	String name;
	String buildingType;
	String address;
	int erf;
	int price;
	int totalPaid;
	String deadline;
	Person architect;
	Person contractor;
	Person customer;

	// Constructor

	public Project(int number, String name, String buildingType, String address, int erf, int price, int totalPaid,
			String deadline, Person architect, Person contractor, Person customer) {

		this.number = number;
		this.name = name;
		this.buildingType = buildingType;
		this.address = address;
		this.erf = erf;
		this.price = price;
		this.totalPaid = totalPaid;
		this.deadline = deadline;
		this.architect = architect;
		this.contractor = contractor;
		this.customer = customer;

	}

	// Getters
	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return buildingType;
	}

	public String getAddress() {
		return address;
	}

	public int getErf() {
		return erf;
	}

	public int getPrice() {
		return price;
	}

	public int getTotalPaid() {
		return totalPaid;
	}

	public String getDeadline() {
		return deadline;
	}

	public Person getArchitect() {
		return architect;
	}

	public Person getContractor() {
		return contractor;
	}

	public Person getCustomer() {
		return customer;
	}

	// Setters
	public void setPrice(int newPrice) {
		price = newPrice;
	}

	public void setTotalPaid(int newTotal) {
		totalPaid = newTotal;
	}

	public void setDeadline(String newDeadline) {
		deadline = newDeadline;
	}

	public void setArchitect(Person newArchitect) {
		architect = newArchitect;
	}

	public void setContractor(Person newContractor) {
		contractor = newContractor;
	}

	// Editors

	/**
	 *
	 * editProject Method. <br>
	 * The method is used to edit an existing project
	 *
	 * @param company Company object which stores the projects and people
	 * @param sc      Scanner object to take in user inputs
	 * @param choice  Integer reflecting index of which project the user chose to
	 *                edit
	 * @param project Project object of the actual project to be edited
	 * @since version 1.00
	 */
	public void editProject(Company company, Scanner sc, int choice, Project project) {		
		
		//System.out.println(project.getCustomer().getName());
		System.out.println(project.getArchitect().getName());
		System.out.println(project.getContractor().getName());

		ArrayList<Person> contractors = company.getContr();
		ArrayList<Project> projects = company.getProj();

		System.out.print("______________________________________________\n");
		System.out.println("The project you have chosen is:\n");
		System.out.println(project);

		while (true) {

			System.out.print("-------------------------------------------\n");
			System.out.println("Here are the edit options:\n\n");
			System.out.print("1)Change due date\n");
			System.out.print("2)Update customer fee payment\n");
			System.out.print("3)Update contractor details\n");
			System.out.print("4)Close Project\n");
			System.out.print("5)Return to main menu\n\n");

			System.out.print("What would you like to change?: ");
			int option = OperationFunctions.retrieveInt(sc);

			if (option == 1) {

				System.out.print("-------------------------------------------\n");
				System.out.println("\nWhat is the new date?:");				
				String newDeadline = sc.nextLine();
				projects.get(choice - 1).setDeadline(newDeadline);
				System.out.println("\nEdit has been successful.");
				
				//Updating the database
				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.dataUpdateProject(projects.get(choice - 1));
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
			} else if (option == 2) {

				System.out.print("-------------------------------------------\n");
				System.out.println("\nWhat is the new total the customer has paid?:");
				int newTotal = sc.nextInt();
				projects.get(choice - 1).setTotalPaid(newTotal);
				System.out.println("\nEdit has been successful.");
				
				//Updating the database
				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.dataUpdateProject(projects.get(choice - 1));
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			} else if (option == 3) {

				Person contractor = project.getContractor();
				int index = contractors.indexOf(contractor);				
				System.out.print("-------------------------------------------\n");
				System.out.println("\nWhat is the contractors new email?:");
				String change1 = sc.nextLine();
				System.out.println("\nWhat is the contractors new telephone number?:");
				String change2 = sc.nextLine();
				System.out.println("\nWhat is the contractors new address?:");
				String change3 = sc.nextLine();

				company.getProj().get(choice - 1).setContractor(contractor);

				company.updateContr(index, "email", change1);
				company.updateContr(index, "tel", change2);
				company.updateContr(index, "address", change3);

				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.dataUpdatePerson(company.getContr().get(index));
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				System.out.println("\nEdit has been successful.");
			} else if (option == 4) {

				try {
					// Opens the output file to write to it
					String fileName = project.getCustomer().getName();
					System.out.println(fileName);
					FileWriter fi = new FileWriter(String.format("./src/Invoice for %s.txt", fileName ), true);
					Formatter form = new Formatter(fi);

					int cost = project.getPrice();
					int paid = project.getTotalPaid();
					int owed = cost - paid;

					// Adds the project and date to the output file
					form.format("\t\tINVOICE:\n\n");
					form.format(String.format("Project cost: \tR%S\n", cost));
					form.format(String.format("Paid: \tR%S\n\n", paid));
					form.format(String.format("Amount Owed: \tR%S\n\n", owed));
					form.format(String.format("Customer Details:\n\n%s", project.getCustomer()));
					form.close();

				} catch (Exception e) {
					System.out.println("Error");
				}

				// Adding the project to the completed text file
				try {
					// Opens the output file to write to it
					FileWriter f = new FileWriter("./src/Completed projects.txt", true);
					Formatter form = new Formatter(f);

					String currentDate = Dates.currentDate();

					// Adds the project and date to the output file
					form.format(String.format("%s\nDate completed: %s\n\n", project, currentDate));
					form.close();

				} catch (Exception e) {
					System.out.println("Error");
				}
				
				try {
					Database database = new Database("jdbc:mysql://localhost:3306/PoisePMS?useSSL=false", "otheruser",
							"swordfish");
					database.dataEndProject(project);
					database.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// Removing the project from the active project list
				int index = projects.indexOf(project);
				company.getProj().remove(index);
				System.out.println("\nThis project has been successfully removed.");
			} else {
				break;
			}
		}
	}

	// To string
	public String toString() {
		String output = "\nProject Number: " + number;
		output += "\nName in the project: " + name;
		output += "\nBuilding Type: " + buildingType;
		output += "\nProject Address: " + address;
		output += "\nErf number: " + erf;
		output += "\nPrice: R" + price;
		output += "\nAmount paid: R" + totalPaid;
		output += "\nProject Dealine: " + deadline + "\n\n";
		return output;
	}
}
