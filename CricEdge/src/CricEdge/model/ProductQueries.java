package CricEdge.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductQueries {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cricedge_db";
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private Connection connection;
    private PreparedStatement selectAllProducts;
    
    // Constructor
    public ProductQueries() {
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            
            // creates the query for all the records in Product table
            selectAllProducts = connection.prepareStatement("SELECT * FROM products");
                        
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public List<Product> getAllProducts() {
        List<Product> results = null;
        ResultSet resultSet = null;
        
        try {
        	// executeQuery returns a ResultSet that contains the desired records
        	resultSet = selectAllProducts.executeQuery();
        	results = new ArrayList<Product>();
        	while (resultSet.next()) {
        		results.add(new Product(
        				resultSet.getInt("productID"),
        				resultSet.getString("productName"),
        				resultSet.getDouble("price"),
        				resultSet.getString("description"),
        				resultSet.getString("image")
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