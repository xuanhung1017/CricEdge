package CricEdge.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerQueries {
    
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cricedge_db";
	
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private Connection connection;
    private PreparedStatement selectCustomerByEmail;
    private PreparedStatement selectCustomerByPassword;
    private PreparedStatement checkEmail;
    private PreparedStatement insertNewCustomer;
    
    // Constructor
    public CustomerQueries() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    
            // creates the query for the records of Customer table with specific Email Address
            selectCustomerByEmail = connection.prepareStatement("SELECT * FROM customers WHERE Email = ?");
            
            // creates the query for the records of Customer table with specific Password
            selectCustomerByPassword = connection.prepareStatement("SELECT * FROM customers WHERE Password = ?");
            
            //creates the query for checking email available with specific Email Address
            checkEmail = connection.prepareStatement("SELECT * FROM customers WHERE Email = ?");
            
            //creates the query for inserting an new record in the Customer table
            insertNewCustomer = connection.prepareStatement("INSERT INTO customers " + " (Email, Password, FirstName, LastName, CompanyName, PhoneNumber, Address1, Address2, City, Country, State, Zip) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    //Selects Customer with UserName for Login Form
    public List<Customer> getCustomerByEmail(String email) {
        List<Customer> results = null;
        ResultSet resultSet = null;
        
        try {
            selectCustomerByEmail.setString(1, email);
            
            // executeQuery returns a ResultSet that contains the desired records
            resultSet = selectCustomerByEmail.executeQuery();
            
            results = new ArrayList<Customer>();
            
            while(resultSet.next()) {
                results.add(new Customer(
                		resultSet.getInt("customerID"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("companyName"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address1"),
                        resultSet.getString("address2"),
                        resultSet.getString("city"),
                        resultSet.getString("country"),
                        resultSet.getString("state"),
                        resultSet.getString("zip")));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return results;
    }
    
    // Selects Customer with Password for Login Form
    public List<Customer> getCustomerByPassword(String password) {
        List<Customer> results = null;
        ResultSet resultSet = null;
        
        try {
            selectCustomerByPassword.setString(1, password);
            
            // executeQuery returns a ResultSet that contains the desired records
            resultSet = selectCustomerByPassword.executeQuery();
            
            results = new ArrayList<Customer>();
            
            while (resultSet.next()) {
                results.add(new Customer(
                		resultSet.getInt("customerID"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("companyName"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address1"),
                        resultSet.getString("address2"),
                        resultSet.getString("city"),
                        resultSet.getString("country"),
                        resultSet.getString("state"),
                        resultSet.getString("zip")));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return results;
    }
    
  //Selects Customer with UserName for Login Form
    public boolean checkEmailAvailable(String email) {
    	boolean emailExists = false;
        ResultSet resultSet = null;
        
        try {
            checkEmail.setString(1, email);
            
            // executeQuery returns a ResultSet that contains the desired records
            resultSet = checkEmail.executeQuery();
                        
            while(resultSet.next()) {
                if(email.equals(resultSet.getString("Email")))
                	emailExists = true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
        }
        
        return emailExists;
    }
    
    // Insert new Customer for Register Form
    public int addCustomer(String email, String password, String firstName, String lastName, String companyName, String phoneNumber,
    		String address1, String address2, String city, String country, String state, String zip) {
        int result = 0;
        
        // defines parameters and then runs insertNewCustomer
        try {
            insertNewCustomer.setString(1, email);
            insertNewCustomer.setString(2, password);
            insertNewCustomer.setString(3, firstName);
            insertNewCustomer.setString(4, lastName);
            insertNewCustomer.setString(5, companyName);
            insertNewCustomer.setString(6, phoneNumber);
            insertNewCustomer.setString(7, address1);
            insertNewCustomer.setString(8, address2);
            insertNewCustomer.setString(9, city);
            insertNewCustomer.setString(10, country);
            insertNewCustomer.setString(11, state);
            insertNewCustomer.setString(12, zip);
            
            // inserts the new record and returns the number of the lines that update
            result = insertNewCustomer.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }
        
        return result;
    }
    
    // close database connection
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }    
}
