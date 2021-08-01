import java.util.ArrayList;
import java.util.Scanner;

public class Project {

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
			String deadline, Person architect, Person contractor, Scanner sc) {

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
		
		sc.nextLine();
		
		//Requesting the required information for the customer			
		System.out.println("\nWhat is the customer's name?: "); 				
		String custName = sc.nextLine();						
		System.out.println("\n\nWhat is their number?: "); 					
		String custNumber = sc.nextLine();    			
		System.out.println("\n\nWhat is their email?: ");				
		String email = sc.nextLine();	    
		System.out.println("\n\nWhat is their address?: "); 					
		String custAddress = sc.nextLine();		
				
		//Creating the customer
				
		this.customer = new Person(custName, "customer" , custNumber, email, custAddress);		 
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

	public void setNumber(int newNumber) {
		number = newNumber;
	}
	public void setName(String newName) {
		name = newName;
	}
	public void setType(String newType) {
		buildingType = newType;
	}
	public void setAddress(String newAddress) {
		address = newAddress;
	}
	public void setErf(int newErf) {
		erf = newErf;
	}
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
	}	public void setContractort(Person newContractor) {
		contractor = newContractor;
	}
	public void setCustomer(Person newCustomer) {
		customer = newCustomer;
	}

	// Editors

	public void editProject(Company company, Scanner sc, int choice, Project project) {

		ArrayList<Person> contractors = company.getContr();
		ArrayList<Project> projects = company.getProj();

		System.out.println("The project you have chosen is:\n");
		System.out.println(project);

		boolean edit = true;

		while (edit == true) {

			System.out.print("-------------------------------------------\n");
			System.out.println("Here are the edit options:\n\n");
			System.out.print("1)Change due date\n");
			System.out.print("2)Update customer fee payment\n");
			System.out.print("3)Update contractor details\n");
			System.out.print("4)Close Project\n");
			System.out.print("5)Return to main menu\n\n");

			System.out.print("What would you like to change?: ");
			int option = sc.nextInt();

			if (option == 1) {
								
				System.out.print("-------------------------------------------\n");
				System.out.println("\nWhat is the new date?:");
				sc.nextLine();
				String newDeadline = sc.nextLine();
				projects.get(choice - 1).setDeadline(newDeadline);
				System.out.println("\nEdit has been successful.");
			} 
			else if (option == 2) {
				
				System.out.print("-------------------------------------------\n");
				System.out.println("\nWhat is the new total the customer has paid?:");
				int newTotal = sc.nextInt();
				projects.get(choice - 1).setTotalPaid(newTotal);
				System.out.println("\nEdit has been successful.");
			}
			else if (option == 3) {

				Person contractor = project.getContractor();
				int index = contractors.indexOf(contractor);
				sc.nextLine();
				System.out.print("-------------------------------------------\n");
				System.out.println("\nWhat is the contractors new email?:");
				String change1 = sc.nextLine();
				System.out.println("\nWhat is the contractors new telephone number?:");
				String change2 = sc.nextLine();
				System.out.println("\nWhat is the contractors new address?:");
				String change3 = sc.nextLine();

				company.getProj().get(choice - 1).setContractort(contractor);

				company.updateContr(index, "email", change1);
				company.updateContr(index, "tel", change2);
				company.updateContr(index, "address", change3);

				System.out.println("\nEdit has been successful.");
			}
			else if (option == 4) {
				
				int index = projects.indexOf(project);
				company.getProj().remove(index);
				System.out.println("\nThis project has been successfully removed.");				
			}
			else {
				edit = false;
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
		output += "\nPrice: " + price;
		output += "\nAmount paid: R" + totalPaid;
		output += "\nProject Dealine: " + deadline + "\n\n";
		return output;
	}
}
