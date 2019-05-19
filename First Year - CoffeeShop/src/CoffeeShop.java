//Evan Day R00139868 DNET1

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class CoffeeShop {
	//Static Integers and Keyboard
	final static Scanner kb = new Scanner(System.in);
	
	static final int MAX_NUMBER_OF_CUSTOMERS = 50;
	static final int DEFAULT_CREDIT_OF_POINTS = 20;
	static final int MINIMUM_POINT_REWARD = 5;
	static final int MINIMUM_SPEND_REQUIRED = 1;
	static final int FREE_COFFEE_POINTS_REQUIRED = 50;
	static final int UNCATEGORISED_UPPER_BOUNDARY = 30;
	static final int BRONZE_UPPER_BOUNDARY = 100;
	static final int SILVER_UPPER_BOUNDARY = 200;
	static final int GOLD_LOWER_BOUNDARY  = 200;
	
	static int numberOfRegisteredCustomers = 0;

	static final double COST_OF_COFFEE = 2.50;
	
	public static void main(String[] args) throws IOException {	
		//Contains Arrays, Strings, ints, etc.
		String[] customerCardNumbers = new String[MAX_NUMBER_OF_CUSTOMERS];
		String[] customerNames = new String[MAX_NUMBER_OF_CUSTOMERS];
		String[] customerEmails = new String[MAX_NUMBER_OF_CUSTOMERS];
		
		int[] numberOfPoints = new int[MAX_NUMBER_OF_CUSTOMERS];
		
		double[] spendToDate = new double[MAX_NUMBER_OF_CUSTOMERS];
		
		boolean programActive = true;
		
		//Reading in the customer file and outputting to the customer file
		programActive = readInCustomerFile(customerCardNumbers, customerNames, customerEmails, spendToDate, numberOfPoints,programActive);
		
		//In the event of no customers.txt file existing, the application will exit but generate a blank customers.txt file
		
		if(programActive == false) {
			System.out.println("Please Create a customers.txt file and restart the application");
		}
		while(programActive == true) {
			programActive = displayMenu(customerCardNumbers, customerNames, customerEmails, spendToDate, numberOfPoints);
		}
		outputToCustomerFile(customerCardNumbers, customerNames, customerEmails, spendToDate, numberOfPoints);
	}
	//Application Methods
	public static boolean readInCustomerFile(String[] customerCardNumbers, String[] customerNames, String[] customerEmails, double[] spendToDate,  int[] numberOfPoints, boolean programActive) throws FileNotFoundException {
		/*
		 * Method which reads in the customer.txt file
		 * Checks to make sure a customer file exists
		 * If the file exists, the application will loop to add the values to each respective array
		 * Closes the input file and returns the value "true" to the programActive boolean, which will continue with the application
		 * If no file exists, the user will be warned that there is now file and will return the value "false" to the programActive boolean
		 * The false value will then alert the user to create the customer.txt file and the application will close
		 */
		File customerFile = new File("Customers.txt");
		
		if (customerFile.exists()) {
			Scanner inputFile = new Scanner(customerFile);
			
			for(int index = 0; inputFile.hasNextLine(); index++) {
				customerCardNumbers[index] = inputFile.nextLine();
				customerNames[index] = inputFile.nextLine();
				customerEmails[index] = inputFile.nextLine();
				spendToDate[index] = inputFile.nextDouble();
				inputFile.nextLine();
				numberOfPoints[index] = inputFile.nextInt();
				inputFile.nextLine();
				numberOfRegisteredCustomers = index + 1;
			}
			inputFile.close();
			return true;
		}
		else {
			System.out.println("Warning, Customers file not found");
			return false;
		}
	}
	public static boolean displayMenu(String[] customerCardNumbers, String[] customerNames, String[] customerEmails, double[] spendToDate,  int[] numberOfPoints) throws IOException {
		/*
		 * Displays the entire menu options to the user
		 * User input is based on the switch statement
		 * Also contains a programActive boolean that begins as true
		 * In the event that the user chooses to exit, the boolean flips to false and is returned to the main. This ends the while loop and outputs to the customer.txt file
		 * Switch statement based on calling various methods of the program.
		 */
		boolean programActive = true;
		
		int choice = 0;
		System.out.println("Welcome to Mimis Cafe (Current #Loyality Cards: " + numberOfRegisteredCustomers);
		System.out.println("1. Register a new customer. ");
		System.out.println("2. Display a customer's details. ");
		System.out.println("3. Purchase Coffee. ");
		System.out.println("4. Generate a report for loyality points above a certain value. ");
		System.out.println("5. Generate reports by customer category. ");
		System.out.println("6. Exit. ");
		System.out.print("Enter an option: ");
		
		choice = kb.nextInt();
		
		switch(choice) {
		case 1:
			registerNewCustomer(customerCardNumbers, customerNames, customerEmails, spendToDate, numberOfPoints);
		break;
		case 2:
			displayCustomerDetails(customerCardNumbers, customerNames, customerEmails, spendToDate, numberOfPoints);
		break;
		case 3:
			purchaseCoffee(spendToDate, numberOfPoints, customerCardNumbers);
		break;
		case 4:
			generateLoyalityReport(customerCardNumbers, customerNames, customerEmails, numberOfPoints);
		break;
		case 5:
			generateCustomerCategories(customerNames, customerEmails, spendToDate, numberOfPoints);
		break;
		case 6:
			programActive = false;
		break;
		}
		
		return programActive;
	}
	public static void registerNewCustomer(String[] customerCardNumbers, String[] customerNames, String[] customerEmails, double[] spendToDate,  int[] numberOfPoints) throws FileNotFoundException {
		/*
		 * Registers a new customer to the customer.txt file.
		 * Takes in all required arrays for registering a customer.
		 * User inputs are all redirected to validationMethods.
		 * IF statement to confirm that the current number of loyality cards is not greater than the maximum allowed.
		 * First and last names are converted to uppercase before being concatenated and characters checked to create the loyality card number.
		 * The card details are then printed to the screen.
		 * The number of registered customers are then incremented.
		 * In the event that the maximum amount of customers has been reached, the application will tell the user that there are too many customers registered.
		 */
		String customerFirstName = "";
		String customerSurname = "";
		String customerEmailAddress = "";
		
		
		System.out.println("Enter Customer First Name: ");
		kb.nextLine();	//clearing the buffer
		customerFirstName = validationOfStringContent();
		System.out.println("Enter Customer Surname");
		customerSurname = validationOfStringContent();
		System.out.println("Enter Customer Email:");
		customerEmailAddress = validationOfStringContent();
		
		if (numberOfRegisteredCustomers < MAX_NUMBER_OF_CUSTOMERS) {
			customerNames[numberOfRegisteredCustomers] = customerFirstName + "" + customerSurname;
			customerEmails[numberOfRegisteredCustomers] = customerEmailAddress;
			spendToDate[numberOfRegisteredCustomers] = 0;
			numberOfPoints[numberOfRegisteredCustomers] = DEFAULT_CREDIT_OF_POINTS;
			
			customerFirstName = customerFirstName.toUpperCase();
			customerSurname = customerSurname.toUpperCase();
			
			customerCardNumbers[numberOfRegisteredCustomers] = customerFirstName.charAt(0) + "" + customerSurname.charAt(0) + "" + (numberOfRegisteredCustomers+1);
			
			System.out.println("Issued Card Details");
			System.out.println("---------------------");
			System.out.println("Name:\t\t" + customerNames[numberOfRegisteredCustomers]);
			System.out.println("Card Number:\t" + customerCardNumbers[numberOfRegisteredCustomers] + "\n");
			
			numberOfRegisteredCustomers++;
		}
		else {
			System.out.println("Too many customers have registered");
		}
	}
	public static void displayCustomerDetails(String[] customerCardNumbers, String[] customerNames, String[] customerEmails, double[] spendToDate,  int[] numberOfPoints) throws FileNotFoundException {
		/*
		 * Allows the user to display a specific customer details by inputting their loyality card number.
		 * Input is managed by method getCustomerCardNumber, which checks that the loyality card number exists and what position it is in in relation to arrays.
		 * returns this position as index.
		 * application then prints out the position of that number in relation to the arrays containing the information
		 */
		kb.nextLine(); //clearing the buffer
		int index = getCustomerCardNumber(customerCardNumbers);
		
		System.out.println("The details of this customer are as follows");
		System.out.println("-------------------------------------------");
		System.out.println("Name:\t\t" + customerNames[index]);
		System.out.println("Email:\t\t" + customerEmails[index]);
		System.out.println("Points:\t\t" + numberOfPoints[index]);
		System.out.println("Money Spent:\t" + spendToDate[index]);
		System.out.println("Category:\t" + findCustomerCategory(spendToDate[index]));
	}
	public static void purchaseCoffee(double[] spendToDate,  int[] numberOfPoints,String[] customerCardNumbers) throws FileNotFoundException {
		/*
		 * Main method of the application.
		 * Asks the user for the amount of coffees they wish to purchase.
		 * Input is handled by a validation method to confirm it's a positive integer.
		 * The cost of the amount of coffees is based on the static constant of the cost of coffee being multiplied by the number of coffees
		 * The cost is printed to the screen and the user is asked if they have a loyality card.
		 * This is a singular response which is handled by a validation method validationOfCustomerResponse.
		 * This will return a boolean value customerFalse of true or false.
		 * If the value is true, it is considered as the response is yes.
		 * if the value is false, it is considered as the response is no.
		 */
		int numberOfCoffees = 0;
		double costOfCoffees = 0;
		
		boolean customerResponse = false;
		
		System.out.println("How many coffees do you want to buy: ");
		numberOfCoffees = validationOfPositiveInteger();
		
		costOfCoffees = numberOfCoffees * COST_OF_COFFEE;
		
		System.out.println("Total Cost of Coffee:� " + costOfCoffees);
		System.out.println("Do you have a loyality card? [Y/N]: ");
		customerResponse = validationOfCustomerResponse();
		
		if (customerResponse == true) {	
			 /*
			 *Executes in the event that the customer response to if they have a loyality card is yes.
			 *Calls the validationMethod to get the customers Loyality Card Number.
			 *Various variables which are exclusive to this method.
			 *User is told how many points the customer has on their card.
			 *User is then asked to they want to use their points to pay.
			 * This is a singular response which is handled by a validation method validationOfCustomerResponse.
			 * This will return a boolean value customerFalse of true or false.
			 * If the value is true, it is considered as the response is yes.
			 * if the value is false, it is considered as the response is no.
			 */
			int position = getCustomerCardNumber(customerCardNumbers);
			int pointsReceived = 0;
			int pointsUsed = 0;
			int numberOfFreeCoffees = 0;
			int coffeesOrdered = numberOfCoffees;
			
			double costReduction = 0;
			double costAfterReduction = 0;
			
			System.out.println("You currently have " + numberOfPoints[position] + "points");
			System.out.print("Do you want to use your available points? [Y/N]: ");
			customerResponse = validationOfCustomerResponse();
			
			if (customerResponse == true) {	
				/*
				*Executes in the event that the customer wants to use their points
				*Based on a while loop
				*Loop checks the amount of points they have is greater than or equaled to the constant of the amount of free coffee points needed.
				*Also checks that the number of coffees ordered is greater than zero which is decrimented as the loop runs.
				*Will print out to the user the number of free coffees they have received.
				*It will also show the final amount due, the amount of points they received and the amount of points they have on their card.	
				 */
				while (numberOfPoints[position] >= FREE_COFFEE_POINTS_REQUIRED && coffeesOrdered > 0) {
					pointsUsed += FREE_COFFEE_POINTS_REQUIRED;
					costReduction += COST_OF_COFFEE;
					numberOfFreeCoffees++;
					coffeesOrdered--;
					numberOfPoints[position] -= FREE_COFFEE_POINTS_REQUIRED;
				}
				costAfterReduction = costOfCoffees - costReduction;
				pointsReceived = findNumberOfPointsGenerated(costOfCoffees);
				numberOfPoints[position] += pointsReceived;
				spendToDate[position] += costAfterReduction;
				
				System.out.println("You have received " + numberOfFreeCoffees + " free coffee costing " + pointsUsed + " points");
				System.out.println("Amount Due (�): \t" + costAfterReduction);
				System.out.println("You Have Received " + pointsReceived + " points in this transaction");
				System.out.println("You have " + numberOfPoints[position] + " points on your card");
			}
			else if (customerResponse == false) {
				 /*
				 *Executes in the event that the customer does not want to use their points.
				 *Finds the number of points generated and adds them to the total amount.
				 *Finds the amount they have spent and adds them to their total spend.
				 *Will print out the total amount due, the points received in this transaction and the total number of points on the user's card.
				 */
				pointsReceived = findNumberOfPointsGenerated(costOfCoffees);
				numberOfPoints[position] += pointsReceived;
				spendToDate[position] += costOfCoffees;
				
				System.out.println("Amount Due (�): \t" + costOfCoffees);
				System.out.println("You Have Received " + pointsReceived + " points in this transaction");
				System.out.println("You have " + numberOfPoints[position] + " points on your card");
			}
		}
		else if (customerResponse == false) {	
			 /*
			 *Executes in the event the customer does not have a loyality card.
			 *Finds the amount of points that the user would have received.
			 *Prints the amount due to the screen.
			 *Prints the amount of points they would have received if they had a loyality card.	
			 */
			
			int pointsReceived = 0;
			
			pointsReceived = findNumberOfPointsGenerated(costOfCoffees);
			
			System.out.println("Amount Due (�): \t" + costOfCoffees);
			System.out.println("You Would have gotten " + pointsReceived + " points in this transaction");
		}
	}
	public static void generateLoyalityReport(String[] customerCardNumbers, String[] customerNames, String[] customerEmails, int[] numberOfPoints) {
		/*
		 * Generates a loyality report depending on the number of points entered.
		 * User input is sent to a validation method to confirm its a positive integer.
		 * Uses a for loop to run through the array of customers above said points limit and prints their details
		 */
		int pointLimit = 0;
		
		System.out.println("Points Balance: ");
		pointLimit = validationOfPositiveInteger();
		
		System.out.println("Loyality Card Report for Points Above: " + pointLimit);
		System.out.println("========================================");
		System.out.println("Card Number \t Customer Name \t\t Customer Email \t\t\t Points");
		
		for (int index = 0; index < numberOfRegisteredCustomers; index++) {
			if (numberOfPoints[index] >= pointLimit) {
				System.out.println(customerCardNumbers[index] + "\t\t" + customerNames[index] + "\t\t" + customerEmails[index] + "\t\t" + numberOfPoints[index]);
			}
		}
	}
	public static void generateCustomerCategories(String[] customerNames, String[] customerEmails, double[] spendToDate, int[] numberOfPoints) throws IOException {
		/*
		 * Generates output text files depending on the category entered.
		 * Category entered is sent to a validation method for String content.
		 * If statement which checks that the value is one of the possible values entered.
		 * For loop runs through respective indexes based on an if statement that the correct category is entered.
		 * Dependent on the category entered, an if and if else statement will output the respective report txt file.
		 * In the event that a category doesn't exist, the user is told so and will have to re-enter the category.
		 */
		String category = "";
		String output = "";
		
		System.out.println("Categories are: Uncategorised, Bronze, Silver and Gold");
		System.out.println("Enter the category to generate a report on: ");
		kb.nextLine(); //Clears the buffer.
		category = validationOfStringContent();
		
		if (category.equalsIgnoreCase("uncategorised") || category.equalsIgnoreCase("bronze") || category.equalsIgnoreCase("silver") || category.equalsIgnoreCase("gold") ) {
			output += "Loyalty Card Report for "+category+" customers\n";
			output += "===============================================\n";
			output += "Customer name\t\tCustomer email\n";
			
			for (int index = 0; index < numberOfRegisteredCustomers; index++) {
				if(findCustomerCategory(spendToDate[index]).equalsIgnoreCase(category)) {
					output += (customerNames[index] + "\t\t" + customerEmails[index] + "\n");
				}
			}
			System.out.println(output);
			reportOutputGeneration(output, category);
		}
		else {
			System.out.println(category + " is not a valid category");
		}
		
	}
	public static void reportOutputGeneration(String output, String category) throws IOException {
		/*
		 * Outputs the report file
		 */
		String outputFile = category.toLowerCase() + ".txt";
		Scanner file = new Scanner(output);
		PrintWriter categoryOutput = new PrintWriter(outputFile);
		
		while (file.hasNext()) {
			categoryOutput.println(file.nextLine() + "\n");
		}
		categoryOutput.close();
		file.close();
	}
	public static void outputToCustomerFile(String[] customerCardNumbers, String[] customerNames, String[] customerEmails, double[] spendToDate,  int[] numberOfPoints) throws FileNotFoundException {
		/*
		 * Outputs to the customer file when the application closes.
		 */
		PrintWriter outputFile = new PrintWriter("customers.txt");
		
		for(int index = 0; index < numberOfRegisteredCustomers; index++) {
			outputFile.println(customerCardNumbers[index]);
			outputFile.println(customerNames[index]);
			outputFile.println(customerEmails[index]);
			outputFile.println(spendToDate[index]); 
			outputFile.println(numberOfPoints[index]);
		}
		outputFile.close();
	}
	//Validation Methods
	public static String validationOfStringContent() {
		/*
		 * Based on the validation boolean.
		 * While the boolean is false, it will tell the user to input the correct string.
		 * When the user inputs the right value, the value is returned to the method which this validation method was called from.
		 */
		String value = "";
		
		boolean validation = false;
		
		while (validation == false) {
			value = kb.nextLine();
			if (value.length() < 1) {
				System.out.println("Data input was incorrect, ensure at least one character is entered");
			}
			else {
				validation = true;
			}
		}
		return value;
	}
	public static int validationOfIntContent() {
		/*
		 * Based on the validation boolean
		 * While the boolean is false, it will tell the user that the value entered must be a integer
		 * When the user inputted an integer, the value will be returned to the method which this validation method was called from.
		 */
		int value = 0;
		
		boolean validation = false;
		
		while (validation == false) {
			if (kb.hasNextInt()) {
				value = kb.nextInt();
				validation = true;
			}
			else {
				System.out.println("Value entered must be an integer. Try Again: ");
				kb.nextLine();
			}
		}
		kb.nextLine();
		return value;
	}
	public static int validationOfPositiveInteger() {
		/*
		 * Based on the validation boolean.
		 * While the boolean is false, it will tell the user the value entered must be a positive integer.
		 * When the user inputted a positive integer, the value will be returned to the method which this validation method was called from.
		 */
		int value = 0;
		
		boolean validation = false;
		
		while (validation == false) {
			value = validationOfIntContent();
			if (value > 0) {
				validation = true;
			}
			else {
				System.out.println("Value must be greater than 0. Please try again: ");
			}
		}
		return value;
	}
	public static int findNumberOfPointsGenerated(double costOfCoffees) {
		/*
		 * Finds the number of points generated on a transaction.
		 * Rounds down the spend and casts it to an int.
		 * Returns the value to the method which called it
		 */
		int numberOfPoints = -1;
		int spendRoundedDown;
		
		spendRoundedDown = (int) Math.floor(costOfCoffees);
		
		numberOfPoints = spendRoundedDown * MINIMUM_POINT_REWARD;
		
		return numberOfPoints;
	}
	public static boolean validationOfCustomerResponse() {
		/*
		 * Validates the customer response to yes or no questions
		 * checks to make sure the first character is either a Y or a N
		 * If the value is Y, the customerResponse is true and the validation is true.
		 * If the value is N, the customerResponse is false and the validation is true.
		 * Returns the customerResponse boolean to the method which called it.
		 */
		
		String customerInput = "";
		
		boolean validation = false;
		boolean customerResponse = false;
		
		while (validation == false) {
			customerInput = validationOfStringContent();
			customerInput = customerInput.toUpperCase();
			
			if (customerInput.charAt(0) == 'Y') {
				customerResponse = true;
				validation = true;
			}
			else if (customerInput.charAt(0) == 'N') {
				customerResponse = false;
				validation = true;
			}
			else {
				System.out.println("Invalid Option. Try again, using Y/N: ");
			}
		}
		return customerResponse;
	}
	public static int getCustomerCardNumber(String[] customerCardNumbers) {
		/*
		 * Asks the user to enter the card number
		 * Card number is validated with a String validation method
		 * Method then checks a confirmation Card number method to make sure that the card number exists
		 * If it does exist, the position is returned to the method which called it.
		 * If it does not exist, the user is told that the customer is not found and to try again.
		 */
		String cardNumber = "";
		
		int index = -1;
		
		boolean validation = false;
		
		while (validation == false) {
			System.out.println("Enter Loyality Card Number");
			cardNumber = validationOfStringContent();
			
			index = confirmCardNumber(customerCardNumbers, cardNumber);
			
			if (index != -1) {
				validation = true;
			}
			else {
				System.out.println("Customer Not Found. Please Try Again" + "\n");
			}
		}
		return index;
	}
	public static int confirmCardNumber(String[] customerCardNumbers, String cardNumber) {
		/*
		 * Confirms that the card number entered is in fact in the array of customerCardNumbers
		 * returns the position of the card number in the array
		 */
		int position = -1;
		
		for (int index = 0; index < numberOfRegisteredCustomers; index++) {
			if (customerCardNumbers[index].equalsIgnoreCase(cardNumber)) {
				position = index;
			}
		}
		return position;
	}
	public static String findCustomerCategory(double spendToDate) {
		/*
		 * Finds the correct category that the customer belongs to by checking the spendToDate
		 * depending on the amount spent,will decide the category the user belongs to.
		 */
		if (spendToDate <= UNCATEGORISED_UPPER_BOUNDARY) {
			return "Uncategorised";
		}
		else if (spendToDate > UNCATEGORISED_UPPER_BOUNDARY && spendToDate <= BRONZE_UPPER_BOUNDARY) {
			return "Bronze";
		}
		else if (spendToDate > BRONZE_UPPER_BOUNDARY && spendToDate <= SILVER_UPPER_BOUNDARY) {
			return "Silver";
		}
		else if (spendToDate > SILVER_UPPER_BOUNDARY) {
			return "Gold";
		}
		return "Error";
	}
}
