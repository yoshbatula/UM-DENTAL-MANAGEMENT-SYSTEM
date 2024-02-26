package org.example.cce107;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class dentalbase {

    // This line of code is for connecting postgresql db
   public Connection dentaldb(String dbname, String user, String pass) {

        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname,user,pass);

            if (con!= null) {
                System.out.print("Connection Established");

            } else {
                System.out.print("Connection Failed");
            }

        } catch (Exception e) {
            System.out.print(e);
        }
        return con;
   }
}
