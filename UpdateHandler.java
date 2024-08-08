/**
 * UpdateHandler.java
 *
 * This class handles the update of the currently existing data in the database by displaying
 * a menu of options to the user and allowing them to enter the nexessary information.
 *
 * The class uses the JDBC API to connect to a database and execute SQL queries.
 * 
 * The class has the following public method:
 *  - handle(Scanner getInput, Connection dbconn):
 *      prompts the user with a menu of options. It uses the getInput Scanner object
 *      and the dbconn (the Database connection), which are its arguments.
 * 
 * The class has the following private methods:
 *  - updateEmployee(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a currently existing employee
 *      and update the information into the databse appropriately.
 * 
 *  - updateEmployeeShift(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a currently existing employee
 *      shift and update the information into the database appropriately.
 * 
 *  - updateCustomer(Scanner getInput, Connection dbconn): 
 *      prompts the user to enter information for a currently existing customer and update the 
 *      information into the database appropriately.
 * 
 *  - updateRoom(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a currently existing room and update the
 *       information into the database appropriately.
 * 
 *  - updateBooking(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a currently existing booking and
 *       update the information into the database appropriately.
 * 
 *  - updateAmenity(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a currently existing amenity and
 *       update the information into the database appropriately.
 * 
 *  - updateBookingAmenity(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a currently existing booking amenity
 *       and updates the information into the database appropriately.
 * 
 *  - updateAmenityRating(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a currently existing amenity rating
 *       and update the information into the database appropriately.
 * 
 *  - updatePayment(Scanner getInput, Connection dbconn): 
 *       prompts the user to enter information for a currently existing payment and update
 *       the information into the database appropriately.
 * 
 * Usage: This class is intended to be used by calling its public methods. It is originally
 *        made to run by the Frontend.java file. It has no main method, and cannot be called
 *        directly.
 *
 * Author: Soumay Agarwal
 * Date: 30th April 2023
 * Course: CSC 460 - Spring 2023
 * Professor: Lester McCann
*/
import java.io.*;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class UpdateHandler {
  /**
   * Method handle(Scanner getInput, Connection dbconn)
   *
   * Purpose: Handles the update of various types of data into the database.
   *          Displays a menu with options for updating employees, employee shifts, 
   *          customers, rooms, bookings, amenities, booking amenities, amenity ratings, 
   *          or payments. User input is read from the Scanner object passed as a parameter
   *          and data is updated into the database using the Connection object passed as 
   *          a parameter.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to
   *                their respective sources.
   *
   * Post-condition: The data updated by the user is stored in the database. Returns "back" 
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
      System.out.println("Update mode");
      System.out.println("Please choose one of the following uptions using a single digit");
      System.out.println("or back to return to the main menu, exit to exit the program:");
      System.out.println("1: Update Employee");
      System.out.println("2: Update Employee Shift");
      System.out.println("3: Update Customer");
      System.out.println("4: Update Booking");
      System.out.println("5: Update Amenity");
      System.out.println("6: Update Booking Amenity");
      System.out.println("7: Update Amenity Rating");
      System.out.println("8: Update Payment");
      System.out.println("9: Update Room");
      input = getInput.nextLine();
      System.out.println();
      switch (input) {
        case "1":
        updateEmployee(getInput, dbconn);
          break;
        case "2":
        updateEmployeeShift(getInput, dbconn);
          break;
        case "3":
        updateCustomer(getInput, dbconn);
          break;
        case "4":
        updateBooking(getInput, dbconn);
          break;
        case "5":
        updateAmenity(getInput, dbconn);
          break;
        case "6":
        updateBookingAmenity(getInput, dbconn);
          break;
        case "7":
        updateAmenityRating(getInput, dbconn);
          break;
        case "8":
        updatePayment(getInput, dbconn);
          break;
        case "9":
        updateRoom(getInput, dbconn);
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
   * Method updateEmployee(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing employee record into the database with the specified
   *          ID, first name, last name, and job title. User input for ID, first name, 
   *          last name, and job title is read from the Scanner object passed as a parameter.
   *          The employee record is updated into the database using the Connection object 
   *          passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update Employee's first name, last name and job title by specifying an employeeId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular employee record is updated in the database with the specified ID,
   *                first name, last name, and job title.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for ID, first name, last name, 
   *                   and job title
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateEmployee(Scanner getInput, Connection dbconn){
    System.out.println("Employee ID: ");
    String id = getInput.nextLine();
    System.out.println("Employee First name: ");
    String fname = getInput.nextLine();
    System.out.println("Employee Last name: ");
    String lname = getInput.nextLine();
    System.out.println("Employee Job Title: ");
    String job = getInput.nextLine();
    String query = "UPDATE soumayagarwal.Employee SET ";

    if(fname.isEmpty()==false){
      query += "firstName = '" + fname + "', ";
    }

    if(lname.isEmpty()==false){
      query += "lastName = '" + lname + "', ";
    }

    if(job.isEmpty()==false){
      query += "jobTitle = '" + job + "', ";
    }
    query = query.substring(0, query.length() - 2);
    query += " WHERE employeeId = " + id;
    update(query, dbconn);
  }

  /**
   * Method updateEmployeeShift(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing employee shift record into the database with the specified
   *          ID, shift time and duties. User input for the respective datam which is
   *          read from the Scanner object passed as a parameter.
   *          The employee shift record is updated into the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement.Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update EmployeeShift's duties, start time and end by specifying an employeeId
   *          and ShiftDate.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular employee shift record is updated into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateEmployeeShift(Scanner getInput, Connection dbConn){
    System.out.println("Employee ID: ");
    String id = getInput.nextLine();
    System.out.println("Shift Date (format: MM/DD/YY): ");
    String date = getInput.nextLine();
    System.out.println("Duties: ");
    String duties = getInput.nextLine();
    System.out.println("Shift Start time hour (0 to 23): ");
    String startTimeHour = getInput.nextLine();
    System.out.println("Shift Start time minute (0 to 59): ");
    String startTimeMinute = getInput.nextLine();
    System.out.println("Shift End time hour (0 to 23): ");
    String endTimeHour = getInput.nextLine();
    System.out.println("Shift End time minute (0 to 59): ");
    String endTimeMinute = getInput.nextLine();

    int startTimeHourInt = 0;
    int startTimeMinuteInt = 0;
    int endTimeHourInt = 0;
    int endTimeMinuteInt = 0;

    if (startTimeHour.isEmpty()==false){
    startTimeHourInt = Integer.parseInt(startTimeHour);
    }
    if(startTimeMinute.isEmpty()==false){
    startTimeMinuteInt = Integer.parseInt(startTimeMinute);
    }
    if(endTimeHour.isEmpty()==false){
    endTimeHourInt = Integer.parseInt(endTimeHour);
    }
    if(endTimeMinute.isEmpty()==false){
    endTimeMinuteInt = Integer.parseInt(endTimeMinute);
    }


    if (startTimeHourInt < 0 || startTimeHourInt > 23 ||
    endTimeHourInt < 0 || endTimeHourInt > 23 ||
    startTimeMinuteInt < 0 || startTimeMinuteInt > 59 ||
    endTimeMinuteInt < 0 || endTimeMinuteInt > 59) {
    System.out.println("The time is incorrectly formatted.");
    return;
}

    String query = "UPDATE soumayagarwal.EmployeeShift SET ";

    if(duties.isEmpty()==false){
      query += "duties = '" + duties + "', ";
    }
    if(startTimeHour.isEmpty()==false && startTimeMinute.isEmpty()==false){
      query += "shiftStartTime = " + Integer.toString(startTimeHourInt * 3600 + startTimeMinuteInt) + ", ";
    }
    if(endTimeHour.isEmpty()==false && endTimeMinute.isEmpty()==false){
      query += "shiftEndTime = " + Integer.toString(endTimeHourInt * 3600 + endTimeMinuteInt) + ", ";
    }
    query = query.substring(0, query.length() - 2);
    if(id.isEmpty()==false){
      query += " WHERE employeeId = " + id;
    }

    if(date.isEmpty()==false){
      query += " AND shiftDate = " + "TO_DATE( '"+date+ "', 'MM/DD/YY')";
    }
    update(query, dbConn);
  }

  /**
   * Method updateCustomer(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing customer record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The customer record is updated into the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update all the Customer's fields using a customerId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular customer record is updated into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateCustomer(Scanner getInput, Connection dbconn) {
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
    String point = getInput.nextLine();

    if (!isCollegeStudent.isEmpty() && !isCollegeStudent.equals("1") && !isCollegeStudent.equals("0")) {
      System.out.println("Wrong format type for isCollegeStudent field");
      return;
    }

    if (!isClubMember.isEmpty() && !isClubMember.equals("1") && !isClubMember.equals("0")) {
      System.out.println("Wrong format type for isClubMember field");
      return;
    }

    String query = 
      "UPDATE soumayagarwal.Customer SET ";
    
    if(fname.isEmpty()==false){
      query += "firstName = '" + fname + "', ";
    }
    if(lname.isEmpty()==false){
      query += "lastName = '" + lname + "', ";
    }
    if(email.isEmpty()==false){
      query += "email = '" + email + "', ";
    }
    if(phone.isEmpty()==false){
      query += "phoneNumber = '" + phone + "', ";
    }
    if(isCollegeStudent.isEmpty()==false){
      query += "isCollegeStudent = " + isCollegeStudent + ", ";
    }
    if(isClubMember.isEmpty()==false){
      query += "isclub460Member = " + isClubMember + ", ";
    }
    if(cardType.isEmpty()==false){
      query += "creditCardType = '" + cardType + "', ";
    }
    if(point.isEmpty()==false){
      query += "point = " + point + ", ";
    }
    query = query.substring(0, query.length() - 2);
    query += " WHERE customerId = " + id;
    update(query, dbconn);
  }

  /**
   * Method updateRoom(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing room record in the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The room record is updated into the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update roomPrice using a roomNo.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular room record is updated in the database with the specified
   *                 information.
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateRoom(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Room Number: ");
      String no = getInput.nextLine();
      System.out.println("Room Price:");
      String price = getInput.nextLine();

      String query =
        "UPDATE soumayagarwal.Room SET ";

      if(price.isEmpty()==false){
        query += "roomPrice = " + price + ", ";
      }
      query = query.substring(0, query.length() - 2);
      query += " WHERE roomNo = " + no;

      update(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method updateBooking(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates a current booking record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The booking record is updated into the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update checkOutDate, customerId, roomNo and checkInDate using bookingId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular booking record is updated into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateBooking(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Booking ID: ");
      String bid = getInput.nextLine();
      System.out.println("Customer ID: ");
      String cid = getInput.nextLine();
      System.out.println("Room Number: ");
      String rno = getInput.nextLine();
      System.out.println("Check In Date (Format: MM/DD/YY):");
      String checkInDate = getInput.nextLine();
      System.out.println("Check Out Date (Format: MM/DD/YY):");
      String checkOutDate = getInput.nextLine();

      String query = 
        "UPDATE soumayagarwal.Booking SET ";

      if(cid.isEmpty()==false){
        query += "customerId = " + cid + ", ";
      }
      if(rno.isEmpty()==false){
        query += "roomNo = " + rno + ", ";
      }
      if(checkInDate.isEmpty()==false){
        query += "checkInDate = " + "TO_DATE( '"+checkInDate+ "', 'MM/DD/YY')" + ", ";
      }
      if(checkOutDate.isEmpty()==false){
        query+= "checkOutDate = " + "TO_DATE( '"+checkOutDate+ "', 'MM/DD/YY')" + ", ";
      }
      query = query.substring(0, query.length() - 2);
      if(bid.isEmpty()==false){
        query += " WHERE bookingId = " + bid;
      }
      if (Utility.checkDoubleBookingUpdate(dbconn, checkInDate, checkOutDate, Integer.parseInt(rno),
      Integer.parseInt(cid))) {
        System.out.println();
        System.out.println("Error: Matching information found, or another query error occured.");
        System.out.println();
      } else {
        update(query, dbconn);
      }
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

   /**
   * Method updateAmenity(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing amenity record in the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The amenity record is updated into the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update name and price using an amenityId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular amenity record is updated into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateAmenity(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Amenity ID:");
      String aid = getInput.nextLine();
      System.out.println("Name: ");
      String name = getInput.nextLine();
      System.out.println("Price: ");
      String price = getInput.nextLine();

      String query = 
        "UPDATE soumayagarwal.Amenity SET ";

      if(name.isEmpty()==false){
        query += "name = '" + name + "', ";
      }
      if(price.isEmpty()==false){
        query += "price = " + price + ", ";
      }
      query = query.substring(0, query.length() - 2);
      query += " WHERE amenityId = " + aid;

      update(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method updateBookingAmenity(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing BookingAmenity record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The BookingAmenity record is updated into the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update quantity using bookingId and amenityId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular BookingAmenity record is updated into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateBookingAmenity(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("booking ID: ");
      String bid = getInput.nextLine();
      System.out.println("Amenity ID: ");
      String aid = getInput.nextLine();
      System.out.println("Quantity: ");
      String qty = getInput.nextLine();

      String query = 
        "UPDATE soumayagarwal.BookingAmenity SET ";
      
      if(qty.isEmpty()==false){
        query += "quantity = " + qty + ", "; 
      }

      query = query.substring(0, query.length() - 2);
      if(bid.isEmpty()==false){
        query += " WHERE bookingId = " + bid;
      }
      if (aid.isEmpty()==false){
        query += " AND amenityId = " + aid;
      }

      update(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

   /**
   * Method updateAmenityRating(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing AmenityRating record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The AmenityRating record is updated in the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update rating and ratingDate using bookingId and amenityId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular AmenityRating record is updated in the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updateAmenityRating(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Booking ID: ");
      String bid = getInput.nextLine();
      System.out.println("Amenity ID: ");
      String aid = getInput.nextLine();
      System.out.println("Rating: ");
      String rating = getInput.nextLine();
      System.out.println("Rating Date (Format: MM/DD/YY):");
      String ratingDate = getInput.nextLine();

      String query = 
        "UPDATE soumayagarwal.AmenityRating SET ";

      if(rating.isEmpty()==false){
        query += "rating = " + rating + ", ";
      }
      if(ratingDate.isEmpty()==false){
        query += "ratingDate = " + "TO_DATE( '"+ratingDate+ "', 'MM/DD/YY')" + ", ";
      }
      query = query.substring(0, query.length() - 2);
      if(bid.isEmpty()==false){
        query+= " WHERE bookingId = " + bid;
      }
      if(aid.isEmpty()==false){
        query+= " AND amenityId = " + aid;
      }

      update(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

   /**
   * Method updatePayment(Scanner getInput, Connection dbconn)
   *
   * Purpose: Updates an existing Payment record into the database with the specified
   *          information. User input for the respective data, which are
   *          read from the Scanner object passed as a parameter.
   *          The Payment record is updated in the database using the Connection
   *          object passed as a parameter and an SQL UPDATE statement. Primary key/keys aren't
   *          updated but are used to identify a particular record. Therefore, the user can only
   *          update totalAmount and Tips using bookingId.
   * 
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: A particular Payment record is updated into the database with the specified
   *                 information
   *
   * Parameters:
   *   @param getInput the Scanner object used to read user input for respective information
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void updatePayment(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("booking ID: ");
      String bid = getInput.nextLine();
      System.out.println("Total Amount: ");
      String amt = getInput.nextLine();
      System.out.println("Tips: ");
      String tips = getInput.nextLine();

      String query = 
        "UPDATE soumayagarwal.Payment SET ";
        if(amt.isEmpty()==false){
          query += "totalAmount = " + amt + ", ";
        }
        if(tips.isEmpty()==false){
          query += "tips = " + tips + ", ";
        }
        query = query.substring(0, query.length() - 2);
        if(bid.isEmpty()==false){
          query += " WHERE bookingId = " + bid;
        }

      update(query, dbconn);
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    }
  }

  /**
   * Method update(String query, Connection dbconn)
   * 
   * Purpose: Executes an SQL UPDATE statement with the provided query on the database 
   *          using the Connection object passed as a parameter. The method prints a message
   *          to the console indicating whether the update was successful or not.
   *
   * Pre-condition: Connection object must be initialized and connected to the database.
   *
   * Post-condition: The SQL UPDATE statement is executed on the database with the provided 
   *                 query, and a message indicating whether the insertion was successful or not
   *                 is printed to the console.
   *
   * Parameters:
   *   @param query the SQL UPDATE statement to be executed on the database
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *
   * Returns: None.
   */

  private static void update(String query, Connection dbconn) {
    System.out.println();
    try {
      Statement stmt = dbconn.createStatement();
      ResultSet answer = stmt.executeQuery(query);
      System.out.println("Update Successful!");
      stmt.close();
    } catch (SQLException e) { Utility.printError(e, "Error updating to Database"); }
    System.out.println();
  }
}
