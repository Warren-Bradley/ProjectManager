import java.sql.*;
import java.util.ArrayList;

/**
 * Company Class <br>
 * This class keeps and controls database and all the methods that interact with it.
 *
 * @author Warren Bradley
 * @version 2.00, 16 Sept 2021
 */

public class Database {

	private Connection conn = null;

	public Database(String url, String user, String password) throws SQLException {

		conn = DriverManager.getConnection(url, user, password);
	}

	/**
	 *
	 * savePerson Method. <br>
	 * The method takes in a person object and saves them to the database
	 *
	 * @param person Person class object to be saved
	 * 
	 * @since version 2.00
	 */
	public void savePerson(Person person) throws SQLException {

		PreparedStatement statement = conn.prepareStatement(
				"insert into People (ID,Name, Role, Phone, Email, Address) " + "values (?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, String.format("%s", person.id));
		statement.setString(2, person.name);
		statement.setString(3, person.role);
		statement.setString(4, person.telNum);
		statement.setString(5, person.email);
		statement.setString(6, person.address);

		statement.executeUpdate();

		statement.close();

	}

	/**
	 *
	 * saveProject Method. <br>
	 * The method takes in a project object and saves it to the database
	 *
	 * @param project Project class object to be saved
	 * 
	 * @since version 2.00
	 */
	public void saveProject(Project project) throws SQLException {

		PreparedStatement statement = conn.prepareStatement(
				"insert into Projects (Proj_Num, Name, Type, Address, ERF, Price,"
						+ " Paid, Deadline, Arch_ID, Contr_ID, Cust_ID) values (?,?,?,?,?,?,?,?,?,?,?) ",
				Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, String.format("%s", project.number));
		statement.setString(2, project.name);
		statement.setString(3, project.buildingType);
		statement.setString(4, project.address);
		statement.setString(5, String.format("%s", project.erf));
		statement.setString(6, String.format("%s", project.price));
		statement.setString(7, String.format("%s", project.totalPaid));
		statement.setString(8, String.format("%s", project.deadline));
		statement.setString(9, String.format("%s", project.architect.id));
		statement.setString(10, String.format("%s", project.contractor.id));
		statement.setString(11, String.format("%s", project.customer.id));

		statement.executeUpdate();

		statement.close();

	}

	/**
	 *
	 * saveCompany Method. <br>
	 * The method takes the company and saves the id tracking data to the database
	 *
	 * @param company Company class object to be saved
	 * 
	 * @since version 2.00
	 */
	public void saveCompany(Company company) throws SQLException {

		// Creating a direct line to the database for running our queries
		Statement statement = conn.createStatement();
		int rowsAffected;

		// Saving the two variables to the Company data table
		rowsAffected = statement.executeUpdate(String.format("UPDATE Company set Last_Proj_Num = %s WHERE Name = '%s'",
				company.getLastProjNum(), company.getName()));
		rowsAffected = statement
				.executeUpdate(String.format("UPDATE Company set Last_Person_Num = %s WHERE Name = '%s'",
						company.getlastPersonID(), company.getName()));

		System.out.println("\nQuery complete, " + rowsAffected + " rows added.");

	}

	public void closeConnection() throws SQLException {
		conn.close();
	}

	/**
	 *
	 * openProgram Method. <br>
	 * The method retrieves the data from the database and sets it into their
	 * relevant classes
	 * 
	 * @return returns a company class object
	 * 
	 * @since version 2.00
	 */
	public Company openProgram(Company company) throws SQLException {

		Statement statement = conn.createStatement();
		ResultSet resultsCompany = statement.executeQuery("Select * FROM Company");

		while (resultsCompany.next()) {
			company.updateLastProjNum(resultsCompany.getInt("Last_Proj_Num"));
			company.updatelastPersonID(resultsCompany.getInt("Last_Person_Num"));
		}

		ResultSet resultsPeople = statement
				.executeQuery("Select * FROM People");

		while (resultsPeople.next()) {
			Person newPerson = OperationFunctions.dataNewPerson(resultsPeople);			
			if (resultsPeople.getString("Role").equals("architect")) {
				company.addArch(newPerson);
			} else if (resultsPeople.getString("Role").equals("contractor")) {
				company.addContr(newPerson);
			} else {				
				company.addCust(newPerson);
			}
		}

		ResultSet resultsProjects = statement.executeQuery("Select * FROM Projects");

		while (resultsProjects.next()) {
			int archID = resultsProjects.getInt("Arch_ID");
			int contrID = resultsProjects.getInt("Contr_ID");
			int custID = resultsProjects.getInt("Cust_ID");

			ArrayList<Person> arch = company.getArch();
			ArrayList<Person> contr = company.getContr();
			ArrayList<Person> cust = company.getCust();

			Project newProject = OperationFunctions.dataNewProject(statement, resultsProjects, archID, contrID,
					custID, arch, contr, cust);
			company.addProj(newProject);
		}

		statement.close();
		return company;
	}

	/**
	 *
	 * dataUpdatePerson Method. <br>
	 * The method takes in a person that has been updated and changes it in the database
	 * 
	 * @param person Person object that has been updated and needs to be changed in the database
	 * 
	 * @since version 2.00
	 */
	public void dataUpdatePerson(Person person) throws SQLException {

		//Calling all the attribute of the person
		Integer id = person.getId();
		String name = person.getName();
		String role = person.getRole();
		String tel = person.getTel();
		String email = person.getEmail();
		String addr = person.getAddress();

		// Creating a direct line to the database for running our queries
		Statement statement = conn.createStatement();
		
		//Changing the name
		int rowsAffected = statement
				.executeUpdate(String.format("UPDATE People set Name = '%s' WHERE id = %s", name, id));
		//Changing the Role
		rowsAffected = statement.executeUpdate(String.format("UPDATE People set Role = '%s' WHERE id = %s", role, id));		
		//Changing the Phone number
		rowsAffected = statement.executeUpdate(String.format("UPDATE People set Phone = '%s' WHERE id = %s", tel, id));		
		//Changing the email
		rowsAffected = statement.executeUpdate(String.format("UPDATE People set Email = '%s' WHERE id = %s", email, id));		
		//Changing the Address
		rowsAffected = statement.executeUpdate(String.format("UPDATE People set Address = '%s' WHERE id = %s", addr, id));
		System.out.println("\nQuery complete, " + rowsAffected + " rows changed.");
	}
	
	/**
	 *
	 * dataUpdateProject Method. <br>
	 * The method takes in a project that has been updated and changes it in the database
	 * 
	 * @param project Project object that has been updated and needs to be changed in the database
	 * 
	 * @since version 2.00
	 */
	public void dataUpdateProject(Project project) throws SQLException {
		
		//Calling all the attributes of the project
		Integer num = project.getNumber();
		String name = project.getName();
		String type = project.getType();
		String addr = project.getAddress();
		Integer erf = project.getErf();
		Integer price = project.getPrice();
		Integer paid = project.getTotalPaid();
		String deadline = project.getDeadline();
		

		// Creating a direct line to the database for running our queries
		Statement statement = conn.createStatement();
		
		//Changing the name
		int rowsAffected = statement
				.executeUpdate(String.format("UPDATE Projects set Name = '%s' WHERE Proj_Num = %s", name, num));		
		//Changing the Type
		rowsAffected = statement.executeUpdate(String.format("UPDATE Projects set Type = '%s' WHERE Proj_Num = %s", type , num));		
		//Changing the Address
		rowsAffected = statement.executeUpdate(String.format("UPDATE Projects set Address = '%s' WHERE Proj_Num = %s", addr, num));		
		//Changing the ERF
		rowsAffected = statement.executeUpdate(String.format("UPDATE Projects set ERF = %s WHERE Proj_Num = %s", erf, num));		
		//Changing the Price
		rowsAffected = statement.executeUpdate(String.format("UPDATE Projects set Price = %s WHERE Proj_Num = %s", price, num));
		//Changing the amount Paid
		rowsAffected = statement.executeUpdate(String.format("UPDATE Projects set Paid = %s WHERE Proj_Num = %s", paid, num));
		//Changing the Deadline
		System.out.println(deadline);
		rowsAffected = statement.executeUpdate(String.format("UPDATE Projects set Deadline = '%s' WHERE Proj_Num = %s", deadline, num));
		System.out.println("\nQuery complete, " + rowsAffected + " rows changed.");
	}
	
	/**
	 *
	 * dataEndProject Method. <br>
	 * The method takes in a project that has been completed and removes it from the database
	 * 
	 * @param project Project object that has been completed and needs to be removed from the database
	 * 
	 * @since version 2.00
	 */
	public void dataEndProject(Project project) throws SQLException {
		
		Integer num = project.getNumber();		
		Statement statement = conn.createStatement();		

		// Removing the project from the database
		int rowsAffected = statement.executeUpdate(String.format("DELETE FROM Projects WHERE Proj_Num = %s", num));
		System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
				
	}
}
