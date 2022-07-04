package server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import object.Account;
import object.Product;

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
      try (InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
            var in = new Scanner(inStream, StandardCharsets.UTF_8);         
            var out = new PrintWriter(
               new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
               true /* autoFlush */))
      {
         String input = in.nextLine();
         String[]  str= input.split(" ");
         Account account = new Account(str[0],str[1]);
         String key;
         if (str.length<3){
            key = "";
         }else {
            key = str[2];
         }
         if (key.equals("login")){
            out.println(checkAccount(getListAccount(),account));
         } else if (key.equals("register")){
            int rs = checkUsername(getListAccount(), account.getUserName());
            out.println(rs);
            if (rs==0){
               registerOnClick(account);
            }
         } else {
            ObservableList<Product> listProduct = getProductList(key);
               for (Product pro : listProduct){
                  out.println(
                     pro.getDescriptions()+","+pro.getPrice()
                  );
//                  System.out.println(pro);
               }
         }
         out.flush();
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }

   }
   int checkAccount(List<Account> accounts, Account account){
      for (Account acc : accounts) {
         if (acc.getUserName().equals(account.getUserName())){
            if (acc.getPassword().equals(account.getPassword())){
               return  0;
            }else return  1;
         }
      }
      return 2;
   }
   int checkUsername(List<Account> accounts, String username){
      for (Account account : accounts){
         if (account.getUserName().equals(username))
            return -1;
      }
      return 0;
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
   public void registerOnClick(Account account){
      final String DB_URL = "jdbc:derby:MyDB";
      try
      {
         // Create a connection to the database.
         Connection conn = DriverManager.getConnection(DB_URL);

         // Create a Statement object.
         Statement stmt = conn.createStatement();

         String sql = "INSERT INTO Account " +
                 "VALUES('"+account.getUserName()+"','"+account.getPassword()+"')";
         stmt.executeUpdate(sql);
         // Close the connection.
         conn.close();
      }
      catch(Exception ex)
      {
         System.out.println("ERROR: " + ex.getMessage());
      }
   }
   private ObservableList<Product> getProductList(String values){
   ObservableList<Product> products = FXCollections.observableArrayList();
   final String DB_URL = "jdbc:derby:MyDB";
   try
   {
      // Create a connection to the database.
      Connection conn = DriverManager.getConnection(DB_URL);

      // Create a Statement object.
      Statement stmt = conn.createStatement();

      // Create a string with a SELECT statement.
      String sqlStatement;
      if (values.equals("")){
         sqlStatement =
                 "SELECT * FROM Product";
      }else{
         sqlStatement =
                 "select * from product where LOWER(descriptions) like LOWER('%"
                         +values+"%')";
      }

      // Send the statement to the DBMS.
      ResultSet result = stmt.executeQuery(sqlStatement);

      // Display the contents of the result set.
      // The result set will have three columns.
      while (result.next())
      {
         Product pro = new Product(
                 result.getString("Descriptions").trim(),
                 result.getDouble("Price"));
         products.add(pro);
      }
      // Close the connection.
      conn.close();
   }
   catch(Exception ex)
   {
      System.out.println("ERROR: " + ex.getMessage());
   }
   return products;
   }
}
