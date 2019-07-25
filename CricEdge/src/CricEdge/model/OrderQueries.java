package CricEdge.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderQueries {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cricedge_db";
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private Connection connection;
    private PreparedStatement selectAllOrders;
    
    // Constructor
    public OrderQueries() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);            
                        
            // creates the query for all orders placed
            selectAllOrders = connection.prepareStatement("SELECT * FROM orders WHERE CustomerID = ? ORDER BY OrderDate DESC");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public List<Order> getAllOrders(int customerID) {
        List<Order> results = null;
        ResultSet resultSet = null;
        
        try {
        	selectAllOrders.setInt(1, customerID);
            
            // executeQuery returns a ResultSet with all the desired records
            resultSet = selectAllOrders.executeQuery();
            
            results = new ArrayList<Order>();
            
            while(resultSet.next()) {
            	results.add(new Order(
                        resultSet.getString("orderID"),
                        resultSet.getDate("orderDate"),
                        resultSet.getString("status"),
                        resultSet.getDouble("totalPrice")
            	));
            } 
        } catch(SQLException sqlException) {
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
    
    // closes the connection with the database
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
