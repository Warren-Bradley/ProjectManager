import java.util.ArrayList;
import java.io.Serializable;

/**
 * Company Class <br>
 * This class keeps track of the company and what projects and people are
 * available
 *
 * @author Warren Bradley
 * @version 1.00, 05 Sept 2021
 */
public class Company implements Serializable {

	// Serial version ID
	private static final long serialVersionUID = 1L;

	// Attributes

	String name;
	ArrayList<Project> projects;
	ArrayList<Person> architects;
	ArrayList<Person> contractors;
	ArrayList<Person> customers;
	Integer lastProjNum = 0;
	Integer lastPersonID = 0;

	// Constructor

	public Company(String name) {

		this.name = name;
		this.projects = new ArrayList<Project>();
		this.architects = new ArrayList<Person>();
		this.contractors = new ArrayList<Person>();
		this.customers = new ArrayList<Person>();
	}

	// Getters

	public String getName() {
		return name;
	}

	public ArrayList<Person> getArch() {
		return architects;
	}

	public ArrayList<Person> getContr() {
		return contractors;
	}
	
	public ArrayList<Person> getCust() {
		return customers;
	}

	public ArrayList<Project> getProj() {
		return projects;
	}

	public Integer getLastProjNum() {
		return lastProjNum;
	}
	
	public Integer getlastPersonID() {
		return lastPersonID;
	}

	// Adders

	public void addProj(Project newProject) {
		
		//In case the newProjects method did't return a project
		if (newProject == null) {
			return;
		} else {
			projects.add(newProject);
		}
	}

	public void addArch(Person newArch) {
		architects.add(newArch);
	}

	public void addContr(Person newContr) {
		contractors.add(newContr);
	}
	
	public void addCust(Person newCust) {
		customers.add(newCust);
	}

	// Updaters

	/**
	 *
	 * updateContr Method. <br>
	 * The method is used to update details of a contractor in the system
	 *
	 * @param index  Int number indicating where in the contractors Arraylist the
	 *               contractor is stored
	 * @param type   String indicating what information needs to be edited
	 * @param change String indicating what the new information is
	 * @since version 1.00
	 */
	public void updateContr(int index, String type, String change) {

		if (type == "tel") {
			contractors.get(index).setTel(change);
		} else if (type == "email") {
			contractors.get(index).setEmail(change);
		} else if (type == "address") {
			contractors.get(index).setAddress(change);
		}
	}

	/**
	 *
	 * updateLastProjNum Method. <br>
	 * The method used to keep track of the project numbers so new projects are
	 * added can get a unique project number
	 *
	 * @param projNum Integer of the project number of th latest created project *
	 * @since version 1.00
	 */
	public void updateLastProjNum(Integer projNum) {
		lastProjNum = projNum;
	}
	
	public void updatelastPersonID(Integer id) {
		lastPersonID = id;
	}

}
