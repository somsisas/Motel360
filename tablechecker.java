


import java.io.*;
import java.sql.*;                 // For access to the SQL interaction methods

public class tablechecker
{
    public static void main (String [] args)
    {

        final String oracleURL =   // Magic lectura -> aloe access spell
                        "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
	//final String query = "CREATE TABLE DEMO " + "(pno INTEGER, weight INTEGER, city VARCHAR2(50), PRIMARY KEY ( pno ))";
	//final String query2 = "INSERT INTO DEMO VALUES(1,12,'London')";
	final String query = "Select * from Payment";
	String username = "soumayagarwal",    // Oracle DBMS username
               password = "a6708";    // Oracle DBMS password


        // if (args.length == 2) {    // get username/password from cmd line args
        //     username = args[0];
        //     password = args[1];
        // } else {
        //     System.out.println("\nUsage:  java JDBC <username> <password>\n"
        //                      + "    where <username> is your Oracle DBMS"
        //                      + " username,\n    and <password> is your Oracle"
        //                      + " password (not your system password).\n");
        //     System.exit(-1);
        // }


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

	Statement stmt = null;
        ResultSet answer = null;

        try {

            stmt = dbconn.createStatement();
	    // stmt.addBatch(query);
	    // answer = stmt.executeBatch();
            answer = stmt.executeQuery(query);
	    if (answer != null) {

                System.out.println("\nThe results of the query [" + query 
                                 + "] are: \n");

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
                    // System.out.println(answer.getInt("customerId") + "\t"
                    //     + answer.getString("firstName")+ "\t" + answer.getString("lastName")+ "\t"
                    //     +answer.getString("email") + "\t" + answer.getString("phoneNumber")+
                    //     "\t"+ answer.getInt("isCollegeStudent")+"\t"+ answer.getInt("isclub460Member")+
                    //     "\t"+answer.getString("creditCardType")+"\t"+answer.getInt("point"));
                    // System.out.println(answer.getInt("customerId") + "\t" + 
                    // answer.getInt("roomNo") + "\t" + answer.getString("checkInDate") + "\t"
                    // +answer.getString("checkOutDate"));
                    // System.out.println(answer.getInt("roomNo") + "\t" + 
                    // answer.getInt("roomPrice"));
                    // System.out.println(answer.getInt("employeeId") + "\t" + 
                    // answer.getString("shiftDate") + "\t"
                    // +answer.getString("duties")+"\t"+answer.getString("shiftStartTime")+"\t"+
                    // answer.getString("shiftEndTime"));
                    // System.out.println(answer.getInt("employeeId") + "\t" + 
                    // answer.getString("firstName") + "\t"
                    // +answer.getString("lastName")+"\t"+answer.getString("jobTitle"));
                    // System.out.println(answer.getInt("amenityId") + "\t" + 
                    // answer.getString("name") + "\t"
                    // +answer.getFloat("price"));
                    // System.out.println(answer.getInt("customerId") + "\t" + 
                    // answer.getInt("roomNo") + "\t"
                    // +answer.getString("checkInDate") + "\t" + answer.getInt("amenityId") + "\t"
                    // + answer.getInt("quantity"));
                    // System.out.println(answer.getInt("customerId") + "\t" + 
                    // answer.getInt("roomNo") + "\t"
                    // +answer.getString("checkInDate") + "\t" + answer.getInt("amenityId") + "\t"
                    // + answer.getInt("rating") + "\t" + answer.getString("ratingDate"));
                    System.out.println(answer.getInt("customerId") + "\t" + 
                    answer.getInt("roomNo") + "\t"
                    +answer.getString("checkInDate") + "\t" + answer.getFloat("totalAmount") + "\t"
                    + answer.getFloat("tips"));
                }
            }
            System.out.println();
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
