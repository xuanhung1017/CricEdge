package CricEdge;

import CricEdge.model.ImageTextCell;
import CricEdge.AccountController;
import CricEdge.CartController;
import CricEdge.Main;
import CricEdge.model.Cart;
import CricEdge.model.Customer;
import CricEdge.model.Product;
import CricEdge.model.ProductQueries;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    private Main application;
    private Stage alertStage;
    
    public void setApp(Main application) {
        this.application = application;
    }
        
    private Product currentProduct;
    private ProductQueries productQueries;
    private List<Product> results;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Cart> cartItems = FXCollections.observableArrayList();
    private ObservableList<Character> size = FXCollections.observableArrayList('S', 'M', 'L');

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA);
    
    @FXML private BorderPane mainBorderPane;
    @FXML private ListView<Product> itemsListView;
    @FXML private ImageView image;
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label descriptionLabel;
    @FXML private Hyperlink cartButton;
    @FXML private Button addToCartButton;
    @FXML private VBox productsVBox, itemDetailVBox;
    @FXML private ComboBox<Character> sizeComboBox;
    @FXML private TextField quantity;

    int userID = Main.getInstance().getLoggedCustomer().getCustomerID();
    
    public void initialize() {
        // defines the database connection and sets the PreparedStatements
        productQueries = new ProductQueries();
        results = productQueries.getAllProducts();

        products.addAll(results);
        
        itemsListView.setItems(products);
        addToCartButton.setDisable(true);
        
        itemsListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                	productNameLabel.setText(newValue.getProductName());
                	priceLabel.setText(currencyFormatter.format(newValue.getPrice()));
                	descriptionLabel.setText(newValue.getDescription());
                	image.setImage(new Image(newValue.getImage()));
                	quantity.setText("1");
                	sizeComboBox.setValue(null);
                	sizeComboBox.setItems(size);               	
                }        
        );
        itemsListView.getSelectionModel().select(0);
        itemsListView.setCellFactory((listview) -> new ImageTextCell());
        
        sizeComboBox.getSelectionModel().selectedItemProperty().addListener(
        		(observableValue, oldValue, newValue) -> {
        			if (sizeComboBox.getValue() == null) {
        	            addToCartButton.setDisable(true);
        	        } else {
        	            addToCartButton.setDisable(false);
        	        }
        		}
        );   
    }
    
    public void proccessLogout(ActionEvent event) throws Exception {
        if (application == null){
            return;
        }
        application.userLogout();
    }

    public void gotoAccount(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Account.fxml"));
        GridPane account = loader.load();
        AccountController accountController = loader.getController();
        accountController.setApp(application);
        mainBorderPane.setCenter(account);
        mainBorderPane.setRight(null);
        mainBorderPane.setLeft(null);
        BorderPane.setAlignment(account, Pos.TOP_LEFT);
    }
    
    public void gotoStore(ActionEvent event) throws Exception {
        productQueries = new ProductQueries();
        mainBorderPane.setLeft(productsVBox);
        mainBorderPane.setRight(itemDetailVBox);
    }
    
    public void gotoOrders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Orders.fxml"));
        VBox orders = loader.load();
        OrdersController ordersController = loader.getController();
        ordersController.mainBorderPaneForOrderDetailUse = mainBorderPane;
        mainBorderPane.setCenter(orders);
        mainBorderPane.setRight(null);
        mainBorderPane.setLeft(null);
    }
    
    public Product getSelectedProduct() {
        currentProduct = itemsListView.getSelectionModel().getSelectedItem();
        return currentProduct;
    }    
    
    public void addToCartButtonClicked(ActionEvent event) throws Exception {
    	if (cartItems.size() != 0) {
    		for (int i = 0; i < cartItems.size(); i++) {
                if (cartItems.get(i).getProductID() == getSelectedProduct().getProductID() && cartItems.get(i).getSize() == sizeComboBox.getValue()) {
                	cartItems.get(i).setQuantity(cartItems.get(i).getQuantity() + Integer.valueOf(quantity.getText()));
                	break;
                }
                else if ((i == cartItems.size() - 1) && (cartItems.get(i).getProductID() != getSelectedProduct().getProductID() || (cartItems.get(i).getProductID() == getSelectedProduct().getProductID() && cartItems.get(i).getSize() != sizeComboBox.getValue()))) {
                	Cart cart = new Cart(getSelectedProduct().getProductID(), getSelectedProduct().getProductName(), getSelectedProduct().getPrice(), getSelectedProduct().getImage(), sizeComboBox.getValue(), Integer.valueOf(quantity.getText()));
                	cartItems.add(cart);
                	break;
                }
    		}
    	}
    	else {
    		Cart cart = new Cart(getSelectedProduct().getProductID(), getSelectedProduct().getProductName(), getSelectedProduct().getPrice(), getSelectedProduct().getImage(), sizeComboBox.getValue(), Integer.valueOf(quantity.getText()));
    		cartItems.add(cart);
    	}
    	
    	int totalItem = 0;
    	for (int i = 0; i < cartItems.size(); i++) {
            totalItem = totalItem + cartItems.get(i).getQuantity();
        }
    	cartButton.setText(String.valueOf(totalItem));
    	Alert alert = new Alert(AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(alertStage);
        alert.getDialogPane().setContentText(quantity.getText() + " ITEM(S) ADDED TO YOUR CART");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait().filter(response -> response == ButtonType.OK);  	
    }
    
    public void showCart(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
        VBox cart = loader.load();
        CartController cartController = loader.getController();
        cartController.updatedCartButton = cartButton;
        cartController.mainBorderPaneForCheckoutUse = mainBorderPane;
        cartController.setCart(this);
        mainBorderPane.setCenter(cart);
        mainBorderPane.setRight(null);
        mainBorderPane.setLeft(null);
    }
    
    public ObservableList<Cart> getCartItems() {
        return cartItems;
    }
    
    public Customer getLoggedCustomer() {
        return application.getLoggedCustomer();
    }
    
}