package server;

import object.Account;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.sql.*;
import java.util.*;

/**
 * This program implements a multithreaded server that listens to port 8189 and echoes back 
 * all client input.
 * @author Cay Horstmann
 * @version 1.23 2018-03-17
 */
public class ThreadedEchoServer
{  
   public static void main(String[] args )
   {  
      try (var s = new ServerSocket(8089))
      {  
         int i = 1;

         while (true)
         {  
            Socket incoming = s.accept();
            System.out.println("Spawning " + i);
            Runnable r = new ThreadedEchoHandler(incoming);
            var t = new Thread(r);
            t.start();
            i++;
         }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
   }
}

/**
 * This class handles the client input for one server socket connection. 
 */
class ThreadedEchoHandler implements Runnable
{ 
   private Socket incoming;

   /**
      Constructs a handler.
      @param incomingSocket the incoming socket
   */
   public ThreadedEchoHandler(Socket incomingSocket)
   { 
      incoming = incomingSocket; 
   }

   public void run()
   {
      final String DB_URL = "jdbc:derby:MyDB;create=true";
      try (InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
            var in = new Scanner(inStream, StandardCharsets.UTF_8);         
            var out = new PrintWriter(
               new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
               true /* autoFlush */))
      {

         String input = in.nextLine();
         String str[] = input.split(" ");
         Account account = null;
         if (str.length == 2) {
            account = new Account(str[0],str[1]);
            // chưa có trường hợp null
         }else {
            account = new Account(str[0],"");
         }

         int rs = checkUsername(getListAccount(),account);
         out.println(rs);
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
   }
   int checkUsername(List<Account> accounts, Account account){
      for (Account acc : accounts) {
         if (acc.getUserName().equals(account.getUserName())){
            if (acc.getPassword().equals(account.getPassword())){
               return  0;
            }else return  1;
         }
      }
      return 2;
   }
   public ArrayList<Account> getListAccount(){
      final String DB_URL = "jdbc:derby:MyDB";
      ArrayList<Account> accounts = new ArrayList<>();
      try
      {
         // Create a connection to the database.
         Connection conn = DriverManager.getConnection(DB_URL);

         // Create a Statement object.
         Statement stmt = conn.createStatement();

         // Create a string with a SELECT statement.
         String sqlStatement =
                 "SELECT * FROM Account";

         // Send the statement to the DBMS.
         ResultSet result = stmt.executeQuery(sqlStatement);

         // Display the contents of the result set.
         // The result set will have three columns.
         while (result.next())
         {
            Account account = new Account(result.getString("Username").replaceAll(" ", ""),
                    result.getString("Password").replaceAll(" ", ""));
            accounts.add(account);
         }
         // Close the connection.
         conn.close();
      }
      catch(Exception ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
      return accounts;
   }
}
