package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This program creates the CoffeeDB database.
 */
public class CreateDB
{
    public static void main(String[] args)
    {
        // Create a named constant for the URL.
        // NOTE: This value is specific for Java DB.
        final String DB_URL = "jdbc:derby:MyDB;create=true";

        try
        {
            // Create a connection to the database.
            Connection conn =
                    DriverManager.getConnection(DB_URL);

            // If the DB already exists, drop the tables.
            dropTables(conn);

            // Build the Coffee table.
            buildAccountTable(conn);

            // Build the Customer table.
            buildProductTable(conn);

            // Close the connection.
            conn.close();
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The dropTables method drops any existing
     * in case the database already exists.
     */
    public static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables.");

        try
        {
            // Get a Statement object.
            Statement stmt  = conn.createStatement();

            try
            {
                // Drop the Customer table.
                stmt.execute("DROP TABLE Product");
                System.out.println("Product table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }

            try
            {
                // Drop the Coffee table.
                stmt.execute("DROP TABLE Account");
                System.out.println("Account table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        catch(SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The buildCoffeeTable method creates the
     * Coffee table and adds some rows to it.
     */
    public static void buildAccountTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Account (" +
                    "Username CHAR(20) NOT NULL PRIMARY KEY, " +
                    "Password char(20)" +
                    ")");

            // Insert row #10.
            stmt.execute("INSERT INTO Account VALUES ( " +
                    "'nvt', " +
                    "'123456'"+")");

            stmt.execute("INSERT INTO Account VALUES ( " +
                    "'htlt', " +
                    "'123456'"+")");

            stmt.execute("INSERT INTO Account VALUES ( " +
                    "'nts', " +
                    "'123456'"+")");

            System.out.println("Account table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The buildCustomerTable method creates the
     * Customer table and adds some rows to it.
     */
    public static void buildProductTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Product" +
                    "( Descriptions CHAR(50) NOT NULL PRIMARY KEY, " +
                    "  Price Double, Quality Char(3), Selected int)");

            // Add some rows to the new table.
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Black Coffee', 12.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Milk Coffee', 15.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Sugar Daddy', 50.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Matcha Milk Tea', 30.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Matcha Freeze', 31.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Yogurt', 20.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Tiger', 20.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Heniken', 25.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Coconut', 25.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Milk', 10.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Olong Tea', 25.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Hello Tea', 10.00, '0', 0)");
            stmt.executeUpdate("INSERT INTO Product VALUES" +
                    "('Water', 10.00, '0', 0)");
            System.out.println("Product table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
