package CricEdge.model;

public class Product {
    private int productID;
    private String productName;
    private double price;
    private String description;
    private String image;
   
    // Default constructor
    public Product(){}
    
    // Constructor
    public Product(int productID, String productName, double price, String description, String image) {
        setProductID(productID);
        setProductName(productName);
        setPrice(price);
        setDescription(description);
        setImage(image);
    }
    
    // Set and get methods
    public void setProductID(int productID){
        this.productID = productID;
    }
    
    public int getProductID(){
        return productID;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public void setPrice(double price) {
        this.price = price;
    } 
    
    public double getPrice() {
        return price;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getImage() {
        return image;
    }
}