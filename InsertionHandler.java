/**
 * InsertionHandler.java
 *
 * This class handles the insertion of new data to the database by displaying
 * a menu of options to the user and allowing them to enter the necessary information.
 *
 * The class uses the JDBC API to connect to a database and execute SQL queries.
 *
 * The class has the following public method:
 *  - handle(Scanner getInput, Connection dbconn): 
 *      prompts the user with a menu of options. It uses the getInput Scanner object
 *      and the dbconn (the Database connection), which are its arguments.
 *
 * The class has the following private methods:
 *  - insertEmployee(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a new employee and inserts the 
 *      information into the database.
 *
 *  - insertEmployeeShift(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a new employee shift and inserts 
 *      the information into the database.
 *
 *  - insertCustomer(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a new customer and inserts the 
 *      information into the database.
 *   - insertRoom(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new room and inserts the information
 *       into the database.
 *   - insertBooking(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new booking and inserts the information 
 *       into the database.
 *   - insertAmenity(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new amenity and inserts the information 
 *       into the database.
 *   - insertBookingAmenity(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new booking amenity and inserts the 
 *       information into the database.
 *   - insertAmenityRating(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new amenity rating and inserts the 
 *       information into the database.
 *   - insertPayment(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new payment and inserts the information
 *       into the database.
 * 
 * Usage: This class is intended to used by calling its public methods. It is originally
 *        made to run by the Frontend.java file. It has no main method, and cannot be called
 *        directly.
 *
 * Author: Minh Duong
 * Date: 29th April 2023
 * Course: CSC 460 - Spring 2023
 * Professor: Lester McCann
*/

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;

class InsertionHandler {
  /**
   * Method handle(Scanner getInput, Connection dbconn)
   *
   * Purpose: Handles the insertion of various types of data into the database.
   *          Displays a menu with options for inserting employees, employee shifts, 
   *          customers, rooms, bookings, amenities, booking amenities, amenity ratings, 
   *          or payments. User input is read from the Scanner object passed as a parameter
   *          and data is inserted into the database using the Connection object passed as 
   *          a parameter.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to
   *                their respective sources.
   *
   * Post-condition: The data inserted by the user is stored in the database. Returns "back" 
   *                 if the user chooses to return to the main menu, "exit" if the user 
   *                 chooses to exit the program, or continues to prompt the user for input until 
   *                 a valid input is provided.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input
   *   @param dbconn the Connection object used to connect to the database
   *
   * Returns:
   *   @return "back" if user chooses to return to main menu, "exit" if user chooses to exit 
   *          the program, or continues to prompt the user for input until a valid input is
   *          provided.
   */
  public static String handle(Scanner getInput, Connection dbconn) {
    String input = "";
    while (true) {
      System.out.println("INSERT MODE");
      System.out.println("Please choose one of the following uptions using a single digit");
      System.out.println("or back to return to the main menu, exit to exit the program:");
      System.out.println("1: Insert Employee");
      System.out.println("2: Insert Employee Shift");
      System.out.println("3: Insert Customer");
      System.out.println("4: Insert Room");
      System.out.println("5: Insert Booking");
      System.out.println("6: Insert Amenity");
      System.out.println("7: Insert Booking Amenity");
      System.out.println("8: Insert Amenity Rating");
      System.out.println("9: Insert Payment");
      input = getInput.nextLine();
      System.out.println();
      switch (input) {
        case "1":
          insertEmployee(getInput, dbconn);
          break;
        case "2":
          insertEmployeeShift(getInput, dbconn);
          break;
        case "3":
          insertCustomer(getInput, dbconn);
          break;
        case "4":
          insertRoom(getInput, dbconn);
          break;
        case "5":
          insertBooking(getInput, dbconn);
          break;
        case "6":
          insertAmenity(getInput, dbconn);
          break;
        case "7":
          insertBookingAmenity(getInput, dbconn);
          break;
        case "8":
          insertAmenityRating(getInput, dbconn);
          break;
        case "9":
          insertPayment(getInput, dbconn);
          break;
        case "back":
          return "back";
        case "exit":
          return "exit";
        default:
          System.out.println("Please only type the provided digit, or back or exit.");
          break;
      }
    }
  }

