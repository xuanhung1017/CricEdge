package CricEdge.model;

import java.util.Date;

public class Order {
 	private String orderID;
    private Date orderDate;
    private String status;
    private double totalPrice;
   
    // Default constructor
    public Order(){}
    
    // Constructor
    public Order(String orderID, Date orderDate, String status, double totalPrice) {
        setOrderNumber(orderID);
        setOrderDate(orderDate);
        setStatus(status);
        setTotalPrice(totalPrice);
    }
    
    // Set and get methods
    public void setOrderNumber(String orderID){
        this.orderID = orderID;
    }
    
    public String getOrderNumber(){
        return orderID;
    }
    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public Date getOrderDate(){
        return orderDate;
    }
    
    public void setStatus(String status) {
        this.status = status;
    } 
    
    public String getStatus() {
        return status;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
}
