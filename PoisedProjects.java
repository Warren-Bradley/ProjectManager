import java.util.ArrayList;
import java.util.Scanner;

public class PoisedProjects {
	
	public static void main(String[] args) {		
		
		//Setting the variable to control the running of the program
		boolean progRunning = true;		
		
		//Opening message to program		
		System.out.print("Welcome to Poised's Project Managment\n\n");
		
		//Creating the Company object called poised
		Company poised = new Company("Poised");		
		
		//Opening the scanner for user input
		Scanner sc= new Scanner(System.in);  
		
		//Starting the program loop
		while (progRunning == true) {				
			
			//Displaying the main menu
			System.out.print("____________________________________________\n");
			System.out.print("\t\tMAIN MENU:\n\n");
			System.out.print("1)Enter new Architect\n");
			System.out.print("2)Enter new Contractor\n");
			System.out.print("3)Create project\n");
			System.out.print("4)Edit a project\n");
			System.out.print("5)Close program\n\n");
						
		    System.out.print("What would you like to do?: ");  
		    
		    //Requesting which an option choice from the user
		    int userChoice = sc.nextInt();	
		    sc.nextLine();
		    		    
			//If user chooses to create a new architect
			if (userChoice == 1) {		
				
				System.out.print("____________________________________________\n");
				
				//Calling the new user method with person role 'architect' as input
				newPerson(poised, sc, "architect");		    
			}
			
			//If user chooses to create a new contractor
			else if (userChoice == 2) {
				
				System.out.print("____________________________________________\n");
				
				//Calling the new user method with person role 'contractor' as input
				newPerson(poised, sc, "contractor");			    			    
			}
			
			//If user chooses to create a new customer
			else if (userChoice == 3) {
										
				System.out.print("____________________________________________\n");
				
				newProject(poised, sc);
			}	
			//If user chooses to edit a project
			else if (userChoice == 4) {
				
				System.out.print("____________________________________________\n");
				
				//Making a list of all the available projects
				ArrayList<Project> projects = poised.getProj();
				
				if (projects.size()==0) {					
					System.out.println("There are no projects in the system.\n");
					System.out.println("Please create a project first.\n");				
				}				
				else {				
					//Counter variable for displaying the options of the projects				
					int count = 1;				
				
					//Displaying all the available projects				
					System.out.println("\nHere are the current projects in our system:\n");				
				
					for (Project proj: projects) {					
						System.out.println(String.format("Option %s)", count));					
						System.out.println(proj);	
						count += 1;
					}				
				
					//Asking the user to choose a project option			
					System.out.println("Which project would you like to edit (Type option number):\n\n");				
					int choice = sc.nextInt();				
					System.out.print("-------------------------------------------\n");
				
					Project project = projects.get(choice-1);				
				
					projects.get(choice-1).editProject(poised, sc, choice, project);				
				}
			}	
			//Closes the program
			else if (userChoice == 5) {
				progRunning = false;
			}
			else {
				System.out.print("\nSorry you have not entered one of the given options!\n");
			}
		}		
		
		//Closing the scanner called sc
		sc.close();
	}	
			
	//Method used to create a new person object		
	public static void newPerson(Company company, Scanner sc, String personRole) {		
		
		//Requesting the required information for the person from the user
		System.out.println(String.format("\nWhat is the %s's name?: ", personRole)); 				
		String name = sc.nextLine();		
		System.out.println("\n\nWhat is their number?: "); 			     
		String number = sc.nextLine();     
		System.out.println("\n\nWhat is their email?: "); 			     
		String email = sc.nextLine();	     
		System.out.println("\n\nWhat is their address?: "); 			     
		String address = sc.nextLine();
		
		//Creating the new object from the class Person
	    Person newPerson = new Person(name, personRole , number, email, address);
	    
	    //Saving it in its role category in the Company object
	    if (personRole == "architect") {	    
	    	company.addArch(newPerson);	
	    }
	    else if (personRole == "contractor") {
	    	company.addContr(newPerson);	
	    }	    
	    
	    //Displaying to the user the list of objects in this person roll that now exist
	    System.out.print("-------------------------------------------\n");
	    System.out.println(String.format("Current %ss now in the system are: ", personRole));
	    System.out.print("-------------------------------------------\n");
	    if (personRole == "architect") {	    
	    	for (Person arch: company.getArch()) {
		    	System.out.println(arch);		    	
	    	}
	    }
	    else if (personRole == "contractor") {
	    	for (Person contr: company.getContr()) {
		    	System.out.println(contr);	    
	    	}	    	
	    }	    
	}
	
	//Method used for creating a new project object
	public static void newProject(Company company, Scanner sc) {
		
		ArrayList<Person> architects = company.getArch(); 
		ArrayList<Person> contractors = company.getContr();		
		ArrayList<Project> projects = company.getProj();
		
		if (architects.size()== 0 || contractors.size()==0) {
			
			System.out.println("\n\nError!: Sorry you can't create a new project yet.");
			System.out.println("\nThere are either no architects, contractors or customers in the system.\n");
			return;			
		}
		
		//Determining the project number		 
		
		int projNum = projects.size() + 1;
		System.out.println(String.format("\nThis new project will be project number %s", projNum));
		System.out.print("-------------------------------------------\n");
		
		//Asking the user for relevant information about the project
		System.out.println("\n\nWhat is the name of the project?: "); 			     
		String name = sc.nextLine(); 
		System.out.println("\n\nWhat is the building type being built?: "); 			     
		String buildingType = sc.nextLine(); 
		System.out.println("\n\nWhat is the address of the build site?: "); 			     
		String address = sc.nextLine(); 
		System.out.println("\n\nWhat is the erf number of the project?: "); 			     
		int erf = sc.nextInt(); 
		System.out.println("\n\nWhat is the final price of the project?: "); 			     
		int price = sc.nextInt(); 
		System.out.println("\n\nHow much has the customer paid so far?: "); 			     
		int totalPaid = sc.nextInt(); 
		sc.nextLine();
		System.out.println("\n\nWhen is the project due?: "); 			     
		String deadline = sc.nextLine(); 
		
		//Asking the user to choose the architect
		int count = 1;
		
		System.out.print("-------------------------------------------\n");
		System.out.println("Here are the available architects in our system:\n");
		System.out.print("-------------------------------------------\n");
				
		for (Person arch: architects) {
			System.out.println(String.format("Option %s)", count));	   
			System.out.println(arch);
			count += 1;
    	}
		
		System.out.println("\n\nType the number of the architect you'd like to allocate to this project.: "); 			     
		int choice = sc.nextInt();
		
		Person architect = architects.get(choice-1);
		
		//Asking the user to choose the contractor
		count = 1;
		
		System.out.print("-------------------------------------------\n");
		System.out.println("Here are the available contractors in our system:\n");
		System.out.print("-------------------------------------------\n");
		
		for (Person contr: contractors) {
			System.out.println(String.format("Option %s)", count));	   
			System.out.println(contr);
			count += 1;
    	}
		
		System.out.println("\n\nType the number of the contractor you'd like to allocate to this project.: "); 			     
		choice = sc.nextInt();
		
		Person contractor = contractors.get(choice-1);		
				
		Project project = new Project(projNum, name, buildingType, address, erf, price, totalPaid, deadline,			   
				architect, contractor, sc);
		
		company.addProj(project);	
	}		
}
