/**
 * Frontend.java
 *
 * This class is the main class of the Hotel Management System. It allows users to interact
 * with the system through a console interface, offering several options for inserting,
 * updating, deleting, and querying the database.
 *
 * The class uses the JDBC API to connect to an Oracle database and execute SQL commands.
 *
 * The class has the following public method:
 *   - main(String[] args):
 *       the entry point of the program, sets up the database connection and calls the loop
 *       method to begin the user interaction.
 * The class has the following private methods:
 *   - loop(Connection dbconn):
 *       handles the console interaction with the user, displaying a menu of options and
 *       calling the appropriate handler based on the user's input.
 *   - setUpDriver():
 *       sets up the JDBC driver for Oracle database.
 *   - getConnection():
 *        establishes a connection with the Oracle database.
 *
 * Usage: This class is intended to be used as the entry point of the Hotel Management System.
 *   - Compile it with the other JAVA file in the repository:
 *       javac *.java
 *
 *   - Call the Frontend class's main method:
 *       java Frontend
 *
 * Note: This class does not require the user to give the username and/or password for the 
 *       Oracle DB server.
 *
 * Conditions: The Database has already been created (on soumayagarwal's user), and it is assumed
 *             that the user has sufficient privilege to access Oracle Aloe server. It is also
 *             assumed that the SQL Driver has already been added to the Classpath for use.
 * 
 * Author: Minh Duong
 * Date: 1st May 2023
 * Course: CSC 460 - Spring 2023
 * Professor: Lester McCann
 */
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Frontend {
  /**
   * Method main(String[] args)
   *
   * Purpose: The main method of the program that sets up the database driver, establishes 
   *          a connection to the database, and starts the main loop of the program.
   *
   * Pre-condition: The database driver must be set up and available in the classpath. 
   *                A valid Connection object must be returned by the getConnection method.
   *
   * Post-condition: The main loop of the program is started with a valid Connection object to the database.
   * 
   * Parameters:
   *   @param args an array of command-line arguments passed to the program (not used in this program)
   *
   * Returns: None.
   */
  public static void main (String [] args) {
    setUpDriver();
    Connection dbconn = getConnection();
    loop(dbconn);
  }

  /**
   * Method loop(Connection dbconn)
   *
   * Purpose: The loop method runs the main loop of the program, displaying options to the user
   *          to insert, update, delete or query the database.
   *
   * Pre-condition: A valid Connection object to the database must be passed to the method.
   * Post-condition: The program runs the main loop until the user chooses to exit, without
   *                  any exception or error. The method returns void.
   *
   * Parameters:                
   *   @param dbconn a valid Connection object to the database.
   *
   * Returns: None.
   */
  private static void loop(Connection dbconn) {
    Scanner getInput = new Scanner(System.in);
    String input = "";
    System.out.println();
    System.out.println("Welcome to the Hotel Management System!");

    while (!input.equals("exit")) {
      System.out.println("Please choose one of the following options using a single digit");
      System.out.println("or exit to exit the program:");
      System.out.println("1: Insert to table");
      System.out.println("2: Update table");
      System.out.println("3: Delete from table");
      System.out.println("4: Query table");
      input = getInput.nextLine();
      System.out.println();
      switch (input) {
        case "1":
          input = InsertionHandler.handle(getInput, dbconn);
          break;
        case "2":
          input = UpdateHandler.handle(getInput, dbconn);
          break;
        case "3":
          input = DeletionHandler.handle(getInput, dbconn);
          break;
        case "4":
          input = QueryHandler.handle(getInput, dbconn);
          break;
        case "exit":
          break;
        default:
          System.out.println("Please only type the provided digit, or exit.");
          break;
      }
    }
  }

  /**
   * Method setUpDriver() 
   *
   * Purpose: Sets up the Oracle JDBC driver required to connect to the database.
   * 
   * Pre-condition: Oracle JDBC driver is exported and found on the Classpath.
   *
   * Post-condition: The Oracle JDBC driver is loaded successfully.
   * 
   * Parameters: None.
   *
   * Returns: None.
   */
  private static void setUpDriver() {
    try {
      Class.forName("oracle.jdbc.OracleDriver");
    } catch (ClassNotFoundException e) {
      System.err.println("*** ClassNotFoundException:  "
          + "Error loading Oracle JDBC driver.  \n"
          + "\tPerhaps the driver is not on the Classpath?");
      System.exit(-1);
    }
  }

  /**
   * Method getConnection() 
   *
   * Purpose: Establishes a connection to an Oracle database using the provided username 
   *          and password. It returns a Connection object that can be used to execute SQL
   *          queries. If it encounters an error while trying to establish a connection, 
   *          it prints an error message and exits the program.
   * 
   * Pre-condition: Oracle JDBC driver is exported and found on the ClassPath, and properly
   *                set up.
   *
   * Post-condition: The Connection is established. No other connections should be made for 
   *                 the same DB server.
   * 
   * Parameters: None.
   *
   * Returns: 
   *   @return Connection The connection to the database.
   */
  private static Connection getConnection() {
    final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
    String username = "soumayagarwal";  
    String password = "a6708";
    Connection dbconn;
    try {
      dbconn = DriverManager.getConnection(oracleURL,username,password);
      return dbconn;
    } catch (SQLException e) {
      Utility.printError(e, "Could not open connection to Oracle DB");
      System.exit(-1);
    }
    return null;
  }
}
