/**
 * QueryHandler.java
 *
 * This class handles queries to the database by displaying a menu of options to 
 * the user and allowing them to enter the necessary information.
 *
 * The class uses the JDBC API to connect to a database and execute SQL queries.
 *
 * The class has the following public method:
 *  - handle(Scanner getInput, Connection dbconn): 
 *      prompts the user with a menu of options. It uses the getInput Scanner object
 *      and the dbconn (the Database connection), which are its arguments.
 *
 * The class has the following private methods:
 *  - query1(Scanner getInput, Connection dbconn): 
 *      This method queries for unpaid bill of a cu stomer.
 *
 *  - query2(Scanner getInput, Connection dbconn): 
 *      This method queries for all customers currently staying.
 *
 * - query2Printer(Scanner getInput, Connection dbconn): 
 *      This method helps print the information from query 2 to the standard output.
 *
 * - query3(Scanner getInput, Connection dbconn): 
 *      This method queries for the schedule of staff given a week.
 *  
 * - query4(Scanner getInput, Connection dbconn): 
 *      This method queries for the average ratings of amenities within a date range.
 *
 * - query5(Scanner getInput, Connection dbconn): 
 *      This method queries for the staffs working in a particular time of a day.
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

class QueryHandler {
  /**
   * Method handle(Scanner getInput, Connection dbconn)
   *
   * Purpose: Handles the queries of various types of data into the database.
   *          Displays a menu with options for querying the database.  
   *          User input is read from the Scanner object passed as a parameter
   *          and data is queried to the database using the Connection object passed as 
   *          a parameter.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected to
   *                their respective sources.
   *
   * Post-condition: The data is unchanged in the database. Returns "back" 
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
      System.out.println("QUERY MODE");
      System.out.println("Please choose one of the following uptions using a single digit");
      System.out.println("or back to return to the main menu, exit to exit the program:");
      System.out.println("1: Get unpaid bill of a customer.");
      System.out.println("2: Get all customers currently staying.");
      System.out.println("3: Get the schedule of staff given a week.");
      System.out.println("4: Get the average ratings of amenities within a date range.");
      System.out.println("5: Get the staffs working in a particular time of a day.");
      input = getInput.nextLine();
      System.out.println();
      switch (input) {
        case "1":
          query1(getInput, dbconn);
          break;
        case "2":
          query2(getInput, dbconn);
          break;
        case "3":
          query3(getInput, dbconn);
          break;
        case "4":
          query4(getInput, dbconn);
          break;
        case "5":
          query5(getInput, dbconn);
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
   * Method query1(Scanner getInput, Connection dbconn)
   *
   * Purpose: Answer the query of "Get unpaid bill of a customer." It will prompt the user
   *          for the information to query, and display the result into the standard output.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: The data is unchaned. The user will be returned to the input loop to 
   *                 continue working with the database.
   * Parameters:
   *   @param getInput the Scanner object used to read user input for necessary information
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL INSERT statement
   *
   * Returns: None.
   */
  private static void query1(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please specify a customer ID: ");
      String id = getInput.nextLine();
     
      String userQuery = 
        "SELECT firstName, lastName, " + 
        "isCollegeStudent, isClub460Member, creditCardType, point FROM soumayagarwal.Customer " +
        "WHERE customerId = " + id;
      
      boolean isCollegeStudent = false;
      boolean isClubMember = false;
      String creditCardType = "";
      Statement stmt = null;
      ResultSet userAnswer = Utility.processQuery(userQuery, dbconn, stmt);
      if (userAnswer != null) {
        if (userAnswer.next()) {
          String fname = userAnswer.getString(1);
          String lname = userAnswer.getString(2);
          isCollegeStudent = userAnswer.getInt(3) == 0 ? false : true;
          isClubMember = userAnswer.getInt(4) == 0 ? false : true;
          creditCardType = userAnswer.getString(5);
          int point = userAnswer.getInt(6);
          System.out.println();
          System.out.println("Username: " + fname + " " + lname);
          System.out.println("Credit card type: " + creditCardType);
          System.out.println("Number of points: " + point);
        } else {
            System.out.println();
            System.out.println("No customer with matching ID found.");
            System.out.println();
            return;
        }
      }

      String roomQuery =
        "SELECT " +
        "b.bookingId, " +
        "r.roomNo, " + 
        "roomPrice, " +
        "checkInDate, " +
        "checkOutDate, " + 
        "(checkOutDate - checkInDate) as stayingPeriod " +
        "FROM soumayagarwal.Booking b, soumayagarwal.Room r " +
        "WHERE b.roomNo = r.roomNo AND customerId = " + id + " AND NOT EXISTS (" +
          "SELECT * FROM soumayagarwal.Payment p WHERE b.bookingId = p.bookingId)";

      stmt = null;
      ResultSet answer = Utility.processQuery(roomQuery, dbconn, stmt);
      float total = 0;
      if (answer != null) {
        System.out.println();
        System.out.println("Current Unpaid Room:");
        while (answer.next()) {
          int bid = answer.getInt(1);
          int roomNo = answer.getInt(2);
          float roomPrice = answer.getFloat(3);
          Date checkInDate = answer.getDate(4);
          int stayingPeriod = answer.getInt(6);
          float roomCost = roomPrice * stayingPeriod;

          System.out.println("Room Number: " + roomNo);
          System.out.println("Price: " + roomPrice);
          System.out.println("Check In Date: " + checkInDate);
          System.out.println("Staying length: " + stayingPeriod + " days");
          
          /* Start of Amenity Query */
          String amenityQuery = 
            "SELECT ba.amenityId, a.name, quantity, a.price " +
            "FROM soumayagarwal.BookingAmenity ba, soumayagarwal.Amenity a WHERE " + 
            "ba.amenityId = a.amenityId AND bookingId = " + bid;
          Statement amenityStmt = null;
          ResultSet amenityResult = Utility.processQuery(amenityQuery, dbconn, amenityStmt);
          if (amenityResult != null) {
            System.out.println("Amenities Used: ");
            while (amenityResult.next()) {
              int amenityId = amenityResult.getInt(1);
              String amenityName = amenityResult.getString(2);
              int amenityQty = amenityResult.getInt(3);
              float amenityPrice = amenityResult.getFloat(4);
              System.out.println();
              System.out.println("    Amenity ID: " + amenityId);
              System.out.println("    Amenity name: " + amenityName);
              System.out.println("    Amenity Quantity: " + amenityQty);
              System.out.println("    Amenity Price: " + amenityPrice);
              if (isClubMember) {
                System.out.println("    -- Fee waived as part of Membership perk.");
              } else {
                roomCost += amenityPrice * amenityQty;
              }
            }
          }
          if (amenityStmt != null) { amenityStmt.close(); }
          /* End Amenity Query */
          System.out.println("Room Cost: " + roomCost);
          float studentOrMemberDiscount = (isClubMember || isCollegeStudent) ? roomCost / 10 : 0;
          float creditCardDiscount = 0;
          if (creditCardType.equals("Platinum")) {
            creditCardDiscount = roomCost / 10;
          } else if (creditCardType.equals("Gold")) {
            creditCardDiscount = roomCost / 20;
          }
          System.out.println("Discount from Club 460 Member/College Student: " + studentOrMemberDiscount);
          System.out.println("Discount from Credit card of " + creditCardType + ": " + creditCardDiscount);
          float finalRoomCost = roomCost - studentOrMemberDiscount - creditCardDiscount;
          System.out.println("Final Room Cost: " + finalRoomCost);
          total += finalRoomCost;
          System.out.println();
        }
      }
      if (stmt != null) { stmt.close(); }
      System.out.println("Total unpaid cost: " + total);
      System.out.println();
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    } catch (SQLException e) {
      Utility.printError(e, "Cannot read froom the SQL answer.");
    }
  }

  /**
   * Method query2(Scanner getInput, Connection dbconn)
   *
   * Purpose: Answer the query of "Get all customers currently staying." It will prompt the user
   *          for the information to query, and display the result into the standard output.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: The data is unchaned. The user will be returned to the input loop to 
   *                 continue working with the database.
   * Parameters:
   *   @param getInput the Scanner object used to read user input for necessary information
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL INSERT statement
   *
   * Returns: None.
   */
  private static void query2(Scanner getInput, Connection dbconn) {
    try {
      Statement stmt = null;
      String firstPart =
        "SELECT " +
        "c.customerId, c.firstName, c.lastName, b.roomNo " + 
        "FROM soumayagarwal.Booking b, soumayagarwal.Customer c " +
        "WHERE b.customerId = c.customerId AND " + 
        "b.checkInDate <= CURRENT_DATE AND b.checkOutDate > CURRENT_DATE AND "; 
       String lastPart = " ORDER BY roomNo";

      String bothQuery = 
        firstPart + "c.isCollegeStudent = 1 AND c.isClub460Member = 1 " + lastPart;
      ResultSet bothAnswer = Utility.processQuery(bothQuery, dbconn, stmt);
      query2Printer("Customers that are both College Student and Club 460 member: ", bothAnswer);

      String onlyCollegeQuery = 
        firstPart + "c.isCollegeStudent = 1 AND c.isClub460Member = 0 " + lastPart;
      ResultSet onlyCollegeAnswer = Utility.processQuery(onlyCollegeQuery, dbconn, stmt);
      query2Printer(
          "Customers that are College Student (but not Club 460 member): ", 
          onlyCollegeAnswer);

      String onlyClubMemberQuery = 
        firstPart + "c.isCollegeStudent = 0 AND c.isClub460Member = 1 " + lastPart;
      ResultSet onlyClubMemberAnswer = Utility.processQuery(onlyClubMemberQuery, dbconn, stmt);
      query2Printer(
          "Customers that are Club 460 member (but not College Student): ", 
          onlyClubMemberAnswer); 


      String normalQuery = 
        firstPart + "c.isCollegeStudent = 0 AND c.isClub460Member = 0 " + lastPart;
      ResultSet normalAnswer = Utility.processQuery(normalQuery, dbconn, stmt);
      query2Printer(
          "Customers that neither Club 460 member nor College Student: ", 
          normalAnswer);
     
     if (stmt != null) { stmt.close(); }
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    } catch (SQLException e) {
      Utility.printError(e, "Strange Error: Cannot close the Statement!");
    }
  }

  /**
   * Method query2Printer(String title, ResultSet answer)
   *
   * Purpose: This method helps the query2 to print out the result from its query.
   *
   * Pre-condition: The answer has the customerId, firstName, lastName and roomNo fields,
   *                in that order.
   *
   * Post-condition: The data is unchaned. The user will be returned to the input loop to 
   *                 continue working with the database.
   *
   * Parameters:
   *   @param title   The String to be printed at the start of the method 
   *   @param answer  The ResultSet to be printed
   *
   * Returns: None.
   */
  private static void query2Printer(String title, ResultSet answer) {
    try {
      System.out.println(title);
      if (answer != null) {
        while (answer.next()) {
          int customerId = answer.getInt(1);
          String firstname = answer.getString(2);
          String lastname = answer.getString(3);
          int roomNo = answer.getInt(4);

          System.out.println();
          System.out.println("    Customer ID: " + customerId);
          System.out.println("    Customer Name: " + firstname + " " + lastname);
          System.out.println("    Room Number: " + roomNo);
        }
      }
      System.out.println();
    } catch (SQLException e) {
      Utility.printError(e, "Cannot read from the SQL answer.");
    }
  }

  /**
   * Method query3(Scanner getInput, Connection dbconn)
   *
   * Purpose: Answer the query of "Get the schedule of staff given a week." It will prompt 
   *          the user for the information to query, and display the result into the standard 
   *          output.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: The data is unchaned. The user will be returned to the input loop to 
   *                 continue working with the database.
   * Parameters:
   *   @param getInput the Scanner object used to read user input for necessary information
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL INSERT statement
   *
   * Returns: None.
   */
  private static void query3(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Give a start date for the week to query (Format: MM/DD/YY): ");
      String startDate = getInput.nextLine();
      
      String employeeQuery = 
        "SELECT UNIQUE e.employeeId, e.firstname, e.lastName " +
        "FROM soumayagarwal.EmployeeShift s, soumayagarwal.Employee e " +
        "WHERE s.employeeId = e.employeeId AND " + 
        "s.shiftDate >= TO_DATE('" + startDate + "', 'MM/DD/YY') AND " + 
        "s.shiftDate < TO_DATE('" + startDate + "', 'MM/DD/YY') + 8";
      Statement stmt = null;

      ResultSet employeeAnswer = Utility.processQuery(employeeQuery, dbconn, stmt);

      if (employeeAnswer != null) {
        while (employeeAnswer.next()) {
          int id = employeeAnswer.getInt(1);
          String firstName = employeeAnswer.getString(2);
          String lastName = employeeAnswer.getString(3);
          
          System.out.println();
          System.out.println("ID: " + id);
          System.out.println("Name: " + firstName + " " + lastName);
          /* Start inner query */
          String shiftQuery =
            "SELECT shiftDate, shiftStartTime, shiftEndTime " + 
            "FROM soumayagarwal.EmployeeShift " +
            "WHERE employeeId = " + id + " AND " +
            "shiftDate >= TO_DATE('" + startDate + "', 'MM/DD/YY') AND " + 
            "shiftDate < TO_DATE('" + startDate + "', 'MM/DD/YY') + 8 " + 
            "ORDER BY shiftDate";
          
          Statement shiftStmt = null;
          
          ResultSet shiftAnswer = Utility.processQuery(shiftQuery, dbconn, shiftStmt);
          
          if (shiftAnswer != null) {
            while (shiftAnswer.next()) {
              Date shiftDate = shiftAnswer.getDate(1);
              int startTime = shiftAnswer.getInt(2);
              int endTime = shiftAnswer.getInt(3);
              int startHour = (int)(Math.floor(startTime / 3600));
              int startMinute = (int)(Math.floor((startTime - startHour * 3600) / 60));
              int endHour = (int)(Math.floor(endTime / 3600));
              int endMinute = (int)(Math.floor((endTime - endHour * 3600) / 60));

              System.out.println();
              System.out.println("    Date: " + shiftDate);
              System.out.println(
                "    Worked time: " + 
                String.format("%02d", startHour) + ":" + String.format("%02d",startMinute) + 
                " - " + 
                String.format("%02d", endHour) + ":" + String.format("%02d",endMinute)
              );
            }
          }
          
          if (shiftStmt != null) { shiftStmt.close(); }
          /* End inner query */
        }
      }
      System.out.println();
      if (stmt != null) { stmt.close(); }
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    } catch (SQLException e) {
      Utility.printError(e, "Cannot read from the SQL answer.");
    }
  }

  /**
   * Method query4(Scanner getInput, Connection dbconn)
   *
   * Purpose: Answer the query of "Get the average ratings of amenities within a date range."
   *          It will prompt  the user for the information to query, and display the result
   *          into the standard output.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: The data is unchaned. The user will be returned to the input loop to 
   *                 continue working with the database.
   * Parameters:
   *   @param getInput the Scanner object used to read user input for necessary information
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL INSERT statement
   *
   * Returns: None.
   */
  private static void query4(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please type the start date (MM/DD/YY): ");
      String startDate = getInput.nextLine();

      System.out.println("Please type the end date (MM/DD/YY): ");
      String endDate = getInput.nextLine();
     
      String query = 
        "SELECT a.amenityId, a.name, AVG(r.rating) " + 
        "FROM soumayagarwal.Amenity a, soumayagarwal.AmenityRating r " +
        "WHERE a.amenityId = r.amenityId AND " +
        "r.ratingDate >= TO_DATE('" + startDate + "', 'MM/DD/YY') AND " + 
        "r.ratingDate <= TO_DATE('" + endDate + "', 'MM/DD/YY') " +
        "GROUP BY a.amenityId, a.name " + 
        "ORDER BY AVG(r.rating) DESC";
      
      Statement stmt = null;

      ResultSet answer = Utility.processQuery(query, dbconn, stmt);

      if (answer != null) {
        while (answer.next()) {
          int id = answer.getInt(1);
          String name = answer.getString(2);
          float rating = answer.getFloat(3);
          System.out.println();
          System.out.println("Amenity ID: " + id + " - Name: " + name);
          System.out.println("Rating: " + rating);
        }
      }

      if (stmt != null) { stmt.close(); }
      System.out.println();
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    } catch (SQLException e) {
      Utility.printError(e, "Cannot read from the SQL answer.");
    }
  }

  /**
   * Method query5(Scanner getInput, Connection dbconn)
   *
   * Purpose: Answer the query of "Get the staffs working in a particular time of a day."
   *          It will prompt  the user for the information to query, and display the result
   *          into the standard output.
   *
   * Pre-condition: Scanner object and Connection object must be initialized and connected 
   *                to their respective sources.
   * 
   * Post-condition: The data is unchaned. The user will be returned to the input loop to 
   *                 continue working with the database.
   * Parameters:
   *   @param getInput the Scanner object used to read user input for necessary information
   *   @param dbconn the Connection object used to connect to the database and execute the 
   *                 SQL INSERT statement
   *
   * Returns: None.
   */
  private static void query5(Scanner getInput, Connection dbconn) {
    try {
      System.out.println("Please type a date as the start date (MM/DD/YY):");
      String date = getInput.nextLine();
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

      int startTime = startTimeHour * 3600 + startTimeMinute * 60;
      int endTime = endTimeHour * 3600 + endTimeMinute * 60;
      
      String query = 
        "SELECT " +
        "e.employeeId, " + 
        "e.firstName, " + 
        "e.lastName, " + 
        "s.shiftStartTime, " + 
        "s.shiftEndTime, " +
        "s.duties " +
        "FROM soumayagarwal.Employee e, soumayagarwal.EmployeeShift s " +
        "WHERE s.employeeId = e.employeeId AND " +
        "s.shiftDate = TO_DATE('" + date + "', 'MM/DD/YY') AND " + 
        "s.shiftStartTime >= " + startTime + " AND " + 
        "s.shiftEndTime <= " + endTime + 
        "ORDER BY shiftStartTime ASC";

      Statement stmt = null;
      ResultSet answer = Utility.processQuery(query, dbconn, stmt);

      if (answer != null) {
        while (answer.next()) {
          int id = answer.getInt(1);
          String firstName = answer.getString(2);
          String lastName = answer.getString(3);
          int startTimeResult = answer.getInt(4);
          int endTimeResult = answer.getInt(5);
          String duty = answer.getString(6);
          int startHour = (int)(Math.floor(startTimeResult / 3600));
          int startMinute = (int)(Math.floor((startTimeResult - startHour * 3600) / 60));
          int endHour = (int)(Math.floor(endTimeResult / 3600));
          int endMinute = (int)(Math.floor((endTimeResult - endHour * 3600) / 60));

          System.out.println();
          System.out.println("Employee Name: " + firstName + " " + lastName);
          System.out.println(
              "Worked time: " + 
              String.format("%02d", startHour) + ":" + String.format("%02d",startMinute) + 
              " - " + 
              String.format("%02d", endHour) + ":" + String.format("%02d",endMinute)
          );
          System.out.println("Duties: " + duty);
        }
      }
      System.out.println();
      if (stmt != null) { stmt.close(); }
    } catch (InputMismatchException e) {
      System.out.println("Input format error. Please check the input and try again.");
      return;
    } catch (SQLException e) {
      Utility.printError(e, "Cannot read from the SQL answer.");
    }
  }
}
