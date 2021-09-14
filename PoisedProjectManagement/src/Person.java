import java.io.Serializable;

/**
 * Person
 * Class 
 * <br>
 * This class keeps track of an individual person stored in the company and all their needed details
 *
 * @author Warren Bradley
 * @version 1.00, 05 Sept 2021
 */

public class Person implements Serializable {

	// Attributes

	//Serial version ID
	private static final long serialVersionUID = 1L;
	
	String name;
	String role;
	String telNum;
	String email;
	String address;

	// Constructor

	public Person(String name, String role, String telNum, String email, String address) {

		this.name = name;
		this.role = role;
		this.telNum = telNum;
		this.email = email;
		this.address = address;
	}

	// Getters

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public String getTel() {
		return telNum;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	// Setters

	public void setName(String newName) {
		name = newName;
	}

	public void setRole(String newRole) {
		role = newRole;
	}

	public void setTel(String newTel) {
		telNum = newTel;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public void setAddress(String newAddr) {
		address = newAddr;
	}

	// To string
	public String toString() {
		String output = "\nName: " + name;
		output += "\nRole in the project: " + role;
		output += "\nPhone number: " + telNum;
		output += "\nEmail address: " + email;
		output += "\nPhysical Address: " + address + "\n\n";
		return output;
	}
}
