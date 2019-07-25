package CricEdge;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
    
    @FXML private TextField loginEmail;
    @FXML private PasswordField loginPassword;
    //@FXML private Button loginButton;
    @FXML private Label errorMessage;
    
    private Main application; 
    
    public void setApp(Main application) {
        this.application = application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMessage.setText("");
    }    
    
    public void processLogin(ActionEvent event) throws Exception {
        if(application == null) {
            errorMessage.setText("Welcome, " + loginEmail.getText());
        } else {
            if(!application.userLogging(loginEmail.getText(), loginPassword.getText())) {
                errorMessage.setText("UserName/Password is Incorrect");
            }
        }
    }
    
    public void registrationLink(ActionEvent event) throws Exception {
        application.gotoRegister();
    }
}
