package CricEdge;

import CricEdge.model.Customer;
import CricEdge.model.CustomerQueries;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Group root = new Group();
    private static Customer loggedCustomer;
    private CustomerQueries customerQueries;
    private List<Customer> emailResults;
    private List<Customer> passwordResults;
    private int numberOfCustomersByEmail = 0;
    private int numberOfCustomersByPassword = 0;
    private int currentCustomerIndex;
        
    private static Main mainInstance;
    public Main() {
        mainInstance = this;
    }
    public static Main getInstance() {
        return mainInstance;
    }
    
    public Parent createContent() {
        gotoLogin();
        return root;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CricEdge");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();       
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    public Customer getLoggedCustomer() {
        return loggedCustomer;
    }
    
    public boolean userLogging(String email, String password) throws IOException{
        customerQueries = new CustomerQueries();
        emailResults = customerQueries.getCustomerByEmail(email);
        passwordResults = customerQueries.getCustomerByPassword(password);
        numberOfCustomersByEmail = emailResults.size();
        numberOfCustomersByPassword = passwordResults.size();
        
        if(numberOfCustomersByEmail != 0 && numberOfCustomersByPassword != 0) {
            currentCustomerIndex = 0;
            loggedCustomer = emailResults.get(currentCustomerIndex);
            gotoStore();
            return true;
        } else {
            return false;
        }
    }
    
    void userLogout() throws Exception {
        loggedCustomer = null;
        Stage primaryStage = (Stage) root.getScene().getWindow();
        primaryStage.setMinWidth(648);
        primaryStage.setMinHeight(706);
        gotoLogin();
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
    }    
    
    public void gotoStore() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        BorderPane store = loader.load();
        MainController mainController = loader.getController();
        mainController.setApp(this);
        root.getChildren().clear();
        root.getChildren().add(store);
        Stage primaryStage = (Stage) root.getScene().getWindow();
        store.prefHeightProperty().bind(primaryStage.heightProperty());
        store.prefWidthProperty().bind(primaryStage.widthProperty());
        primaryStage.setMinWidth(970);
        primaryStage.setMinHeight(776);
        primaryStage.setResizable(false);
    }
    
    public void gotoRegister() {
        try {
            RegisterController registerController = (RegisterController) replaceSceneContent("Register.fxml");
            registerController.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        root.getChildren().clear();
        root.getChildren().addAll(page);
        return (Initializable) loader.getController();
    }
    
}