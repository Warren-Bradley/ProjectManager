import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.util.Hashtable;

/**
 * Dates Interface 
 * <br>
 * This interface contains all the methods pertaining to dates and the
 * manipulation thereof.
 *
 * @author Warren Bradley
 * @version 1.00, 05 Sept 2021
 */

public interface Dates {

	/**
	 *
	 * checkDate Method. 
	 * <br>
	 * The method is used to see if the date of a project is overdue or not	 
	 *
	 * @param proj Project object which will have is date checked
	 * @return result String indicating if the project is either 'ok' or
	 * 'overdue'
	 * 
	 * @since version 1.00
	 */
	public static String checkDate(Project proj) throws Exception {

		Hashtable<String, String> dates = new Hashtable<String, String>();

		dates.put("jan", "01");
		dates.put("feb", "02");
		dates.put("mar", "03");
		dates.put("apr", "04");
		dates.put("may", "05");
		dates.put("jun", "06");
		dates.put("jul", "07");
		dates.put("aug", "08");
		dates.put("sep", "09");
		dates.put("oct", "10");
		dates.put("nov", "11");
		dates.put("dec", "12");

		String result = "ok";

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateNow = new Date();

		String[] dateParts = proj.getDeadline().split(" ");

		String projDate = String.format("%s/%s/%s", dateParts[0], dates.get(dateParts[1].toLowerCase()), dateParts[2]);

		Date dateProj = dateFormat.parse(projDate);

		if (dateProj.compareTo(dateNow) < 0) {
			result = "overdue";
		}

		return result;
	}

	/**
	 *
	 * currentDate Method. 
	 * <br>
	 * The method produces the current date in the desired format as a string 	 
	 * 
	 * @return String of the current date of the format dd/MM/yyyy	 
	 * 
	 * @since version 1.00
	 */	 
	public static String currentDate() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateNow = new Date();
		return dateFormat.format(dateNow);
	}

	/**
	 *
	 * checkDateFormat Method. 
	 * <br>
	 * The method is used to check the user entered the correct date format
	 *
	 * @param sc Scanner object to take in user inputs
	 * @return date String of the entered date	 
	 * 
	 * @since version 1.00
	 */
	public static String checkDateFormat(Scanner sc) {
		String date;
		int day = 0;
		int year = 0;
		String month = "";
		String[] dateParts = new String[3];
		boolean numbersEntered = false;
		boolean monthCheck = false;

		while (numbersEntered == false || monthCheck == false) {

			numbersEntered = false;
			monthCheck = false;

			System.out.println("\nWhen is the project due?: ");
			date = sc.nextLine();
			dateParts = date.split(" ");

			try {
				day = Integer.parseInt(dateParts[0]);
				year = Integer.parseInt(dateParts[2]);
				numbersEntered = true;

			} catch (Exception e) {
				System.out.println("Error!: You did not enter a numbers for your day or year\n\n");
				System.out.println("Please enter the date as a number for day, "
						+ "first three letters of month and numbers for year.\n");
				System.out.println("Example: 02 Jul 2021");
			}

			month = dateParts[1];

			if (month.length() == 3) {
				monthCheck = true;
			} else {
				System.out.println("Error!: You did not enter only the first three letters of the month.\n\n");
				System.out.println("Please enter the date as a number for day, "
						+ "first three letters of month and numbers for year.\n");
				System.out.println("Example: 02 Jul 2021");
			}
		}

		date = String.format("%s %s %s", day, month, year);
		return date;
	}
}
