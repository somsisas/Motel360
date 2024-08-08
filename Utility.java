/**
 * Utility.java
 *
 * This class contains some utility methods to access to the Database, handle errors
 * and check for some pre-conditions before mutating the database.
 *
 * The class has the following public method:
 *   - printError(SQLException e, String error)
 *       This method is used to print the SQL Error with a custom message to the standard error.
 * 
 *    - checkDoubleBookingUpdate(Connection dbconn, String checkInDate, String checkOutDate, int roomNo)
 *       This method is used to check if a book has been double booked during a particular 
 *       period. Specifially altered for the update part
 *
 *   - checkDoubleBooking(Connection dbconn, String checkInDate, String checkOutDate, int roomNo)
 *       This method is used to check if a book has been double booked during a particular 
 *       period
 *
 *   - processQuery(String query, Connection dbconn, Statement stmt)
 *       This methood is used to process a query and return the ResultSet to its user.
 * 
 *
 * Usage: This class is intended to used by calling its public methods. It is originally
 *        made to run by the Frontend.java file. It has no main method, and cannot be called
 *        directly.
 *
 * Author: Minh Duong and Soumay Agarwal
 * Date: 29th April 2023
 * Course: CSC 460 - Spring 2023
 * Professor: Lester McCann
*/

import java.io.*;
import java.sql.*;
import java.util.Scanner;

class Utility {
  public static void printError(SQLException e, String error) {
    System.err.println("Error:");
    System.err.println("Message:   " + e.getMessage());
    System.err.println("SQLState:  " + e.getSQLState());
    System.err.println("ErrorCode: " + e.getErrorCode());
  }

  /**
   * Method checkDoubleBookingUpdate(Connection dbconn, String checkInDate,
   *                                String checkOutDate, int roomno, int cid)
   *
   * Purpose: Checks whether the same room has been booked for different customers over the 
   *          conflicting time period (including checkInDate and checkOutDate). Specifically
   *          for update function.
   * 
   * Pre-condition: Connection object must be initialized and connected 
   *                to their respective sources. checkInDate and checkOutDate must
   *                be formated correctly.
   * 
   * Post-condition: The checking would be done and appropriate response would be returned.
   *
   * Parameters:
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 UPDATE statement
   *   @param checkInDate the provided checkInDate by the update function.
   *   @param checkOutDate the provided checkOutDate by the update function.
   *   @param roomNo the provided roomNo by the update function.
   *   @param cid the provided cid by the update function.
   * Returns: boolean. True if double booked, otherwise false.
   */

  public static boolean checkDoubleBookingUpdate(
      Connection dbconn, 
      String checkInDate, 
      String checkOutDate, 
      int roomNo,
      int cid) {
    try {
      String query = 
        "SELECT * " +
        "FROM Booking " + 
        "WHERE checkOutDate > TO_DATE('" + checkInDate + "', 'MM/DD/YY') AND " +
        "checkInDate < TO_DATE('" + checkOutDate + "', 'MM/DD/YY') AND " + 
        "roomNo = " + roomNo + " AND " + 
        "customerId != " + cid;

      Statement stmt = null;
      ResultSet answer = processQuery(query, dbconn, stmt);

      if (answer != null) {
        if (answer.next()) {
          return true;
        } else {
          return false;
        }
      } else {
        System.out.println("Could not query to the database. The query will be stopped.");
        return true;
      }
    } catch (SQLException e) {
      printError(e, "Cannot not query to the database. The query will be stopped.");
      return true;
    } 
  }

  /**
   * Method checkDoubleBooking(Connection dbconn, String checkInDate,
   *                                String checkOutDate, int roomno)
   *
   * Purpose: Checks whether the same room has been booked for different customers over the 
   *          conflicting time period (including checkInDate and checkOutDate). Specifically
   *          for insert function.
   * 
   * Pre-condition: Connection object must be initialized and connected 
   *                to their respective sources. checkInDate and checkOutDate must
   *                be formated correctly.
   * 
   * Post-condition: The checking would be done and appropriate response would be returned.
   *
   * Parameters:
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 INSERT statement
   *   @param checkInDate the provided checkInDate by the insert function.
   *   @param checkOutDate the provided checkOutDate by the insert function.
   *   @param roomNo the provided roomNo by the insert function.
   * Returns: boolean. True if double booked, otherwise false.
   */

  public static boolean checkDoubleBooking(
      Connection dbconn, 
      String checkInDate, 
      String checkOutDate, 
      int roomNo) {
    try {
      String query = 
        "SELECT * " +
        "FROM Booking " + 
        "WHERE checkOutDate > TO_DATE('" + checkInDate + "', 'MM/DD/YY') AND " +
        "checkInDate < TO_DATE('" + checkOutDate + "', 'MM/DD/YY') AND " + 
        "roomNo = " + roomNo;

      Statement stmt = null;
      ResultSet answer = processQuery(query, dbconn, stmt);

      if (answer != null) {
        if (answer.next()) {
          return true;
        } else {
          return false;
        }
      } else {
        System.out.println("Could not query to the database. The query will be stopped.");
        return true;
      }
    } catch (SQLException e) {
      printError(e, "Cannot not query to the database. The query will be stopped.");
      return true;
    } 
  }

  /**
   * Method processQuery(String query, Connection dbconn, Statement stmt)
   *
   * Purpose: Process a query with the query string and returns the result set.
   * 
   * Pre-condition: Connection object must be initialized and connected 
   *                to their respective sources. query should correctly formatted.
   * 
   * Post-condition: The calling client should know the format of the answer. The client
   *                 should close the statement.
   *
   * Parameters:
   *   @param query the provided query string
   *   @param dbconn the Connection object used to connect to the database and execute the SQL 
   *                 statement
   *   @param stmt the statement to run on the databse
   * Returns: ResultSet. The result from the query.
   */

  public static ResultSet processQuery(String query, Connection dbconn, Statement stmt) {
    ResultSet answer = null;
    try {
      stmt = dbconn.createStatement();
      answer = stmt.executeQuery(query);
      return answer;
    } catch (SQLException e) {
      Utility.printError(e, "Could not fetch any result.");
      return null;
    }
  }
}
