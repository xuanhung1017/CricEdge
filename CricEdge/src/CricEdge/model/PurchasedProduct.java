package CricEdge.model;

public class PurchasedProduct {
    private String image;
    private String productName;
    private String size;
    private double price;
    private int quantity;
    
    // Default constructor
    public PurchasedProduct(){}

    // Constructor
    public PurchasedProduct(String image, String productName, String size, double price, int quantity) {
    	setImage(image);
    	setProductName(productName);
    	setSize(size);
    	setPrice(price); 
        setQuantity(quantity);
    }    
    
    // Set/Get methods
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductName(){
        return productName;
    }
      
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public void setPrice(double price) {
        this.price = price;
    } 
    
    public double getPrice() {
        return price;
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
