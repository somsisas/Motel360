/**
 * DeletionHandler.java
 *
 * This class handles the deleteion of new data to the database by displaying
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
 *  - deleteEmployee(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a employee and deletes the 
 *      information FROM the database.
 *
 *  - deleteEmployeeShift(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a employee shift and deletes 
 *      the information FROM the database.
 *
 *  - deleteCustomer(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a customer and deletes the 
 *      information FROM the database.
 *
 *   - deleteRoom(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a room and deletes the information
 *       FROM the database.
 *
 *   - deleteBooking(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a booking and deletes the information 
 *       FROM the database.
 *
 *   - deleteAmenity(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for an amenity and deletes the information 
 *       FROM the database.
 *
 *   - deleteBookingAmenity(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a new booking amenity and deletes the 
 *       information FROM the database.
 *
 *   - deleteAmenityRating(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for an amenity rating and deletes the 
 *       information FROM the database.
 *
 *   - deletePayment(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a payment and deletes the information
 *       FROM the database.
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
import java.util.InputMismatchException;
import java.util.Scanner;

class DeletionHandler {
  /**
   * Method handle(Scanner getInput, Connection dbconn)
   *
   * Purpose: Handles the deletion of various types of data FROM the database.
   *          Displays a menu with options for deleting employees, employee shifts, 
   *          customers, rooms, bookings, amenities, booking amenities, amenity ratings, 
   *          or payments. User input is read FROM the Scanner object passed as a parameter
   *          and data is deleted FROM the database using the Connection object passed as 
   *          a parameter.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to
   *                their respective sources.
   *
   * Post-condition: The data speficied by the user is deleted froom the database. Returns "back" 
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
      System.out.println("DELETE MODE");
      System.out.println("Please choose one of the following uptions using a single digit");
      System.out.println("or back to return to the main menu, exit to exit the program:");
      System.out.println("1: Delete Employee");
      System.out.println("2: Delete Employee Shift");
      System.out.println("3: Delete Booking");
      System.out.println("4: Delete Booking Amenity");
      System.out.println("5: Delete Amenity Rating");
      System.out.println("6: Delete Payment");
      System.out.println("7: Delete Customer");
      System.out.println("8: Delete Room");
      System.out.println("9: Delete Amenity");
      input = getInput.nextLine();
      System.out.println();
      switch (input) {
        case "1":
          deleteEmployee(getInput, dbconn);
          break;
        case "2":
          deleteEmployeeShift(getInput, dbconn);
          break;
        case "3":
          deleteBooking(getInput, dbconn);
          break;
        case "4":
          deleteBookingAmenity(getInput, dbconn);
          break;
        case "5":
          deleteAmenityRating(getInput, dbconn);
          break;
        case "6":
          deletePayment(getInput, dbconn);
          break;
        case "7":
          deleteCustomer(getInput, dbconn);
          break;
        case "8":
          deleteRoom(getInput, dbconn);
          break;
        case "9":
          deleteAmenity(getInput, dbconn);
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
   * Method deleteEmployee(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes an employee record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          ID of the employee record to be deleted, and then deletes that record FROM the 
   *          database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources. 
   * 
   * Post-condition: The employee record with the specified ID is deleted FROM the database.
   *                 Related data FROM Employee Shift will be deleted.
   * Parameters:
   *   @param getInput the Scanner object used to read user input for the ID of the employee 
   *                   record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteEmployee(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the employee ID: ");
      String id = getInput.nextLine();
      
      System.out.println("Warning: The deletion will also delete all associated " +
          "Employee Shift records with the employee ID.");
      System.out.println("Do you still want to continue? (Type 'Y' to continue)");
      String confirm = getInput.nextLine();
      if (!confirm.equals("Y")) {
        System.out.println();
        return; 
      }

      String deleteShiftQuery = 
        "DELETE FROM soumayagarwal.EmployeeShift WHERE employeeId = " + id;
      delete(deleteShiftQuery, dbconn, "");

      String query = 
        "DELETE FROM soumayagarwal.Employee WHERE employeeId = " + id;
      delete(query, dbconn, "Employee deleted successfully.");
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteEmployeeShift(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes an employee shift record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          ID and date of the employee shift record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The employee shift record with the specified ID and date is deleted FROM
   *                 the database.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for the ID and date of the
   *                   employee shift record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteEmployeeShift(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the employee ID:");
      String id = getInput.nextLine();
      System.out.println("Please specify the date to delete (Format: MM/DD/YY):");
      String date = getInput.nextLine();
      String query = 
        "DELETE FROM soumayagarwal.EmployeeShift " + 
        "WHERE employeeId = " + id + " AND shiftDate = TO_DATE('" + date + "', 'MM/DD/YY')";
      delete(query, dbconn, "Employee Shift deleted successfully.");
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteBooking(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes a booking record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          booking information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources. 
   * 
   * Post-condition: The booking record with the specified ID and date is deleted FROM
   *                 the database. Related data FROM Booking Amenity, Amenity Rating and 
   *                 Payment will be deleted.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   booking record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteBooking(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the Booking ID");
      int bid = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Warning: The deletion will also delete all associated " +
          "Booking Amenity, Amenity Rating and Payment.");
      System.out.println("Do you still want to continue? (Type 'Y' to continue)");
      String confirm = getInput.nextLine();

      if (!confirm.equals("Y")) {
        System.out.println();
        return; 
      }

      String deleteRating =
        "DELETE FROM soumayagarwal.AmenityRating WHERE bookingId = " + bid + ")"; 
      delete(deleteRating, dbconn, "");

      String deleteBookingAmenity =
        "DELETE FROM soumayagarwal.BookingAmenity WHERE bookingId = " + bid + ")";
      delete(deleteBookingAmenity, dbconn, "");

      String deletePayment = 
        "DELETE FROM soumayagarwal.Payment WHERE bookingId = " + bid + ")";
      delete(deletePayment, dbconn, "");

      String query = 
        "DELETE FROM soumayagarwal.Booking WHERE bookingId = " + bid + ")";
      delete(query, dbconn, "Booking deleted successfully.");;
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteBookingAmenity(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes a booking amenity record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          booking amenity information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The booking amenity record with the specified ID and date is deleted FROM
   *                 the database. Related data FROM Amenity Rating will also be deleted.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   booking amenity record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteBookingAmenity(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the Booking ID");
      int bid = getInput.nextInt();
      System.out.println("Please specify the Amenity ID:");
      int aid = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Warning: The deletion will also delete all associated " +
                         "Amenity Rating.");
      System.out.println("Do you still want to continue? (Type 'Y' to continue)");
      String confirm = getInput.nextLine();

      if (!confirm.equals("Y")) {
        System.out.println();
        return; 
      }

      String deleteRating =
        "DELETE FROM soumayagarwal.AmenityRating WHERE " + 
        "bookingId = " + bid + " AND " +
        "amenityId = " + Integer.toString(aid);
      delete(deleteRating, dbconn, "");

      String deleteBookingAmenity =
        "DELETE FROM soumayagarwal.BookingAmenity WHERE " + 
        "bookingId = " + bid + " AND " +
        "amenityId = " + Integer.toString(aid);
      delete(deleteBookingAmenity, dbconn, "Booking Amenity deleted successfully.");
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteAmenityRating(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes a Amenity Rating record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          Amenity Rating information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The Amenity Rating record with the specified ID and date is deleted FROM
   *                 the database.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   amenity rating record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteAmenityRating(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the Booking ID");
      int bid = getInput.nextInt();
      System.out.println("Please specify the Amenity ID:");
      int aid = getInput.nextInt();
      getInput.nextLine();

      String deleteRating =
        "DELETE FROM soumayagarwal.AmenityRating WHERE " + 
        "bookingId = " + bid + " AND " +
        "amenityId = " + Integer.toString(aid);
      delete(deleteRating, dbconn, "Amenity Rating deleted successfully.");
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deletePayment(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes a Payment record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          Payment information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The Payment record with the specified ID and date is deleted FROM
   *                 the database.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   Payment record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deletePayment(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the Booking ID");
      int bid = getInput.nextInt();
      getInput.nextLine();

      String deletePayment =
        "DELETE FROM soumayagarwal.Payment WHERE bookingId = " + bid;
      delete(deletePayment, dbconn, "Payment deleted successfully.");
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteCustomer(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes a customer record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          customer information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The customer record with the specified ID and date is deleted FROM
   *                 the database. Related data (referencing customerId) will also be deleted.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   custmoer record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteCustomer(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the customer ID: ");
      int cid = getInput.nextInt();
      getInput.nextLine();

      System.out.println("Warning: The deletion will also delete all associated " +
          "Booking, Booking Amenity, Amenity Rating and Payment.");
      System.out.println("Do you still want to continue? (Type 'Y' to continue)");
      String confirm = getInput.nextLine();

      if (!confirm.equals("Y")) {
        System.out.println();
        return; 
      }

      String deleteRating =
        "DELETE FROM soumayagarwal.AmenityRating " +
        "WHERE bookingId IN " + 
        "(SELECT bookingId FROM soumayagarwal.Booking WHERE customerId = " + cid + ")";
      delete(deleteRating, dbconn, "");

      String deleteBookingAmenity =
        "DELETE FROM soumayagarwal.BookingAmenity " + 
        "WHERE bookingId IN " + 
        "(SELECT bookingId FROM soumayagarwal.Booking WHERE customerId = " + cid + ")";
      delete(deleteBookingAmenity, dbconn, "");

      String deletePayment = 
        "DELETE FROM soumayagarwal.Payment " + 
        "WHERE bookingId IN " + 
        "(SELECT bookingId FROM soumayagarwal.Booking WHERE customerId = " + cid + ")";
      delete(deletePayment, dbconn, "");

      String deleteBooking = 
        "DELETE FROM soumayagarwal.Booking WHERE " + 
        "customerId = " + cid;
      delete(deleteBooking, dbconn, "");
      
      String deleteCustomer = 
        "DELETE FROM soumayagarwal.Customer WHERE " + 
        "customerId = " + cid;
      delete(deleteCustomer, dbconn, "Customer deleted successfully.");;
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteRoom(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes a room record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          room information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The room record with the specified ID and date is deleted FROM
   *                 the database. The related date (references the roomNo) will also be
   *                 delete.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   room record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteRoom(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the Room number: ");
      int rno = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Warning: The deletion will also delete all associated " +
          "Booking, Booking Amenity, Amenity Rating and Payment.");
      System.out.println("Do you still want to continue? (Type 'Y' to continue)");
      String confirm = getInput.nextLine();

      if (!confirm.equals("Y")) {
        System.out.println();
        return; 
      }

      String deleteRating =
        "DELETE FROM soumayagarwal.AmenityRating " + 
        "WHERE bookingId IN (SELECT bookingId FROM Booking WHERE roomNo = " + rno + ")";
      delete(deleteRating, dbconn, "");

      String deleteBookingAmenity =
        "DELETE FROM soumayagarwal.BookingAmenity " + 
        "WHERE bookingId IN (SELECT bookingId FROM Booking WHERE roomNo = " + rno + ")";
      delete(deleteBookingAmenity, dbconn, "");

      String deletePayment = 
        "DELETE FROM soumayagarwal.Payment " + 
        "WHERE bookingId IN (SELECT bookingId FROM Booking WHERE roomNo = " + rno + ")";
      delete(deletePayment, dbconn, "");

      String deleteBooking = 
        "DELETE FROM soumayagarwal.Booking WHERE " + 
        "roomNo = " + rno;
      delete(deleteBooking, dbconn, "");
      
      String deleteRoom = 
        "DELETE FROM soumayagarwal.Room WHERE " + 
        "roomNo = " + rno;
      delete(deleteRoom, dbconn, "Room deleted successfully.");;
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method deleteAmenity(Scanner getInput, Connection dbconn)
   *
   * Purpose: Deletes an amenity record FROM the database using the provided Scanner
   *          object to read user input and the Connection object to connect to the database
   *          and execute the SQL DELETE statement. The method prompts the user for the 
   *          amenity information of the record to be deleted, and then deletes that record
   *          FROM the database.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to 
   *                their respective sources.
   * 
   * Post-condition: The amenity record with the specified ID and date is deleted FROM
   *                 the database. The related date (references the amenityId) will also be
   *                 deleted.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input with the information of the
   *                   amenity record to be deleted
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL DELETE statement
   *
   * Returns: None.
   */
  private static void deleteAmenity(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify the Amenity ID: ");
      int aid = getInput.nextInt();
      getInput.nextLine();
      System.out.println("Warning: The deletion will also delete all associated " +
          "Booking Amenity, Amenity Rating.");
      System.out.println("Do you still want to continue? (Type 'Y' to continue)");
      String confirm = getInput.nextLine();

      if (!confirm.equals("Y")) {
        System.out.println();
        return; 
      }

      String deleteRating =
        "DELETE FROM soumayagarwal.AmenityRating WHERE " + 
        "amenityId = " + aid;
      delete(deleteRating, dbconn, "");

      String deleteBookingAmenity =
        "DELETE FROM soumayagarwal.BookingAmenity WHERE " + 
        "amenityId = " + aid;
      delete(deleteBookingAmenity, dbconn, "");
      
      String deleteAmenity = 
        "DELETE FROM soumayagarwal.Amenity WHERE " + 
        "amenityId = " + aid;
      delete(deleteAmenity, dbconn, "Amenity deleted successfully.");;
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }
  /**
   * Method delete(String query, Connection dbconn)
   * 
   * Purpose: Executes an SQL DELETE statement with the provided query on the database 
   *          using the Connection object passed as a parameter. The method prints a message
   *          to the console indicating whether the deleteion was successful or not.
   *
   * Pre-condition: Connection object must be initialized and connected to the database.
   *
   * Post-condition: The SQL DELETE statement is executed on the database with the provided 
   *                 query, and a message indicating whether the deleteion was successful or not
   *                 is printed to the console.
   *
   * Parameters:
   *   @param query the SQL DELETE statement to be executed on the database
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 DELETE statement
   *
   * Returns: None.
   */
  private static void delete(String query, Connection dbconn, String message) {
    if (!message.equals("")) { System.out.println(); }
    try {
      Statement stmt = dbconn.createStatement();
      ResultSet answer = stmt.executeQuery(query);
      if (!message.equals("")) { System.out.println(message); }
      stmt.close();
    } catch (SQLException e) { Utility.printError(e, "Error deleting from the Database"); }
    if (!message.equals("")) { System.out.println(); }
  }
}
