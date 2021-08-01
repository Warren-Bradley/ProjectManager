import java.util.ArrayList;

public class Company {
	
	
	// Attributes
	
	String name;	   
	ArrayList<Project> projects;	   
	ArrayList<Person> architects;	   
	ArrayList<Person> contractors; 		
	   
	// Constructor
	   
	public Company(String name) {	      
		   
		this.name = name;		   
		this.projects = new ArrayList<Project>();		   
		this.architects = new ArrayList<Person>();	   
		this.contractors = new ArrayList<Person>();			
	}	
	 
	// Getters
	  
	public ArrayList<Person> getArch() {	     
		return architects;	   
	}	   
	  
	public ArrayList<Person> getContr() {		      
		return contractors;			   
	}	   
	public ArrayList<Project> getProj() {		      
		return projects;		 	   
	}
	   
	//Adders	   
	  
	public void addProj(Project newProject) {		   
		projects.add(newProject);			  
	}	   
	   
	public void addArch(Person newArch) {		   
		architects.add(newArch);		 	   
	}	   
	   
	public void addContr(Person newContr) {		   
		contractors.add(newContr);			   
	}			   
	
	//Updaters
	
	public void updateContr(int index, String type, String change) {
		
		if(type == "tel") {		
			contractors.get(index).setTel(change);				
		}
		else if(type == "email") {		
			contractors.get(index).setEmail(change);
		}
		else if(type == "address") {			
			contractors.get(index).setAddress(change);			
		}		
	}

	
}
