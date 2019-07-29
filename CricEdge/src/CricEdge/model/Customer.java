package CricEdge.model;

public class Customer {
    
    private int customerID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String companyName;
    private String phoneNumber;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String state;
    private String zip;
    
    
    // Default constructor
    public Customer(){}

    // Constructor
    public Customer(int customerID, String email, String password, String firstName, String lastName, String companyName, String phoneNumber,
    		String address1, String address2, String city, String country, String state, String zip) {
        setCustomerID(customerID);
        setEmail(email);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setCompanyName(companyName);
        setPhoneNumber(phoneNumber);
        setAddress1(address1);
        setAddress2(address2);
        setCity(city);
        setCountry(country);
        setState(state);
        setZip(zip);
    }    
    
    // Set/Get methods
    public int getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress1() {
        return address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
}
