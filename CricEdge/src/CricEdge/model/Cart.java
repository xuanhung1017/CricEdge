package CricEdge.model;

public class Cart {
	private int productID;
    private String productName;
    private double price;
    private String image;
    private char size;
    private int quantity;
    
    // Default constructor
    public Cart(){}

    // Constructor
    public Cart(int productID, String productName, double price, String image, char size, int quantity) {
    	setProductID(productID);
        setProductName(productName);
        setPrice(price);
        setImage(image);
        setSize(size);
        setQuantity(quantity);
    }    
    
    // Set/Get methods
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
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getImage() {
        return image;
    }
    
    public char getSize() {
        return size;
    }
    
    public void setSize(char size) {
        this.size = size;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getSubtotal() {
        return price * quantity;
    }
}
