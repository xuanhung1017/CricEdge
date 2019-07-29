package CricEdge;

import CricEdge.model.CustomerQueries;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

    @FXML private TextField registerEmailAddress;
    @FXML private PasswordField registerPassword;
    @FXML private PasswordField registerConfirmPassword;
    @FXML private TextField registerFirstName;
    @FXML private TextField registerLastName;
    @FXML private TextField registerCompanyName;
    @FXML private TextField registerPhoneNumber;
    @FXML private TextField registerAddress1;
    @FXML private TextField registerAddress2;
    @FXML private TextField registerCity;
    @FXML private TextField registerCountry;
    @FXML private TextField registerState;
    @FXML private TextField registerZip;
    @FXML private Label errorCreateAccountMessage;
    
    private CustomerQueries customerQueries;
    private Main application; 
    private AlertType type = AlertType.INFORMATION;
    private Stage stage;
    
    public void setAlertType(AlertType at) {
        type = at;
    }
    
    protected Alert createAlert() {
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText("Your Account Has Been Created\n\nThank you for creating your account at Rx Smart Gear. Your account details have been emailed to " + registerEmailAddress.getText());
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("The alert was approved"));
        return alert;
    }
    
    public void setApp(Main application) {
        this.application = application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	errorCreateAccountMessage.setText("");
    }    
    
    public void processRegister(ActionEvent event) throws IOException {
        customerQueries = new CustomerQueries();
                
        if(registerEmailAddress.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Email Address' field is required.");
        else if(customerQueries.checkEmailAvailable(registerEmailAddress.getText()))
        	errorCreateAccountMessage.setText("The email address " + registerEmailAddress.getText() +" is already in use.");
        else if(registerPassword.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Password' field is required.");
        else if(registerConfirmPassword.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Confirm Password' field is required.");
        else if(registerFirstName.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'First Name' field is required.");
        else if(registerLastName.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Last Name' field is required.");
        else if(registerPhoneNumber.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Phone Number' field is required.");
        else if(registerAddress1.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Address Line 1' field is required.");
        else if(registerCity.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'City' field is required.");
        else if(registerCountry.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Country' field is required.");
        else if(registerState.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'State/Provine' field is required.");
        else if(registerZip.getText().trim().isEmpty())
        	errorCreateAccountMessage.setText("The 'Zip/Postcode' field is required.");
        else if(!registerEmailAddress.getText().matches("\\b[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,}\\b"))
        	errorCreateAccountMessage.setText("Please type in a valid email address, such as joe@example.com");
        else if(registerPassword.getText().length() < 7)
        	errorCreateAccountMessage.setText("Passwords must be at least 7 characters.");
        else if(!registerPassword.getText().equals(registerConfirmPassword.getText()))
        	errorCreateAccountMessage.setText("Your passwords don't match.");
        
        else {
            customerQueries.addCustomer(registerEmailAddress.getText(), registerPassword.getText(), registerFirstName.getText(), registerLastName.getText(),
            		registerCompanyName.getText(), registerPhoneNumber.getText(), registerAddress1.getText(), registerAddress2.getText(), 
            		registerCity.getText(), registerCountry.getText(), registerState.getText(), registerZip.getText());
            createAlert();
            application.gotoLogin();
        }
    }
    
    public void backtoLogin(ActionEvent event) throws IOException {
    	application.gotoLogin();
    }
}
