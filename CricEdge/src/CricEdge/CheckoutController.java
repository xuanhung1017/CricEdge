package CricEdge;

import CricEdge.model.Cart;
import CricEdge.model.Customer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CheckoutController {
    
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cricedge_db";
	
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private AlertType type = AlertType.INFORMATION;
    private Stage stage;
    public Hyperlink checkoutCartButton;
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA);
    
    public void setAlertType(AlertType at) {
        type = at;
    }
    
    protected Alert createInfoAlert() {
        Alert alert = new Alert(type, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText("THANK YOU!\n\nYour order has been placed.");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        return alert;
    }
    
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField phoneNumber;
    @FXML private TextField address;
    @FXML private TextField city;
    @FXML private TextField state;
    @FXML private TextField country;
    @FXML private TextField zip;
    
    @FXML private TextField cardNumber;
    @FXML private TextField nameOnCard;
    @FXML private TextField cardExpiration;
    @FXML private TextField cardCVV;
    
    @FXML private Label shippingAddressLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;
    @FXML private Label errorLabel;
    
    @FXML CheckBox sameAddressCheckBox;
    @FXML Pane billingPane;
    @FXML TableView<Cart> checkoutTable;
    @FXML private TableColumn<Cart, Cart> checkoutImageCol;
    @FXML private TableColumn<Cart, Cart> checkoutProductNameCol;
    @FXML private TableColumn<Cart, Double> checkoutPriceCol;
    
    Double subtotal, tax, total;
    int getValue;
        
    public void setCheckoutPage(CartController cartController) {
    	Customer loggedCustomer = Main.getInstance().getLoggedCustomer();
    	if (loggedCustomer.getAddress2().isEmpty()) {
    		shippingAddressLabel.setText(loggedCustomer.getFirstName() + " " +loggedCustomer.getLastName() + "\n"
    									+ loggedCustomer.getAddress1() + "\n"
    									+ loggedCustomer.getCity() + ", " + loggedCustomer.getState() + ", " + loggedCustomer.getZip() + "\n"
    									+ loggedCustomer.getCountry());
    	}
    	else {
    		shippingAddressLabel.setText(loggedCustomer.getFirstName() + " " +loggedCustomer.getLastName() + "\n"
        								+ loggedCustomer.getAddress1() + "\n"
        								+ loggedCustomer.getAddress2() + "\n"
        								+ loggedCustomer.getCity() + ", " + loggedCustomer.getState() + ", " + loggedCustomer.getZip() + "\n"
        								+ loggedCustomer.getCountry());
    	}
    	checkoutImageCol.setCellValueFactory(img -> new ReadOnlyObjectWrapper<>(img.getValue()));
    	checkoutImageCol.setCellFactory(img -> new TableCell<Cart, Cart>(){
            private final ImageView coverImageView = new ImageView();
            @Override 
            protected void updateItem(Cart cart, boolean empty) {
                super.updateItem(cart, empty);
                if(cart == null) {
                    setGraphic(null);
                    return;
                }
                coverImageView.setImage(new Image(cart.getImage()));
                coverImageView.setFitHeight(70);
                coverImageView.setFitWidth(70);
                setGraphic(coverImageView);
            }
        });
    	
    	checkoutProductNameCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    	checkoutProductNameCol.setCellFactory(param -> new TableCell<Cart,Cart>(){
            @Override
            protected void updateItem(Cart cart, boolean empty) {
                super.updateItem(cart, empty);
                if(cart == null) {
                    setGraphic(null);
                    return;
                }
                VBox infoVBox = new VBox();
                Label name = new Label(cart.getProductName());
                name.setStyle("-fx-font-size: 14");
                name.setStyle("-fx-font-weight: BOLD");
                name.setAlignment(Pos.TOP_LEFT);
                Label size = new Label("Size: " + cart.getSize());
                size.setAlignment(Pos.TOP_LEFT);
                Label quantity = new Label("Qty: " + cart.getQuantity());
                quantity.setAlignment(Pos.TOP_LEFT);
                infoVBox.setPrefWidth(70.0);
                infoVBox.setAlignment(Pos.TOP_LEFT);
                infoVBox.getChildren().addAll(name, size, quantity);
                setGraphic(infoVBox);
            }
        });
        
        checkoutPriceCol.setCellValueFactory(new PropertyValueFactory<>("Subtotal"));
        checkoutPriceCol.setCellFactory(col -> new TableCell<Cart, Double>(){
            @Override
            public void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setGraphic(null);
                } else {
                    String currencyPrice = currencyFormatter.format(price);
                    Label priceLabel = new Label(currencyPrice);
                    setGraphic(priceLabel);
                }
            }
        });
        
        checkoutTable.setItems(cartController.getCartItemsForCheckout());
        
        subtotal = cartController.total;
        String currencySubtotal = currencyFormatter.format(subtotal);
        subtotalLabel.setText(currencySubtotal);
        
        tax = subtotal * 12 / 100;
        String currencyTax = currencyFormatter.format(tax);
        taxLabel.setText(currencyTax);
        
        total = subtotal + tax;
        String currencyTotal = currencyFormatter.format(total);
        totalLabel.setText(currencyTotal);       
    }
    
    public void initialize() {
    	checkoutTable.setSelectionModel(null);
    	errorLabel.setOpacity(0);
    }
    
    public void sameAddressBoxChecked(ActionEvent event) throws SQLException, ParseException, IOException {
    	if (sameAddressCheckBox.isSelected()) {
        	billingPane.setDisable(true);
        }
        else {
        	billingPane.setDisable(false);
        }
    }
    
    public void generateOrderNumber(String passQuery) throws SQLException, ParseException, IOException {
    	Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    	try {
    		Statement st = connection.createStatement();
    		ResultSet set = st.executeQuery(passQuery);
    		if(set.next()) {
    			getValue = Integer.parseInt(set.getString(1));
    		}
    	} catch (Exception e) {
    		// TODO: handle exception
    	} finally {
    		try {
    			connection.close();
    		} catch (Exception e2) {
    			// TODO: handle exception
    		}
    	}
    }
    
    public void purchaseButtonClicked(ActionEvent event) throws SQLException, ParseException, IOException {
    	if (!sameAddressCheckBox.isSelected() && firstName.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'First Name' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && lastName.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Last Name' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && phoneNumber.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Phone Number' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && address.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Address' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && city.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'City' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && state.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'State/Provine' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && country.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Country' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (!sameAddressCheckBox.isSelected() && zip.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Zip/PostCode' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (cardNumber.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Card Number' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (nameOnCard.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Name on Card' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (cardExpiration.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'Expiration' field cannot leave blank.");
    		animateMessage();
    	}
    	else if (cardCVV.getText().trim().isEmpty()) {
    		errorLabel.setText("The 'CVV' field cannot leave blank.");
    		animateMessage();
    	}
    	else {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();    
            int userID = Main.getInstance().getLoggedCustomer().getCustomerID();
            Date date = new Date();
            generateOrderNumber("select count(OrderID)+1 from orders");
            Random random = new Random();
            int n = random.nextInt(900) + 100;
            String inv = Integer.toString(n) + new SimpleDateFormat("ddMMyy").format(date) + getValue;
            final String INSERT_ORDER_QUERY = "INSERT INTO orders (OrderID, OrderDate, ToTalPrice, Status, CustomerID) VALUES ('" + inv + "','" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "','" + total + "','" + "Order Processing" + "','" + userID + "')";
            statement.executeUpdate(INSERT_ORDER_QUERY);
            for(Cart cart : checkoutTable.getItems()) {
                final String INSERT_ORDER_DETAIL_QUERY = "INSERT INTO order_details (OrderID, ProductID, Price, Size, Quantity) VALUES ('" + inv + "','" + cart.getProductID() + "','" + cart.getPrice() + "','" + cart.getSize() + "','" + cart.getQuantity() + "')";
                statement.executeUpdate(INSERT_ORDER_DETAIL_QUERY);
            }
            setAlertType(AlertType.INFORMATION);
            createInfoAlert();
            checkoutTable.getItems().clear();
            checkoutCartButton.setText("Cart");
            // go back to store
            Main.getInstance().gotoStore();
        }
    }
    
    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), errorLabel);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
}