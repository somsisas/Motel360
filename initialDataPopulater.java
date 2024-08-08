/*+----------------------------------------------------------------------
||
||         Author:  Soumay Agarwal
||
||		   Course:  CSC460 (DatabaseDesign)
||
||	   Assignment:  Program #4: Database Design and Implementation
||
||	   Instructor:  L. McCann
||
||			  TAs:  Tanner and Aayush
||
||	     Due Date:  2 May 2023
||
||        Description:  This program is the second piece of our overall program. This program is responsible for
||                      populating the tables in our databse with the appropriate inital data.
||
||   Compilation info: Firstly run the statement "export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}"
||                     on lectura and then use "javac initialDataPopulater.java" to compile the program and then use
||                     "java initialDataPopulater" to run the program.
||
||     Information about bugs- No particular bug is present in the system. As long as appropriate inputs are provided the
||                             the program should function fine.
||
++-----------------------------------------------------------------------*/

import java.sql.Statement;
import java.io.*;
import java.sql.*;                 // For access to the SQL interaction methods

public class initialDataPopulater{

    public static void main(String[] args){
        final String oracleURL =   // Magic lectura -> aloe access spell
        "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

        final String query =       //Query to do appropriate insertion of data in Customer
        "INSERT INTO Customer VALUES(1, 'Soumay', 'Agarwal', 'soma251272@gmail.com', '5219219230', "
        + " 1, 1, 'Platinum', 0)";

        final String query2 =       //Query to do appropriate insertion of data in Customer
        "INSERT INTO Customer VALUES(2, 'Minh', 'Duong', 'hongminh4402@gmail.com', '5229229232', "
        + " 1, 1, 'Gold', 1)";

        final String givenCheckInDate = "5/15/23";
        final String givenCheckOutDate = "5/21/23";

        final String givenCheckInDate2 = "5/20/23";
        final String givenCheckOutDate2 = "5/23/23";

        final String query10 =      //Query to do appropriate insertion of data in Booking
        "INSERT INTO Booking VALUES(1, 1, 210, TO_DATE( '"+givenCheckInDate+ "', 'MM/DD/YY'), "
        + "TO_DATE( '"+givenCheckOutDate+ "', 'MM/DD/YY'))";

        final String query11 =      //Query to do appropriate insertion of data in Booking
        "INSERT INTO Booking VALUES(2, 2, 250, TO_DATE( '"+givenCheckInDate2+ "', 'MM/DD/YY'), "
        + "TO_DATE( '"+givenCheckOutDate2+ "', 'MM/DD/YY'))";

        final String query3 =       //Query to do appropriate insertion of data in Room
        "INSERT INTO Room VALUES(205, 129.5)";

        final String query4 =       //Query to do appropriate insertion of data in Room
        "INSERT INTO Room VALUES(210, 210.99)";

        final String query5 =       //Query to do appropriate insertion of data in Room
        "INSERT INTO Room VALUES(220, 540.89)";

        final String query6 =       //Query to do appropriate insertion of data in Room
        "INSERT INTO Room VALUES(250, 320.15)";

        final String query7 =       //Query to do appropriate insertion of data in Amenity
        "INSERT INTO Amenity VALUES(100, 'InfinityPool', 50)";

        final String query8 =       //Query to do appropriate insertion of data in Amenity
        "INSERT INTO Amenity VALUES(101, 'LowerLevelSwimmingPool', 10)";

        final String query9 =       //Query to do appropriate insertion of data in Amenity
        "INSERT INTO Amenity VALUES(102, 'OasisRestaurant', 20)";

        final String query12 =      //Query to do appropriate insertion of data in Payment
        "INSERT INTO Payment VALUES(1, 1265.94, 200)";

        final String query13 =      //Query to do appropriate insertion of data in Payment
        "INSERT INTO Payment VALUES(2, 960.45, 100)";

        final String query14 =      //Query to do appropriate insertion of data in BookingAmenity
        "INSERT INTO BookingAmenity VALUES(1, 100, 1)";

        final String rateDate = "5/20/23";

        final String query15 =      //Query to do appropriate insertion of data in AmenityRating
        "INSERT INTO AmenityRating VALUES(1, 100, 9, TO_DATE( '"+rateDate+ "', 'MM/DD/YY'))";

        final String query16 =      //Query to do appropriate insertion of data in Employee
        "INSERT INTO Employee VALUES(1001, 'Lon', 'Sav', 'Receptionist')";

        final String workDate = "5/15/23";

        final String query17 =      //Query to do appropriate insertion of data in EmployeeShift
        "INSERT INTO EmployeeShift VALUES(1001, TO_DATE( '"+workDate+ "', 'MM/DD/YY'), 'CheckingInGuests', 31530, 60330)";


        String username = "soumayagarwal",    // Oracle DBMS username
        password = "a6708";    // Oracle DBMS password

        // load the (Oracle) JDBC driver by initializing its base
        // class, 'oracle.jdbc.OracleDriver'.

        try {
            Class.forName("oracle.jdbc.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.err.println("*** ClassNotFoundException:  "
                + "Error loading Oracle JDBC driver.  \n"
                + "\tPerhaps the driver is not on the Classpath?");
            System.exit(-1);

        }

        // make and return a database connection to the user's
        // Oracle database

        Connection dbconn = null;

        try {
                dbconn = DriverManager.getConnection
                               (oracleURL,username,password);

        } catch (SQLException e) {

                System.err.println("*** SQLException:  "
                    + "Could not open JDBC connection.");
                System.err.println("\tMessage:   " + e.getMessage());
                System.err.println("\tSQLState:  " + e.getSQLState());
                System.err.println("\tErrorCode: " + e.getErrorCode());
                System.exit(-1);

        }

        // Send the query to the DBMS

        Statement stmt = null;
        ResultSet answer = null;
        try {
            stmt = dbconn.createStatement();
            stmt.addBatch(query);
            stmt.addBatch(query2);
            stmt.addBatch(query3);
            stmt.addBatch(query4);
            stmt.addBatch(query5);
            stmt.addBatch(query6);
            stmt.addBatch(query7);
            stmt.addBatch(query8);
            stmt.addBatch(query9);
            stmt.addBatch(query10);
            stmt.addBatch(query11);
            stmt.addBatch(query12);
            stmt.addBatch(query13);
            stmt.addBatch(query14);
            stmt.addBatch(query15);
            stmt.addBatch(query16);
            stmt.addBatch(query17);
            stmt.executeBatch();

            answer = stmt.executeQuery("Select * from Customer");

            if (answer != null) {

                System.out.println("\nThe results of the query are:\n");

                    // Get the data about the query result to learn
                    // the attribute names and use them as column headers

                ResultSetMetaData answermetadata = answer.getMetaData();

                for (int i = 1; i <= answermetadata.getColumnCount(); i++) {
                    System.out.print(answermetadata.getColumnName(i) + "\t");
                }
                System.out.println();

                    // Use next() to advance cursor through the result
                    // tuples and print their attribute values
                while (answer.next()) {
                    System.out.println(answer.getInt("customerId") + "\t"
                        + answer.getString("firstName")+ "\t" + answer.getString("lastName")+ "\t"
                        +answer.getString("email") + "\t" + answer.getString("phoneNumber")+
                        "\t"+ answer.getInt("isCollegeStudent")+"\t"+ answer.getInt("isclub460Member")+
                        "\t"+answer.getString("creditCardType")+"\t"+answer.getInt("point"));
                }
            }
            // Shut down the connection to the DBMS.
            stmt.close();
            dbconn.close();
        } catch (SQLException e) {

            System.err.println("*** SQLException:  "
                + "Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);

        }
    }
}
