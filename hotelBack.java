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
||        Description:  This program is the first piece of our overall program. This program is responsible for
||                      creating the nine tables including Customer, Room, Booking, Amenity, Payment, BookingAmenity,
||                      AmenityRating, Employee and EmployeeShift. Next part involves using the initialDataPopulater.java
||                      program to populate the tables.
||
||   Compilation info: Firstly run the statement "export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}"
||                     on lectura and then use "javac hotelBack.java" to compile the program and then use
||                     "java hotelBack" to run the program.
||
||     Information about bugs- No particular bug is present in the system. As long as appropriate inputs are provided the
||                             the program should function fine.
||
++-----------------------------------------------------------------------*/

import java.sql.Statement;
import java.io.*;
import java.sql.*;                 // For access to the SQL interaction methods

public class hotelBack{

    public static void main(String[] args){
        final String oracleURL =   // Magic lectura -> aloe access spell
        "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

        final String query =        // query to create Customer table
        "CREATE TABLE Customer " + "(customerId INT, firstName VARCHAR(100), lastName VARCHAR(100), "
        + "email VARCHAR(200),  phoneNumber VARCHAR(20), isCollegeStudent NUMBER(1), isclub460Member NUMBER(1), "
        + "creditCardType VARCHAR(100), point INT, PRIMARY KEY ( customerId ))";

        final String query2 =       // query to create Room table
        "CREATE TABLE Room " + "(roomNo INT, roomPrice REAL, PRIMARY KEY ( roomNo ))";

        final String query3 =       // query to create Booking table
        "CREATE TABLE Booking " + "(bookingId INT, customerId INT, roomNo INT, checkInDate DATE, checkOutDate DATE, "
        + "PRIMARY KEY ( bookingId ))";

        final String query4 =       // query to create Amenity table
        "CREATE TABLE Amenity " + "(amenityId INT, name VARCHAR(100), price REAL, PRIMARY KEY ( amenityId ))";

        final String query5 =       // query to create BookingAmenity table
        "CREATE TABLE BookingAmenity " + "(bookingId INT, amenityId INT, quantity INT, "
        + "PRIMARY KEY ( bookingId, amenityId ))";

        final String query6 =       // query to create Payment table
        "CREATE TABLE Payment " + "(bookingId INT, totalAmount REAL, "
        + "tips REAL, PRIMARY KEY( bookingId ))";

        final String query7 =       // query to create Employee table
        "CREATE TABLE Employee " + "(employeeId INT, firstName VARCHAR(100), lastName VARCHAR(100), "
        + "jobTitle VARCHAR(100), PRIMARY KEY ( employeeId ))";

        final String query8 =       // query to create EmployeeShift table
        "CREATE TABLE EmployeeShift" + "(employeeId INT, shiftDate DATE, duties VARCHAR(100), shiftStartTime  INT, shiftEndTime INT, "
        + "PRIMARY KEY( employeeId, shiftDate ))";

        final String query9 =       // query to create AmenityRating table
        "CREATE TABLE AmenityRating" + "(bookingId INT, amenityId INT, rating INT NOT NULL, "
        + "ratingDate DATE NOT NULL, PRIMARY KEY ( bookingId, amenityId ))";

        final String assertion1 =       // assertion to have only_positive_customerIDs
        "ALTER TABLE Customer ADD CONSTRAINT only_positive_customerIDs CHECK (customerId > 0)";

        final String assertion2 =       // assertion to have only_positive_amenityIDs
        "ALTER TABLE Amenity ADD CONSTRAINT only_positive_amenityIDs CHECK (amenityId > 0)";

        final String assertion3 =       // assertion to have only_positive_amenity_price
        "ALTER TABLE Amenity ADD CONSTRAINT only_positive_amenity_price CHECK (price > 0.0)";

        final String assertion4 =       // assertion to have only_positive_roomNo
        "ALTER TABLE Room ADD CONSTRAINT only_positive_roomNo CHECK (roomNo > 0)";

        final String assertion5 =       // assertion to have only_positive_roomPrice
        "ALTER TABLE Room ADD CONSTRAINT only_positive_roomPrice CHECK (roomPrice > 0.0)";

        final String assertion6 =       // assertion to have only_positive_totalAmount
        "ALTER TABLE Payment ADD CONSTRAINT only_positive_totalAmount CHECK (totalAmount > 0.0)";

        final String assertion7 =       // assertion to have only_positive_tips
        "ALTER TABLE Payment ADD CONSTRAINT only_positive_tips CHECK (tips > 0.0)";

        final String assertion8 =       // assertion to have only_positive_employeeId
        "ALTER TABLE Employee ADD CONSTRAINT only_positive_employeeId CHECK (employeeId > 0)";
        
        final String assertion9 =       // assertion to have checkOutDate being greater the checkInDate
        "ALTER TABLE Booking ADD CONSTRAINT checkOutDate_must_be_greater CHECK (checkOutDate > checkInDate)";

        final String foreignKeyReference =      // foreign key reference of customer Id
        "ALTER TABLE Booking ADD FOREIGN KEY (customerId) REFERENCES Customer(customerId)";

        final String foreignKeyReference2 =     // foreign key reference of roomNo
        "ALTER TABLE Booking ADD FOREIGN KEY (roomNo) REFERENCES Room(roomNo)";

        final String foreignKeyReference3 =     // foreign key reference of bookingId
        "ALTER TABLE Payment ADD FOREIGN KEY (bookingId) REFERENCES Booking(bookingId)";

        final String foreignKeyReference4 =     // foreign key reference of bookingId
        "ALTER TABLE BookingAmenity ADD FOREIGN KEY (bookingId) REFERENCES Booking(bookingId)";

        final String foreignKeyReference5 =     // foreign key reference of amenityId
        "ALTER TABLE BookingAmenity ADD FOREIGN KEY (amenityId) REFERENCES Amenity(amenityId)";

        final String foreignKeyReference6 =     // foreign key reference of bookingId and amenityId
        "ALTER TABLE AmenityRating ADD FOREIGN KEY (bookingId, amenityId) REFERENCES BookingAmenity(bookingId, amenityId)";

        final String foreignKeyReference7 =     // foreign key reference of employeeId
        "ALTER TABLE EmployeeShift ADD FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)";


        String username = "soumayagarwal",    // Oracle DBMS username
        password = "a6708";    // Oracle DBMS password

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
            stmt.addBatch(assertion1);
            stmt.addBatch(assertion2);
            stmt.addBatch(assertion3);
            stmt.addBatch(assertion4);
            stmt.addBatch(assertion5);
            stmt.addBatch(assertion6);
            stmt.addBatch(assertion7);
            stmt.addBatch(assertion8);
            stmt.addBatch(assertion9);
            stmt.addBatch(foreignKeyReference);
            stmt.addBatch(foreignKeyReference2);
            stmt.addBatch(foreignKeyReference3);
            stmt.addBatch(foreignKeyReference4);
            stmt.addBatch(foreignKeyReference5);
            stmt.addBatch(foreignKeyReference6);
            stmt.addBatch(foreignKeyReference7);
            stmt.executeBatch();

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