  /**
   * Method insertEmployee(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new employee record into the database with the specified
   *          ID, first name, last name, and job title. User input for ID, first name, 
   *          last name, and job title is read from the Scanner object passed as a parameter.
   *          The employee record is inserted into the database using the Connection object 
   *          passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new employee record is inserted into the database with the specified ID,
   *                first name, last name, and job title.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for ID, first name, last name, 
   *                   and job title
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertEmployee(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("New Employee ID: ");
      String id = getInput.nextLine();
      System.out.println("New Employee First name: ");
      String fname = getInput.nextLine();
      System.out.println("New Employee Last name: ");
      String lname = getInput.nextLine();
      System.out.println("New Employee Job Title: ");
      String job = getInput.nextLine();

      String query = 
        "INSERT INTO soumayagarwal.Employee VALUES(" +
        id + ", '" + fname + "', '" + lname + "', '" + job + "')";
      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertEmployeeShift(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new employee shift record into the database with the specified
   *          ID, shift time and duties. User input for the respective datam which is
   *          read from the Scanner object passed as a parameter.
   *          The employee shift record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new employee shift record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertEmployeeShift(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Employee ID: ");
      String id = getInput.nextLine();
      System.out.println("Shift Date (format: MM/DD/YY): ");
      String date = getInput.nextLine();
      System.out.println("Duties: ");
      String duties = getInput.nextLine();
      System.out.println("Shift Start time hour (0 to 23): ");
      int startTimeHour = getInput.nextInt();
      System.out.println("Shift Start time minute (0 to 59): ");
      int startTimeMinute = getInput.nextInt();
      System.out.println("Shift End time hour (0 to 23): ");
      int endTimeHour = getInput.nextInt();
      System.out.println("Shift End time minute (0 to 59): ");
      int endTimeMinute = getInput.nextInt();
      getInput.nextLine();

      if (startTimeHour < 0 || startTimeHour > 23 ||
          endTimeHour < 0 || endTimeHour > 23 ||
          startTimeMinute < 0 || startTimeMinute > 59 ||
          endTimeMinute < 0 || endTimeMinute > 59) {
        System.out.println("The time is incorrectly formatted.");
        System.out.println();
      }

      String query = 
        "INSERT INTO soumayagarwal.EmployeeShift VALUES(" +
        id + ", " +
        "TO_DATE('" + date + "', 'MM/DD/YY'), " +
        "'" + duties + "', " +
        Integer.toString(startTimeHour * 3600 + startTimeMinute * 60) + ", " +
        Integer.toString(endTimeHour * 3600 + endTimeMinute * 60) + ")";

      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertCustomer(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new customer record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The customer record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new customer record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertCustomer(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Customer ID:");
      String id = getInput.nextLine();
      System.out.println("Customer First Name:");
      String fname = getInput.nextLine();
      System.out.println("Customer Last Name:");
      String lname = getInput.nextLine();
      System.out.println("Email:");
      String email = getInput.nextLine();
      System.out.println("Phone Number:");
      String phone = getInput.nextLine();
      System.out.println("Is the customer a college student? (0: No, 1: Yes)");
      String isCollegeStudent = getInput.nextLine();
      System.out.println("Is the customer a Club 460 Member? (0: No, 1: Yes)");
      String isClubMember = getInput.nextLine();
      System.out.println("Credit Card Type:");
      String cardType = getInput.nextLine();
      System.out.println("Point:");
      int point = getInput.nextInt();
      getInput.nextLine();

      if (!isCollegeStudent.equals("1") && !isCollegeStudent.equals("0")) {
        System.out.println("Wrong format type for isCollegeStudent field");
        System.out.println();
        return;
      }

      if (!isClubMember.equals("1") && !isClubMember.equals("0")) {
        System.out.println("Wrong format type for isClubMember field");
        System.out.println();
        return;
      }

      String query = 
        "INSERT INTO soumayagarwal.Customer VALUES(" +
        id + ", '" + fname + "', '" + lname + "', '" + email + "', '" + phone + "', " +
        isCollegeStudent + ", " + isClubMember + ", " +
        "'" + cardType + "', " + point + ")";

      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertRoom(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new room record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The room record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new room record is inserted into the database with the specified
   *                 information.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertRoom(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Room Number: ");
      int no = getInput.nextInt();
      System.out.println("Room Price:");
      float price = getInput.nextFloat();
      getInput.nextLine();

      String query =
        "INSERT INTO soumayagarwal.Room VALUES(" +
        Integer.toString(no) + ", " + Float.toString(price) + ")";

      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertBooking(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new booking record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The booking record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new booking record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertBooking(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Booking ID: ");
      int bid = getInput.nextInt();
      System.out.println("Customer ID: ");
      int cid = getInput.nextInt();
      System.out.println("Room Number: ");
      int rno = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Check In Date (Format: MM/DD/YY):");
      String checkInDate = getInput.nextLine();
      System.out.println("Check Out Date (Format: MM/DD/YY):");
      String checkOutDate = getInput.nextLine();

      String query = 
        "INSERT INTO soumayagarwal.Booking VALUES(" +
        Integer.toString(bid) + ", " + Integer.toString(cid) + ", " + Integer.toString(rno) + ", " + 
        "TO_DATE('" + checkInDate + "', 'MM/DD/YY'), " +
        "TO_DATE('" + checkOutDate + "', 'MM/DD/YY')" + ")";
      
      if (Utility.checkDoubleBooking(dbconn, checkInDate, checkOutDate, rno)) {
        System.out.println();
        System.out.println("Error: Matching information found, or another query error occured.");
        System.out.println();
      } else {
        insert(query, dbconn);
      }
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertAmenity(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new amenity record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The amenity record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new amenity record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertAmenity(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Amenity ID:");
      int aid = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Name: ");
      String name = getInput.nextLine();
      System.out.println("Price: ");
      float price = getInput.nextFloat();
      getInput.nextLine();

      String query = 
        "INSERT INTO soumayagarwal.Amenity VALUES(" + 
        Integer.toString(aid) + ", '" + name + "', " + Float.toString(price) + ")";

      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertBookingAmenity(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new BookingAmenity record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The BookingAmenity record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new BookingAmenity record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertBookingAmenity(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Booking ID: ");
      int bid = getInput.nextInt();
      System.out.println("Amenity ID: ");
      int aid = getInput.nextInt();
      System.out.println("Quantity: ");
      int qty = getInput.nextInt();
      getInput.nextLine();

      String query = 
        "INSERT INTO soumayagarwal.BookingAmenity VALUES(" + 
        Integer.toString(bid) + ", " + Integer.toString(aid) + ", " + Integer.toString(qty) + 
        ")";

      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertAmenityRating(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new AmenityRating record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The AmenityRating record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new AmenityRating record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertAmenityRating(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Booking ID: ");
      int bid = getInput.nextInt();
      System.out.println("Amenity ID: ");
      int aid = getInput.nextInt();
      System.out.println("Rating: ");
      int rating = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Rating Date (Format: MM/DD/YY):");
      String ratingDate = getInput.nextLine();

      String query = 
        "INSERT INTO soumayagarwal.AmenityRating VALUES(" + 
        Integer.toString(bid) + ", " + Integer.toString(aid) + ", " + 
        Integer.toString(rating) + ", " +
        "TO_DATE('" + ratingDate + "', 'MM/DD/YY')" +
        ")";

      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insertPayment(Scanner getInput, Connection dbconn)
   *
   * Purpose: Inserts a new Payment record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The Payment record is inserted into the database using the Connection
   *          object passed as a parameter and an SQL INSERT statement.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A new Payment record is inserted into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insertPayment(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Booking ID: ");
      int bid = getInput.nextInt();
      System.out.println("Total Amount: ");
      int amt = getInput.nextInt();
      System.out.println("Tips: ");
      int tips = getInput.nextInt();
      getInput.nextLine();

      String query = 
        "INSERT INTO soumayagarwal.Payment VALUES(" +
        Integer.toString(bid) + ", " +
        Integer.toString(amt) + ", " + Integer.toString(tips) + ")";
      insert(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method insert(String query, Connection dbconn)
   * 
   * Purpose: Executes an SQL INSERT statement with the provided query on the database 
   *          using the Connection object passed as a parameter. The method prints a message
   *          to the console indicating whether the insertion was successful or not.
   *
   * Pre-condition: Connection object must be initialized and connected to the database.
   *
   * Post-condition: The SQL INSERT statement is executed on the database with the provided 
   *                 query, and a message indicating whether the insertion was successful or not
   *                 is printed to the console.
   *
   * Parameters:
   *   @param query the SQL INSERT statement to be executed on the database
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *
   * Returns: None.
   */
  private static void insert(String query, Connection dbconn) {
    System.out.println();
    try {
      Statement stmt = dbconn.createStatement();
      ResultSet answer = stmt.executeQuery(query);
      System.out.println("Insertion Successful!");
      stmt.close();
    } catch (SQLException e) { Utility.printError(e, "Error inserting to Database"); }
    System.out.println();
  }
}
