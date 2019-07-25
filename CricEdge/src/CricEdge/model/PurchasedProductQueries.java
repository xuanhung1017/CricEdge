package CricEdge.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchasedProductQueries {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cricedge_db";
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private Connection connection;
    private PreparedStatement selectAllProductsInAnOrder;
    
    // Constructor
    public PurchasedProductQueries() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);       
            
            // creates the query for all purchased products in an order 
            selectAllProductsInAnOrder = connection.prepareStatement("SELECT * FROM products INNER JOIN order_details ON products.`ProductID` = order_details.`ProductID` WHERE OrderID = ?");
                        
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public List<PurchasedProduct> getAllPurchasedProducts(String order) {
        List<PurchasedProduct> results = null;
        ResultSet resultSet = null;
        
        try {        	
        	selectAllProductsInAnOrder.setString(1, order);

        	// executeQuery returns a ResultSet that contains the desired records
        	resultSet = selectAllProductsInAnOrder.executeQuery();
        	results = new ArrayList<PurchasedProduct>();
        	while (resultSet.next()) {
        		results.add(new PurchasedProduct( 				
        				resultSet.getString("image"),
        				resultSet.getString("productName"),
        				resultSet.getString("size"),
        				resultSet.getDouble("price"),
        				resultSet.getInt("quantity")
        		));
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
    
    // closes the connection with the database
    public void close() {
        try {
            connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}