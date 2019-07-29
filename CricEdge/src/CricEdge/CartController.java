package CricEdge;

import CricEdge.model.Cart;
import CricEdge.model.Customer;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CartController {
    
    @FXML private TableView<Cart> cartTable;
    @FXML private TableColumn<Cart, Cart> imageCol;
    @FXML private TableColumn<Cart, String> productNameCol;
    @FXML private TableColumn<Cart, String> sizeCol;
    @FXML private TableColumn<Cart, Double> priceCol;
    @FXML private TableColumn<Cart, Cart> quantityCol;  
    @FXML private TableColumn<Cart, Double> subtotalCol;
    @FXML private Label totalLabel;    

    private MainController mainController;
    double total = 0 ;
    public Hyperlink updatedCartButton;
    public BorderPane mainBorderPaneForCheckoutUse;
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA);
       
    public void initialize() {
    	cartTable.setSelectionModel(null);
        cartTable.setPlaceholder(new Label("Your shopping cart is empty."));
    }
    
    public void setCart(MainController mainController) {
        this.mainController = mainController;
        cartTable.setItems(mainController.getCartItems());
        
        for (Cart cart : cartTable.getItems()) {
            total = total + cart.getSubtotal();
        }
        String currencyPrice = currencyFormatter.format(total);
        totalLabel.setText(currencyPrice);    
        
        imageCol.setCellValueFactory(img -> new ReadOnlyObjectWrapper<>(img.getValue()));
        imageCol.setCellFactory(img -> new TableCell<Cart, Cart>(){
            private final ImageView coverImageView = new ImageView();
            @Override 
            protected void updateItem(Cart cart, boolean empty) {
                super.updateItem(cart, empty);
                if(cart == null) {
                    setGraphic(null);
                    return;
                }
                coverImageView.setImage(new Image(cart.getImage()));
                coverImageView.setFitHeight(100);
                coverImageView.setFitWidth(100);
                setGraphic(coverImageView);
            }
        });
        
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("Size"));
        
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        priceCol.setCellFactory(col -> new TableCell<Cart, Double>(){
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
        
        quantityCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        quantityCol.setCellFactory(param -> new TableCell<Cart,Cart>(){
            private final Hyperlink removeFromCart = new Hyperlink("");
            @Override
            protected void updateItem(Cart cart, boolean empty) {
                super.updateItem(cart, empty);
                if(cart == null) {
                    setGraphic(null);
                    return;
                }
                HBox removeHBox = new HBox();
                TextField quantity = new TextField(Integer.toString(cart.getQuantity()));
                quantity.setPrefWidth(35.0);
                quantity.setAlignment(Pos.CENTER_RIGHT);
                quantity.setStyle("-fx-background-radius: 0;");
                quantity.setOnAction(e -> {
                	if (Integer.valueOf(quantity.getText()) == 0) {
                        getTableView().getItems().remove(cart);
                	}
                	else if (Integer.valueOf(quantity.getText()) > cart.getQuantity()) {
                		total = total + (Integer.valueOf(quantity.getText()) - cart.getQuantity()) * cart.getPrice();
                	}
                	else if (Integer.valueOf(quantity.getText()) < cart.getQuantity()) {
                		total = total - (cart.getQuantity() - Integer.valueOf(quantity.getText())) * cart.getPrice();
                	}
                    cart.setQuantity(Integer.valueOf(quantity.getText()));
                    cartTable.refresh();
                    String currencyPrice = currencyFormatter.format(total);
                    totalLabel.setText(currencyPrice);
                    int totalItem = 0;
                	for (int i = 0; i < mainController.getCartItems().size(); i++) {
                        totalItem = totalItem + mainController.getCartItems().get(i).getQuantity();
                    }
                	updatedCartButton.setText(String.valueOf(totalItem));
                });
                removeHBox.setAlignment(Pos.CENTER);
                removeHBox.getChildren().addAll(quantity, removeFromCart);
                setGraphic(removeHBox);
                Image deleteIcon = new Image(getClass().getResourceAsStream("/resources/icons/ic_delete_black_18dp_1x.png"));
                removeFromCart.setGraphic(new ImageView(deleteIcon));
                removeFromCart.setStyle("-fx-text-fill: black;");
                removeFromCart.setOnAction(e -> {
                    getTableView().getItems().remove(cart);
                    total = total - cart.getSubtotal();
                    String currencyPrice = currencyFormatter.format(total);
                    totalLabel.setText(currencyPrice);
                    int totalItem = 0;
                	for (int i = 0; i < mainController.getCartItems().size(); i++) {
                        totalItem = totalItem + mainController.getCartItems().get(i).getQuantity();
                    }
                	updatedCartButton.setText(String.valueOf(totalItem));
                });
            }
        });
        
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("Subtotal"));
        subtotalCol.setCellFactory(col -> new TableCell<Cart, Double>(){
            @Override
            public void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setGraphic(null);
                } else {
                    String currencySubtotal = currencyFormatter.format(price);
                    Label subtotalLabel = new Label(currencySubtotal);
                    setGraphic(subtotalLabel);
                }
            }
        });
    }
    
    public void continueShopping(ActionEvent event) throws Exception {
        mainController.gotoStore(event);
    }
    
    public void proceedToCheckout(ActionEvent event) throws IOException {
        if(!mainController.getCartItems().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
            GridPane checkout = loader.load();
            CheckoutController checkoutController = loader.getController();
            checkoutController.setCheckoutPage(this);
            checkoutController.checkoutCartButton = updatedCartButton;
            mainBorderPaneForCheckoutUse.setCenter(checkout);
        }
    }
    
    public ObservableList<Cart> getCartItemsForCheckout() {
        return cartTable.getItems();
    }
    
    public Customer getLoggedCustomer() {
        return mainController.getLoggedCustomer();
    }      
}
