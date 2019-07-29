package CricEdge;

import CricEdge.model.Customer;
import CricEdge.model.CustomerQueries;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AccountController implements Initializable {
    
    @FXML private TextField emailAddress_TF;
    @FXML private PasswordField currentPassword_TF;
    @FXML private PasswordField newPassword_TF;
    @FXML private PasswordField confirmNewPassword_TF;
    @FXML private TextField firstName_TF;
    @FXML private TextField lastName_TF;
    @FXML private TextField companyName_TF;
    @FXML private TextField phoneNumber_TF;
    @FXML private TextField addressLine1_TF;
    @FXML private TextField addressLine2_TF;
    @FXML private TextField city_TF;
    @FXML private TextField country_TF;
    @FXML private TextField state_TF;
    @FXML private TextField zip_TF;
    @FXML private Label successLabel;
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cricedge_db";
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";    
    private Connection connection;
    private Statement statement;
    
    private CustomerQueries customerQueries;
    
    private Main application;
        
    public void setApp(Main application) {
        this.application = application;
        Customer loggedCustomer = application.getLoggedCustomer();
        emailAddress_TF.setText(loggedCustomer.getEmail());
        firstName_TF.setText(loggedCustomer.getFirstName());
        lastName_TF.setText(loggedCustomer.getLastName());
        companyName_TF.setText(loggedCustomer.getCompanyName());
        phoneNumber_TF.setText(loggedCustomer.getPhoneNumber());
        addressLine1_TF.setText(loggedCustomer.getAddress1());
        addressLine2_TF.setText(loggedCustomer.getAddress2());
        city_TF.setText(loggedCustomer.getCity());
        country_TF.setText(loggedCustomer.getCountry());
        state_TF.setText(loggedCustomer.getState());
        zip_TF.setText(loggedCustomer.getZip());
        successLabel.setOpacity(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void saveProfile(ActionEvent event) {
        if(application == null) {
            animateMessage();
            return;
        }
        Customer loggedCustomer = application.getLoggedCustomer();
        customerQueries = new CustomerQueries();
        
        try {
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            
            final String UPDATE_EMAIL = "UPDATE customers SET Email = '" + emailAddress_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_PASSWORD = "UPDATE customers SET Password = '" + newPassword_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_FIRSTNAME = "UPDATE customers SET FirstName = '" + firstName_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_LASTNAME = "UPDATE customers SET LastName = '" + lastName_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_COMPANYNAME = "UPDATE customers SET CompanyName = '" + companyName_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_PHONENUMBER = "UPDATE customers SET PhoneNumber = '" + phoneNumber_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_ADDRESS1 = "UPDATE customers SET Address1 = '" + addressLine1_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_ADDRESS2 = "UPDATE customers SET Address2 = '" + addressLine2_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_CITY = "UPDATE customers SET City = '" + city_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_COUNTRY = "UPDATE customers SET Country = '" + country_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_STATE = "UPDATE customers SET State = '" + state_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
            final String UPDATE_ZIP = "UPDATE customers SET Zip = '" + zip_TF.getText() + "' WHERE CustomerID=" + loggedCustomer.getCustomerID();
                        
            if(!emailAddress_TF.getText().matches("\\b[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,}\\b")) {
            	successLabel.setText("Please enter a valid email address, such as joe@example.com.");
                animateMessage();
            }
            else if(!emailAddress_TF.getText().matches(loggedCustomer.getEmail()) && customerQueries.checkEmailAvailable(emailAddress_TF.getText())) {
            	successLabel.setText("The email address " + emailAddress_TF.getText() +" is already in use.");
            	animateMessage();
            }
            else if(!currentPassword_TF.getText().trim().isEmpty() && !currentPassword_TF.getText().matches(loggedCustomer.getPassword())) {
            	successLabel.setText("Your current password is incorrect.");
                animateMessage();    
            }
            else if ((!newPassword_TF.getText().trim().isEmpty() || !confirmNewPassword_TF.getText().trim().isEmpty()) && !newPassword_TF.getText().matches(confirmNewPassword_TF.getText())) {
            	successLabel.setText("Your new passwords don't match.");
            	animateMessage();
            }
            else if (!newPassword_TF.getText().trim().isEmpty() && newPassword_TF.getText().length() < 7) {
            	successLabel.setText("Your new password must be at least 7 characters.");
            	animateMessage();
            }
            else if (!newPassword_TF.getText().trim().isEmpty() && currentPassword_TF.getText().trim().isEmpty()) {
            	successLabel.setText("Please enter your current password to change password.");
            	animateMessage();
            }
            else if(firstName_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'First Name' field cannot leave blank.");
                animateMessage();    
            }
            else if(lastName_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'Last Name' field cannot leave blank.");
                animateMessage();    
            }
            else if(phoneNumber_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'Phone Number' field cannot leave blank.");
                animateMessage();    
            }
            else if(addressLine1_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'Address Line 1' field cannot leave blank.");
                animateMessage();    
            }
            else if(city_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'City' field cannot leave blank.");
                animateMessage();    
            }
            else if(country_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'Country' field cannot leave blank.");
                animateMessage();    
            }
            else if(state_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'State/Provine' field cannot leave blank.");
                animateMessage();    
            }
            else if(zip_TF.getText().trim().isEmpty()) {
            	successLabel.setText("The 'Zip/PostCode' field cannot leave blank.");
                animateMessage();    
            }
            else {  
            	statement.executeUpdate(UPDATE_EMAIL);
                loggedCustomer.setEmail(emailAddress_TF.getText());
                emailAddress_TF.setText(emailAddress_TF.getText());
                
                if (!currentPassword_TF.getText().trim().isEmpty() && !newPassword_TF.getText().trim().isEmpty()) {
                	statement.executeUpdate(UPDATE_PASSWORD);
                    loggedCustomer.setPassword(phoneNumber_TF.getText());
                    phoneNumber_TF.setText(phoneNumber_TF.getText());
                }
            	
                statement.executeUpdate(UPDATE_FIRSTNAME);
                loggedCustomer.setFirstName(firstName_TF.getText());
                firstName_TF.setText(firstName_TF.getText());
                
                statement.executeUpdate(UPDATE_LASTNAME);
                loggedCustomer.setLastName(lastName_TF.getText());
                lastName_TF.setText(lastName_TF.getText());
                
                statement.executeUpdate(UPDATE_COMPANYNAME);
                loggedCustomer.setCompanyName(companyName_TF.getText());
                companyName_TF.setText(companyName_TF.getText());
                
                statement.executeUpdate(UPDATE_PHONENUMBER);
                loggedCustomer.setPhoneNumber(phoneNumber_TF.getText());
                phoneNumber_TF.setText(phoneNumber_TF.getText());
                
                statement.executeUpdate(UPDATE_ADDRESS1);
                loggedCustomer.setAddress1(addressLine1_TF.getText());
                addressLine1_TF.setText(addressLine1_TF.getText());
                
                statement.executeUpdate(UPDATE_ADDRESS2);
                loggedCustomer.setAddress2(addressLine2_TF.getText());
                addressLine2_TF.setText(addressLine2_TF.getText());
                
                statement.executeUpdate(UPDATE_CITY);
                loggedCustomer.setCity(city_TF.getText());
                city_TF.setText(city_TF.getText());
                
                statement.executeUpdate(UPDATE_COUNTRY);
                loggedCustomer.setCountry(country_TF.getText());
                country_TF.setText(country_TF.getText());
                
                statement.executeUpdate(UPDATE_STATE);
                loggedCustomer.setState(state_TF.getText());
                state_TF.setText(state_TF.getText());
                
                statement.executeUpdate(UPDATE_ZIP);
                loggedCustomer.setZip(zip_TF.getText());
                zip_TF.setText(zip_TF.getText());
                
                successLabel.setText("Profile successfully updated!");
                animateMessage();
            }    
        }
        catch(SQLException sqlException) {
        	sqlException.printStackTrace();
        }   
    }
    
    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), successLabel);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
}
